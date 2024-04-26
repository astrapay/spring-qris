package com.astrapay.qris.cpm.validations;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.PayloadFormatIndicatorValidator;
import com.astrapay.qris.cpm.validation.constraints.PayloadFormatIndicator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValueFormatIndicatorValidatorTest {

    @InjectMocks
    private PayloadFormatIndicatorValidator valueFormatIndicatorValidator;

    @Mock
    private PayloadFormatIndicator valueFormatIndicator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        when(valueFormatIndicator.id()).thenReturn("85");
        when(valueFormatIndicator.hexValue()).thenReturn("4350563031");

        valueFormatIndicatorValidator.initialize(valueFormatIndicator);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("85", new QrisDataObject("85", "05", "4350563031"));

        assertTrue(valueFormatIndicatorValidator.isValid(value, context));
    }

    @Test
    public void testIsNotValid() {
        when(valueFormatIndicator.id()).thenReturn("85");
        when(valueFormatIndicator.hexValue()).thenReturn("4350563031");

        valueFormatIndicatorValidator.initialize(valueFormatIndicator);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("85", new QrisDataObject("85", "05", "4350563032"));

        assertFalse(valueFormatIndicatorValidator.isValid(value, context));
    }
}
