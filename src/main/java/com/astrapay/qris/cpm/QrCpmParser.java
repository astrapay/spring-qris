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
    private static final Integer FIRST_INDEX = 0;
    private static final Integer DEFAULT_TAG_LENGTH_IN_BYTES = 1;

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


    /***
     * main function to parse HEX based data.
     * @param qrisCpmSubTag -> list of the tag that needed to be parsed from parent level .can refer to QrCpmConfiguration for the list
     * @param payloadHexBased -> HEX based data.
     * @param qrisCpmMap -> map to store the parsed data
     *
     * step by step :
     * 1. loop through the qrisCpmSubTag list
     * 2. compare the tag from the list with the current tag in the payloadHexBased
     * 3. if the tag is the same, extract the length of the value. if not then just skip to the next tag
     * 4. extract the value based on the length
     * 5. set the value to qrisCpmMap
     * 6. keep track of the current position because not all tag is mandatory (can refer to https://astrapay.atlassian.net/wiki/spaces/PD/pages/2686451746/Solutioning+QR+CPM )
     */
    private void parser(List<String> qrisCpmSubTag, String payloadHexBased, Map<String, QrCpmDataObject> qrisCpmMap) throws DecoderException {
        int counter = 0;
        int currentPosition = 0;
        int maxLength = payloadHexBased.length();

        while(counter < qrisCpmSubTag.size() && currentPosition < maxLength){
            String tag = qrisCpmSubTag.get(counter);
            String currentTag =  payloadHexBased.substring(currentPosition, currentPosition + tag.length());

            if(tag.equals(currentTag)){
                int lengthTagStartIndex = currentPosition + tag.length();
                int lengthTagByteCount = getTagLengthByteCount(qrisHexConverter.convertByteOrNumberToArrayByte(payloadHexBased.substring(lengthTagStartIndex)));
                int lengthTagHexCount = getLengthInHex(lengthTagByteCount);
                int lengthTagEndIndex = currentPosition + tag.length() + lengthTagHexCount;
                //multiply by 2 because each value represent pair of hex. ex : length 1 = 00, length 2 = 00 00, length 3 = 00 00 00
                String lengthTag = payloadHexBased.substring(lengthTagStartIndex, lengthTagEndIndex);
                Integer valueLengthInHex= this.getLengthValue(lengthTag, lengthTagByteCount);
                String valueInHexString = payloadHexBased.substring(lengthTagEndIndex, lengthTagEndIndex + valueLengthInHex);
                String value = this.getRealTagValue(currentTag, valueInHexString);
                this.insertTLVMap(currentTag, value, qrisCpmMap);

                currentPosition = lengthTagEndIndex + valueLengthInHex;
            }

            counter++;
        }
    }
    private int getLengthValue(String lengthTag, int lengthTagByteCount) {
        // function to get the length of the tag in bytes
        if(lengthTagByteCount > DEFAULT_TAG_LENGTH_IN_BYTES) {
            // if the length is represented by more than 1 byte, then the first doesn't actually represent the length
            lengthTag = lengthTag.substring(DEFAULT_TAG_LENGTH_IN_BYTES * HEX_PAIR_REPRESENTATION);
        }
        return Integer.parseInt(lengthTag, HEX_RADIX) * HEX_PAIR_REPRESENTATION;
    }
    
    
    private int getTagLengthByteCount(byte[] qrByte) {
        // get the number of bytes representing the length of the tag
        // if byte 8 is 1, then the length of the tag is represented by more than 1 byte:
        //    byte 7 to 1 represents the number of subsequent bytes representing the length of the tag
        // else, the length is 1 byte
        // refer to EMV 4.4 Book 3 Application Specification, Annex B, B2
        byte tagLength = qrByte[FIRST_INDEX];
        // 0x80 = 10000000, tagLength & 0x80 will remove bits b7-b1
        if ((tagLength & 0x80) == 0x80) {
            int unsignedLength = this.getUnsignedInt(tagLength);
            // total length = first byte + subsequent bytes
            return DEFAULT_TAG_LENGTH_IN_BYTES + unsignedLength;
        } else {
            return DEFAULT_TAG_LENGTH_IN_BYTES;
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
            return qrisHexConverter.convertCompressedNumericHexToString(valueInHexString, FIRST_INDEX);
        }

        return valueInHexString;
    }

    /***
     * to parse the root level of the QR CPM (can refer to QrCpmConfiguration @Bean qrisCpmSubTag)
     */
    private void parseRoot(String payloadHexBased, Map<String, QrCpmDataObject> qrisCpmMap) throws DecoderException {
        parser(qrisCpmSubTag, payloadHexBased, qrisCpmMap);
    }

    /***
     * to parse the application template sub tag (can refer to QrCpmConfiguration @Bean applicationTemplateSubTag)
     * in order to parse application template need to parse the root first
     */
    private void parseApplicationTemplate(Map<String, QrCpmDataObject> qrCpmDataObject) throws DecoderException {
        if(qrCpmDataObject.containsKey(TagIndicator.APPLICATION_TEMPLATE.getValue())){
            Map<String, QrCpmDataObject> qrCpmDataObjectSubMap = new LinkedHashMap<>();
            QrCpmDataObject applicationTemplate = qrCpmDataObject.get(TagIndicator.APPLICATION_TEMPLATE.getValue());
            String applicationTemplateValue = qrCpmDataObject.get(TagIndicator.APPLICATION_TEMPLATE.getValue()).getValue();

            parser(applicationTemplateSubTag, applicationTemplateValue, qrCpmDataObjectSubMap);

            applicationTemplate.setTemplateMap(qrCpmDataObjectSubMap);
        }
    }
    /***
     * to parse the application specific transparent template sub tag (can refer to QrCpmConfiguration @Bean applicationSpecificTransparentTemplateSubTag)
     * in order to parse application specific transparent template need to parse the application template first
     */

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
    
    private int getLengthInHex(int lengthInBytes) {
        // one byte is represented by two hex characters
        return lengthInBytes * HEX_PAIR_REPRESENTATION;
    }
    
    private int getUnsignedInt(byte val) {
        // Byte is represented in two's complement, so we need to convert it to unsigned
        // 0x7F = 10000000, tagLength & 0x7F will remove bit b8
        return Byte.toUnsignedInt(val) & 0x7F;
    }

}
