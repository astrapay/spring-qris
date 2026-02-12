package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.QrisNationalNumberingSystem.Pjsp;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferAccountInformationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator implementation untuk {@link TransferAccountInformationValid} annotation.
 * <p>
 * Validator ini memvalidasi struktur dan isi dari Transfer Account Information (ID "40")
 * pada QRIS Transfer payload.
 * </p>
 *
 * <p><b>Validasi yang dilakukan:</b></p>
 * <ul>
 *     <li>Tag 40 harus exist dalam qrisRoot</li>
 *     <li>Tag 40 harus memiliki templateMap (parsed sub-tags)</li>
 *     <li>Sub-tag 00 (Reverse Domain) mandatory, tidak boleh kosong</li>
 *     <li>Sub-tag 01 (Customer PAN) mandatory, numeric, 16-19 digit</li>
 *     <li>Sub-tag 02 (Beneficiary ID) mandatory, tidak boleh kosong, max 15 chars</li>
 *     <li>Sub-tag 04 (BIC) optional, jika ada harus 8-11 chars</li>
 * </ul>
 *
 */
public class TransferAccountInformationValidator implements ConstraintValidator<TransferAccountInformationValid, QrisPayload> {

    // Tag IDs
    private static final int TRANSFER_ACCOUNT_INFO_ID = 40;
    private static final int REVERSE_DOMAIN_TAG = 0;
    private static final int CUSTOMER_PAN_TAG = 1;
    private static final int BENEFICIARY_ID_TAG = 2;
    private static final int BIC_TAG = 4;

    // Validation Rules
    private static final int REVERSE_DOMAIN_MAX_LENGTH = 32;
    private static final int BENEFICIARY_ID_MAX_LENGTH = 15;
    private static final int BIC_MIN_LENGTH = 8;
    private static final int BIC_MAX_LENGTH = 11;
    private static final int PAN_MIN_LENGTH = 16;
    private static final int PAN_MAX_LENGTH = 19;
    private static final int NNS_START_LENGTH = 0;
    private static final int NNS_END_LENGTH = 8;  // National Numbering System code is 8 digits
    
    private static final String NUMERIC_PATTERN = "\\d+";

    @Override
    public void initialize(TransferAccountInformationValid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        // Skip validation jika payload null (handled by other validators)
        if (isNullPayload(payload)) {
            return true;
        }

        // Get Transfer Account Information template
        Map<Integer, QrisDataObject> accountInfo = extractTransferAccountInfo(payload.getQrisRoot());
        if (Objects.isNull(accountInfo)) {
            return false;
        }

        // Validate semua mandatory fields
        return isReverseDomainValid(accountInfo) &&
               isCustomerPanValid(accountInfo) &&
               isBeneficiaryIdValid(accountInfo) &&
               isBicValid(accountInfo);
    }

    private boolean isNullPayload(QrisPayload payload) {
        return Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot());
    }

    private Map<Integer, QrisDataObject> extractTransferAccountInfo(Map<Integer, QrisDataObject> qrisRoot) {
        QrisDataObject transferAccount = qrisRoot.get(TRANSFER_ACCOUNT_INFO_ID);
        
        if (Objects.isNull(transferAccount)) {
            return null;
        }

        Map<Integer, QrisDataObject> templateMap = transferAccount.getTemplateMap();
        
        if (Objects.isNull(templateMap) || templateMap.isEmpty()) {
            return null;
        }

        return templateMap;
    }

    // ===== Reverse Domain Validation =====
    
    private boolean isReverseDomainValid(Map<Integer, QrisDataObject> accountInfo) {
        String reverseDomain = getFieldValue(accountInfo, REVERSE_DOMAIN_TAG);
        
        if (reverseDomain == null) {
            return false;
        }

        return !reverseDomain.isEmpty() && reverseDomain.length() <= REVERSE_DOMAIN_MAX_LENGTH;
    }

    // ===== Customer PAN Validation =====
    
    private boolean isCustomerPanValid(Map<Integer, QrisDataObject> accountInfo) {
        String pan = getFieldValue(accountInfo, CUSTOMER_PAN_TAG);
        
        if (pan == null) {
            return false;
        }

        return isPanFormatValid(pan) && isPanNnsValid(pan);
    }

    private boolean isPanFormatValid(String pan) {
        if (pan.isEmpty() || !pan.matches(NUMERIC_PATTERN)) {
            return false;
        }

        int length = pan.length();
        return length >= PAN_MIN_LENGTH && length <= PAN_MAX_LENGTH;
    }

    private boolean isPanNnsValid(String pan) {
        try {
            String nnsCode = pan.substring(NNS_START_LENGTH, NNS_END_LENGTH);
            int nns = Integer.parseInt(nnsCode);
            Pjsp.valueOf(nns); // Throws IllegalArgumentException if invalid
            return true;
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    // ===== Beneficiary ID Validation =====
    
    private boolean isBeneficiaryIdValid(Map<Integer, QrisDataObject> accountInfo) {
        String beneficiaryId = getFieldValue(accountInfo, BENEFICIARY_ID_TAG);
        
        if (beneficiaryId == null) {
            return false;
        }

        return !beneficiaryId.isEmpty() && beneficiaryId.length() <= BENEFICIARY_ID_MAX_LENGTH;
    }

    // ===== BIC Validation (Optional) =====
    
    private boolean isBicValid(Map<Integer, QrisDataObject> accountInfo) {
        String bic = getFieldValue(accountInfo, BIC_TAG);
        
        // BIC optional, jika tidak ada return true
        if (bic == null) {
            return true;
        }

        int length = bic.length();
        return length >= BIC_MIN_LENGTH && length <= BIC_MAX_LENGTH;
    }

    // ===== Helper Methods =====
    
    private String getFieldValue(Map<Integer, QrisDataObject> accountInfo, int tag) {
        QrisDataObject field = accountInfo.get(tag);
        
        if (Objects.isNull(field) || Objects.isNull(field.getValue())) {
            return null;
        }

        return field.getValue().trim();
    }
}
