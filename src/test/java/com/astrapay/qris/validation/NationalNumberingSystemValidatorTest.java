package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.NationalNumberingSystemValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class NationalNumberingSystemValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private NationalNumberingSystemValidator nationalNumberingSystemValidator;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.nationalNumberingSystemValidator = NationalNumberingSystemValidator.builder().build();
    }

    @Test
    void isValidTestThrowException(){
        QrisDataObject qrisDataObject = new QrisDataObject("27", "02", "10");
        Map<Integer, QrisDataObject> templateMap = new HashMap<>();
        templateMap.put(1, new QrisDataObject("1", "10", "ABCEFGHIJKLMNOP"));
        qrisDataObject.setTemplateMap(templateMap);
        assertFalse(this.nationalNumberingSystemValidator.isValid(qrisDataObject, constraintValidatorContext));
    }
}
