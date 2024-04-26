package com.astrapay.qris.cpm.validations;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.Track2EquivalentDataValidator;
import com.astrapay.qris.cpm.validation.constraints.Track2EquivalentData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Track2EquivalentDataValidatorTest {

    @InjectMocks
    private Track2EquivalentDataValidator track2EquivalentDataValidator;

    @Mock
    private Track2EquivalentData track2EquivalentData;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    public void testIsValid() {
        when(track2EquivalentData.id()).thenReturn("57");

        track2EquivalentDataValidator.initialize(track2EquivalentData);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("57", new QrisDataObject("57", "13", "1234567890123456789D2512123ABCDE"));

        assertTrue(track2EquivalentDataValidator.isValid(value, context));
    }
}
