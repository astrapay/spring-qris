package com.astrapay.qris.cpm;


import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.enums.TagIndicator;
import com.astrapay.qris.cpm.object.QrCpmDataObject;
import com.astrapay.qris.cpm.object.QrCpmPayload;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class QrCpmParserTest {

    @InjectMocks
    private QrCpmParser qrCpmParser;

    @Spy
    private QrisHexConverter qrisHexConverter;

    /**
     * DATA_MOCK_BASE64_1 all available tag = 85, 61 > 4F, 50, 5A, 5F2D, 63 >> 9F74
     * DATA_MOCK_BASE64_2 all available tag = 85, 61 > 4F, 50, 5A, 5F20, 5F2D, 9F25, 63 >> 9F74
     * DATA_MOCK_BASE64_3 all available tag = 85, 61 > 4F, 50, 5A, 5F20, 5F2D, 5F50, 63 >> 9F74
     * DATA_MOCK_BASE64_4 all available tag = 85, 61 > 4F, 50, 5A, 9F25, 9F76 (UNKNOWN TAG), 63 >> 9F74
     * DATA_MOCK_BASE64_5 all available tag = 85, 61 > 4F, 50, 5A, 5F20, 5F2D, 9F25, 63 >> 9F74, 9F26, 9F10, 9F36, 82, 9F37
     * DATA_MOCK_BASE64_5 all available tag = 85, 61 > 4F, 50, 5A, 5F20, 5F2D, 9F25, 9F19, 9F24, 63 >> 9F74, 9F26, 9F10, 9F36, 82, 9F37
     * DATA_MOCK_BASE64_5 all available tag = 85
     */
    private static final String DATA_MOCK_BASE64_1 = "hQVDUFYwMWFFTwegAAAGAiAgUAdRUklTQ1BNWgqTYAkUMBAFMXIPXy0EaWRlbmMen3QbY2hlcXVlOjk1Njc3MjQ5NTA5MDUxODk4MDc0";
    private static final String DATA_MOCK_BASE64_2 = "hQVDUFYwMWFQTwegAAAGAiAgUAdRUklTQ1BNWgqTYAAUF0URgIWfXyAOU0VORFkgQUdVU1RJQU5fLQRpZGVunyUCCFljE590EMl/VZmz53/iKI8rM1dUcFs=";
    private static final String DATA_MOCK_BASE64_3 = "hQVDUFYwMWFbTwegAAAGAiAgUAdRUklTQ1BNWgqTYAkVMCcQV0FPXyAOU0VORFkgQUdVU1RJQU5fLQJpZF9QB0RBTkEuSURjG590GDI4MTAxMjAyMTU1Nzk2NDA2NjczMTE4MQ==";
    private static final String DATA_MOCK_BASE64_4 = "hQVDUFYwMWFiTwegAAAGAiAgUAdRUklTQ1BNWgqTYAkYNBNTM0g/nyUCNIOfdi9kLd4UOTE4OTg4MjIwNDE3MTA5MDMyNTfEBIATEA/FAQ/HCVNob3BlZVBhecsBAWMLn3QIMTA5MDMyNTc=";
    private static final String DATA_MOCK_BASE64_5 = "hQVDUFYwMWFtTwegAAAGAiAgUAdRUklTQ1BNWgqTYAAUF0URgIWfXyAOU0VORFkgQUdVU1RJQU5fLQRpZGVunyUCCFljMJ90EMl/VZmz53/iKI8rM1dUcFufJgIRIp8nAhEinxACESKfNgIRIoICESKfNwIRIg==";
    private static final String DATA_MOCK_BASE64_6 = "hQVDUFYwMWF3TwegAAAGAiAgUAdRUklTQ1BNWgqTYAAUF0URgIWfXyAOU0VORFkgQUdVU1RJQU5fLQRpZGVunyUCCFmfGQIRIp8kAmFiYzCfdBDJf1WZs+d/4iiPKzNXVHBbnyYCESKfJwIRIp8QAhEinzYCESKCAhEinzcCESI=";
    private static final String DATA_MOCK_BASE64_7 = "hQVDUFYwMQ==";


    @BeforeEach
    void setup(){
        List<String> qrisCpmSubTag = new ArrayList<>();
        qrisCpmSubTag.add(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        qrisCpmSubTag.add(TagIndicator.APPLICATION_TEMPLATE.getValue());

        List<String> applicationTemplateSubTag = new ArrayList<>();
        applicationTemplateSubTag.add(TagIndicator.ADF_NAME.getValue());
        applicationTemplateSubTag.add(TagIndicator.APPLICATION_LABEL.getValue());
        applicationTemplateSubTag.add(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue());
        applicationTemplateSubTag.add(TagIndicator.APP_PAN.getValue());
        applicationTemplateSubTag.add(TagIndicator.CARDHOLDER_NAME.getValue());
        applicationTemplateSubTag.add(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        applicationTemplateSubTag.add(TagIndicator.ISSUER_URL.getValue());
        applicationTemplateSubTag.add(TagIndicator.APPLICATION_VERSION_NUMBER.getValue());
        applicationTemplateSubTag.add(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        applicationTemplateSubTag.add(TagIndicator.TOKEN_REQUESTOR_ID.getValue());
        applicationTemplateSubTag.add(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue());
        applicationTemplateSubTag.add(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());

        List<String> applicationSpecificTransparentTemplateSubTag = new ArrayList<>();
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.ISSUER_QRIS_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.UNPREDICTABLE_NUMBER.getValue());

        qrCpmParser.setQrisCpmSubTag(qrisCpmSubTag);
        qrCpmParser.setApplicationTemplateSubTag(applicationTemplateSubTag);
        qrCpmParser.setApplicationSpecificTransparentTemplateSubTag(applicationSpecificTransparentTemplateSubTag);
    }

    @Test
    void testParse_withDataMock_1() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_1);
        expectedResult.setPayloadHex("8505435056303161454F07A000000602202050075152495343504D5A0A9360091430100531720F5F2D046964656E631E9F741B6368657175653A3935363737323439353039303531383938303734");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("54");
        tag9F74.setValue("6368657175653A3935363737323439353039303531383938303734");

        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360091430100531720");


        QrCpmDataObject tag5F2D = new QrCpmDataObject();
        tag5F2D.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        tag5F2D.setLength("4");
        tag5F2D.setValue("iden");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("60");
        tag63.setValue("9F741B6368657175653A3935363737323439353039303531383938303734");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), tag5F2D);
        tag61TemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), tag63);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("138");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360091430100531720F5F2D046964656E631E9F741B6368657175653A3935363737323439353039303531383938303734");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_1);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());


        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength());

        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));

        Map<String, QrCpmDataObject> actualTag63TemplateMap = actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        Assertions.assertEquals(tag63TemplateMap.size(), actualTag63TemplateMap.size());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength());

        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()));

    }

    @Test
     void testParse_withDataMock_2() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_2);
        expectedResult.setPayloadHex("8505435056303161504F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F2502085963139F7410C97F5599B3E77FE2288F2B335754705B");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("32");
        tag9F74.setValue("C97F5599B3E77FE2288F2B335754705B");

        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360001417451180859");

        QrCpmDataObject tag5F2D = new QrCpmDataObject();
        tag5F2D.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        tag5F2D.setLength("4");
        tag5F2D.setValue("iden");

        QrCpmDataObject tag5F20 = new QrCpmDataObject();
        tag5F20.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        tag5F20.setLength("14");
        tag5F20.setValue("SENDY AGUSTIAN");

        QrCpmDataObject tag9F25 = new QrCpmDataObject();
        tag9F25.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        tag9F25.setLength("4");
        tag9F25.setValue("0859");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("38");
        tag63.setValue("9F7410C97F5599B3E77FE2288F2B335754705B");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), tag5F20);
        tag61TemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), tag5F2D);
        tag61TemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), tag9F25);
        tag61TemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), tag63);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("160");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F2502085963139F7410C97F5599B3E77FE2288F2B335754705B");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_2);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());

        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength());

        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));

        Map<String, QrCpmDataObject> actualTag63TemplateMap = actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        Assertions.assertEquals(tag63TemplateMap.size(), actualTag63TemplateMap.size());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength());

        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()));

    }

    @Test
    void testParse_withDataMock_3() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_3);
        expectedResult.setPayloadHex("85054350563031615B4F07A000000602202050075152495343504D5A0A9360091530271057414F5F200E53454E445920414755535449414E5F2D0269645F500744414E412E4944631B9F7418323831303132303231353537393634303636373331313831");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("48");
        tag9F74.setValue("323831303132303231353537393634303636373331313831");

        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360091530271057414");

        QrCpmDataObject tag5F2D = new QrCpmDataObject();
        tag5F2D.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        tag5F2D.setLength("2");
        tag5F2D.setValue("id");

        QrCpmDataObject tag5F20 = new QrCpmDataObject();
        tag5F20.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        tag5F20.setLength("14");
        tag5F20.setValue("SENDY AGUSTIAN");

        QrCpmDataObject tag5F50 = new QrCpmDataObject();
        tag5F50.setTag(TagIndicator.ISSUER_URL.getValue());
        tag5F50.setLength("7");
        tag5F50.setValue("DANA.ID");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("54");
        tag63.setValue("9F7418323831303132303231353537393634303636373331313831");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), tag5F20);
        tag61TemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), tag5F2D);
        tag61TemplateMap.put(TagIndicator.ISSUER_URL.getValue(), tag5F50);
        tag61TemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), tag63);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("182");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360091530271057414F5F200E53454E445920414755535449414E5F2D0269645F500744414E412E4944631B9F7418323831303132303231353537393634303636373331313831");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_3);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());

        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()).getLength());

        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()));

        Map<String, QrCpmDataObject> actualTag63TemplateMap = actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        Assertions.assertEquals(tag63TemplateMap.size(), actualTag63TemplateMap.size());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength());

        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()));
        Assertions.assertNull(actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()));

    }

    @Test
    void testParse_withDataMock_4() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_4);
        expectedResult.setPayloadHex("8505435056303161624F07A000000602202050075152495343504D5A0A9360091834135333483F9F250234839F762F642DDE143931383938383232303431373130393033323537C4048013100FC5010FC70953686F706565506179CB0101630B9F74083130393033323537");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("16");
        tag9F74.setValue("3130393033323930");

        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360091834135333483");

        QrCpmDataObject tag9F25 = new QrCpmDataObject();
        tag9F25.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        tag9F25.setLength("4");
        tag9F25.setValue("3483");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("22");
        tag63.setValue("9F74083130393033323930");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), tag9F25);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("196");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360091834135333483F9F250234839F762F642DDE143931383938383232303431373130393033323537C4048013100FC5010FC70953686F706565506179CB0101630B9F74083130393033323537");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_4);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());

        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength());

        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()));
    }

    @Test
    void testParse_withDataMock_5() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_5);
        expectedResult.setPayloadHex("85054350563031616D4F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F2502085963309F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("32");
        tag9F74.setValue("C97F5599B3E77FE2288F2B335754705B");

        QrCpmDataObject tag9F26 = new QrCpmDataObject();
        tag9F26.setTag(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        tag9F26.setLength("4");
        tag9F26.setValue("1122");

        QrCpmDataObject tag9F27 = new QrCpmDataObject();
        tag9F27.setTag(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        tag9F27.setLength("4");
        tag9F27.setValue("1122");

        QrCpmDataObject tag9F10 = new QrCpmDataObject();
        tag9F10.setTag(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        tag9F10.setLength("4");
        tag9F10.setValue("1122");

        QrCpmDataObject tag9F36 = new QrCpmDataObject();
        tag9F36.setTag(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        tag9F36.setLength("4");
        tag9F36.setValue("1122");

        QrCpmDataObject tag82 = new QrCpmDataObject();
        tag82.setTag(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        tag82.setLength("4");
        tag82.setValue("1122");

        QrCpmDataObject tag9F37 = new QrCpmDataObject();
        tag9F37.setTag(TagIndicator.UNPREDICTABLE_NUMBER.getValue());
        tag9F37.setLength("4");
        tag9F37.setValue("1122");


        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);
        tag63TemplateMap.put(TagIndicator.APPLICATION_CRYPTOGRAM.getValue(), tag9F26);
        tag63TemplateMap.put(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue(), tag9F27);
        tag63TemplateMap.put(TagIndicator.ISSUER_APPLICATION_DATA.getValue(), tag9F10);
        tag63TemplateMap.put(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue(), tag9F36);
        tag63TemplateMap.put(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue(), tag82);
        tag63TemplateMap.put(TagIndicator.UNPREDICTABLE_NUMBER.getValue(), tag9F37);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360001417451180859");

        QrCpmDataObject tag5F2D = new QrCpmDataObject();
        tag5F2D.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        tag5F2D.setLength("4");
        tag5F2D.setValue("iden");

        QrCpmDataObject tag5F20 = new QrCpmDataObject();
        tag5F20.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        tag5F20.setLength("14");
        tag5F20.setValue("SENDY AGUSTIAN");

        QrCpmDataObject tag9F25 = new QrCpmDataObject();
        tag9F25.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        tag9F25.setLength("4");
        tag9F25.setValue("0859");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("96");
        tag63.setValue("9F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), tag5F20);
        tag61TemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), tag5F2D);
        tag61TemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), tag9F25);
        tag61TemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), tag63);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("218");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F2502085963309F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_5);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());

        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength());

        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));

        Map<String, QrCpmDataObject> actualTag63TemplateMap = actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        Assertions.assertEquals(tag63TemplateMap.size(), actualTag63TemplateMap.size());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getValue());

        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getLength());
    }

    @Test
    void testParse_withDataMock_6() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_6);
        expectedResult.setPayloadHex("8505435056303161774F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F250208599F190211229F2402616263309F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");

        QrCpmDataObject tag9F74 = new QrCpmDataObject();
        tag9F74.setTag(TagIndicator.ISSUER_QRIS_DATA.getValue());
        tag9F74.setLength("32");
        tag9F74.setValue("C97F5599B3E77FE2288F2B335754705B");

        QrCpmDataObject tag9F26 = new QrCpmDataObject();
        tag9F26.setTag(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        tag9F26.setLength("4");
        tag9F26.setValue("1122");

        QrCpmDataObject tag9F27 = new QrCpmDataObject();
        tag9F27.setTag(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        tag9F27.setLength("4");
        tag9F27.setValue("1122");

        QrCpmDataObject tag9F10 = new QrCpmDataObject();
        tag9F10.setTag(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        tag9F10.setLength("4");
        tag9F10.setValue("1122");

        QrCpmDataObject tag9F36 = new QrCpmDataObject();
        tag9F36.setTag(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        tag9F36.setLength("4");
        tag9F36.setValue("1122");

        QrCpmDataObject tag82 = new QrCpmDataObject();
        tag82.setTag(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        tag82.setLength("4");
        tag82.setValue("1122");

        QrCpmDataObject tag9F37 = new QrCpmDataObject();
        tag9F37.setTag(TagIndicator.UNPREDICTABLE_NUMBER.getValue());
        tag9F37.setLength("4");
        tag9F37.setValue("1122");

        Map<String, QrCpmDataObject> tag63TemplateMap = new HashMap<>();
        tag63TemplateMap.put(TagIndicator.ISSUER_QRIS_DATA.getValue(), tag9F74);
        tag63TemplateMap.put(TagIndicator.APPLICATION_CRYPTOGRAM.getValue(), tag9F26);
        tag63TemplateMap.put(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue(), tag9F27);
        tag63TemplateMap.put(TagIndicator.ISSUER_APPLICATION_DATA.getValue(), tag9F10);
        tag63TemplateMap.put(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue(), tag9F36);
        tag63TemplateMap.put(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue(), tag82);
        tag63TemplateMap.put(TagIndicator.UNPREDICTABLE_NUMBER.getValue(), tag9F37);

        QrCpmDataObject tag4F = new QrCpmDataObject();
        tag4F.setTag(TagIndicator.ADF_NAME.getValue());
        tag4F.setLength("14");
        tag4F.setValue("A0000006022020");

        QrCpmDataObject tag50 = new QrCpmDataObject();
        tag50.setTag(TagIndicator.APPLICATION_LABEL.getValue());
        tag50.setLength("7");
        tag50.setValue("QRISCPM");

        QrCpmDataObject tag5A = new QrCpmDataObject();
        tag5A.setTag(TagIndicator.APP_PAN.getValue());
        tag5A.setLength("19");
        tag5A.setValue("9360001417451180859");

        QrCpmDataObject tag5F2D = new QrCpmDataObject();
        tag5F2D.setTag(TagIndicator.LANGUAGE_PREFERENCE.getValue());
        tag5F2D.setLength("4");
        tag5F2D.setValue("iden");

        QrCpmDataObject tag5F20 = new QrCpmDataObject();
        tag5F20.setTag(TagIndicator.CARDHOLDER_NAME.getValue());
        tag5F20.setLength("14");
        tag5F20.setValue("SENDY AGUSTIAN");

        QrCpmDataObject tag9F25 = new QrCpmDataObject();
        tag9F25.setTag(TagIndicator.LAST_4_DIGIT_PAN.getValue());
        tag9F25.setLength("4");
        tag9F25.setValue("0859");

        QrCpmDataObject tag9F19 = new QrCpmDataObject();
        tag9F19.setTag(TagIndicator.TOKEN_REQUESTOR_ID.getValue());
        tag9F19.setLength("4");
        tag9F19.setValue("1122");

        QrCpmDataObject tag9F24 = new QrCpmDataObject();
        tag9F24.setTag(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue());
        tag9F24.setLength("2");
        tag9F24.setValue("ab");

        QrCpmDataObject tag63 = new QrCpmDataObject();
        tag63.setTag(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue());
        tag63.setLength("96");
        tag63.setValue("9F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");
        tag63.setTemplateMap(tag63TemplateMap);

        Map<String, QrCpmDataObject> tag61TemplateMap = new HashMap<>();
        tag61TemplateMap.put(TagIndicator.ADF_NAME.getValue(), tag4F);
        tag61TemplateMap.put(TagIndicator.APPLICATION_LABEL.getValue(), tag50);
        tag61TemplateMap.put(TagIndicator.APP_PAN.getValue(), tag5A);
        tag61TemplateMap.put(TagIndicator.CARDHOLDER_NAME.getValue(), tag5F20);
        tag61TemplateMap.put(TagIndicator.LANGUAGE_PREFERENCE.getValue(), tag5F2D);
        tag61TemplateMap.put(TagIndicator.LAST_4_DIGIT_PAN.getValue(), tag9F25);
        tag61TemplateMap.put(TagIndicator.TOKEN_REQUESTOR_ID.getValue(), tag9F19);
        tag61TemplateMap.put(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue(), tag9F24);
        tag61TemplateMap.put(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue(), tag63);

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmDataObject tag61 = new QrCpmDataObject();
        tag61.setTag(TagIndicator.APPLICATION_TEMPLATE.getValue());
        tag61.setLength("238");
        tag61.setValue("4F07A000000602202050075152495343504D5A0A9360001417451180859F5F200E53454E445920414755535449414E5F2D046964656E9F250208599F190211229F2402616263309F7410C97F5599B3E77FE2288F2B335754705B9F260211229F270211229F100211229F36021122820211229F37021122");
        tag61.setTemplateMap(tag61TemplateMap);

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_6);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());

        QrCpmDataObject actualTag61 = qrCpmPayload.getQrisRoot().get(TagIndicator.APPLICATION_TEMPLATE.getValue());
        Assertions.assertEquals(tag61.getTag(), actualTag61.getTag());
        Assertions.assertEquals(tag61.getValue(), actualTag61.getValue());
        Assertions.assertEquals(tag61.getLength(), actualTag61.getLength());

        Map<String, QrCpmDataObject> actualTag61TemplateMap = actualTag61.getTemplateMap();
        Assertions.assertEquals(tag61TemplateMap.size(), actualTag61TemplateMap.size());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()).getValue());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()).getValue(), actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()).getValue());

        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.ADF_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_LABEL.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APP_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.CARDHOLDER_NAME.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()).getLength());
        Assertions.assertEquals(tag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()).getLength(), actualTag61TemplateMap.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()).getLength());


        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.ISSUER_URL.getValue()));
        Assertions.assertNull(actualTag61TemplateMap.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()));

        Map<String, QrCpmDataObject> actualTag63TemplateMap = actualTag61TemplateMap.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        Assertions.assertEquals(tag63TemplateMap.size(), actualTag63TemplateMap.size());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getValue());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getValue(), actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getValue());

        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getLength());
        Assertions.assertEquals(tag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getLength(), actualTag63TemplateMap.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getLength());
    }

    @Test
    void testParse_withDataMock_7() throws IOException, DecoderException {
        QrCpmPayload expectedResult = new QrCpmPayload();
        expectedResult.setPayloadBase64(DATA_MOCK_BASE64_7);
        expectedResult.setPayloadHex("85054350563031");

        QrCpmDataObject tag85 = new QrCpmDataObject();
        tag85.setTag(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        tag85.setLength("5");
        tag85.setValue("CPV01");

        QrCpmPayload qrCpmPayload = qrCpmParser.parse(DATA_MOCK_BASE64_7);

        Assertions.assertEquals(expectedResult.getPayloadHex(), qrCpmPayload.getPayloadHex());

        QrCpmDataObject actualTag85 = qrCpmPayload.getQrisRoot().get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        Assertions.assertEquals(tag85.getTag(), actualTag85.getTag());
        Assertions.assertEquals(tag85.getValue(), actualTag85.getValue());
        Assertions.assertEquals(tag85.getLength(), actualTag85.getLength());
    }

}
