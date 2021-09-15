package com.astrapay.qris.object;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalData {

    @Builder.Default
    private Map<Integer, String> dataObjects= new HashMap<>();
    private String consumerDataRequest;

    @Builder.Default
    private ProprietaryDomestic proprietaryDomestic = new ProprietaryDomestic();
    private String value;
}
