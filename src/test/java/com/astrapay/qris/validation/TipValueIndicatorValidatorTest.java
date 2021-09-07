package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class TipValueIndicatorValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private TipValueIndicatorValidator tipValueIndicatorValidator;

    private Map<Integer, QrisDataObject> value;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.tipValueIndicatorValidator = TipValueIndicatorValidator.builder().build();
        this.value = new HashMap<>();
    }

    @Test
    void isValidTest03True(){
        this.value.put(55, new QrisDataObject("55", "02", "03"));
        this.value.put(57, new QrisDataObject("57", "02", "03"));
        assertTrue(this.tipValueIndicatorValidator.isValid(this.value, constraintValidatorContext));
    }

    @Test
    void isValidTest03False(){
        this.value.put(55, new QrisDataObject("55", "02", "03"));
        assertFalse(this.tipValueIndicatorValidator.isValid(this.value, constraintValidatorContext));
    }

    @Test
    void isValidTestDefault(){
        this.value.put(55, new QrisDataObject("55", "02", "04"));
        assertFalse(this.tipValueIndicatorValidator.isValid(this.value, constraintValidatorContext));
    }
}
