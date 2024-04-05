package com.astrapay.qris.mpm.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
