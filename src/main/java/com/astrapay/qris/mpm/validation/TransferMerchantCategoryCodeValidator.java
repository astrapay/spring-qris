package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferMerchantCategoryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator untuk {@link TransferMerchantCategoryCode}.
 * <p>
 * Memvalidasi bahwa Merchant Category Code (ID "52") untuk Transfer:
 * <ul>
 *     <li>Jika ada, wajib bernilai "4829" (Transfer)</li>
 *     <li>MCC lain tidak diperbolehkan untuk Transfer</li>
 * </ul>
 * </p>
 * 
 * <p><b>Referensi:</b> Spesifikasi 4.2 - Verifikasi Data QRIS MPM Transaksi Transfer, Poin 3</p>
 * 
 */
public class TransferMerchantCategoryCodeValidator implements ConstraintValidator<TransferMerchantCategoryCode, QrisPayload> {

    private static final int MERCHANT_CATEGORY_CODE_ID = 52;
    private static final String TRANSFER_MCC_VALUE = "4829";

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot())) {
            return true; // Handled by other validators
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        QrisDataObject mcc = qrisRoot.get(MERCHANT_CATEGORY_CODE_ID);

        // Jika MCC ada, wajib bernilai "4829"
        if (!Objects.isNull(mcc) && !Objects.isNull(mcc.getValue())) {
            String value = mcc.getValue();
            
            if (!TRANSFER_MCC_VALUE.equals(value)) {
                return false;
            }
        }

        return true;
    }
}
