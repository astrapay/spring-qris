package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.PostalCodeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"rawtypes", "unchecked"})
class PostalCodeValidatorTest {

    @Test
    void isValid(){
        Map map = mock(Map.class);
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(map.get(58)).thenReturn(qrisDataObject);
        when(qrisDataObject.getValue()).thenReturn("ID");
        when(map.get(61)).thenReturn(null);
        PostalCodeValidator postalCodeValidator = new PostalCodeValidator();
        Assertions.assertFalse(postalCodeValidator.isValid(map, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void isValidTrue(){
        Map map = mock(Map.class);
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(map.get(58)).thenReturn(qrisDataObject);
        when(qrisDataObject.getValue()).thenReturn("EN");
        PostalCodeValidator postalCodeValidator = new PostalCodeValidator();
        Assertions.assertTrue(postalCodeValidator.isValid(map, mock(ConstraintValidatorContext.class)));
    }
}
