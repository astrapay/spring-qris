package com.astrapay.qris.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Qris {

    String payloadFormatIndicator;
    Integer pointOfInitiationMethod;
    Map<Integer, MerchantAccountInformation> merchantAccountInformationDomestics;
    MerchantAccountInformation domesticCentralRepository;
    Integer merchantCategoryCode;
    Currency transactionCurrency;
    Double transactionAmount;
    Tip tip;
    Locale countryCode;
    String merchantName;
    String merchantCity;
    Integer postalCode;
    AdditionalData additionalData;
    MerchantInformationLanguage merchantInformationLanguage;
    String crc;
}
