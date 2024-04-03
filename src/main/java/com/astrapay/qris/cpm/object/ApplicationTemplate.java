package com.astrapay.qris.cpm.object;

import com.astrapay.qris.mpm.object.ApplicationSpecificTransparentTemplate;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ApplicationTemplate {
    // tag 4F
    Byte[] adfName;
    // tag 50
    String applicationLabel;
    // tag 57
    Byte[] track2EquivalentData;
    // tag 5A
    String applicationPan;
    // tag 5F20
    String cardholderName;
    // tag 5F2D
    String languagePreference;
    // tag 5F50
    String issuerUrl;
    // tag 9F08
    Byte[] applicationVersionNumber;
    // tag 9F19
    BigDecimal tokenRequestorId;
    //tag 9F24
    String paymentAccountReference;
    // tag 9F25
    String last4DigitsPan;
    ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate;
}
