package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.constraints.CpmMandatoryField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CpmMandatoryFieldValidator implements ConstraintValidator<CpmMandatoryField, Map<String, QrisDataObject>> {

    private String id;

    @Override
    public void initialize(CpmMandatoryField constraintAnnotation) {
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        return value.get(this.id) != null;
    }

}
