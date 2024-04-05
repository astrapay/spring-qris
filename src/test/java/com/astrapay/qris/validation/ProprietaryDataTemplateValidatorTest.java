package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.ProprietaryDataTemplateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ProprietaryDataTemplateValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;

    private ProprietaryDataTemplateValidator proprietaryDataTemplateValidator;

    private QrisDataObject qrisDataObject;

    private Map<Integer, QrisDataObject> templateMap;

    @BeforeEach
    private void before(){
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.proprietaryDataTemplateValidator = ProprietaryDataTemplateValidator.builder().build();
        this.qrisDataObject = new QrisDataObject("62", "02", "10");
        this.templateMap = new HashMap<>();
        templateMap.put(99, new QrisDataObject("62", "02", "10"));
        qrisDataObject.setTemplateMap(templateMap);
    }

    @Test
    void isValidTestThrowException(){
        assertFalse(this.proprietaryDataTemplateValidator.isValid(this.qrisDataObject, constraintValidatorContext));
    }

    @Test
    void isValidTestLengthGreater32(){
        Map<Integer, QrisDataObject> dataObjectMap = new HashMap<>();
        dataObjectMap.put(0, new QrisDataObject("99", "10", "1234567890123456789012345678901234567890"));
        this.templateMap.get(99).setTemplateMap(dataObjectMap);
        assertFalse(this.proprietaryDataTemplateValidator.isValid(this.qrisDataObject, constraintValidatorContext));
    }

    @Test
    void isValidTestLengthGreater81(){
        Map<Integer, QrisDataObject> dataObjectMap = new HashMap<>();
        dataObjectMap.put(0, new QrisDataObject("99", "10", "123456789012345678901234567890"));
        dataObjectMap.put(1, new QrisDataObject("99", "10", "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"));
        this.templateMap.get(99).setTemplateMap(dataObjectMap);
        assertFalse(this.proprietaryDataTemplateValidator.isValid(this.qrisDataObject, constraintValidatorContext));
    }
}
