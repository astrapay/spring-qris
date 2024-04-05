package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.AdditionalConsumerDataRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AdditionalConsumerDataRequestValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private AdditionalConsumerDataRequestValidator additionalConsumerDataRequestValidator;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.additionalConsumerDataRequestValidator = AdditionalConsumerDataRequestValidator.builder().build();
    }

    @Test
    void isValidTestNoTag(){
        QrisDataObject qrisDataObject = new QrisDataObject("10", "02", "10");
        assertTrue(this.additionalConsumerDataRequestValidator.isValid(qrisDataObject, constraintValidatorContext));
    }

    @Test
    void isValidTestWithTag(){
        QrisDataObject qrisDataObject = new QrisDataObject("62", "02", "10");
        Map<Integer, QrisDataObject> templateMap = new HashMap<>();
        templateMap.put(9, new QrisDataObject("10", "10", "BB"));
        qrisDataObject.setTemplateMap(templateMap);
        assertFalse(this.additionalConsumerDataRequestValidator.isValid(qrisDataObject, constraintValidatorContext));
    }
}
