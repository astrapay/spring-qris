package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.validation.TransferPointOfInitiationMethodValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test untuk {@link TransferPointOfInitiationMethodValidator}.
 * <p>
 * Test validasi Point of Initiation Method (ID "01") untuk Transfer QRIS:
 * <ul>
 *     <li>Nilai WAJIB = "12" (Dynamic QR)</li>
 *     <li>Jika nilai bukan "12", transaksi TIDAK BOLEH diteruskan</li>
 * </ul>
 * </p>
 */
@DisplayName("Transfer Point of Initiation Method Validator Test")
class TransferPointOfInitiationMethodValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private TransferPointOfInitiationMethodValidator validator;

    @BeforeEach
    void setUp() {
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.validator = new TransferPointOfInitiationMethodValidator();
    }

    @Test
    @DisplayName("Should return true when payload is null")
    void testNullPayload() {
        assertTrue(validator.isValid(null, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when qrisRoot is null")
    void testNullQrisRoot() {
        QrisTransferPayload payload = new QrisTransferPayload();
        payload.setQrisRoot(null);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when Point of Initiation Method (ID 01) is '12' (Dynamic)")
    void testValidDynamicQR() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "12"));
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Point of Initiation Method (ID 01) is '11' (Static)")
    void testInvalidStaticQR() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "11"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Point of Initiation Method (ID 01) has invalid value")
    void testInvalidValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "99"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Point of Initiation Method (ID 01) is missing")
    void testMissingPointOfInitiation() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        // ID 01 not present
        qrisRoot.put(40, new QrisDataObject("40", "10", "0123456789"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Point of Initiation Method (ID 01) value is empty")
    void testEmptyValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "00", ""));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }
}
