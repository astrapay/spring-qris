package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.validation.TransferCurrencyCountryCodeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test untuk {@link TransferCurrencyCountryCodeValidator}.
 * <p>
 * Test validasi kesesuaian Transaction Currency (ID "53") dan Country Code (ID "58"):
 * <ul>
 *     <li>Jika Country Code = "ID", maka Transaction Currency WAJIB = "360"</li>
 *     <li>Jika kondisi ini tidak terpenuhi, transaksi TIDAK BOLEH diteruskan</li>
 * </ul>
 * </p>
 */
@DisplayName("Transfer Currency Country Code Validator Test")
class TransferCurrencyCountryCodeValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private TransferCurrencyCountryCodeValidator validator;

    @BeforeEach
    void setUp() {
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.validator = new TransferCurrencyCountryCodeValidator();
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
    @DisplayName("Should return true when Country Code is 'ID' and Currency is '360' (IDR)")
    void testValidIndonesiaCurrency() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(58, new QrisDataObject("58", "02", "ID"));
        qrisRoot.put(53, new QrisDataObject("53", "03", "360"));
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when Country Code is not 'ID'")
    void testNonIndonesiaCountry() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(58, new QrisDataObject("58", "02", "SG")); // Singapore
        qrisRoot.put(53, new QrisDataObject("53", "03", "702")); // SGD
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Country Code is 'ID' but Currency is not '360'")
    void testInvalidIndonesiaCurrency() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(58, new QrisDataObject("58", "02", "ID"));
        qrisRoot.put(53, new QrisDataObject("53", "03", "840")); // USD
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Country Code is 'ID' but Currency is missing")
    void testMissingCurrencyForIndonesia() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(58, new QrisDataObject("58", "02", "ID"));
        // ID 53 not present
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Country Code is 'ID' and Currency value is empty")
    void testEmptyCurrencyValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(58, new QrisDataObject("58", "02", "ID"));
        qrisRoot.put(53, new QrisDataObject("53", "00", ""));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when Country Code is missing")
    void testMissingCountryCode() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(53, new QrisDataObject("53", "03", "360"));
        // ID 58 not present
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when both Country Code and Currency are missing")
    void testBothMissing() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "12"));
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }
}
