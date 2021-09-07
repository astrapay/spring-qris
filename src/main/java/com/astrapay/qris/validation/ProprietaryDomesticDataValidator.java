package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.ProprietaryDomesticData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.8.1.4</b> Jika ID “99” diisi, maka template mengacu pada Tabel 4.8 dengan panjang karakter yang akan tidak lebih dari 91 karakter.
 */
public class ProprietaryDomesticDataValidator implements ConstraintValidator<ProprietaryDomesticData, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if (value.getIntId().equals(62) && value.getTemplateMap().containsKey(99)) {
            return value.getTemplateMap().get(99).getValue().length() <= 91;
        }
        return true;
    }
}