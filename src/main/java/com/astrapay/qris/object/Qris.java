package com.astrapay.qris.object;

import lombok.*;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Qris {

    String payloadFormatIndicator;
    Integer pointOfInitiationMethod;

    @Builder.Default
    Map<Integer, MerchantAccountInformation> merchantAccountInformationDomestics = new HashMap<>();

    @Builder.Default
    MerchantAccountInformation domesticCentralRepository = new MerchantAccountInformation();
    Integer merchantCategoryCode;
    Currency transactionCurrency;
    @Builder.Default
    Double transactionAmount = 0.0;
    Tip tip;
    Locale countryCode;
    String merchantName;
    String merchantCity;
    Integer postalCode;

    @Builder.Default
    AdditionalData additionalData = new AdditionalData();

    @Builder.Default
    MerchantInformationLanguage merchantInformationLanguage = new MerchantInformationLanguage();
    String crc;
}
