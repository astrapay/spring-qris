package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.QrisNationalNumberingSystem;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.NationalNumberingSystem;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.7.5.4</b> Merchant PAN yang mengacu pada ID “26”-“45” dengan sub ID “01” menandakan merchant yang melakukan transaksi, panjang karakter dari Value tersebut mencapai 19 digit.<br/>
 * <b>4.7.5.5</b> Delapan digit pertama merupakan NNS yang digunakan di Indonesia.
 */
@Builder
@NoArgsConstructor
public class NationalNumberingSystemValidator implements ConstraintValidator<NationalNumberingSystem, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId() >= 26 && value.getIntId() <= 45){
            try {
                QrisNationalNumberingSystem.Pjsp.valueOf(Integer.valueOf(value.getTemplateMap().get(1).getValue().substring(0,8)));
                return true;
            } catch (IllegalArgumentException|NullPointerException|IndexOutOfBoundsException ex) {
                return false;
            }
        }
        return true;
    }
}
