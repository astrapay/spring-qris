package com.astrapay.qris.cpm.validations;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.CpmConditionalFieldValidator;
import com.astrapay.qris.cpm.validation.constraints.CpmConditionalField;
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
public class CpmConditionalFieldValidatorTest {

    @InjectMocks
    private CpmConditionalFieldValidator cpmConditionalFieldValidator;

    @Mock
    private CpmConditionalField cpmConditionalField;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValidWhenData5AObjectIsNullAndData57ObjectIsNotNull() {
        when(cpmConditionalField.id()).thenReturn("57");
        when(cpmConditionalField.applicationPanTag()).thenReturn("5A");

        cpmConditionalFieldValidator.initialize(cpmConditionalField);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("57", new QrisDataObject("57", "13", "1234567890123456789D2512123ABCDE"));

        assertTrue(cpmConditionalFieldValidator.isValid(value, context));
    }

    @Test
    public void testIsValidWhenData5AObjectIsNotNullAndData57ObjectIsNull() {
        when(cpmConditionalField.id()).thenReturn("57");
        when(cpmConditionalField.applicationPanTag()).thenReturn("5A");

        cpmConditionalFieldValidator.initialize(cpmConditionalField);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("5A", new QrisDataObject("5A", "0A", "1234567890"));

        assertTrue(cpmConditionalFieldValidator.isValid(value, context));
    }

    @Test
    public void testIsValidWhenData5AObjectIsNotNullAndData57ObjectIsNotNull() {
        when(cpmConditionalField.id()).thenReturn("57");
        when(cpmConditionalField.applicationPanTag()).thenReturn("5A");

        cpmConditionalFieldValidator.initialize(cpmConditionalField);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("57", new QrisDataObject("57", "13", "1234567890123456789D2512123ABCDE"));
        value.put("5A", new QrisDataObject("5A", "0A", "1234567890123456789"));

        assertTrue(cpmConditionalFieldValidator.isValid(value, context));
    }

    @Test
    public void testIsNotValidWhenData5AObjectIsNotNullAndData57ObjectIsNotNull() {
        when(cpmConditionalField.id()).thenReturn("57");
        when(cpmConditionalField.applicationPanTag()).thenReturn("5A");

        cpmConditionalFieldValidator.initialize(cpmConditionalField);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("57", new QrisDataObject("57", "13", "1234567890123456789D2512123ABCyE"));
        value.put("5A", new QrisDataObject("5A", "0A", "123456789123456789"));

        assertFalse(cpmConditionalFieldValidator.isValid(value, context));
    }
}
