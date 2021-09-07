package com.astrapay.qris.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalData {

    private Map<Integer, String> dataObjects;
    private String consumerDataRequest;
    private ProprietaryDomestic proprietaryDomestic;
}
