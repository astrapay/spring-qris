package com.astrapay.qris.cpm;

import com.astrapay.qris.cpm.enums.TagIndicator;
import com.astrapay.qris.cpm.object.ApplicationTemplate;
import com.astrapay.qris.cpm.object.QrCpmDataObject;
import com.astrapay.qris.cpm.object.QrisCpm;
import com.astrapay.qris.mpm.object.ApplicationSpecificTransparentTemplate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QrCpmMapper {
    // map from QrCpmPayload to QrisCpm


    public QrisCpm map(Map<String, QrCpmDataObject> payload) {
        QrisCpm qrisCpm = new QrisCpm();
        qrisCpm.setPayloadFormatIndicator(payload.get(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue()).getValue());
        this.mapApplicationTemplate(payload.get(TagIndicator.APPLICATION_TEMPLATE.getValue()).getTemplateMap(), qrisCpm);
        return qrisCpm;
    }

    private void mapApplicationTemplate(Map<String, QrCpmDataObject> applicationTemplateData, QrisCpm qrisCpm){

        ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate = new ApplicationSpecificTransparentTemplate();
        ApplicationTemplate applicationTemplate = new ApplicationTemplate();

        String adfName = applicationTemplateData.get(TagIndicator.ADF_NAME.getValue()).getValue();
        String applicationLabel = applicationTemplateData.get(TagIndicator.APPLICATION_LABEL.getValue()).getValue();

        this.mapApplicationTemplateOptionalData(applicationTemplateData, applicationTemplate);
        this.mapApplicationTemplateOptionalDataAdditionalTagXXXX(applicationTemplateData, applicationTemplate);

        applicationTemplate.setAdfName(adfName);
        applicationTemplate.setApplicationLabel(applicationLabel);

        Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateData = applicationTemplateData.get(TagIndicator.APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getValue()).getTemplateMap();
        this.mapApplicationSpecificTransparentTemplate(applicationSpecificTransparentTemplateData, applicationSpecificTransparentTemplate);

        applicationTemplate.setApplicationSpecificTransparentTemplate(applicationSpecificTransparentTemplate);

        qrisCpm.setApplicationTemplate(applicationTemplate);
    }

    private void mapApplicationTemplateOptionalData(Map<String, QrCpmDataObject> applicationTemplateData, ApplicationTemplate applicationTemplate){
        if(applicationTemplateData.containsKey(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue())){
            String track2EquivalentData = applicationTemplateData.get(TagIndicator.TRACK_2_EQUIVALENT_DATA.getValue()).getValue();
            applicationTemplate.setTrack2EquivalentData(track2EquivalentData);
        }

        if(applicationTemplateData.containsKey(TagIndicator.APP_PAN.getValue())){
            String applicationPan = applicationTemplateData.get(TagIndicator.APP_PAN.getValue()).getValue();
            applicationTemplate.setApplicationPan(applicationPan);
        }

        if(applicationTemplateData.containsKey(TagIndicator.CARDHOLDER_NAME.getValue())){
            String cardholderName = applicationTemplateData.get(TagIndicator.CARDHOLDER_NAME.getValue()).getValue();
            applicationTemplate.setCardholderName(cardholderName);
        }

        if(applicationTemplateData.containsKey(TagIndicator.LANGUAGE_PREFERENCE.getValue())){
            String languagePreference = applicationTemplateData.get(TagIndicator.LANGUAGE_PREFERENCE.getValue()).getValue();
            applicationTemplate.setLanguagePreference(languagePreference);
        }

        if(applicationTemplateData.containsKey(TagIndicator.ISSUER_URL.getValue())){
            String issuerUrl = applicationTemplateData.get(TagIndicator.ISSUER_URL.getValue()).getValue();
            applicationTemplate.setIssuerUrl(issuerUrl);
        }

        if(applicationTemplateData.containsKey(TagIndicator.APPLICATION_VERSION_NUMBER.getValue())){
            String applicationVersionNumber = applicationTemplateData.get(TagIndicator.APPLICATION_VERSION_NUMBER.getValue()).getValue();
            applicationTemplate.setApplicationVersionNumber(applicationVersionNumber);
        }

        if(applicationTemplateData.containsKey(TagIndicator.LAST_4_DIGIT_PAN.getValue())){
            String last4DigitPan = applicationTemplateData.get(TagIndicator.LAST_4_DIGIT_PAN.getValue()).getValue();
            applicationTemplate.setLast4DigitsPan(last4DigitPan);
        }
    }

    private void mapApplicationTemplateOptionalDataAdditionalTagXXXX(Map<String, QrCpmDataObject> applicationTemplateData, ApplicationTemplate applicationTemplate){
        if(applicationTemplateData.containsKey(TagIndicator.TOKEN_REQUESTOR_ID.getValue())){
            String tokenRequestorId = applicationTemplateData.get(TagIndicator.TOKEN_REQUESTOR_ID.getValue()).getValue();
            applicationTemplate.setTokenRequestorId(tokenRequestorId);
        }

        if(applicationTemplateData.containsKey(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue())){
            String paymentAccountReference = applicationTemplateData.get(TagIndicator.PAYMENT_ACCOUNT_REFERENCE.getValue()).getValue();
            applicationTemplate.setPaymentAccountReference(paymentAccountReference);
        }
    }

    private void mapApplicationSpecificTransparentTemplate(Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateData, ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate){

        String issuerQrisData = applicationSpecificTransparentTemplateData.get(TagIndicator.ISSUER_QRIS_DATA.getValue()).getValue();
        applicationSpecificTransparentTemplate.setIssuerData(issuerQrisData);

        this.mapApplicationSpecificTransparentTemplateOptionalDataAdditionalTagXXXX(applicationSpecificTransparentTemplateData, applicationSpecificTransparentTemplate);
    }

    private void mapApplicationSpecificTransparentTemplateOptionalDataAdditionalTagXXXX(Map<String, QrCpmDataObject> applicationSpecificTransparentTemplateData, ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate){
        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.APPLICATION_CRYPTOGRAM.getValue())){
            String applicationCryptogram = applicationSpecificTransparentTemplateData.get(TagIndicator.APPLICATION_CRYPTOGRAM.getValue()).getValue();
            applicationSpecificTransparentTemplate.setApplicationCryptogram(applicationCryptogram);
        }

        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue())){
            String cryptogramInformationData = applicationSpecificTransparentTemplateData.get(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue()).getValue();
            applicationSpecificTransparentTemplate.setCryptogramInformationData(cryptogramInformationData);
        }

        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.ISSUER_APPLICATION_DATA.getValue())){
            String issuerApplicationData = applicationSpecificTransparentTemplateData.get(TagIndicator.ISSUER_APPLICATION_DATA.getValue()).getValue();
            applicationSpecificTransparentTemplate.setIssuerApplicationData(issuerApplicationData);
        }

        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue())){
            String applicationTransactionCounter = applicationSpecificTransparentTemplateData.get(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue()).getValue();
            applicationSpecificTransparentTemplate.setApplicationTransactionCounter(applicationTransactionCounter);
        }

        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue())){
            String applicationInterchangeProfile = applicationSpecificTransparentTemplateData.get(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue()).getValue();
            applicationSpecificTransparentTemplate.setApplicationInterchangeProfile(applicationInterchangeProfile);
        }

        if(applicationSpecificTransparentTemplateData.containsKey(TagIndicator.UNPREDICTABLE_NUMBER.getValue())){
            String unpredictableNumber = applicationSpecificTransparentTemplateData.get(TagIndicator.UNPREDICTABLE_NUMBER.getValue()).getValue();
            applicationSpecificTransparentTemplate.setUnpredictableNumber(unpredictableNumber);
        }
    }



}
