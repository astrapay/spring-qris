package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.AdditionalDataFieldValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class AdditionalDataFieldValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private AdditionalDataFieldValidator additionalDataFieldValidator;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.additionalDataFieldValidator = AdditionalDataFieldValidator.builder().build();
    }

    @Test
    void isValidTestNoTag(){
        QrisDataObject qrisDataObject = new QrisDataObject("10", "10", "10");
        assertTrue(this.additionalDataFieldValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    void isValidTestTagWithoutContent(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        assertFalse(this.additionalDataFieldValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestTagWitContent(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        assertTrue(this.additionalDataFieldValidator.isValid(qrisDataObject, constraintValidatorContext));
    }
}
