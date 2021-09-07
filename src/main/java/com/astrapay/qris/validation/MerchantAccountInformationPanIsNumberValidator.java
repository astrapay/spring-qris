package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.MerchantAccountInformationPanIsNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Arthur Purnama
 */
public class MerchantAccountInformationPanIsNumberValidator implements ConstraintValidator<MerchantAccountInformationPanIsNumber, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId() >= 26 && value.getIntId() <= 45){
            try {
                Double.parseDouble(value.getTemplateMap().get(1).getValue());
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }
}