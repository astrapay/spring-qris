package com.astrapay.qris.mpm.object;

import lombok.Builder;

@Builder
public class ApplicationSpecificTransparentTemplate {
    // tag 9F74
    String issuerData;
    // tag 9F26
    Byte[] applicationCryptogramInformation;
    // tag 9F27
    Byte cryptogramInformationData;
    // tag 9F10
    Byte[] issuerApplicationDate;
    // tag 9F36
    Byte[] applicationTransactionCounter;
    // tag 82
    Byte[] applicationInterchangeProfile;
    // tag 9F37
    Byte[] unpredictableNumber;
}
