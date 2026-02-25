package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.object.PurposeOfTransaction;
import com.astrapay.qris.mpm.validation.constraints.PurposeOfTransactionValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator implementation untuk {@link PurposeOfTransactionValid} annotation.
 * <p>
 * Validator ini memvalidasi bahwa Purpose of Transaction pada Additional Data Field Template
 * memiliki value yang valid untuk QRIS Transfer.
 * </p>
 * 
 * <p><b>Valid Purpose Values:</b></p>
 * <ul>
 *     <li>BOOK - Transfer/Booking</li>
 *     <li>DMCT - Debit Merchant Credit Transfer</li>
 *     <li>XBCT - Cross Border Credit Transfer</li>
 * </ul>
 * 
 */
public class PurposeOfTransactionValidator implements ConstraintValidator<PurposeOfTransactionValid, QrisPayload> {

    private static final int ADDITIONAL_DATA_ID = 62;
    private static final int PURPOSE_TAG_ID = 8;

    @Override
    public void initialize(PurposeOfTransactionValid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot())) {
            return true; // Let @MandatoryField handle null checks
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check if Additional Data Field Template (ID 62) exists
        QrisDataObject additionalData = qrisRoot.get(ADDITIONAL_DATA_ID);
        if (Objects.isNull(additionalData)) {
            return false;
        }

        // Check if templateMap is parsed
        Map<Integer, QrisDataObject> templateMap = additionalData.getTemplateMap();
        if (Objects.isNull(templateMap) || templateMap.isEmpty()) {
            return false;
        }

        // Check if Purpose of Transaction (tag 08) exists
        QrisDataObject purposeOfTransaction = templateMap.get(PURPOSE_TAG_ID);
        if (Objects.isNull(purposeOfTransaction) || Objects.isNull(purposeOfTransaction.getValue())) {
            return false;
        }

        // Validate purpose value using enum
        String purposeValue = purposeOfTransaction.getValue().trim();
        if (purposeValue.isEmpty()) {
            return false;
        }

        // Check if purpose value is valid using PurposeOfTransaction enum
        return PurposeOfTransaction.isValid(purposeValue);

    }
}
