package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.MerchantAccountInformation51Exist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.7</b> Merchant Account Information Template (ID “51”) ID “51” wajib ditampilkan jika Value dari Point of Initiation Method “11”.<br />
 */
public class MerchantAccountInformation51ExistValidator implements ConstraintValidator<MerchantAccountInformation51Exist, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if("11".equals(value.get(1).getValue())){
            return value.containsKey(51);
        }
        return true;
    }
}