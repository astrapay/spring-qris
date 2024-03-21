package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.MerchantNameAlternateLanguage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.9.3</b> Merchant Name—Alternate Language (ID "01")
 * <b>4.9.3.1</b> Merchant Name—Alternate Language wajib ditampilkan. Merchant Name—Alternate Language direkomendasikan merupakan indikasi dari nama toko atau merchant dalam bahasa alternatif
 */
public class MerchantNameAlternateLanguageValidator implements ConstraintValidator<MerchantNameAlternateLanguage, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId().equals(64)){
            return value.getTemplateMap().get(1) != null;
        }
        return true;
    }
}
