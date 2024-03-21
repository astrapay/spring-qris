package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.LanguagePreferance;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

/**
 * <b>4.9.2</b> Language Preference (ID "00")
 * <b>4.9.2.1</b> Language Preference wajib berisi dua karakter alfabet yang didefinisikan oleh [ISO 639]. Value pada data object Language Preference (ID "00") harus sesuai dengan Merchant Name—Alternate Language dan Merchant City—Alternate Language.
 */
@Builder
@NoArgsConstructor
public class LanguagePreferenceValidator implements ConstraintValidator<LanguagePreferance, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId().equals(64)){
            String localeStr = value.getTemplateMap().get(0).getValue();
            for(Locale locale : Locale.getAvailableLocales()){
                if(localeStr.length() == 2 && localeStr.toLowerCase(Locale.ROOT).equals(locale.getCountry().toLowerCase(Locale.ROOT))){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
