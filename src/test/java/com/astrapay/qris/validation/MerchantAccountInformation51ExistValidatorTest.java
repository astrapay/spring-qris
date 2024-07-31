package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.MerchantAccountInformation51ExistValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class MerchantAccountInformation51ExistValidatorTest {

    private MerchantAccountInformation51ExistValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new MerchantAccountInformation51ExistValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void testIsValid_WhenPointOfInitiationIs11AndKey51Exists_ShouldReturnTrue() {
        Map<Integer, QrisDataObject> value = new HashMap<>();
        value.put(1, new QrisDataObject("2","2","11"));
        value.put(51, new QrisDataObject("2","2","SomeData"));

        assertTrue(validator.isValid(value, context));
    }

    @Test
    void testIsValid_WhenPointOfInitiationIs11AndKey51DoesNotExist_ShouldReturnFalse() {
        Map<Integer, QrisDataObject> value = new HashMap<>();
        value.put(1, new QrisDataObject("2","2","11"));

        assertFalse(validator.isValid(value, context));
    }

    @Test
    void testIsValid_WhenPointOfInitiationIsNot11_ShouldReturnTrue() {
        Map<Integer, QrisDataObject> value = new HashMap<>();
        value.put(1, new QrisDataObject("2","2","12"));

        assertTrue(validator.isValid(value, context));
    }
}
