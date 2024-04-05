package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.MerchantAccountInformationPanIsNumberValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MerchantAccountInformationPanIsNumberValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValid(){
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(qrisDataObject.getIntId()).thenReturn(26);
        Map map = mock(Map.class);
        when(qrisDataObject.getTemplateMap()).thenReturn(map);
        QrisDataObject panObject = mock(QrisDataObject.class);
        when(map.get(1)).thenReturn(panObject);
        when(panObject.getValue()).thenReturn("error");
        MerchantAccountInformationPanIsNumberValidator merchantAccountInformationPanIsNumberValidator = new MerchantAccountInformationPanIsNumberValidator();
        Assertions.assertFalse(merchantAccountInformationPanIsNumberValidator.isValid(qrisDataObject, mock(ConstraintValidatorContext.class)));
    }
}
