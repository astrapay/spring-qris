package com.astrapay.qris.cpm;

import com.astrapay.qris.cpm.enums.TagIndicator;
import com.astrapay.qris.cpm.object.QrCpmDataObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class QrCpmMapperTest {

    @InjectMocks
    private QrCpmMapper qrCpmMapper;

    @Test
    void testMap_allFieldFilled(){
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject adfName = new QrCpmDataObject();
        adfName.setTag(TagIndicator.ADF_NAME.getValue());
        adfName.setValue("A0000006022020");

        QrCpmDataObject appLabel = new QrCpmDataObject();
        appLabel.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        appLabel.setValue("QRISCPM");

        QrCpmDataObject track2EquivalentData = new QrCpmDataObject();
        track2EquivalentData.setTag(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        track2EquivalentData.setValue("9360001417451180859");

        QrCpmDataObject applicationPan = new QrCpmDataObject();
        applicationPan.setTag(TagIndicator.APP_PAN.getValue());
        applicationPan.setValue("9360001417451180859");

        QrCpmDataObject cardholderName = new QrCpmDataObject();
        cardholderName.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        cardholderName.setValue("RIKI DERIAN");

        QrCpmDataObject langPref = new QrCpmDataObject();
        langPref.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        langPref.setValue("iden");

        QrCpmDataObject issuerUrl = new QrCpmDataObject();
        issuerUrl.setTag(TagIndicator.ISSUER_URL.getValue());
        issuerUrl.setValue("https://www.astrapay.com");

        QrCpmDataObject appVersionNumber = new QrCpmDataObject();
        appVersionNumber.setTag(TagIndicator.APPLICATION_VERSION_NUMBER.getValue());
        appVersionNumber.setValue("01");

        QrCpmDataObject last4DigitPan = new QrCpmDataObject();
        last4DigitPan.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        last4DigitPan.setValue("0859");

        QrCpmDataObject tokenRequestorId = new QrCpmDataObject();
        tokenRequestorId.setTag(TagIndicator.TOKEN_REQUESTOR_ID.getValue());
        tokenRequestorId.setValue("11223344556");

        QrCpmDataObject paymentAccountReference = new QrCpmDataObject();
        paymentAccountReference.setTag(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue());
        paymentAccountReference.setValue("11223344556677889911223344556");


        applicationTemplateMap.put(TagIndicator.ADF_NAME.getValue(), adfName);
        applicationTemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), appLabel);
        applicationTemplateMap.put(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue(), track2EquivalentData);
        applicationTemplateMap.put(TagIndicator.APP_PAN.getValue(), applicationPan);
        applicationTemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), cardholderName);
        applicationTemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), langPref);
        applicationTemplateMap.put(TagIndicator.ISSUER_URL.getValue(), issuerUrl);
        applicationTemplateMap.put(TagIndicator.APPLICATION_VERSION_NUMBER.getValue(), appVersionNumber);
        applicationTemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), last4DigitPan);
        applicationTemplateMap.put(TagIndicator.TOKEN_REQUESTOR_ID.getValue(), tokenRequestorId);
        applicationTemplateMap.put(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue(), paymentAccountReference);

        QrCpmDataObject payloadFormatIndicator = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        payloadFormatIndicator.setValue("CPV01");

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        applicationTemplate.setTemplateMap(applicationTemplateMap);

        payload.put(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), payloadFormatIndicator);
        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject issuerQrisData = new QrCpmDataObject();
        issuerQrisData.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        issuerQrisData.setValue("abcde12345");

        QrCpmDataObject applicationCryptogram = new QrCpmDataObject();
        applicationCryptogram.setTag(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        applicationCryptogram.setValue("abcabcdd");

        QrCpmDataObject cryptogramInformationData = new QrCpmDataObject();
        cryptogramInformationData.setTag(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        cryptogramInformationData.setValue("9");

        QrCpmDataObject issuerApplicationData = new QrCpmDataObject();
        issuerApplicationData.setTag(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        issuerApplicationData.setValue("9999");

        QrCpmDataObject applicationTransactionCounter = new QrCpmDataObject();
        applicationTransactionCounter.setTag(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        applicationTransactionCounter.setValue("12");

        QrCpmDataObject applicationInterchangeProfile = new QrCpmDataObject();
        applicationInterchangeProfile.setTag(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        applicationInterchangeProfile.setValue("12");

        QrCpmDataObject unpredictableNumber = new QrCpmDataObject();
        unpredictableNumber.setTag(TagIndicator.UNPREDICTABLE_NUMBER.getValue());
        unpredictableNumber.setValue("4444");


        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), issuerQrisData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_CRYPTOGRAM.getValue(), applicationCryptogram);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue(), cryptogramInformationData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue(), applicationTransactionCounter);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_APPLICATION_DATA.getValue(), issuerApplicationData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue(), applicationInterchangeProfile);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.UNPREDICTABLE_NUMBER.getValue(), applicationInterchangeProfile);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        var result = qrCpmMapper.map(payload);

        String expectedPayloadFormatIndicator = "CPV01";
        String expectedAdfName = "A0000006022020";
        String expectedApplicationLabel = "QRISCPM";
        String expectedTrack2EquivalentData = "9360001417451180859";
        String expectedApplicationPan = "9360001417451180859";
        String expectedCardholderName = "RIKI DERIAN";
        String expectedLanguagePreference = "iden";
        String expectedIssuerUrl = "https://www.astrapay.com";
        String expectedApplicationVersionNumber = "01";
        String expectedLast4DigitsPan = "0859";
        String expectedTokenRequestorId = "11223344556";
        String expectedPaymentAccountReference = "11223344556677889911223344556";
        String expectedIssuerData = "abcde12345";
        String expectedApplicationCryptogram = "abcabcdd";
        String expectedCryptogramInformationData = "9";
        String expectedIssuerApplicationData = "9999";
        String expectedApplicationTransactionCounter = "12";
        String expectedApplicationInterchangeProfile = "12";
        String expectedUnpredictableNumber = "12";

        Assertions.assertEquals(expectedPayloadFormatIndicator, result.getPayloadFormatIndicator());
        Assertions.assertEquals(expectedAdfName, result.getApplicationTemplate().getAdfName());
        Assertions.assertEquals(expectedApplicationLabel, result.getApplicationTemplate().getApplicationLabel());
        Assertions.assertEquals(expectedTrack2EquivalentData, result.getApplicationTemplate().getTrack2EquivalentData());
        Assertions.assertEquals(expectedApplicationPan, result.getApplicationTemplate().getApplicationPan());
        Assertions.assertEquals(expectedCardholderName, result.getApplicationTemplate().getCardholderName());
        Assertions.assertEquals(expectedLanguagePreference, result.getApplicationTemplate().getLanguagePreference());
        Assertions.assertEquals(expectedIssuerUrl, result.getApplicationTemplate().getIssuerUrl());
        Assertions.assertEquals(expectedApplicationVersionNumber, result.getApplicationTemplate().getApplicationVersionNumber());
        Assertions.assertEquals(expectedLast4DigitsPan, result.getApplicationTemplate().getLast4DigitsPan());
        Assertions.assertEquals(expectedTokenRequestorId, result.getApplicationTemplate().getTokenRequestorId());
        Assertions.assertEquals(expectedPaymentAccountReference, result.getApplicationTemplate().getPaymentAccountReference());
        Assertions.assertEquals(expectedIssuerData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerData());
        Assertions.assertEquals(expectedApplicationCryptogram, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationCryptogram());
        Assertions.assertEquals(expectedCryptogramInformationData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getCryptogramInformationData());
        Assertions.assertEquals(expectedIssuerApplicationData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerApplicationData());
        Assertions.assertEquals(expectedApplicationTransactionCounter, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationTransactionCounter());
        Assertions.assertEquals(expectedApplicationInterchangeProfile, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationInterchangeProfile());
        Assertions.assertEquals(expectedUnpredictableNumber, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getUnpredictableNumber());
    }

    @Test
    void testMap_withoutAdditionalXXXXTag61(){
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject adfName = new QrCpmDataObject();
        adfName.setTag(TagIndicator.ADF_NAME.getValue());
        adfName.setValue("A0000006022020");

        QrCpmDataObject appLabel = new QrCpmDataObject();
        appLabel.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        appLabel.setValue("QRISCPM");

        QrCpmDataObject track2EquivalentData = new QrCpmDataObject();
        track2EquivalentData.setTag(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        track2EquivalentData.setValue("9360001417451180859");

        QrCpmDataObject applicationPan = new QrCpmDataObject();
        applicationPan.setTag(TagIndicator.APP_PAN.getValue());
        applicationPan.setValue("9360001417451180859");

        QrCpmDataObject cardholderName = new QrCpmDataObject();
        cardholderName.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        cardholderName.setValue("RIKI DERIAN");

        QrCpmDataObject langPref = new QrCpmDataObject();
        langPref.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        langPref.setValue("iden");

        QrCpmDataObject issuerUrl = new QrCpmDataObject();
        issuerUrl.setTag(TagIndicator.ISSUER_URL.getValue());
        issuerUrl.setValue("https://www.astrapay.com");

        QrCpmDataObject appVersionNumber = new QrCpmDataObject();
        appVersionNumber.setTag(TagIndicator.APPLICATION_VERSION_NUMBER.getValue());
        appVersionNumber.setValue("01");

        QrCpmDataObject last4DigitPan = new QrCpmDataObject();
        last4DigitPan.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        last4DigitPan.setValue("0859");

        QrCpmDataObject tokenRequestorId = new QrCpmDataObject();
        tokenRequestorId.setTag(TagIndicator.TOKEN_REQUESTOR_ID.getValue());
        tokenRequestorId.setValue("11223344556");

        QrCpmDataObject paymentAccountReference = new QrCpmDataObject();
        paymentAccountReference.setTag(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue());
        paymentAccountReference.setValue("11223344556677889911223344556");


        applicationTemplateMap.put(TagIndicator.ADF_NAME.getValue(), adfName);
        applicationTemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), appLabel);
        applicationTemplateMap.put(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue(), track2EquivalentData);
        applicationTemplateMap.put(TagIndicator.APP_PAN.getValue(), applicationPan);
        applicationTemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), cardholderName);
        applicationTemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), langPref);
        applicationTemplateMap.put(TagIndicator.ISSUER_URL.getValue(), issuerUrl);
        applicationTemplateMap.put(TagIndicator.APPLICATION_VERSION_NUMBER.getValue(), appVersionNumber);
        applicationTemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), last4DigitPan);

        QrCpmDataObject payloadFormatIndicator = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        payloadFormatIndicator.setValue("CPV01");

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        applicationTemplate.setTemplateMap(applicationTemplateMap);


        payload.put(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), payloadFormatIndicator);
        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject issuerQrisData = new QrCpmDataObject();
        issuerQrisData.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        issuerQrisData.setValue("abcde12345");

        QrCpmDataObject applicationCryptogram = new QrCpmDataObject();
        applicationCryptogram.setTag(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        applicationCryptogram.setValue("abcabcdd");

        QrCpmDataObject cryptogramInformationData = new QrCpmDataObject();
        cryptogramInformationData.setTag(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        cryptogramInformationData.setValue("9");

        QrCpmDataObject issuerApplicationData = new QrCpmDataObject();
        issuerApplicationData.setTag(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        issuerApplicationData.setValue("9999");

        QrCpmDataObject applicationTransactionCounter = new QrCpmDataObject();
        applicationTransactionCounter.setTag(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        applicationTransactionCounter.setValue("12");

        QrCpmDataObject applicationInterchangeProfile = new QrCpmDataObject();
        applicationInterchangeProfile.setTag(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        applicationInterchangeProfile.setValue("12");

        QrCpmDataObject unpredictableNumber = new QrCpmDataObject();
        unpredictableNumber.setTag(TagIndicator.UNPREDICTABLE_NUMBER.getValue());
        unpredictableNumber.setValue("4444");


        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), issuerQrisData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_CRYPTOGRAM.getValue(), applicationCryptogram);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue(), cryptogramInformationData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue(), applicationTransactionCounter);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_APPLICATION_DATA.getValue(), issuerApplicationData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue(), applicationInterchangeProfile);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.UNPREDICTABLE_NUMBER.getValue(), applicationInterchangeProfile);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        var result = qrCpmMapper.map(payload);

        String expectedPayloadFormatIndicator = "CPV01";
        String expectedAdfName = "A0000006022020";
        String expectedApplicationLabel = "QRISCPM";
        String expectedTrack2EquivalentData = "9360001417451180859";
        String expectedApplicationPan = "9360001417451180859";
        String expectedCardholderName = "RIKI DERIAN";
        String expectedLanguagePreference = "iden";
        String expectedIssuerUrl = "https://www.astrapay.com";
        String expectedApplicationVersionNumber = "01";
        String expectedLast4DigitsPan = "0859";
        String expectedIssuerData = "abcde12345";
        String expectedApplicationCryptogram = "abcabcdd";
        String expectedCryptogramInformationData = "9";
        String expectedIssuerApplicationData = "9999";
        String expectedApplicationTransactionCounter = "12";
        String expectedApplicationInterchangeProfile = "12";
        String expectedUnpredictableNumber = "12";

        Assertions.assertEquals(expectedPayloadFormatIndicator, result.getPayloadFormatIndicator());
        Assertions.assertEquals(expectedAdfName, result.getApplicationTemplate().getAdfName());
        Assertions.assertEquals(expectedApplicationLabel, result.getApplicationTemplate().getApplicationLabel());
        Assertions.assertEquals(expectedTrack2EquivalentData, result.getApplicationTemplate().getTrack2EquivalentData());
        Assertions.assertEquals(expectedApplicationPan, result.getApplicationTemplate().getApplicationPan());
        Assertions.assertEquals(expectedCardholderName, result.getApplicationTemplate().getCardholderName());
        Assertions.assertEquals(expectedLanguagePreference, result.getApplicationTemplate().getLanguagePreference());
        Assertions.assertEquals(expectedIssuerUrl, result.getApplicationTemplate().getIssuerUrl());
        Assertions.assertEquals(expectedApplicationVersionNumber, result.getApplicationTemplate().getApplicationVersionNumber());
        Assertions.assertEquals(expectedLast4DigitsPan, result.getApplicationTemplate().getLast4DigitsPan());
        Assertions.assertEquals(expectedIssuerData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerData());
        Assertions.assertEquals(expectedApplicationCryptogram, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationCryptogram());
        Assertions.assertEquals(expectedCryptogramInformationData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getCryptogramInformationData());
        Assertions.assertEquals(expectedIssuerApplicationData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerApplicationData());
        Assertions.assertEquals(expectedApplicationTransactionCounter, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationTransactionCounter());
        Assertions.assertEquals(expectedApplicationInterchangeProfile, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationInterchangeProfile());
        Assertions.assertEquals(expectedUnpredictableNumber, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getUnpredictableNumber());

        Assertions.assertNull(result.getApplicationTemplate().getTokenRequestorId());
        Assertions.assertNull(result.getApplicationTemplate().getPaymentAccountReference());
    }

    @Test
    void testMap_withoutAdditionalXXXXTag63(){
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject adfName = new QrCpmDataObject();
        adfName.setTag(TagIndicator.ADF_NAME.getValue());
        adfName.setValue("A0000006022020");

        QrCpmDataObject appLabel = new QrCpmDataObject();
        appLabel.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        appLabel.setValue("QRISCPM");

        QrCpmDataObject track2EquivalentData = new QrCpmDataObject();
        track2EquivalentData.setTag(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        track2EquivalentData.setValue("9360001417451180859");

        QrCpmDataObject applicationPan = new QrCpmDataObject();
        applicationPan.setTag(TagIndicator.APP_PAN.getValue());
        applicationPan.setValue("9360001417451180859");

        QrCpmDataObject cardholderName = new QrCpmDataObject();
        cardholderName.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        cardholderName.setValue("RIKI DERIAN");

        QrCpmDataObject langPref = new QrCpmDataObject();
        langPref.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        langPref.setValue("iden");

        QrCpmDataObject issuerUrl = new QrCpmDataObject();
        issuerUrl.setTag(TagIndicator.ISSUER_URL.getValue());
        issuerUrl.setValue("https://www.astrapay.com");

        QrCpmDataObject appVersionNumber = new QrCpmDataObject();
        appVersionNumber.setTag(TagIndicator.APPLICATION_VERSION_NUMBER.getValue());
        appVersionNumber.setValue("01");

        QrCpmDataObject last4DigitPan = new QrCpmDataObject();
        last4DigitPan.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        last4DigitPan.setValue("0859");

        QrCpmDataObject tokenRequestorId = new QrCpmDataObject();
        tokenRequestorId.setTag(TagIndicator.TOKEN_REQUESTOR_ID.getValue());
        tokenRequestorId.setValue("11223344556");

        QrCpmDataObject paymentAccountReference = new QrCpmDataObject();
        paymentAccountReference.setTag(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue());
        paymentAccountReference.setValue("11223344556677889911223344556");


        applicationTemplateMap.put(TagIndicator.ADF_NAME.getValue(), adfName);
        applicationTemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), appLabel);
        applicationTemplateMap.put(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue(), track2EquivalentData);
        applicationTemplateMap.put(TagIndicator.APP_PAN.getValue(), applicationPan);
        applicationTemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), cardholderName);
        applicationTemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), langPref);
        applicationTemplateMap.put(TagIndicator.ISSUER_URL.getValue(), issuerUrl);
        applicationTemplateMap.put(TagIndicator.APPLICATION_VERSION_NUMBER.getValue(), appVersionNumber);
        applicationTemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), last4DigitPan);
        applicationTemplateMap.put(TagIndicator.TOKEN_REQUESTOR_ID.getValue(), tokenRequestorId);
        applicationTemplateMap.put(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue(), paymentAccountReference);

        QrCpmDataObject payloadFormatIndicator = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        payloadFormatIndicator.setValue("CPV01");

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        applicationTemplate.setTemplateMap(applicationTemplateMap);


        payload.put(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), payloadFormatIndicator);
        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject issuerQrisData = new QrCpmDataObject();
        issuerQrisData.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        issuerQrisData.setValue("abcde12345");

        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), issuerQrisData);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        var result = qrCpmMapper.map(payload);

        String expectedPayloadFormatIndicator = "CPV01";
        String expectedAdfName = "A0000006022020";
        String expectedApplicationLabel = "QRISCPM";
        String expectedTrack2EquivalentData = "9360001417451180859";
        String expectedApplicationPan = "9360001417451180859";
        String expectedCardholderName = "RIKI DERIAN";
        String expectedLanguagePreference = "iden";
        String expectedIssuerUrl = "https://www.astrapay.com";
        String expectedApplicationVersionNumber = "01";
        String expectedLast4DigitsPan = "0859";
        String expectedTokenRequestorId = "11223344556";
        String expectedPaymentAccountReference = "11223344556677889911223344556";
        String expectedIssuerData = "abcde12345";

        Assertions.assertEquals(expectedPayloadFormatIndicator, result.getPayloadFormatIndicator());
        Assertions.assertEquals(expectedAdfName, result.getApplicationTemplate().getAdfName());
        Assertions.assertEquals(expectedApplicationLabel, result.getApplicationTemplate().getApplicationLabel());
        Assertions.assertEquals(expectedTrack2EquivalentData, result.getApplicationTemplate().getTrack2EquivalentData());
        Assertions.assertEquals(expectedApplicationPan, result.getApplicationTemplate().getApplicationPan());
        Assertions.assertEquals(expectedCardholderName, result.getApplicationTemplate().getCardholderName());
        Assertions.assertEquals(expectedLanguagePreference, result.getApplicationTemplate().getLanguagePreference());
        Assertions.assertEquals(expectedIssuerUrl, result.getApplicationTemplate().getIssuerUrl());
        Assertions.assertEquals(expectedApplicationVersionNumber, result.getApplicationTemplate().getApplicationVersionNumber());
        Assertions.assertEquals(expectedLast4DigitsPan, result.getApplicationTemplate().getLast4DigitsPan());
        Assertions.assertEquals(expectedTokenRequestorId, result.getApplicationTemplate().getTokenRequestorId());
        Assertions.assertEquals(expectedPaymentAccountReference, result.getApplicationTemplate().getPaymentAccountReference());
        Assertions.assertEquals(expectedIssuerData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerData());

        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationCryptogram());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getCryptogramInformationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerApplicationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationTransactionCounter());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationInterchangeProfile());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getUnpredictableNumber());
    }

    @Test
    void testMap_withoutAllOptionalTag() {
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject adfName = new QrCpmDataObject();
        adfName.setTag(TagIndicator.ADF_NAME.getValue());
        adfName.setValue("A0000006022020");

        QrCpmDataObject appLabel = new QrCpmDataObject();
        appLabel.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        appLabel.setValue("QRISCPM");

        QrCpmDataObject track2EquivalentData = new QrCpmDataObject();
        track2EquivalentData.setTag(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        track2EquivalentData.setValue("9360001417451180859");

        QrCpmDataObject applicationPan = new QrCpmDataObject();
        applicationPan.setTag(TagIndicator.APP_PAN.getValue());
        applicationPan.setValue("9360001417451180859");

        applicationTemplateMap.put(TagIndicator.ADF_NAME.getValue(), adfName);
        applicationTemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), appLabel);
        applicationTemplateMap.put(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue(), track2EquivalentData);
        applicationTemplateMap.put(TagIndicator.APP_PAN.getValue(), applicationPan);

        QrCpmDataObject payloadFormatIndicator = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        payloadFormatIndicator.setValue("CPV01");

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        applicationTemplate.setTemplateMap(applicationTemplateMap);


        payload.put(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), payloadFormatIndicator);
        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject issuerQrisData = new QrCpmDataObject();
        issuerQrisData.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        issuerQrisData.setValue("abcde12345");


        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), issuerQrisData);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        var result = qrCpmMapper.map(payload);

        String expectedPayloadFormatIndicator = "CPV01";
        String expectedAdfName = "A0000006022020";
        String expectedApplicationLabel = "QRISCPM";
        String expectedTrack2EquivalentData = "9360001417451180859";
        String expectedApplicationPan = "9360001417451180859";
        String expectedIssuerData = "abcde12345";

        Assertions.assertEquals(expectedPayloadFormatIndicator, result.getPayloadFormatIndicator());
        Assertions.assertEquals(expectedAdfName, result.getApplicationTemplate().getAdfName());
        Assertions.assertEquals(expectedApplicationLabel, result.getApplicationTemplate().getApplicationLabel());
        Assertions.assertEquals(expectedTrack2EquivalentData, result.getApplicationTemplate().getTrack2EquivalentData());
        Assertions.assertEquals(expectedApplicationPan, result.getApplicationTemplate().getApplicationPan());
        Assertions.assertEquals(expectedIssuerData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerData());

        Assertions.assertNull(result.getApplicationTemplate().getCardholderName());
        Assertions.assertNull(result.getApplicationTemplate().getLanguagePreference());
        Assertions.assertNull(result.getApplicationTemplate().getIssuerUrl());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationVersionNumber());
        Assertions.assertNull(result.getApplicationTemplate().getLast4DigitsPan());
        Assertions.assertNull(result.getApplicationTemplate().getTokenRequestorId());
        Assertions.assertNull(result.getApplicationTemplate().getPaymentAccountReference());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationCryptogram());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getCryptogramInformationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerApplicationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationTransactionCounter());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationInterchangeProfile());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getUnpredictableNumber());
    }

    @Test
    void testMap_withoutMandatoryTag() {
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();

        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        Assertions.assertThrows(NullPointerException.class, () -> {
            qrCpmMapper.map(payload);
        });
    }

    @Test
    void testMap_withTag9f7aAnd9f7b() {
        Map<String, QrCpmDataObject> payload = new HashMap<>();
        Map<String, QrCpmDataObject> applicationTemplateMap = new HashMap<>();
        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateMap = new HashMap<>();

        QrCpmDataObject adfName = new QrCpmDataObject();
        adfName.setTag(TagIndicator.ADF_NAME.getValue());
        adfName.setValue("A0000006022020");

        QrCpmDataObject appLabel = new QrCpmDataObject();
        appLabel.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        appLabel.setValue("QRISCPM");

        QrCpmDataObject track2EquivalentData = new QrCpmDataObject();
        track2EquivalentData.setTag(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        track2EquivalentData.setValue("9360001417451180859");

        QrCpmDataObject applicationPan = new QrCpmDataObject();
        applicationPan.setTag(TagIndicator.APP_PAN.getValue());
        applicationPan.setValue("9360001417451180859");

        applicationTemplateMap.put(TagIndicator.ADF_NAME.getValue(), adfName);
        applicationTemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), appLabel);
        applicationTemplateMap.put(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue(), track2EquivalentData);
        applicationTemplateMap.put(TagIndicator.APP_PAN.getValue(), applicationPan);


        QrCpmDataObject payloadFormatIndicator = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        payloadFormatIndicator.setValue("CPV01");

        QrCpmDataObject applicationTemplate = new QrCpmDataObject();
        payloadFormatIndicator.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        applicationTemplate.setTemplateMap(applicationTemplateMap);


        payload.put(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), payloadFormatIndicator);
        payload.put(TagIndicator.APPLICATION_TEMPLATE.getValue(), applicationTemplate);

        QrCpmDataObject issuerQrisData = new QrCpmDataObject();
        issuerQrisData.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        issuerQrisData.setValue("abcde12345");

        QrCpmDataObject issuerPublicKeyCertificate = new QrCpmDataObject();
        issuerPublicKeyCertificate.setTag(TagIndicator.ISSUER_PUBLIC_KEY_CERTIFICATE.getValue());
        issuerPublicKeyCertificate.setValue("Miiskjsaifsdf12312");

        QrCpmDataObject issuerQrisDataEncypted = new QrCpmDataObject();
        issuerQrisDataEncypted.setTag(TagIndicator.ISSUER_QRIS_DATA_ENCRYPTED.getValue());
        issuerQrisDataEncypted.setValue("Ciiqrwqjr123153");


        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), issuerQrisData);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_PUBLIC_KEY_CERTIFICATE.getValue(), issuerPublicKeyCertificate);
        applicationSpecificTransparentTemplateMap.put(TagIndicator.ISSUER_QRIS_DATA_ENCRYPTED.getValue(), issuerQrisDataEncypted);

        QrCpmDataObject applicationSpecificTransparentTemplate = new QrCpmDataObject();
        applicationSpecificTransparentTemplate.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        applicationSpecificTransparentTemplate.setTemplateMap(applicationSpecificTransparentTemplateMap);

        applicationTemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), applicationSpecificTransparentTemplate);

        var result = qrCpmMapper.map(payload);

        String expectedPayloadFormatIndicator = "CPV01";
        String expectedAdfName = "A0000006022020";
        String expectedApplicationLabel = "QRISCPM";
        String expectedTrack2EquivalentData = "9360001417451180859";
        String expectedApplicationPan = "9360001417451180859";
        String expectedIssuerData = "abcde12345";
        String expectedIssuerPublicKeyCertificate = "Miiskjsaifsdf12312";
        String expectedIssuerQrisDataEncryted= "Ciiqrwqjr123153";

        Assertions.assertEquals(expectedPayloadFormatIndicator, result.getPayloadFormatIndicator());
        Assertions.assertEquals(expectedAdfName, result.getApplicationTemplate().getAdfName());
        Assertions.assertEquals(expectedApplicationLabel, result.getApplicationTemplate().getApplicationLabel());
        Assertions.assertEquals(expectedTrack2EquivalentData, result.getApplicationTemplate().getTrack2EquivalentData());
        Assertions.assertEquals(expectedApplicationPan, result.getApplicationTemplate().getApplicationPan());
        Assertions.assertEquals(expectedIssuerData, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerData());
        Assertions.assertEquals(expectedIssuerPublicKeyCertificate, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerPublicKeyCertificate());
        Assertions.assertEquals(expectedIssuerQrisDataEncryted, result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerQrisDataEncrypted());

        Assertions.assertNull(result.getApplicationTemplate().getCardholderName());
        Assertions.assertNull(result.getApplicationTemplate().getLanguagePreference());
        Assertions.assertNull(result.getApplicationTemplate().getIssuerUrl());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationVersionNumber());
        Assertions.assertNull(result.getApplicationTemplate().getLast4DigitsPan());
        Assertions.assertNull(result.getApplicationTemplate().getTokenRequestorId());
        Assertions.assertNull(result.getApplicationTemplate().getPaymentAccountReference());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationCryptogram());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getCryptogramInformationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getIssuerApplicationData());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationTransactionCounter());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getApplicationInterchangeProfile());
        Assertions.assertNull(result.getApplicationTemplate().getApplicationSpecificTransparentTemplate().getUnpredictableNumber());
    }
}
