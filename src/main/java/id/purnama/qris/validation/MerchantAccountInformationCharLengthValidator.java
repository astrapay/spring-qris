package id.purnama.qris.validation;

import id.purnama.qris.QrisDataObject;
import id.purnama.qris.validation.constraints.MerchantAccountInformationCharLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b>4.7.5.4</b> Merchant PAN yang mengacu pada ID “26”-“45” dengan sub ID “01” menandakan merchant yang melakukan transaksi, panjang karakter dari Value tersebut mencapai 19 digit.
 */
public class MerchantAccountInformationCharLengthValidator implements ConstraintValidator<MerchantAccountInformationCharLength, QrisDataObject> {

    private int from;
    private int to;
    private int id;
    private int min;
    private int max;

    @Override
    public void initialize(MerchantAccountInformationCharLength constraintAnnotation) {
        this.from = constraintAnnotation.from();
        this.to = constraintAnnotation.to();
        this.id = constraintAnnotation.id();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if (value.getIntId() > 25 && value.getIntId() < 52) {
            return value.getTemplateMap().get(id).getIntLength() >= this.min &&
                    value.getTemplateMap().get(id).getIntLength() <= max ? true : false;
        }
        return true;
    }
}