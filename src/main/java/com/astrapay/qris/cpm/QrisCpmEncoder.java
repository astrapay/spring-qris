package com.astrapay.qris.cpm;

import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.enums.TagIndicator;
import com.astrapay.qris.cpm.object.ApplicationTemplate;
import com.astrapay.qris.cpm.object.QrisCpm;
import com.astrapay.qris.mpm.object.ApplicationSpecificTransparentTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class QrisCpmEncoder {
    
    @Autowired
    private QrisHexConverter qrisHexConverter;
    
    private static final Integer FIRST_INDEX = 0;
    
    public String encode(QrisCpm qrisCpm) throws IOException {
        return qrisHexConverter.encodeToBase64(this.getBytes(qrisCpm));
    }
    public byte[] getBytes(QrisCpm qrisCpm) throws IOException {
        try {
            ByteArrayOutputStream qrisCpmByteStream = new ByteArrayOutputStream();
            qrisCpmByteStream.write(this.getPayloadFormatIndicator());
            qrisCpmByteStream.write(this.getApplicationTemplate(qrisCpm.getApplicationTemplate()));
            return qrisCpmByteStream.toByteArray();
        } catch (IOException | DecoderException e) {
            log.error("QrisCpmEncoder::Encode failed to encode QR CPM ", e);
            throw new IOException("Failed to encode QR CPM");
        }
    }
    
    public byte[] getApplicationTemplate(ApplicationTemplate applicationTemplate) throws DecoderException, IOException {
        if(Objects.isNull(applicationTemplate)) {
            return new byte[FIRST_INDEX];
        }
        byte[] track2EquivalentDataSubTag = Optional.ofNullable(applicationTemplate.getTrack2EquivalentData()).map(track2EquivalentData ->
                this.concatByteArrays(TagIndicator.TRACK_2_EQUIVALENT_DATA.getByteTag(), this.getTagLength(track2EquivalentData), track2EquivalentData))
                .orElse(new byte[FIRST_INDEX]);
        byte[] cardholderNameSubTag = Optional.ofNullable(applicationTemplate.getCardholderName()).map(this::getCardholderName).orElse(new byte[FIRST_INDEX]);
        byte[] issuerUrlSubtag = Optional.ofNullable(applicationTemplate.getIssuerUrl()).map(this::getIssuerUrl).orElse(new byte[FIRST_INDEX]);
        byte[] appVersionNumberSubTag = Optional.ofNullable(applicationTemplate.getApplicationVersionNumber()).map(appVersionNumber ->
                this.concatByteArrays(TagIndicator.APPLICATION_VERSION_NUMBER.getByteTag(), this.getTagLength(appVersionNumber), appVersionNumber))
                .orElse(new byte[FIRST_INDEX]);
        byte[] paymentAccountRefSubTag = Optional.ofNullable(applicationTemplate.getPaymentAccountReference()).map(this::getPaymentAccountReference).orElse(new byte[FIRST_INDEX]);
        ByteArrayOutputStream applicationTemplateStream = new ByteArrayOutputStream();
        applicationTemplateStream.write(this.getAdfName());
        applicationTemplateStream.write(this.getApplicationLabel());
        applicationTemplateStream.write(track2EquivalentDataSubTag);
        if(Objects.nonNull(applicationTemplate.getApplicationPan())) {
            applicationTemplateStream.write(this.getApplicationPan(applicationTemplate.getApplicationPan()));
        }
        applicationTemplateStream.write(cardholderNameSubTag);
        applicationTemplateStream.write(this.getLanguagePreference());
        applicationTemplateStream.write(issuerUrlSubtag);
        applicationTemplateStream.write(appVersionNumberSubTag);
        if (Objects.nonNull(applicationTemplate.getLast4DigitsPan())) {
            applicationTemplateStream.write(this.getLast4DigitsPan(applicationTemplate.getLast4DigitsPan()));
        }
        if (Objects.nonNull(applicationTemplate.getTokenRequestorId())) {
            applicationTemplateStream.write(this.getTokenRequestorId(applicationTemplate.getTokenRequestorId()));
        }
        applicationTemplateStream.write(paymentAccountRefSubTag);
        applicationTemplateStream.write(this.getApplicationSpecificTransparentTemplate(applicationTemplate.getApplicationSpecificTransparentTemplate()));
        byte[] applicationTemplateByteArray = applicationTemplateStream.toByteArray();
        return this.concatByteArrays(TagIndicator.APPLICATION_TEMPLATE.getByteTag(), this.getTagLength(applicationTemplateByteArray), applicationTemplateByteArray);
    }
    
    private byte[] getApplicationSpecificTransparentTemplate(ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate) throws IOException{
        if(Objects.isNull(applicationSpecificTransparentTemplate)) {
            return new byte[FIRST_INDEX];
        }
        byte[] issuerDataSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getIssuerData()).map(this::getIssuerData).orElse(new byte[FIRST_INDEX]);
        byte[] appCryptogramSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getApplicationCryptogram()).map(appCryptogram ->
                        this.concatByteArrays(TagIndicator.APPLICATION_CRYPTOGRAM.getByteTag(), this.getTagLength(appCryptogram), appCryptogram))
                .orElse(new byte[FIRST_INDEX]);
        byte[] cryptogramInformationSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getCryptogramInformationData()).map(cryptogramInformationData ->
                        this.concatByteArrays(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getByteTag(), this.getTagLength(new byte[]{cryptogramInformationData}), new byte[]{cryptogramInformationData}))
                .orElse(new byte[FIRST_INDEX]);
        byte[] issuerApplicationDataSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getIssuerApplicationData()).map(issuerApplicationData ->
                        this.concatByteArrays(TagIndicator.ISSUER_APPLICATION_DATA.getByteTag(), this.getTagLength(issuerApplicationData), issuerApplicationData))
                .orElse(new byte[FIRST_INDEX]);
        byte[] applicationTransactionCounterSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getApplicationTransactionCounter()).map(applicationTransactionCounter ->
                        this.concatByteArrays(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getByteTag(), this.getTagLength(applicationTransactionCounter), applicationTransactionCounter))
                .orElse(new byte[FIRST_INDEX]);
        byte[] applicationInterchangeProfileSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getApplicationInterchangeProfile()).map(applicationInterchangeProfile ->
                        this.concatByteArrays(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getByteTag(), this.getTagLength(applicationInterchangeProfile), applicationInterchangeProfile))
                .orElse(new byte[FIRST_INDEX]);
        byte[] unpredictableNumberSubTag = Optional.ofNullable(applicationSpecificTransparentTemplate.getUnpredictableNumber()).map(unpredictableNumber ->
                        this.concatByteArrays(TagIndicator.UNPREDICTABLE_NUMBER.getByteTag(), this.getTagLength(unpredictableNumber), unpredictableNumber))
                .orElse(new byte[FIRST_INDEX]);
        
        ByteArrayOutputStream appSpecificTransparentTemplateStream = new ByteArrayOutputStream();
        appSpecificTransparentTemplateStream.write(issuerDataSubTag);
        appSpecificTransparentTemplateStream.write(appCryptogramSubTag);
        appSpecificTransparentTemplateStream.write(cryptogramInformationSubTag);
        appSpecificTransparentTemplateStream.write(issuerApplicationDataSubTag);
        appSpecificTransparentTemplateStream.write(applicationTransactionCounterSubTag);
        appSpecificTransparentTemplateStream.write(applicationInterchangeProfileSubTag);
        appSpecificTransparentTemplateStream.write(unpredictableNumberSubTag);
        byte[] appSpecificTransparentTemplateBytes = appSpecificTransparentTemplateStream.toByteArray();
        return this.concatByteArrays(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getByteTag(), this.getTagLength(appSpecificTransparentTemplateBytes), appSpecificTransparentTemplateBytes);
    }
    private byte[] getPayloadFormatIndicator() {
        String payloadFormatIndicator = "CPV01";
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(payloadFormatIndicator);
        byte[] length = this.getTagLength(byteValue);
        return this.concatByteArrays(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getByteTag(), length, byteValue);
    }
    
    private byte[] getAdfName() throws DecoderException {
        String adfName = "A0000006022020";
        byte[] byteValue = qrisHexConverter.convertByteOrNumberToArrayByte(adfName);
        return this.concatByteArrays(TagIndicator.ADF_NAME.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getApplicationLabel() {
        String applicationLabel = "QRISCPM";
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(applicationLabel);
        return this.concatByteArrays(TagIndicator.APPLICATION_LABEL.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getApplicationPan(String applicationPan) throws DecoderException {
        byte[] byteValue = qrisHexConverter.convertCompressedNumericToArrayByte(applicationPan);
        return this.concatByteArrays(TagIndicator.APP_PAN.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getCardholderName(String name) {
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(name);
        return this.concatByteArrays(TagIndicator.CARDHOLDER_NAME.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    private byte[] getLanguagePreference() {
        String languagePreference = "iden";
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(languagePreference);
        return this.concatByteArrays(TagIndicator.LANGUAGE_PREFERENCE.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getIssuerUrl(String issuerUrl) {
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(issuerUrl);
        return this.concatByteArrays(TagIndicator.ISSUER_URL.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    private byte[] getLast4DigitsPan(String last4DigitsPan) throws DecoderException {
        byte[] byteValue = qrisHexConverter.convertByteOrNumberToArrayByte(last4DigitsPan);
        return this.concatByteArrays(TagIndicator.LAST_4_DIGIT_PAN.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getTokenRequestorId(String tokenRequestorId) throws DecoderException {
        byte[] byteValue = qrisHexConverter.convertByteOrNumberToArrayByte(tokenRequestorId);
        return this.concatByteArrays(TagIndicator.TOKEN_REQUESTOR_ID.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    private byte[] getPaymentAccountReference(String paymentAccountReference) {
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(paymentAccountReference);
        return this.concatByteArrays(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    private byte[] getIssuerData(String issuerData) {
        byte[] byteValue = qrisHexConverter.convertAlphaNumericToArrayByte(issuerData);
        return this.concatByteArrays(TagIndicator.ISSUER_QRIS_DATA.getByteTag(), this.getTagLength(byteValue), byteValue);
    }
    
    public byte[] getTagLength(byte[] value) {
        int valLength = value.length;
        if(valLength < 0x80) {
            // 0x80 = 10000000, max value when bit b8 is 0
            return new byte[] {(byte) valLength};
        } else if (valLength <0x100) {
            // length of up to 255(0xFF) can be represented in one byte
            // 0x81 = 10000001
            return new byte[] {(byte) 0x81, (byte) valLength};
        } else if( valLength < 0x10000) {
            // length of up to 65535(0xFFFF) can be represented in two bytes
            // 0x82 = 10000010
            return new byte[] {(byte) 0x82, (byte) (valLength / 0x100), (byte) (valLength % 0x100)};
            
        } else if( valLength < 0x1000000 ) {
            // length of up to 16777215(0xFFFFFF) can be represented in three bytes
            // 0x83 = 10000011
            return new byte[] {(byte) 0x83,  (byte) (valLength / 0x10000), (byte) (valLength/ 0x100), (byte) (valLength % 0x100)};
        } else {
            throw new IllegalStateException("Length ["+valLength+"] out of range (0x1000000)");
        }
    }
    
    private byte[] concatByteArrays(byte[] tag, byte[] length, byte[] value) {
        byte[] result = new byte[tag.length + length.length + value.length];
        System.arraycopy(tag, FIRST_INDEX, result, 0, tag.length);
        System.arraycopy(length, FIRST_INDEX, result, tag.length, length.length);
        System.arraycopy(value, FIRST_INDEX, result, tag.length + length.length, value.length);
        return result;
    }
}

