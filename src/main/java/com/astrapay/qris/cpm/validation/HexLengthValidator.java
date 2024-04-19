package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.QrisCpmEncoder;
import com.astrapay.qris.cpm.validation.constraints.HexLength;
import com.astrapay.qris.mpm.object.QrisDataObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class HexLengthValidator implements ConstraintValidator<HexLength, Map<String, QrisDataObject>> {

    private String id;
    private int min;
    private int max;

    @Autowired
    QrisCpmEncoder qrisCpmEncoder;

    @Autowired
    QrisHexConverter qrisHexConverter;

    @Override
    public void initialize(HexLength constraintAnnotation) {
        this.id = constraintAnnotation.id();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        if(value.containsKey(this.id)){
            String hexString = value.get(this.id).getValue();
            byte[] bytes = this.qrisHexConverter.hexStringToByteArray(hexString);
            int tagLength = bytes.length;
            return tagLength >= this.min && tagLength <= this.max;
        }
        return true;
    }
}
