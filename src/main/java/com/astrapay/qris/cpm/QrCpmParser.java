package com.astrapay.qris.cpm;

import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.enums.DataType;
import com.astrapay.qris.cpm.enums.TagIndicator;
import com.astrapay.qris.cpm.object.QrCpmDataObject;
import com.astrapay.qris.cpm.object.QrCpmPayload;
import lombok.Setter;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QrCpmParser {
    // function to parse qr cpm

    @Autowired
    private QrisHexConverter qrisHexConverter;

    private static final Integer HEX_RADIX = 16;

    private static final Integer SIZE_TO_EXTRACT_LENGTH = 2;
    private static final Integer HEX_PAIR_REPRESENTATION = 2;
    private static final Integer START_INDEX_TO_CONVERT_COMPRESSED_NUMERIC = 0;

    @Autowired
    @Setter
    private List<String> qrisCpmSubTag;

    @Autowired
    @Setter
    private List<String> applicationTemplateSubTag;

    @Autowired
    @Setter
    private List<String> applicationSpecificTransparentTemplateSubTag;

    public QrCpmPayload parse(String payloadBase64) throws IOException, DecoderException {

        byte[] payloadByteArrayBased = qrisHexConverter.decodeFromBase64(payloadBase64);
        String payloadHexBased = qrisHexConverter.encode(payloadByteArrayBased);

        QrCpmPayload qrCpmPayload = new QrCpmPayload();
        qrCpmPayload.setPayloadBase64(payloadBase64);
        qrCpmPayload.setPayloadHex(payloadHexBased);

        parse(qrCpmPayload);

        return qrCpmPayload;
    }

    private void parse(QrCpmPayload qrCpmPayload) throws DecoderException {
        Map<String, QrCpmDataObject> qrisCpmMap = new LinkedHashMap<>();
        parseRoot(qrCpmPayload.getPayloadHex(), qrisCpmMap);
        parseApplicationTemplate(qrisCpmMap);
        parseApplicationSpecificTransparentTemplate(qrisCpmMap);

        qrCpmPayload.setQrisRoot(qrisCpmMap);
    }

    private void parser(List<String> qrisCpmSubTag, String payloadHexBased, Map<String, QrCpmDataObject> qrisCpmMap) throws DecoderException {
        int counter = 0;
        int currentPosition = 0;
        int maxLength = payloadHexBased.length();

        while(counter < qrisCpmSubTag.size() && currentPosition < maxLength){
            String tag = qrisCpmSubTag.get(counter);
            String currentTag =  payloadHexBased.substring(currentPosition, currentPosition + tag.length());

            if(tag.equals(currentTag)){
                int lengthStartIndex = currentPosition + tag.length();
                int lengthEndIndex = currentPosition + tag.length() + SIZE_TO_EXTRACT_LENGTH;

                String lengthHexBased = payloadHexBased.substring(lengthStartIndex, lengthEndIndex);

                //multiply by 2 because each value represent pair of hex. ex : length 1 = 00, length 2 = 00 00, length 3 = 00 00 00
                Integer lengthIntegerBased = Integer.parseInt(lengthHexBased, HEX_RADIX) * HEX_PAIR_REPRESENTATION;

                String valueInHexString = payloadHexBased.substring(lengthEndIndex, lengthEndIndex + lengthIntegerBased);
                String value = this.getRealTagValue(currentTag, valueInHexString);
                this.insertTLVMap(currentTag, value, qrisCpmMap);

                currentPosition = lengthEndIndex + lengthIntegerBased;
            }

            counter++;
        }
    }

    private void insertTLVMap(String tag, String value, Map<String, QrCpmDataObject> qrisCpmMap){
        QrCpmDataObject qrCpmDataObjectSub = new QrCpmDataObject();
        qrCpmDataObjectSub.setTag(tag);
        qrCpmDataObjectSub.setLength(String.valueOf(value.length()));
        qrCpmDataObjectSub.setValue(value);

        qrisCpmMap.merge(qrCpmDataObjectSub.getTag(), qrCpmDataObjectSub, (v1, v2) -> {
            throw new IllegalArgumentException("Duplicate tag '" + qrCpmDataObjectSub.getTag() + "'.");
        });

    }
    private String getRealTagValue(String currentTag, String valueInHexString) throws DecoderException {
        DataType dataType = TagIndicator.getDataType(currentTag);

        //no need to convert TAG 9F74 to string,decrypt in qris-service
        if(!TagIndicator.ISSUER_QRIS_DATA.getValue().equals(currentTag) &&
                (DataType.ALPHA_NUMERIC.equals(dataType) ||
                        DataType.ALPHA_NUMERIC_SPECIAL.equals(dataType))){
            return qrisHexConverter.convertAlphaNumericHexToString(valueInHexString);
        }

        if(DataType.COMPRESSED_NUMERIC.equals(dataType)){
            return qrisHexConverter.convertCompressedNumericHexToString(valueInHexString, START_INDEX_TO_CONVERT_COMPRESSED_NUMERIC);
        }

        return valueInHexString;
    }
    private void parseRoot(String payloadHexBased, Map<String, QrCpmDataObject> qrisCpmMap) throws DecoderException {
        parser(qrisCpmSubTag, payloadHexBased, qrisCpmMap);
    }

    private void parseApplicationTemplate(Map<String, QrCpmDataObject> qrCpmDataObject) throws DecoderException {
        if(qrCpmDataObject.containsKey(TagIndicator.APPLICATION_TEMPLATE.getValue())){
            Map<String, QrCpmDataObject> qrCpmDataObjectSubMap = new LinkedHashMap<>();
            QrCpmDataObject applicationTemplate = qrCpmDataObject.get(TagIndicator.APPLICATION_TEMPLATE.getValue());
            String applicationTemplateValue = qrCpmDataObject.get(TagIndicator.APPLICATION_TEMPLATE.getValue()).getValue();

            parser(applicationTemplateSubTag, applicationTemplateValue, qrCpmDataObjectSubMap);

            applicationTemplate.setTemplateMap(qrCpmDataObjectSubMap);
        }
    }

    private void parseApplicationSpecificTransparentTemplate(Map<String, QrCpmDataObject> qrCpmDataObject) throws DecoderException {
        QrCpmDataObject applicationTemplate = qrCpmDataObject.get(TagIndicator.APPLICATION_TEMPLATE.getValue());

        if(qrCpmDataObject.containsKey(TagIndicator.APPLICATION_TEMPLATE.getValue()) && applicationTemplate.getTemplateMap().containsKey(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue())){
            Map<String, QrCpmDataObject> qrCpmDataObjectSubMap = new LinkedHashMap<>();
            QrCpmDataObject applicationSpecificTransparentTemplate = applicationTemplate.getTemplateMap().get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
            String applicationSpecificTransparentTemplateValue = applicationTemplate.getTemplateMap().get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue();

            parser(applicationSpecificTransparentTemplateSubTag, applicationSpecificTransparentTemplateValue, qrCpmDataObjectSubMap);

            applicationSpecificTransparentTemplate.setTemplateMap(qrCpmDataObjectSubMap);

        }
    }

}
