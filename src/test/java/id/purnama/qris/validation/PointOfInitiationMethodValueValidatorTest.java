package id.purnama.qris.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PointOfInitiationMethodValueValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValid(){
        Map map = mock(Map.class);
        when(map.containsKey(1)).thenReturn(false);
        PointOfInitiationMethodValueValidator pointOfInitiationMethodValueValidator = new PointOfInitiationMethodValueValidator();
        Assertions.assertTrue(pointOfInitiationMethodValueValidator.isValid(map, mock(ConstraintValidatorContext.class)));
    }
}
