package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.PointOfInitiationMethodValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>4.7.2.1</b> Jika tersedia, maka Point of Initiation Method harus berisi Value "11" atau "12".<br/>
 * Data Object ini mengidentifikasikan teknologi yang digunakan dalam QR Code, apakah datanya statis atau dinamis. Value lainnya diperuntukkan dalam penggunaan yang belum didefinisikan. <br/>
 * Point of Initiation Method memiliki Value "11" untuk QR Code statis dan Value "12" untuk QR Code dinamis:
 * <ul>
 *     <li>Value "11" digunakan saat QR Code yang sama ditampilkan pada setiap transaksi.</li>
 *     <li>Value "12" digunakan saat QR Code baru dibuat dan ditampilkan untuk tiap-tiap transaksi.</li>
 * </ul>
 */
public class PointOfInitiationMethodValueValidator implements ConstraintValidator<PointOfInitiationMethodValue, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if(value.containsKey(1)){
            return "11".equals(value.get(1).getValue()) || "12".equals(value.get(1).getValue());
        }
        return true;
    }
}
