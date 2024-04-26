package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.IdNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * @author Arthur Purnama
 */
public class IdNotNullValidator implements ConstraintValidator<IdNotNull, Map<Integer, QrisDataObject>> {

    private int id;

    @Override
    public void initialize(IdNotNull constraintAnnotation) {
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        return value.get(this.id) != null;
    }
}
