package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.PostalCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.15</b> Postal Code (ID "61")<br/>
 * Postal Code untuk mengindikasikan kode pos tempat merchant beroperasi. Wajib ditampilkan jika Value ID “58” adalah “ID” (Indonesia).
 */
public class PostalCodeValidator implements ConstraintValidator<PostalCode, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if ("ID".equals(value.get(58).getValue())) {
            return value.get(61) != null;
        }
        return true;
    }

}
