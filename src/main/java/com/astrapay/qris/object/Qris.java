package com.astrapay.qris.object;

import lombok.*;

import java.util.Currency;
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
    Map<Integer, MerchantAccountInformation> merchantAccountInformationDomestics;

    MerchantAccountInformation domesticCentralRepository;
    Integer merchantCategoryCode;
    Currency transactionCurrency;
    @Builder.Default
    Double transactionAmount = 0.0;

    @Builder.Default
    Tip tip = new Tip();
    Locale countryCode;
    String merchantName;
    String merchantCity;
    Integer postalCode;


    AdditionalData additionalData;

    MerchantInformationLanguage merchantInformationLanguage;
    String crc;
}
