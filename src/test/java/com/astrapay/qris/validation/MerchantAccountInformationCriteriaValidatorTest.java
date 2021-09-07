package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MerchantAccountInformationCriteriaValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValid(){
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        MerchantAccountInformationCriteriaValidator merchantAccountInformationCriteriaValidator = new MerchantAccountInformationCriteriaValidator();
        ConstraintValidatorContext constraintValidatorContext = mock(ConstraintValidatorContext.class);
        Map map = mock(Map.class);
        when(qrisDataObject.getIntId()).thenReturn(26);
        when(qrisDataObject.getTemplateMap()).thenReturn(map);
        QrisDataObject criteriaObject = mock(QrisDataObject.class);
        when(map.get(3)).thenReturn(criteriaObject);
        when(criteriaObject.getValue()).thenReturn("");
        assertFalse(merchantAccountInformationCriteriaValidator.isValid(qrisDataObject, constraintValidatorContext));

    }
}
