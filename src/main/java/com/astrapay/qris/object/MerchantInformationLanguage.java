package com.astrapay.qris.object;

import lombok.*;

import java.util.Locale;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInformationLanguage {

    private Locale languagePreference;
    private String merchantNameAlternateLanguage;
    private String merchantCityAlternateLanguage;
}
