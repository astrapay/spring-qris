package com.astrapay.qris.cpm.validation;

import com.astrapay.qris.cpm.validation.constraints.Track2EquivalentData;
import com.astrapay.qris.mpm.object.QrisDataObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class Track2EquivalentDataValidator implements ConstraintValidator<Track2EquivalentData, Map<String, QrisDataObject>> {

    private String id;
    private String discretionaryData;
    private String pan;
    private String expirationDate;
    private String serviceCode;
    private String padding;
    private static final String FIELD_SEPARATOR = "D";
    private static final Integer FIRST_INDEX = 0;
    private static final Integer EVEN_NUMBER_CHECKER = 2;
    private static final Integer FIELD_SEPARATOR_LAST_INDEX = 1;
    private static final Integer EXPIRATION_DATE_LAST_INDEX = 5;
    private static final Integer SERVICE_CODE_LAST_INDEX = 8;
    //field separator length
    private static final Integer FIELD_SEPARATOR_LENGTH = 1;
    //expiration date length
    private static final Integer EXPIRATION_DATE_LENGTH = 4;
    //service code length
    private static final Integer SERVICE_CODE_LENGTH = 3;
    //pan max length
    private static final Integer PAN_MAX_LENGTH = 19;

    @Override
    public void initialize(Track2EquivalentData constraintAnnotation) {
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Map<String, QrisDataObject> value, ConstraintValidatorContext context) {
        // Check if the input field contains the hex value of tag "57"
        if(!value.containsKey(this.id)) {
            var hexString = value.get(this.id).getValue();
            hexString = hexString.replaceAll("\\s+", "");
            var fieldSeparatorIndex = hexString.indexOf(FIELD_SEPARATOR);
            if (!FIRST_INDEX.equals(hexString.length() % EVEN_NUMBER_CHECKER)){
                return false;
            }

            discretionaryData = hexString.substring(fieldSeparatorIndex + SERVICE_CODE_LAST_INDEX, hexString.length()); // Discretionary Data

            // Extract each field
            pan = hexString.substring(FIRST_INDEX, fieldSeparatorIndex); // Primary Account Number (PAN)
            String fieldSeparatorString = hexString.substring(fieldSeparatorIndex, fieldSeparatorIndex + FIELD_SEPARATOR_LAST_INDEX); // Field Separator
            expirationDate = hexString.substring(fieldSeparatorIndex + FIELD_SEPARATOR_LAST_INDEX, fieldSeparatorIndex + EXPIRATION_DATE_LAST_INDEX); // Expiration Date (YYMM)
            serviceCode = hexString.substring(fieldSeparatorIndex + EXPIRATION_DATE_LAST_INDEX, fieldSeparatorIndex + SERVICE_CODE_LAST_INDEX); // Service Code


            if (pan.length() > PAN_MAX_LENGTH) {
                return false;
            }

            return fieldSeparatorString.equals(FIELD_SEPARATOR)
                    && isValidNibbleLength(fieldSeparatorString, FIELD_SEPARATOR_LENGTH)
                    && isValidNibbleLength(expirationDate, EXPIRATION_DATE_LENGTH)
                    && isValidNibbleLength(serviceCode, SERVICE_CODE_LENGTH);
        }
        return true;
    }

    private static boolean isValidNibbleLength(String field, int expectedLength) {
        return field.length() == expectedLength;
    }
}
