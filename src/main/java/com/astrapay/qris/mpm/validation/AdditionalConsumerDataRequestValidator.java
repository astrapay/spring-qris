package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.AdditionalConsumerDataRequest;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.8.1.3</b> Jika ditampilkan Additional Consumer Data Request (ID "09") wajib berisi kombinasi karakter seperti "A", "M" dan/atau "E", kemudian wajib ada konten yang mencerminkan setiap karakter tersebut. <br/>
 * Jika satu atau beberapa karakter berikut muncul pada Additional Consumer Data Request (ID "09") konsumen harus mengisi seluruh informasi yang diminta dengan lengkap. Setiap karakter yang mewakili permintaan data tambahan, yaitu:
 * <ul>
 *     <li>"A" = Alamat konsumen;</li>
 *     <li>"M"= Nomor telepon konsumen;</li>
 *     <li>"E" = Alamat email konsumen;</li>
 * </ul>
 * hanya diperbolehkan muncul satu kali.
 * Contoh:
 * <ul>
 *     <li>0903AME -> diperbolehkan</li>
 *     <li>0902EA -> diperbolehkan</li>
 *     <li>0902MM -> tidak diperbolehkan</li>
 * </ul>
 */
@Builder
@NoArgsConstructor
public class AdditionalConsumerDataRequestValidator implements ConstraintValidator<AdditionalConsumerDataRequest, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if (value.getIntId() == 62 && value.getTemplateMap().containsKey(9)) {
            String str = value.getTemplateMap().get(9).getValue();
            for (int i = 0; i < str.length() - 1; i++) {
                for (int j = i + 1; j < str.length(); j++) {
                    if (str.charAt(i) == str.charAt(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
