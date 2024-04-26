package com.astrapay.qris.cpm.validations;

import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.QrisCpmEncoder;
import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.cpm.validation.HexLengthValidator;
import com.astrapay.qris.cpm.validation.constraints.HexLength;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HexLengthValidatorTest {

    @InjectMocks
    private HexLengthValidator hexLengthValidator;

    @Mock
    private HexLength hexLength;

    @Mock
    private ConstraintValidatorContext context;

    @Spy
    private QrisHexConverter qrisHexConverter;

    @Spy
    private QrisCpmEncoder qrisCpmEncoder;

    @Test
    public void testIsValid() {
        when(hexLength.id()).thenReturn("85");
        when(hexLength.min()).thenReturn(5);
        when(hexLength.max()).thenReturn(5);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("85", new QrisDataObject("85", "05", "4350563031"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag4FIsValid() {
        when(hexLength.id()).thenReturn("4F");
        when(hexLength.min()).thenReturn(7);
        when(hexLength.max()).thenReturn(7);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("4F", new QrisDataObject("4F", "07", "A0000006022020"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag50IsValid() {
        when(hexLength.id()).thenReturn("50");
        when(hexLength.min()).thenReturn(7);
        when(hexLength.max()).thenReturn(7);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("50", new QrisDataObject("50", "07", "5152495343504D"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag5AIsValid() {
        when(hexLength.id()).thenReturn("5A");
        when(hexLength.min()).thenReturn(10);
        when(hexLength.max()).thenReturn(10);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("5A", new QrisDataObject("5A", "0A", "9360123411234567899F"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag5F20IsValid() {
        when(hexLength.id()).thenReturn("5F20");
        when(hexLength.min()).thenReturn(11);
        when(hexLength.max()).thenReturn(11);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("5F20", new QrisDataObject("5F20", "0B", "52696B692044657269616E"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag5F2DIsValid() {
        when(hexLength.id()).thenReturn("5F2D");
        when(hexLength.min()).thenReturn(4);
        when(hexLength.max()).thenReturn(4);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("5F2D", new QrisDataObject("5F2D", "04", "6964656E"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag5F50IsValid() {
        when(hexLength.id()).thenReturn("5F50");
        when(hexLength.min()).thenReturn(23);
        when(hexLength.max()).thenReturn(23);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("5F50", new QrisDataObject("5F50", "17", "72696B692E64657269616E407172697363706D2E636F6D"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F25IsValid() {
        when(hexLength.id()).thenReturn("9F25");
        when(hexLength.min()).thenReturn(2);
        when(hexLength.max()).thenReturn(2);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F25", new QrisDataObject("9F25", "02", "7899"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag63IsValid() {
        when(hexLength.id()).thenReturn("63");
        when(hexLength.min()).thenReturn(63);
        when(hexLength.max()).thenReturn(63);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("63", new QrisDataObject("63", "3F", "9F743C313233343536373839303132333435363738393031323334353637383930313233343536373839303132333435363738393031323334353637383930"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F74IsValid() {
        when(hexLength.id()).thenReturn("9F74");
        when(hexLength.min()).thenReturn(60);
        when(hexLength.max()).thenReturn(60);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F74", new QrisDataObject("9F74", "3C", "313233343536373839303132333435363738393031323334353637383930313233343536373839303132333435363738393031323334353637383930"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag61IsValid() {
        when(hexLength.id()).thenReturn("61");
        when(hexLength.min()).thenReturn(0);
        when(hexLength.max()).thenReturn(512);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("61", new QrisDataObject("61", "8193", "4F07A000000602202050075152495343504D5A0A9360123411234567899F5F200B52696B692044657269616E5F2D046964656E5F501772696B692E64657269616E407172697363706D2E636F6D9F25027899633F9F743C313233343536373839303132333435363738393031323334353637383930313233343536373839303132333435363738393031323334353637383930"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }


    @Test
    public void testIsNotValid() {
        when(hexLength.id()).thenReturn("85");
        when(hexLength.min()).thenReturn(5);
        when(hexLength.max()).thenReturn(5);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("85", new QrisDataObject("85", "06", "435056303132"));

        assertFalse(hexLengthValidator.isValid(value, context));
    }


    @Test
    public void testTag9F26IsValid() {
        when(hexLength.id()).thenReturn("9F26");
        when(hexLength.min()).thenReturn(8);
        when(hexLength.max()).thenReturn(8);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F26", new QrisDataObject("9F26", "08", "0123456789ABCDEF"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F27IsValid() {
        when(hexLength.id()).thenReturn("9F27");
        when(hexLength.min()).thenReturn(1);
        when(hexLength.max()).thenReturn(1);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F27", new QrisDataObject("9F27", "01", "80"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F10IsValid() {
        when(hexLength.id()).thenReturn("9F10");
        when(hexLength.min()).thenReturn(8);
        when(hexLength.max()).thenReturn(32);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F10", new QrisDataObject("9F10", "08", "0123456789ABCDEF0123456789ABCDEF"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F36IsValid() {
        when(hexLength.id()).thenReturn("9F36");
        when(hexLength.min()).thenReturn(2);
        when(hexLength.max()).thenReturn(2);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F36", new QrisDataObject("9F36", "02", "0123"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag82IsValid() {
        when(hexLength.id()).thenReturn("82");
        when(hexLength.min()).thenReturn(2);
        when(hexLength.max()).thenReturn(2);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("82", new QrisDataObject("82", "02", "0120"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag9F37IsValid() {
        when(hexLength.id()).thenReturn("9F37");
        when(hexLength.min()).thenReturn(4);
        when(hexLength.max()).thenReturn(4);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("9F37", new QrisDataObject("9F37", "04", "01234567"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }

    @Test
    public void testTag57IsValid() {
        when(hexLength.id()).thenReturn("57");
        when(hexLength.min()).thenReturn(0);
        when(hexLength.max()).thenReturn(19);

        hexLengthValidator.initialize(hexLength);

        Map<String, QrisDataObject> value = new HashMap<>();
        value.put("57", new QrisDataObject("57", "13", "1234567890123456789D2512123ABCDE"));

        assertTrue(hexLengthValidator.isValid(value, context));
    }
}
