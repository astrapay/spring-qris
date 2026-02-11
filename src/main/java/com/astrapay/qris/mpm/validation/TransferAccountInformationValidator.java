package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.QrisNationalNumberingSystem.Pjsp;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferAccountInformationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
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

    private static final int TRANSFER_ACCOUNT_INFO_ID = 40;
    private static final int REVERSE_DOMAIN_TAG = 0;
    private static final int CUSTOMER_PAN_TAG = 1;
    private static final int BENEFICIARY_ID_TAG = 2;
    private static final int BIC_TAG = 4;

    private static final int REVERSE_DOMAIN_MAX_LENGTH = 32;
    private static final int BENEFICIARY_ID_MAX_LENGTH = 15;
    private static final int INT_EIGHT = 8;
    private static final int INT_ELEVEN = 11;
    private static final int INT_ZERO = 0;
    private static final String CUSTOMER_PAN_PATTERN = "\\d{16,19}";

    @Override
    public void initialize(TransferAccountInformationValid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot())) {
            return true; // Let @MandatoryField handle null checks
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();

        // Check if Transfer Account Information (ID 40) exists
        QrisDataObject transferAccountInfo = qrisRoot.get(TRANSFER_ACCOUNT_INFO_ID);
        if (Objects.isNull(transferAccountInfo)) {
            return false;
        }

        // Check if templateMap is parsed
        Map<Integer, QrisDataObject> templateMap = transferAccountInfo.getTemplateMap();
        if (Objects.isNull(templateMap) || templateMap.isEmpty()) {
            return false;
        }

        boolean isValid = true;

        // Validate sub-tag 00: Reverse Domain (Mandatory, max 32 chars)
        QrisDataObject reverseDomain = templateMap.get(REVERSE_DOMAIN_TAG);
        if (Objects.isNull(reverseDomain) || Objects.isNull(reverseDomain.getValue()) ||
            reverseDomain.getValue().trim().isEmpty() || reverseDomain.getValue().length() > REVERSE_DOMAIN_MAX_LENGTH) {
            isValid = false;
        }

        // Validate sub-tag 01: Customer PAN (Mandatory)
        QrisDataObject customerPan = templateMap.get(CUSTOMER_PAN_TAG);
        if (Objects.isNull(customerPan) || Objects.isNull(customerPan.getValue()) || customerPan.getValue().trim().isEmpty()) {
            isValid = false;
        }

        // Validate Customer PAN format(16-19 numeric digits)
        String panValue = customerPan.getValue();
        if (!panValue.matches(CUSTOMER_PAN_PATTERN)) {
            isValid = false;
        }

        // validate sub-tag 01: check NNS if exists
        String panNns = customerPan.getValue().substring(INT_ZERO, INT_EIGHT);
        try {
            Integer nnsCode = Integer.parseInt(panNns);
            Pjsp.valueOf(nnsCode);
        } catch (IllegalArgumentException e) {
            return false;
        }

        // Validate sub-tag 02: Beneficiary ID (Mandatory, max 15 chars)
        QrisDataObject beneficiaryId = templateMap.get(BENEFICIARY_ID_TAG);
        if (Objects.isNull(beneficiaryId) || Objects.isNull(beneficiaryId.getValue()) || 
            beneficiaryId.getValue().trim().isEmpty() || beneficiaryId.getValue().length() > BENEFICIARY_ID_MAX_LENGTH) {
            isValid = false;
        }

        // Validate sub-tag 04: Bank Identifier Code (Optional, 8-11 chars if present)
        QrisDataObject bic = templateMap.get(BIC_TAG);
        if (!Objects.isNull(bic) && !Objects.isNull(bic.getValue())) {
            int bicLength = bic.getValue().length();
            if (bicLength < INT_EIGHT || bicLength > INT_ELEVEN) {
                isValid = false;
            }
        }

        return isValid;
    }
}
