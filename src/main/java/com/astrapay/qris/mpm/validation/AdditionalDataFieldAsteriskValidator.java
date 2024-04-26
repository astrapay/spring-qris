package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.AdditionalDataFieldAsterisk;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.8.1.2</b> Jika ditampilkan, konten dari data object ID "01" - "08" wajib berupa "***" (tiga karakter asterisk) atau Value yang telah didefinisi oleh merchant. Ketersediaan dari "***" (tiga karakter asterisk) mengindikasikan bahwa aplikasi mobile meminta untuk memasukan informasi yang diperlukan.<br />
 */
@Builder
@NoArgsConstructor
public class AdditionalDataFieldAsteriskValidator implements ConstraintValidator<AdditionalDataFieldAsterisk, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId().equals(62)){
            for(int i=1; i<=8; i++){
                if(value.getTemplateMap().containsKey(i)){
                    if (!checkTemplate(value, i)) {
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private boolean checkTemplate(QrisDataObject value, int i) {
        String str = value.getTemplateMap().get(i).getValue();
        return "".equals(str);
    }
}
