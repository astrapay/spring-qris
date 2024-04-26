package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.CharLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CharLengthValidator implements ConstraintValidator<CharLength, Map<Integer, QrisDataObject>> {

    private int from;
    private int to;
    private int min;
    private int max;

    @Override
    public void initialize(CharLength constraintAnnotation) {
        this.from = constraintAnnotation.from();
        this.to = constraintAnnotation.to();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        for(int i = from; i<= to; i++){
            if(value.containsKey(i)){
                return value.get(i).getIntLength() >= this.min &&
                        value.get(i).getIntLength() <= this.max;
            }
        }
        return true;
    }
}
