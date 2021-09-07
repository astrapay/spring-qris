package com.astrapay.qris.validation;

import com.astrapay.qris.MerchantCategoryCodes;
import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.MerchantCategoryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.6</b> Merchant Category Code (ID "52")
 * <b>4.7.6.1</b> Merchant Category Code (MCC) harus memuat informasi MCC yang didefinisikan oleh [ISO 18245].
 */
public class MerchantCategoryCodeValidator implements ConstraintValidator<MerchantCategoryCode, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        try {
            MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(Integer.parseInt(value.get(52).getValue()));
            return value.get(52).getValue().length() == 4;
        } catch (IllegalArgumentException nfe) {
            return false;
        }
    }
}