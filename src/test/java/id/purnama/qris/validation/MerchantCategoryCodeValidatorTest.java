package id.purnama.qris.validation;

import id.purnama.qris.object.QrisDataObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MerchantCategoryCodeValidatorTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void isValidTest(){
        Map map = mock(Map.class);
        QrisDataObject qrisDataObject = mock(QrisDataObject.class);
        when(map.get(52)).thenReturn(qrisDataObject);
        when(qrisDataObject.getValue()).thenReturn("error");
        MerchantCategoryCodeValidator merchantCategoryCodeValidator = new MerchantCategoryCodeValidator();
        Assertions.assertFalse(merchantCategoryCodeValidator.isValid(map, mock(ConstraintValidatorContext.class)));
    }
}
