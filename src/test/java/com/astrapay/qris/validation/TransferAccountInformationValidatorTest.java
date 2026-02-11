package com.astrapay.qris.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.validation.TransferAccountInformationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test untuk {@link TransferAccountInformationValidator}.
 * <p>
 * Test validasi Transfer Account Information (ID "40") untuk Transfer QRIS:
 * <ul>
 *     <li>Sub-tag 00 (Reverse Domain) mandatory, tidak boleh kosong, max 32 chars</li>
 *     <li>Sub-tag 01 (Customer PAN) mandatory, numeric, 16-19 digit, NNS valid</li>
 *     <li>Sub-tag 02 (Beneficiary ID) mandatory, tidak boleh kosong, max 15 chars</li>
 *     <li>Sub-tag 04 (BIC) optional, jika ada harus 8-11 chars</li>
 * </ul>
 * </p>
 */
@DisplayName("Transfer Account Information Validator Test")
class TransferAccountInformationValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private TransferAccountInformationValidator validator;

    @BeforeEach
    void setUp() {
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
        this.validator = new TransferAccountInformationValidator();
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
    @DisplayName("Should return false when Transfer Account Information (ID 40) is missing")
    void testMissingTransferAccountInfo() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        qrisRoot.put(1, new QrisDataObject("01", "02", "12"));
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Transfer Account Information (ID 40) has no templateMap")
    void testEmptyTemplateMap() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "10", "0123456789");
        // templateMap is null or empty
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when all mandatory fields are valid")
    void testAllValidFields() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392")); // BCA NNS
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "53", "0013ID.CO.BCA.WWW0118936000141517031392021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when all fields including optional BIC are valid")
    void testAllFieldsWithBIC() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392")); // BCA NNS
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        templateMap.put(4, new QrisDataObject("04", "08", "CENAIDJA")); // BIC 8 chars
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "63", "0013ID.CO.BCA.WWW01189360001415170313920210170313927504 08CENAIDJA");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Reverse Domain (tag 00) is missing")
    void testMissingReverseDomain() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        // tag 00 missing
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "40", "0118936000141517031392021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Reverse Domain (tag 00) is empty")
    void testEmptyReverseDomain() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "00", ""));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "40", "00000118936000141517031392021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Customer PAN (tag 01) has less than 16 digits")
    void testCustomerPANTooShort() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "15", "936000141517031")); // Only 15 digits
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "50", "0013ID.CO.BCA.WWW0115936000141517031021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Customer PAN (tag 01) has more than 19 digits")
    void testCustomerPANTooLong() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "20", "93600014151703139212")); // 20 digits
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "55", "0013ID.CO.BCA.WWW012093600014151703139212021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Customer PAN (tag 01) contains non-numeric characters")
    void testCustomerPANNonNumeric() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "9360001415170ABC92")); // Contains ABC
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "53", "0013ID.CO.BCA.WWW01189360001415170ABC92021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Customer PAN has invalid NNS")
    void testCustomerPANInvalidNNS() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "999999991517031392")); // Invalid NNS
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "53", "0013ID.CO.BCA.WWW0118999999991517031392021017031392750");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Beneficiary ID (tag 02) is missing")
    void testMissingBeneficiaryID() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        // tag 02 missing
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "43", "0013ID.CO.BCA.WWW01189360001415170313920");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Beneficiary ID (tag 02) is empty")
    void testEmptyBeneficiaryID() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "00", ""));
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "45", "0013ID.CO.BCA.WWW011893600014151703139202000");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when Beneficiary ID (tag 02) exceeds 15 chars")
    void testBeneficiaryIDTooLong() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "16", "1234567890123456")); // 16 chars
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "59", "0013ID.CO.BCA.WWW0118936000141517031392021612345678901234560");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when BIC (tag 04) has less than 8 chars")
    void testBICTooShort() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        templateMap.put(4, new QrisDataObject("04", "07", "CENAIDJ")); // Only 7 chars
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "62", "0013ID.CO.BCA.WWW0118936000141517031392021017031392750407CENAIDJ");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return false when BIC (tag 04) has more than 11 chars")
    void testBICTooLong() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        templateMap.put(4, new QrisDataObject("04", "12", "CENAIDJAXXX1")); // 12 chars
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "67", "0013ID.CO.BCA.WWW01189360001415170313920210170313927504 12CENAIDJAXXX1");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertFalse(validator.isValid(payload, constraintValidatorContext));
    }

    @Test
    @DisplayName("Should return true when BIC (tag 04) has 11 chars (valid)")
    void testBIC11Chars() {
        QrisTransferPayload payload = new QrisTransferPayload();
        Map<Integer, QrisDataObject> qrisRoot = new LinkedHashMap<>();
        
        Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();
        templateMap.put(0, new QrisDataObject("00", "13", "ID.CO.BCA.WWW"));
        templateMap.put(1, new QrisDataObject("01", "18", "936000141517031392"));
        templateMap.put(2, new QrisDataObject("02", "10", "1703139275"));
        templateMap.put(4, new QrisDataObject("04", "11", "CENAIDJAXXX")); // 11 chars valid
        
        QrisDataObject transferAccountInfo = new QrisDataObject("40", "66", "0013ID.CO.BCA.WWW0118936000141517031392021017031392750411CENAIDJAXXX");
        transferAccountInfo.setTemplateMap(templateMap);
        qrisRoot.put(40, transferAccountInfo);
        payload.setQrisRoot(qrisRoot);
        
        assertTrue(validator.isValid(payload, constraintValidatorContext));
    }
}
