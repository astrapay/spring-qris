package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

class MerchantAccountInformation2To45ExistValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private MerchantAccountInformation2To45ExistValidator merchantAccountInformation2To45ExistValidator;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.merchantAccountInformation2To45ExistValidator = MerchantAccountInformation2To45ExistValidator.builder().build();
    }

    @Test
    void isValidNotInRange(){
        Map<Integer, QrisDataObject> templateMap = new HashMap<>();
        templateMap.put(1, new QrisDataObject("1", "10", "ABCEFGHIJKLMNOP"));
        assertFalse(this.merchantAccountInformation2To45ExistValidator.isValid(templateMap, constraintValidatorContext));
    }
}
