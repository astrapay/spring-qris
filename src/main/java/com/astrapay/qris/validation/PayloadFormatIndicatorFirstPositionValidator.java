package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.PayloadFormatIndicatorFirstPosition;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <b>3.2 Organisasi Data</b>
 * Payload Format Indicator (ID "00") adalah data object pertama di bawah root.<br/>
 * <b>4.6.1.1</b> Payload Format Indicator (ID "00") harus menjadi urutan pertama data object dalam QR Code.
 */
public class PayloadFormatIndicatorFirstPositionValidator implements ConstraintValidator<PayloadFormatIndicatorFirstPosition, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        List<QrisDataObject> list = new LinkedList<>(value.values());
        return "00".equals(list.get(0).getId());
    }
}
