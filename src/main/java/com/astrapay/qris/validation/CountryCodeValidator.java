package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.CountryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.Map;

/**
 * <b>4.7.12</b> Country Code (ID "58")<br />
 * <b>4.7.12.1</b> Country Code wajib mengandung Value yang didefinisikan oleh [ISO 3166-1 alpha 2]. Sebagai contoh, Indonesia memiliki Value data object "ID".
 */
public class CountryCodeValidator implements ConstraintValidator<CountryCode, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        return Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2).contains(value.get(58).getValue());
    }
}