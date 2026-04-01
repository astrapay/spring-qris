package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.AdditionalDataFieldTransferChildIsExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator untuk memastikan Additional Data Field Template (tag 62) pada QRIS Transfer
 * memiliki semua child tags yang mandatory.
 *
 * <p>Berdasarkan spesifikasi QRIS MPM Transfer, tag 62 harus memiliki:</p>
 * <ul>
 *   <li>Sub-tag 08: Purpose of Transaction (Mandatory, 4 chars)</li>
 *   <li>Sub-tag 99: Unique per generated (Mandatory, var up to 74 chars)</li>
 *   <li>Sub-tag 00: Default Value (Mandatory, 2 chars)</li>
 *   <li>Sub-tag 01: Unique Data (Mandatory, var 8-64 chars)</li>
 * </ul>
 *
 * @see AdditionalDataFieldTransferChildIsExist
 */
public class AdditionalDataFieldTransferValidator implements ConstraintValidator<AdditionalDataFieldTransferChildIsExist, QrisPayload> {

    private static final int TAG_ADDITIONAL_DATA = 62;
    private static final int SUB_TAG_PURPOSE_OF_TRANSACTION = 8;  // Tag 08
    private static final int SUB_TAG_UNIQUE_PER_GENERATED = 99;    // Tag 99
    private static final int SUB_TAG_DEFAULT_VALUE = 0;            // Tag 00
    private static final int SUB_TAG_UNIQUE_DATA = 1;              // Tag 01

    @Override
    public boolean isValid(QrisPayload value, ConstraintValidatorContext context) {
        // Jika tag 62 tidak ada, anggap valid (akan dihandle oleh validator lain)
        QrisDataObject additionalData = value.getQrisRoot().get(TAG_ADDITIONAL_DATA);
        if (Objects.isNull(additionalData)) {
            return true;
        }

        // Tag 62 ada, maka templateMap harus exist
        Map<Integer, QrisDataObject> templateMap = additionalData.getTemplateMap();
        if (Objects.isNull(templateMap)) {
            return false;
        }

        // Cek sub-tag 08: Purpose of Transaction & sub-tag 99: Unique per generated
        if (!templateMap.containsKey(SUB_TAG_PURPOSE_OF_TRANSACTION) ||
                !templateMap.containsKey(SUB_TAG_UNIQUE_PER_GENERATED)) {
            return false;
        }

        // Cek sub-tag 00 & 01 hanya jika tag 99 ada
        Map<Integer, QrisDataObject> tag99TemplateMap =
                templateMap.get(SUB_TAG_UNIQUE_PER_GENERATED).getTemplateMap();

        if (Objects.isNull(tag99TemplateMap)) {
            return false;
        }

        return tag99TemplateMap.containsKey(SUB_TAG_DEFAULT_VALUE)
                && tag99TemplateMap.containsKey(SUB_TAG_UNIQUE_DATA);
    }
}
