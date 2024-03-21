package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.PayloadFormatIndicatorValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.1.1</b> Payload Format Indicator wajib memiliki Value “01”.
 */
public class PayloadFormatIndicatorValueValidator implements ConstraintValidator<PayloadFormatIndicatorValue, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        return "01".equals(value.get(0).getValue());
    }
}
