package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.CRCCustom;
import com.astrapay.qris.mpm.validation.constraints.CRCLastPosition;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <b>3.2 Organisasi Data</b>
 * CRC (ID "63") adalah data object terakhir di bawah root.<br/>
 * <b>4.6.1.2</b> CRC (ID "63") harus menjadi urutan terakhir data object dalam QR Code.
 */
public class CRCLastPositionCustomValidator implements ConstraintValidator<CRCCustom, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        List<QrisDataObject> list = new LinkedList<>(value.values());
        return list.get(list.size()-1).getValue().equals("1234");
    }
}
