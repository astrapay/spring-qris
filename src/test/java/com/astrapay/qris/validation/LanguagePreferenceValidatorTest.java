package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.LanguagePreferenceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LanguagePreferenceValidatorTest {
    private ConstraintValidatorContext constraintValidatorContext = mock(ConstraintValidatorContext.class);

    private LanguagePreferenceValidator languagePreferenceValidator;

    @BeforeEach
    private void before(){
        this.languagePreferenceValidator = LanguagePreferenceValidator.builder().build();
    }

    @Test
    void isValidTestNoTag(){
        QrisDataObject qrisDataObject = new QrisDataObject("10", "10", "10");
        assertTrue(this.languagePreferenceValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestValueNotFound(){
        QrisDataObject qrisDataObject = new QrisDataObject("64", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        when(map.get(any())).thenReturn(new QrisDataObject("00", "", "123"));
        assertFalse(this.languagePreferenceValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestValueFound(){
        QrisDataObject qrisDataObject = new QrisDataObject("64", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        when(map.get(any())).thenReturn(new QrisDataObject("00", "", "ID"));
        assertTrue(this.languagePreferenceValidator.isValid(qrisDataObject, constraintValidatorContext));
    }
}
