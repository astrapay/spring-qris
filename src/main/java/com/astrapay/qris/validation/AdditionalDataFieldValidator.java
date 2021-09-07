package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.AdditionalDataField;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.8</b> Data objects—Additional Data Field Template (ID "62")
 * <b>4.8.1.1</b> Jika ditampilkan, minimal berisi satu data object.
 */
@Builder
@NoArgsConstructor
public class AdditionalDataFieldValidator implements ConstraintValidator<AdditionalDataField, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if(value.getIntId().equals(62)){
            return value.getTemplateMap() != null;
        }
        return true;
    }
}