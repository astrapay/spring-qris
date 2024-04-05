package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.MerchantCriteria;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.MerchantAccountInformationCriteria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.7.5.3</b> Reverse Domain pada ID “26”-“45” dengan sub ID “00” harus memiliki nilai default “00” atau dapat berisi informasi reverse domain-nya.
 */
public class MerchantAccountInformationCriteriaValidator implements ConstraintValidator<MerchantAccountInformationCriteria, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId() >= 26 && value.getIntId() <= 45){
            for (MerchantCriteria enums : MerchantCriteria.values()) {
                if (enums.name().equals(value.getTemplateMap().get(3).getValue())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
