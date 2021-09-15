package com.astrapay.qris.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantAccountInformation {

    private String globallyUniqueIdentifier;
    private String personalAccountNumber;
    private String merchantId;
    private MerchantCriteria criteria;

}
