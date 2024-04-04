package com.astrapay.qris.mpm.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApplicationSpecificTransparentTemplate {
    // tag 9F74
    String issuerData;
    // tag 9F26
    byte[] applicationCryptogram;
    // tag 9F27
    Byte cryptogramInformationData;
    // tag 9F10
    byte[] issuerApplicationData;
    // tag 9F36
    byte[] applicationTransactionCounter;
    // tag 82
    byte[] applicationInterchangeProfile;
    // tag 9F37
    byte[] unpredictableNumber;
}
