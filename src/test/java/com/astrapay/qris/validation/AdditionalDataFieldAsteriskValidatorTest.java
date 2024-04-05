package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.AdditionalDataFieldAsteriskValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdditionalDataFieldAsteriskValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private AdditionalDataFieldAsteriskValidator additionalDataFieldAsteriskValidator;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.additionalDataFieldAsteriskValidator = AdditionalDataFieldAsteriskValidator.builder().build();
    }

    @Test
    void isValidTestNoTag(){
        QrisDataObject qrisDataObject = new QrisDataObject("10", "10", "10");
        assertTrue(this.additionalDataFieldAsteriskValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    void isValidTestTagWithoutContent(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        assertThrows(NullPointerException.class, () -> this.additionalDataFieldAsteriskValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestContentEmpty(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        final OngoingStubbing<Boolean> booleanOngoingStubbing = when(map.containsKey(any())).thenReturn(true);
        when(map.get(any())).thenReturn(new QrisDataObject("", "", ""));
        assertFalse(this.additionalDataFieldAsteriskValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestContentNotAsteriks(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        final OngoingStubbing<Boolean> booleanOngoingStubbing = when(map.containsKey(any())).thenReturn(true);
        final OngoingStubbing<Object> objectOngoingStubbing = when(map.get(any())).thenReturn(new QrisDataObject("", "", "123"));
        assertTrue(this.additionalDataFieldAsteriskValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    @SuppressWarnings("unchecked")
    void isValidTestContentAsteriks(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "10", "10");
        var map = mock(Map.class);
        qrisDataObject.setTemplateMap(map);
        final OngoingStubbing<Boolean> booleanOngoingStubbing = when(map.containsKey(any())).thenReturn(true);
        final OngoingStubbing<Object> objectOngoingStubbing = when(map.get(any())).thenReturn(new QrisDataObject("", "", "***"));
        assertTrue(this.additionalDataFieldAsteriskValidator.isValid(qrisDataObject, constraintValidatorContext));
    }
}
