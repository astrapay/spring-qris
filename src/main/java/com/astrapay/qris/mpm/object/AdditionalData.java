package com.astrapay.qris.mpm.object;


import lombok.*;

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

    private Map<Integer, String> dataObjects;
    private String consumerDataRequest;
    private ProprietaryDomestic proprietaryDomestic;
    private String value;
}
