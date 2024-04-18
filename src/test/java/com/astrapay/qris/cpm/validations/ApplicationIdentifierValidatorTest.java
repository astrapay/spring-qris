package com.astrapay.qris.cpm.validations;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.ApplicationIdentifierValidator;
import com.astrapay.qris.cpm.validation.constraints.ApplicationIdentifier;
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
public class ApplicationIdentifierValidatorTest {

    @InjectMocks
    private ApplicationIdentifierValidator applicationIdentifierValidator;

    @Mock
    private ApplicationIdentifier applicationIdentifier;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        when(applicationIdentifier.id()).thenReturn("4F");
        when(applicationIdentifier.hexValue()).thenReturn("A0000006022020");

        applicationIdentifierValidator.initialize(applicationIdentifier);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("4F", new QrisDataObject("4F", "07", "A0000006022020"));

        assertTrue(applicationIdentifierValidator.isValid(value, context));
    }

    @Test
    public void testIsNotValid() {
        when(applicationIdentifier.id()).thenReturn("4F");
        when(applicationIdentifier.hexValue()).thenReturn("A0000006022020");

        applicationIdentifierValidator.initialize(applicationIdentifier);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("4F", new QrisDataObject("4F", "07", "B0000006022020"));

        assertFalse(applicationIdentifierValidator.isValid(value, context));
    }
}
