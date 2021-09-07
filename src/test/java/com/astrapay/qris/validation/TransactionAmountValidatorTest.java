package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionAmountValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValidTest(){
        Map map = mock(Map.class);
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(map.get(any())).thenReturn(qrisDataObject);
        when(qrisDataObject.getValue()).thenReturn("10000.");
        TransactionAmountValidator transactionAmountValidator = new TransactionAmountValidator();
        Assertions.assertTrue(transactionAmountValidator.isValid(map, mock(ConstraintValidatorContext.class)));
    }
}
