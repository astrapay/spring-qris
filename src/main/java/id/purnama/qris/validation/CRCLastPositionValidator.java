package id.purnama.qris.validation;

import id.purnama.qris.object.QrisDataObject;
import id.purnama.qris.validation.constraints.CRCLastPosition;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <b>3.2 Organisasi Data</b>
 * CRC (ID "63") adalah data object terakhir di bawah root.<br/>
 * <b>4.6.1.2</b> CRC (ID "63") harus menjadi urutan terakhir data object dalam QR Code.
 */
public class CRCLastPositionValidator implements ConstraintValidator<CRCLastPosition, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        List<QrisDataObject> list = new LinkedList<>(value.values());
        return "63".equals(list.get(list.size()-1).getId());
    }
}
