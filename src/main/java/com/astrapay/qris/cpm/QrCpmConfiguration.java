package com.astrapay.qris.cpm;

import com.astrapay.qris.cpm.enums.TagIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class QrCpmConfiguration {

    @Bean
    public List<String> qrisCpmSubTag() {
        List<String> qrisCpmSubTag = new ArrayList<>();
        qrisCpmSubTag.add(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue());
        qrisCpmSubTag.add(TagIndicator.APPLICATION_TEMPLATE.getValue());

        return qrisCpmSubTag;
    }

    @Bean
    public List<String> applicationTemplateSubTag() {
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

        return applicationTemplateSubTag;
    }

    @Bean
    public List<String> applicationSpecificTransparentTemplateSubTag() {
        List<String> applicationSpecificTransparentTemplateSubTag = new ArrayList<>();
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.ISSUER_QRIS_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_CRYPTOGRAM.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.CRYPTOGRAM_INFORMATION_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.ISSUER_APPLICATION_DATA.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_TRANSACTION_COUNTER.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.APPLICATION_INTERCHANGE_PROFILE.getValue());
        applicationSpecificTransparentTemplateSubTag.add(TagIndicator.UNPREDICTABLE_NUMBER.getValue());

        return applicationSpecificTransparentTemplateSubTag;
    }



}
