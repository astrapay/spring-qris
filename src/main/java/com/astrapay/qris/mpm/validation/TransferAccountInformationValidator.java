package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferAccountInformationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

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
 * @author Arthur Purnama
 * @see TransferAccountInformationValid
 */
public class TransferAccountInformationValidator implements ConstraintValidator<TransferAccountInformationValid, QrisPayload> {

    @Override
    public void initialize(TransferAccountInformationValid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (payload == null || payload.getQrisRoot() == null) {
            return true; // Let @MandatoryField handle null checks
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check if Transfer Account Information (ID 40) exists
        QrisDataObject transferAccountInfo = qrisRoot.get(40);
        if (transferAccountInfo == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information (ID 40) is missing"
            ).addConstraintViolation();
            return false;
        }

        // Check if templateMap is parsed
        Map<Integer, QrisDataObject> templateMap = transferAccountInfo.getTemplateMap();
        if (templateMap == null || templateMap.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information (ID 40) is empty or not parsed"
            ).addConstraintViolation();
            return false;
        }

        boolean isValid = true;

        // Validate sub-tag 00: Reverse Domain (Mandatory, max 32 chars)
        QrisDataObject reverseDomain = templateMap.get(0);
        if (reverseDomain == null || reverseDomain.getValue() == null || reverseDomain.getValue().trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information: Reverse Domain (tag 00) is mandatory"
            ).addConstraintViolation();
            isValid = false;
        } else if (reverseDomain.getValue().length() > 32) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information: Reverse Domain (tag 00) exceeds 32 characters"
            ).addConstraintViolation();
            isValid = false;
        }

        // Validate sub-tag 01: Customer PAN (Mandatory, numeric, 16-19 digits)
        QrisDataObject customerPan = templateMap.get(1);
        if (customerPan == null || customerPan.getValue() == null || customerPan.getValue().trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information: Customer PAN (tag 01) is mandatory"
            ).addConstraintViolation();
            isValid = false;
        } else {
            String panValue = customerPan.getValue();
            if (!panValue.matches("\\d{16,19}")) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Transfer Account Information: Customer PAN (tag 01) must be 16-19 numeric digits, found: " + panValue
                ).addConstraintViolation();
                isValid = false;
            }
        }

        // Validate sub-tag 02: Beneficiary ID (Mandatory, max 15 chars)
        QrisDataObject beneficiaryId = templateMap.get(2);
        if (beneficiaryId == null || beneficiaryId.getValue() == null || beneficiaryId.getValue().trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information: Beneficiary ID (tag 02) is mandatory"
            ).addConstraintViolation();
            isValid = false;
        } else if (beneficiaryId.getValue().length() > 15) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Transfer Account Information: Beneficiary ID (tag 02) exceeds 15 characters"
            ).addConstraintViolation();
            isValid = false;
        }

        // Validate sub-tag 04: Bank Identifier Code (Optional, 8-11 chars if present)
        QrisDataObject bic = templateMap.get(4);
        if (bic != null && bic.getValue() != null) {
            int bicLength = bic.getValue().length();
            if (bicLength < 8 || bicLength > 11) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Transfer Account Information: Bank Identifier Code (tag 04) must be 8-11 characters, found: " + bicLength
                ).addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
