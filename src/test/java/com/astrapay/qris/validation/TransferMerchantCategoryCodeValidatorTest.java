package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.validation.TransferMerchantCategoryCodeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test untuk {@link TransferMerchantCategoryCodeValidator}.
 * <p>
 * Test validasi Merchant Category Code (ID "52") untuk Transfer QRIS:
 * <ul>
 *     <li>MCC yang diperbolehkan: <b>4829</b> (Transfer)</li>
 *     <li>Jika MCC selain 4829, transaksi TIDAK DAPAT diproses</li>
 *     <li>MCC bersifat conditional (boleh tidak ada)</li>
 * </ul>
 * </p>
 */
@DisplayName("Transfer Merchant Category Code Validator Test")
class TransferMerchantCategoryCodeValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private TransferMerchantCategoryCodeValidator validator;

    @BeforeEach
    void setUp() {
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.validator = new TransferMerchantCategoryCodeValidator();
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
    @DisplayName("Should return true when MCC (ID 52) is not present (conditional field)")
    void testMissingMCC() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        // ID 52 not present - this is valid for Transfer
        qrisRoot.put(1, new QrisDataObject("01", "02", "12"));
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when MCC (ID 52) is '4829' (Transfer)")
    void testValidTransferMCC() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "04", "4829"));
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when MCC (ID 52) is not '4829'")
    void testInvalidMCC() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "04", "5411")); // Grocery stores
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when MCC (ID 52) is '0000'")
    void testMCCZero() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "04", "0000"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when MCC (ID 52) value is null")
    void testNullValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "04", null));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when MCC (ID 52) value is empty")
    void testEmptyValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "00", ""));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when MCC (ID 52) has wrong length")
    void testWrongLength() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(52, new QrisDataObject("52", "03", "482")); // Only 3 digits
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }
}
