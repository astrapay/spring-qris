package com.astrapay.qris.cpm.validations;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.ApplicationLabelValidator;
import com.astrapay.qris.cpm.validation.constraints.ApplicationLabel;
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
public class ApplicationLabelValidatorTest {

    @InjectMocks
    private ApplicationLabelValidator applicationLabelValidator;

    @Mock
    private ApplicationLabel applicationLabel;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        when(applicationLabel.id()).thenReturn("50");
        when(applicationLabel.hexValue()).thenReturn("5152495343504D");

        applicationLabelValidator.initialize(applicationLabel);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("50", new QrisDataObject("50", "07", "5152495343504D"));

        assertTrue(applicationLabelValidator.isValid(value, context));
    }

    @Test
    public void testIsNotValid() {
        when(applicationLabel.id()).thenReturn("50");
        when(applicationLabel.hexValue()).thenReturn("5152495343504D");

        applicationLabelValidator.initialize(applicationLabel);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("50", new QrisDataObject("50", "07", "5152495343504E"));

        assertFalse(applicationLabelValidator.isValid(value, context));
    }
}
