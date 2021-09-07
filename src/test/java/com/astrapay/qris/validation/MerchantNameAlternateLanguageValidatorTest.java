package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MerchantNameAlternateLanguageValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValid(){
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(qrisDataObject.getIntId()).thenReturn(64);
        Map map = mock(Map.class);
        when(qrisDataObject.getTemplateMap()).thenReturn(map);
        when(map.get(1)).thenReturn(null);
        MerchantNameAlternateLanguageValidator merchantNameAlternateLanguageValidator = new MerchantNameAlternateLanguageValidator();
        Assertions.assertFalse(merchantNameAlternateLanguageValidator.isValid(qrisDataObject, mock(ConstraintValidatorContext.class)));
    }
}
