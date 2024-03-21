package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.AdditionalDataFieldCharLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Tabel 3.7 Data objects for Additional Data Field Template (ID "62")
 * <table>
 *     <tr><td>Name</td><td>ID</td><td>Format</td><td>Panjang karakter</td><td>Ketersediaan</td></tr>
 *     <tr><td>Bill Number</td><td>"01"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Mobile Number</td><td>"02"</td><td>ans</td><td>var. up to "25 "</td><td>O</td></tr>
 *     <tr><td>Store Label</td><td>"03"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Loyalty Number</td><td>"04"/td><td>ans/td><td>var. up to "25"/td><td>O</td></tr>
 *     <tr><td>Reference Label</td><td>"05"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Customer Label</td><td>"06"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Terminal label</td><td>"07"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Purpose of Transaction</td><td>"08"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Additional Consumer Data Request</td><td>"09"</td><td>ans</td><td>var. up to "03"</td><td>O</td></tr>
 *     <tr><td>RFU for EMVCo</td><td>"10"-"49"</td><td>S</td><td>-</td><td>O</td></tr>
 *     <tr><td>Payment System specific templates.</td><td>"50"-"98"</td><td>S</td><td>-</td><td>O</td></tr>
 *     <tr><td>Proprietary data</td><td>"99"</td><td>ans</td><td>var. up to "95"</td><td>O</td></tr>
 * </table>
 */
public class AdditionalDataFieldCharLengthValidator implements ConstraintValidator<AdditionalDataFieldCharLength, QrisDataObject> {

    private int from;
    private int to;
    private int min;
    private int max;

    @Override
    public void initialize(AdditionalDataFieldCharLength constraintAnnotation) {
        this.from = constraintAnnotation.from();
        this.to = constraintAnnotation.to();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if (value.getIntId() == 62) {
            for(int i = from; i <= to; i++) {
                if (value.getTemplateMap().containsKey(i)) {
                    return value.getTemplateMap().get(i).getIntLength() >= this.min &&
                            value.getTemplateMap().get(i).getIntLength() <= this.max;
                }
            }
        }
        return true;
    }
}
