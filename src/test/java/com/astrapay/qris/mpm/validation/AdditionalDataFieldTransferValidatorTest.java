package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdditionalDataFieldTransferValidatorTest {

    private AdditionalDataFieldTransferValidator validator;
    private ConstraintValidatorContext context;

    private static final int TAG_62 = 62;
    private static final int TAG_08 = 8;
    private static final int TAG_99 = 99;
    private static final int TAG_00 = 0;
    private static final int TAG_01 = 1;

    @BeforeEach
    void setUp() {
        validator = new AdditionalDataFieldTransferValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void shouldReturnTrue_whenTag62NotExist() {
        QrisPayload payload = mock(QrisPayload.class);

        Map<Integer, QrisDataObject> root = new HashMap<>();
        when(payload.getQrisRoot()).thenReturn(root);

        boolean result = validator.isValid(payload, context);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenTemplateMapNull() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> root = Map.of(TAG_62, tag62);

        when(payload.getQrisRoot()).thenReturn(root);
        when(tag62.getTemplateMap()).thenReturn(null);

        boolean result = validator.isValid(payload, context);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenSubTag08Missing() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);
        QrisDataObject tag99 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> template = Map.of(TAG_99, tag99);

        when(payload.getQrisRoot()).thenReturn(Map.of(TAG_62, tag62));
        when(tag62.getTemplateMap()).thenReturn(template);

        boolean result = validator.isValid(payload, context);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenSubTag99Missing() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);
        QrisDataObject tag08 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> template = Map.of(TAG_08, tag08);

        when(payload.getQrisRoot()).thenReturn(Map.of(TAG_62, tag62));
        when(tag62.getTemplateMap()).thenReturn(template);

        boolean result = validator.isValid(payload, context);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenTag99TemplateNull() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);
        QrisDataObject tag08 = mock(QrisDataObject.class);
        QrisDataObject tag99 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> template = new HashMap<>();
        template.put(TAG_08, tag08);
        template.put(TAG_99, tag99);

        when(payload.getQrisRoot()).thenReturn(Map.of(TAG_62, tag62));
        when(tag62.getTemplateMap()).thenReturn(template);
        when(tag99.getTemplateMap()).thenReturn(null);

        boolean result = validator.isValid(payload, context);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalse_whenSubTag00Or01Missing() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);
        QrisDataObject tag08 = mock(QrisDataObject.class);
        QrisDataObject tag99 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> template99 = Map.of(TAG_00, mock(QrisDataObject.class));

        Map<Integer, QrisDataObject> template = new HashMap<>();
        template.put(TAG_08, tag08);
        template.put(TAG_99, tag99);

        when(payload.getQrisRoot()).thenReturn(Map.of(TAG_62, tag62));
        when(tag62.getTemplateMap()).thenReturn(template);
        when(tag99.getTemplateMap()).thenReturn(template99);

        boolean result = validator.isValid(payload, context);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrue_whenAllMandatoryTagsExist() {
        QrisPayload payload = mock(QrisPayload.class);
        QrisDataObject tag62 = mock(QrisDataObject.class);
        QrisDataObject tag08 = mock(QrisDataObject.class);
        QrisDataObject tag99 = mock(QrisDataObject.class);

        Map<Integer, QrisDataObject> template99 = new HashMap<>();
        template99.put(TAG_00, mock(QrisDataObject.class));
        template99.put(TAG_01, mock(QrisDataObject.class));

        Map<Integer, QrisDataObject> template = new HashMap<>();
        template.put(TAG_08, tag08);
        template.put(TAG_99, tag99);

        when(payload.getQrisRoot()).thenReturn(Map.of(TAG_62, tag62));
        when(tag62.getTemplateMap()).thenReturn(template);
        when(tag99.getTemplateMap()).thenReturn(template99);

        boolean result = validator.isValid(payload, context);

        assertTrue(result);
    }
}