package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.PurposeOfTransactionValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
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
    private static final String PURPOSE_BOOK = "BOOK";
    private static final String PURPOSE_DMCT = "DMCT";
    private static final String PURPOSE_XBCT = "XBCT";
    private static final List<String> VALID_PURPOSE_VALUES = Arrays.asList(PURPOSE_BOOK, PURPOSE_DMCT, PURPOSE_XBCT);

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

        // Validate purpose value
        String purposeValue = purposeOfTransaction.getValue().trim();
        if (purposeValue.isEmpty()) {
            return false;
        }

        // Check if purpose value contains any of the valid values (case-insensitive but recommend exact match)
        boolean isValidPurpose = VALID_PURPOSE_VALUES.stream()
                .anyMatch(validValue -> purposeValue.toUpperCase().contains(validValue));

        return isValidPurpose;
    }
}
