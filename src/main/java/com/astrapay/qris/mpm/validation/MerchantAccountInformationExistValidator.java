package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.MerchantAccountInformationExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.3.1</b> Setidaknya satu data object Merchant Account Information dari "02" - "51" harus ditampilkan.
 */
public class MerchantAccountInformationExistValidator implements ConstraintValidator<MerchantAccountInformationExist, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        for (int i = 2; i <= 51; i++) {
            if (value.containsKey(i)) {
                return true;
            }
        }
        return false;
    }
}
