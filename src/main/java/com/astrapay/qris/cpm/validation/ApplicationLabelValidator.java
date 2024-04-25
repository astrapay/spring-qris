package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.constraints.ApplicationLabel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class ApplicationLabelValidator implements ConstraintValidator<ApplicationLabel, Map<String, QrisDataObject>> {

    private String hexValue;
    private String id;

    @Override
    public void initialize(ApplicationLabel constraintAnnotation) {
        this.id = constraintAnnotation.id();
        this.hexValue = constraintAnnotation.hexValue();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        // Check if the input field contains the hex value of tag '50'
        return value.get(this.id) != null && value.get(this.id).getValue().contains(this.hexValue);
    }
}
