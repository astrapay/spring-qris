package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.constraints.ApplicationIdentifier;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class ApplicationIdentifierValidator implements ConstraintValidator<ApplicationIdentifier, Map<String, QrisDataObject>> {

    private String hexValue;
    private String id;

    @Override
    public void initialize(ApplicationIdentifier constraintAnnotation) {
        this.id = constraintAnnotation.id();
        this.hexValue = constraintAnnotation.hexValue();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        // Check if the input field contains the hex value of tag '4F'
        return value.get(this.id) != null && value.get(this.id).getValue().contains(this.hexValue);
    }
}
