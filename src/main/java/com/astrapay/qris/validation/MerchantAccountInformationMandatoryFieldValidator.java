package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.MerchantAccountInformationMandatoryField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Arthur Purnama
 */
public class MerchantAccountInformationMandatoryFieldValidator implements ConstraintValidator<MerchantAccountInformationMandatoryField, QrisDataObject> {

    private int id;
    private int from;
    private int to;

    @Override
    public void initialize(MerchantAccountInformationMandatoryField constraintAnnotation) {
        this.from = constraintAnnotation.from();
        this.to = constraintAnnotation.to();
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId() >= this.from && value.getIntId() <= this.to){
            return this.id == value.getTemplateMap().get(this.id).getIntId();
        }
        return true;
    }
}