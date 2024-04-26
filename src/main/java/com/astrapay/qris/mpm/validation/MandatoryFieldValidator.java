package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.validation.constraints.MandatoryField;
import com.astrapay.qris.mpm.object.QrisDataObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class MandatoryFieldValidator implements ConstraintValidator<MandatoryField, Map<Integer, QrisDataObject>> {

    private int id;

    @Override
    public void initialize(MandatoryField constraintAnnotation) {
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        return value.get(this.id) != null;
    }
}
