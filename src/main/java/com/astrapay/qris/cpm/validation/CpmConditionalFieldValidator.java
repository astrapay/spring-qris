package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.constraints.CpmConditionalField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CpmConditionalFieldValidator implements ConstraintValidator<CpmConditionalField, Map<String, QrisDataObject>> {

    private String id;
    private String applicationPanTag;
    private String fieldSeparator = "D";
    private int startIndex;
    @Override
    public void initialize(CpmConditionalField constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.id = constraintAnnotation.id();
        this.applicationPanTag = constraintAnnotation.applicationPanTag();
        this.startIndex = constraintAnnotation.startIndex();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        QrisDataObject data57Object = value.get(this.id);
        QrisDataObject data5AObject = value.get(applicationPanTag);
        if(data5AObject == null && data57Object != null){
            return true;
        }
        if(data5AObject != null && data57Object == null){
            return true;
        }
        if(data5AObject != null && data57Object != null){
            int indexOfD = data57Object.getValue().indexOf(fieldSeparator);
            String data57Value = data57Object.getValue().substring(startIndex, indexOfD);
            String data5AValue = data5AObject.getValue();
            return data57Value.equals(data5AValue);
        }
        return false;
    }
}
