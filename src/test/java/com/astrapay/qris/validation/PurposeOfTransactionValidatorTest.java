package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.validation.PurposeOfTransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test untuk {@link PurposeOfTransactionValidator}.
 * <p>
 * Test validasi Purpose of Transaction pada Additional Data Field Template (ID "62"):
 * <ul>
 *     <li>Tag 62 (Additional Data) wajib ada pada Transfer payload</li>
 *     <li>Sub-tag 08 (Purpose of Transaction) wajib ada di dalam tag 62</li>
 *     <li>Value harus salah satu dari: BOOK, DMCT, atau XBCT</li>
 *     <li>Value tidak boleh kosong atau null</li>
 * </ul>
 * </p>
 */
@DisplayName("Purpose of Transaction Validator Test")
class PurposeOfTransactionValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private PurposeOfTransactionValidator validator;

    @BeforeEach
    void setUp() {
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.validator = new PurposeOfTransactionValidator();
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
    @DisplayName("Should return false when Additional Data (ID 62) is missing")
    void testMissingAdditionalData() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "12"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Additional Data (ID 62) has no templateMap")
    void testEmptyTemplateMap() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        QrisDataObject additionalData = new QrisDataObject("62", "10", "0123456789");
        // templateMap is null
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Purpose of Transaction (tag 08) is missing")
    void testMissingPurpose() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "10", "DefaultVal"));
        // tag 08 missing
        
        QrisDataObject additionalData = new QrisDataObject("62", "14", "0010DefaultVal");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @ParameterizedTest
    @ValueSource(strings = {"BOOK", "DMCT", "XBCT"})
    @DisplayName("Should return true when Purpose is valid")
    void testValidPurpose(String purpose) {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "04", purpose));
        
        QrisDataObject additionalData = new QrisDataObject("62", "08", "0804" + purpose);
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext),
            "Purpose '" + purpose + "' should be valid");
    }

    @Test
    @DisplayName("Should return false when Purpose is invalid value")
    void testInvalidPurpose() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "04", "PYMT")); // Invalid
        
        QrisDataObject additionalData = new QrisDataObject("62", "08", "0804PYMT");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Purpose value is whitespace only")
    void testWhitespacePurposeValue() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "04", "    ")); // spaces
        
        QrisDataObject additionalData = new QrisDataObject("62", "08", "0804    ");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when Purpose contains 'BOOK' with other data")
    void testPurposeContainsBOOK() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "04", "BOOK"));
        templateMap.put(99, new QrisDataObject("99", "10", "1234567890"));
        
        QrisDataObject additionalData = new QrisDataObject("62", "22", "0804BOOK9910123456 7890");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Purpose has partial match 'BOO' (not complete)")
    void testPartialPurposeMatch() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "03", "BOO")); // Incomplete
        
        QrisDataObject additionalData = new QrisDataObject("62", "07", "0803BOO");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Purpose has typo 'DMTC'")
    void testPurposeTypo() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(8, new QrisDataObject("08", "04", "DMTC")); // Typo
        
        QrisDataObject additionalData = new QrisDataObject("62", "08", "0804DMTC");
        additionalData.setTemplateMap(templateMap);
        qrisRoot.put(62, additionalData);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }
}
