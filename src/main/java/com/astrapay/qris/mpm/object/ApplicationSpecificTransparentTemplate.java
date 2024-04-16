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
    String applicationCryptogram;
    // tag 9F27
    String cryptogramInformationData;
    // tag 9F10
    String issuerApplicationData;
    // tag 9F36
    String applicationTransactionCounter;
    // tag 82
    String applicationInterchangeProfile;
    // tag 9F37
    String unpredictableNumber;
}
