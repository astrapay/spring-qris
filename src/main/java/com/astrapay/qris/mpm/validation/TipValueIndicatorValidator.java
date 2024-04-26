package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.TipValueIndicator;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.9</b> Tip Indicator (ID "55")
 * <b>4.7.9.1</b> Jika ditampilkan maka Tip Indicator wajib mengandung Value dari "01", "02" atau "03". Value lainnya belum ditetapkan.
 * <ul>
 *     <li>Value "01" wajib digunakan jika aplikasi mobile meminta konfirmasi konsumen untuk memasukan jumlah tip yang akan dibayarkan ke merchant</li>
 *     <li>Value "02" wajib digunakan untuk mengindikasi akan diisinya Value data object Tip Value Fixed (ID "56").</li>
 *     <li>Value “03” wajib digunakan untuk mengindikasi akan diisinya Value data object Tip Value Percentage (ID “57”).</li>
 * </ul>
 * Catatan: Walaupun Transaction Amount (ID “54”) tidak tersedia pada QR Code maka data object ini boleh ditampilkan.
 */
@Builder
@NoArgsConstructor
public class TipValueIndicatorValidator implements ConstraintValidator<TipValueIndicator, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if (value.get(55) != null) {
            String valStr = value.get(55).getValue();
            switch (valStr) {
                case "01":
                    return true;
                case "02":
                    return value.get(56) != null;
                case "03":
                    return value.get(57) != null;
                default:
                    return false;
            }
        }
        return true;
    }
}
