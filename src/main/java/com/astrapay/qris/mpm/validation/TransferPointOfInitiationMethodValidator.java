package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferPointOfInitiationMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator untuk {@link TransferPointOfInitiationMethod}.
 * <p>
 * Memvalidasi bahwa Point of Initiation Method (ID "01") untuk Transfer:
 * <ul>
 *     <li>Wajib ada</li>
 *     <li>Wajib bernilai "12" (Dynamic QR)</li>
 * </ul>
 * </p>
 * 
 * <p><b>Referensi:</b> Spesifikasi 4.2 - Verifikasi Data QRIS MPM Transaksi Transfer, Poin 1</p>
 * 
 */
public class TransferPointOfInitiationMethodValidator implements ConstraintValidator<TransferPointOfInitiationMethod, QrisPayload> {

    private static final int POINT_OF_INITIATION_METHOD_ID = 1;
    private static final String DYNAMIC_QR_VALUE = "12";

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot())) {
            return true; // Handled by other validators
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        QrisDataObject pointOfInitiation = qrisRoot.get(POINT_OF_INITIATION_METHOD_ID);

        // Point of Initiation Method wajib ada untuk Transfer
        if (Objects.isNull(pointOfInitiation)) {
            return false;
        }

        String value = pointOfInitiation.getValue();
        
        // Nilai wajib "12" (Dynamic QR)
        if (!DYNAMIC_QR_VALUE.equals(value)) {
            return false;
        }

        return true;
    }
}
