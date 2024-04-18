package com.astrapay.qris.cpm.validations;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.CpmMandatoryFieldValidator;
import com.astrapay.qris.cpm.validation.constraints.CpmMandatoryField;
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
public class CpmMandatoryFieldValidatorTest {

    @InjectMocks
    private CpmMandatoryFieldValidator cpmMandatoryFieldValidator;

    @Mock
    private CpmMandatoryField cpmMandatoryField;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        when(cpmMandatoryField.id()).thenReturn("50");

        cpmMandatoryFieldValidator.initialize(cpmMandatoryField);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("50", new QrisDataObject("50", "07", "5152495343504D"));

        assertTrue(cpmMandatoryFieldValidator.isValid(value, context));
    }

    @Test
    public void testIsNotValid() {
        when(cpmMandatoryField.id()).thenReturn("50");

        cpmMandatoryFieldValidator.initialize(cpmMandatoryField);

        Map<String, QrisDataObject> value = new HashMap<>();

        assertFalse(cpmMandatoryFieldValidator.isValid(value, context));
    }
}
