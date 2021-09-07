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

class TransactionCurrencyValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private TransactionCurrencyValidator transactionCurrencyValidator;

    private Map<Integer, QrisDataObject> templateMap;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.transactionCurrencyValidator = TransactionCurrencyValidator.builder().build();
        this.templateMap = new HashMap<>();
    }

    @Test
    void isValidTestNoCurrency(){
        templateMap.put(53, new QrisDataObject("53", "02", "BB"));
        assertFalse(this.transactionCurrencyValidator.isValid(templateMap, constraintValidatorContext));
    }

    @Test
    void isValidTestFoundCurrency(){
        templateMap.put(53, new QrisDataObject("53", "03", "380"));
        templateMap.put(58, new QrisDataObject("58", "02", "DE"));
        assertTrue(this.transactionCurrencyValidator.isValid(templateMap, constraintValidatorContext));
    }

    @Test
    void isValidTestIdNotFound(){
        templateMap.put(53, new QrisDataObject("53", "03", "380"));
        templateMap.put(58, new QrisDataObject("58", "02", "ID"));
        assertFalse(this.transactionCurrencyValidator.isValid(templateMap, constraintValidatorContext));
    }

    @Test
    void isValidTestIdFound(){
        templateMap.put(53, new QrisDataObject("53", "03", "360"));
        templateMap.put(58, new QrisDataObject("58", "02", "ID"));

        assertTrue(this.transactionCurrencyValidator.isValid(templateMap, constraintValidatorContext));
    }
}
