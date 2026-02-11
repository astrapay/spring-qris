package com.astrapay.qris.mpm;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisMpmPaymentPayload;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.object.QrisType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test untuk {@link QrisParser} dengan fokus pada MPM Payment QRIS.
 * <p>
 * Test ini memvalidasi:
 * <ul>
 *     <li>Type detection untuk MPM Payment QR</li>
 *     <li>Parsing struktur Merchant Account Information (ID 26-45)</li>
 *     <li>Parsing Merchant Domestic Repository (ID 51)</li>
 *     <li>Validasi MPM Payment standard</li>
 * </ul>
 * </p>
 */
@DisplayName("QRIS Parser MPM Payment Test")
class QrisParserMpmPaymentTest {

    private QrisParser parser;
    private Validator validator;
    
    /**
     * Real MPM Payment QR with characteristics:
     * - ID 26: Merchant Account Information (AstraPay)
     * - ID 51: Merchant Domestic Repository (QRIS)
     * - MCC (ID 52) = "5812" (Restaurant)
     * - Transaction Currency (ID 53) = "360" (IDR)
     * - Country Code (ID 58) = "ID"
     * - Merchant Name (ID 59) = "AHASS TDM Natar"
     * - Merchant City (ID 60) = "Jakarta"
     * - Postal Code (ID 61) = "44335"
     * - Additional Data (ID 62) with Default Value (tag 07) = "AP01"
     * - CRC (ID 63) = "2702"
     */
    private static final String MPM_PAYMENT_QR = 
        "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000024002092100002400303UBE51440014ID.CO.QRIS.WWW0215ID20210662463920303UBE5204581253033605802ID5915AHASS TDM Natar6007Jakarta61054433562080704AP0163042702";
    
    @BeforeEach
    void setUp() {
        parser = new QrisParser();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    @DisplayName("Should detect QR type as MPM_PAYMENT")
    void testDetectMpmPaymentType() {
        // When
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        
        // Then
        assertNotNull(payload, "Payload should not be null");
        assertEquals(QrisType.MPM_PAYMENT, payload.getQrisType(), "QRIS type should be MPM_PAYMENT");
        assertInstanceOf(QrisMpmPaymentPayload.class, payload, "Should be QrisMpmPaymentPayload instance");
    }
    
    @Test
    @DisplayName("Should parse MPM Payment QR text correctly")
    void testParseMpmPaymentQR() {
        // When
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        
        // Then
        assertNotNull(payload, "Payload should not be null");
        assertEquals(MPM_PAYMENT_QR, payload.getPayload(), "Payload string should match");
        assertNotNull(payload.getQrisRoot(), "QrisRoot should not be null");
        assertFalse(payload.getQrisRoot().isEmpty(), "QrisRoot should not be empty");
    }
    
    @Test
    @DisplayName("Should have all mandatory root fields")
    void testMandatoryRootFields() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Verify mandatory fields exist
        assertTrue(qrisRoot.containsKey(0), "Should have Payload Format Indicator (ID 00)");
        assertTrue(qrisRoot.containsKey(1), "Should have Point of Initiation Method (ID 01)");
        assertTrue(qrisRoot.containsKey(52), "Should have Merchant Category Code (ID 52)");
        assertTrue(qrisRoot.containsKey(53), "Should have Transaction Currency (ID 53)");
        assertTrue(qrisRoot.containsKey(58), "Should have Country Code (ID 58)");
        assertTrue(qrisRoot.containsKey(59), "Should have Merchant Name (ID 59)");
        assertTrue(qrisRoot.containsKey(60), "Should have Merchant City (ID 60)");
        assertTrue(qrisRoot.containsKey(63), "Should have CRC (ID 63)");
    }
    
    @Test
    @DisplayName("Should have Payload Format Indicator = '01'")
    void testPayloadFormatIndicator() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject formatIndicator = qrisRoot.get(0);
        assertNotNull(formatIndicator, "Payload Format Indicator should exist");
        assertEquals("01", formatIndicator.getValue(), "Payload Format Indicator must be '01'");
    }
    
    @Test
    @DisplayName("Should have Point of Initiation Method = '11' (Static)")
    void testPointOfInitiationMethod() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject pointOfInitiation = qrisRoot.get(1);
        assertNotNull(pointOfInitiation, "Point of Initiation Method should exist");
        assertEquals("11", pointOfInitiation.getValue(), "Point of Initiation Method is '11' (Static QR)");
    }
    
    @Test
    @DisplayName("Should parse Merchant Account Information (ID 26)")
    void testMerchantAccountInformationId26() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check ID 26 (AstraPay Merchant Account)
        assertTrue(qrisRoot.containsKey(26), "Should have Merchant Account Information (ID 26)");
        QrisDataObject merchantAccount = qrisRoot.get(26);
        assertNotNull(merchantAccount.getTemplateMap(), "Merchant Account template should be parsed");
        
        Map<Integer, QrisDataObject> templateMap = merchantAccount.getTemplateMap();
        
        // Check sub-tag 00: Globally Unique Identifier
        assertTrue(templateMap.containsKey(0), "Should have Globally Unique Identifier (tag 00)");
        assertEquals("ID.CO.ASTRAPAY.WWW", templateMap.get(0).getValue(), 
            "Globally Unique Identifier should be 'ID.CO.ASTRAPAY.WWW'");
        
        // Check sub-tag 01: Merchant PAN
        assertTrue(templateMap.containsKey(1), "Should have Merchant PAN (tag 01)");
        assertEquals("936008223210000240", templateMap.get(1).getValue(), 
            "Merchant PAN should match");
        
        // Check sub-tag 02: Merchant ID
        assertTrue(templateMap.containsKey(2), "Should have Merchant ID (tag 02)");
        assertEquals("210000240", templateMap.get(2).getValue(), 
            "Merchant ID should match");
        
        // Check sub-tag 03: Merchant Criteria
        assertTrue(templateMap.containsKey(3), "Should have Merchant Criteria (tag 03)");
        assertEquals("UBE", templateMap.get(3).getValue(), 
            "Merchant Criteria should be 'UBE'");
    }
    
    @Test
    @DisplayName("Should parse Merchant Domestic Repository (ID 51)")
    void testMerchantDomesticRepository() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check ID 51 (QRIS Merchant Domestic)
        assertTrue(qrisRoot.containsKey(51), "Should have Merchant Domestic Repository (ID 51)");
        QrisDataObject domesticRepo = qrisRoot.get(51);
        assertNotNull(domesticRepo.getTemplateMap(), "Domestic Repository template should be parsed");
        
        Map<Integer, QrisDataObject> templateMap = domesticRepo.getTemplateMap();
        
        // Check sub-tag 00: Globally Unique Identifier
        assertTrue(templateMap.containsKey(0), "Should have Globally Unique Identifier (tag 00)");
        assertEquals("ID.CO.QRIS.WWW", templateMap.get(0).getValue(), 
            "Globally Unique Identifier should be 'ID.CO.QRIS.WWW'");
        
        // Check sub-tag 02: Merchant PAN
        assertTrue(templateMap.containsKey(2), "Should have Merchant PAN (tag 02)");
        assertEquals("ID2021066246392", templateMap.get(2).getValue(), 
            "Merchant PAN should match");
        
        // Check sub-tag 03: Merchant Criteria
        assertTrue(templateMap.containsKey(3), "Should have Merchant Criteria (tag 03)");
        assertEquals("UBE", templateMap.get(3).getValue(), 
            "Merchant Criteria should be 'UBE'");
    }
    
    @Test
    @DisplayName("Should have Merchant Category Code = '5812' (Restaurant)")
    void testMerchantCategoryCode() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject mcc = qrisRoot.get(52);
        assertNotNull(mcc, "Merchant Category Code should exist");
        assertEquals("5812", mcc.getValue(), "MCC should be '5812' (Restaurant)");
        assertEquals(4, mcc.getValue().length(), "MCC must be 4 chars");
    }
    
    @Test
    @DisplayName("Should have Transaction Currency = '360' (IDR)")
    void testTransactionCurrency() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject currency = qrisRoot.get(53);
        assertNotNull(currency, "Transaction Currency should exist");
        assertEquals("360", currency.getValue(), "Currency must be '360' (IDR)");
    }
    
    @Test
    @DisplayName("Should have Country Code = 'ID' (Indonesia)")
    void testCountryCode() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject countryCode = qrisRoot.get(58);
        assertNotNull(countryCode, "Country Code should exist");
        assertEquals("ID", countryCode.getValue(), "Country Code should be 'ID' (Indonesia)");
    }
    
    @Test
    @DisplayName("Should have Merchant Name = 'AHASS TDM Natar'")
    void testMerchantName() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject merchantName = qrisRoot.get(59);
        assertNotNull(merchantName, "Merchant Name should exist");
        assertEquals("AHASS TDM Natar", merchantName.getValue(), "Merchant Name should match");
        assertTrue(merchantName.getValue().length() <= 25, "Merchant Name max 25 chars");
    }
    
    @Test
    @DisplayName("Should have Merchant City = 'Jakarta'")
    void testMerchantCity() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject merchantCity = qrisRoot.get(60);
        assertNotNull(merchantCity, "Merchant City should exist");
        assertEquals("Jakarta", merchantCity.getValue(), "Merchant City should be 'Jakarta'");
        assertTrue(merchantCity.getValue().length() <= 15, "Merchant City max 15 chars");
    }
    
    @Test
    @DisplayName("Should have Postal Code = '44335'")
    void testPostalCode() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject postalCode = qrisRoot.get(61);
        assertNotNull(postalCode, "Postal Code should exist for Country Code 'ID'");
        assertEquals("44335", postalCode.getValue(), "Postal Code should be '44335'");
        assertTrue(postalCode.getValue().length() <= 10, "Postal Code max 10 chars");
    }
    
    @Test
    @DisplayName("Should parse Additional Data (ID 62)")
    void testAdditionalData() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject additionalData = qrisRoot.get(62);
        assertNotNull(additionalData, "Additional Data should exist");
        assertNotNull(additionalData.getTemplateMap(), "Additional Data template should be parsed");
        
        Map<Integer, QrisDataObject> templateMap = additionalData.getTemplateMap();
        
        // Check tag 07: Default Value (optional)
        if (templateMap.containsKey(7)) {
            QrisDataObject defaultValue = templateMap.get(7);
            assertEquals("AP01", defaultValue.getValue(), "Default Value should be 'AP01'");
        }
    }
    
    @Test
    @DisplayName("Should have CRC checksum = '2702'")
    void testCRCChecksum() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject crc = qrisRoot.get(63);
        assertNotNull(crc, "CRC should exist");
        assertEquals("2702", crc.getValue(), "CRC should be '2702'");
        assertEquals(4, crc.getValue().length(), "CRC must be 4 chars");
    }
    
    @Test
    @DisplayName("Should NOT have Transfer-specific fields")
    void testNoTransferFields() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Transfer Account Information (ID 40) should NOT exist for MPM Payment
        assertFalse(qrisRoot.containsKey(40), 
            "Should NOT have Transfer Account Information (ID 40) for MPM Payment");
        
        // Additional Data should NOT have Purpose of Transaction (tag 08)
        if (qrisRoot.containsKey(62)) {
            QrisDataObject additionalData = qrisRoot.get(62);
            if (additionalData.getTemplateMap() != null) {
                assertFalse(additionalData.getTemplateMap().containsKey(8), 
                    "Should NOT have Purpose of Transaction (tag 08) for MPM Payment");
            }
        }
    }
    
    @Test
    @DisplayName("Should have at least one Merchant Account Information (ID 26-45)")
    void testHasMerchantAccountInformation() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check if any Merchant Account Information exists (ID 26-45)
        boolean hasMerchantAccount = false;
        for (int i = 26; i <= 45; i++) {
            if (qrisRoot.containsKey(i)) {
                hasMerchantAccount = true;
                break;
            }
        }
        
        assertTrue(hasMerchantAccount, 
            "Should have at least one Merchant Account Information (ID 26-45)");
    }
    
    @Test
    @DisplayName("Should pass all MPM Payment validations")
    void testMpmPaymentValidations() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        
        Set<ConstraintViolation<QrisPayload>> violations = validator.validate(payload);
        
        // Print violations for debugging
        if (!violations.isEmpty()) {
            System.out.println("Validation violations found:");
            for (ConstraintViolation<QrisPayload> violation : violations) {
                System.out.println("  - " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
        }
        
        assertTrue(violations.isEmpty(), 
            "Valid MPM Payment QR should have no constraint violations. Found: " + violations.size());
    }
    
    @Test
    @DisplayName("Should have exactly same QR text after parsing")
    void testQRTextIntegrity() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        
        assertEquals(MPM_PAYMENT_QR, payload.getPayload(), "Payload string should match exactly");
        assertEquals(MPM_PAYMENT_QR.length(), payload.getPayload().length(), 
            "Payload length should match");
    }
    
    @Test
    @DisplayName("Should parse QR with correct data object count")
    void testDataObjectCount() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Expected tags: 00, 01, 26, 51, 52, 53, 58, 59, 60, 61, 62, 63
        assertTrue(qrisRoot.size() >= 12, "Should have at least 12 root data objects");
        
        // Verify key tags exist
        assertTrue(qrisRoot.containsKey(0));  // Payload Format Indicator
        assertTrue(qrisRoot.containsKey(1));  // Point of Initiation
        assertTrue(qrisRoot.containsKey(26)); // Merchant Account Info
        assertTrue(qrisRoot.containsKey(51)); // Merchant Domestic Repo
        assertTrue(qrisRoot.containsKey(52)); // MCC
        assertTrue(qrisRoot.containsKey(53)); // Currency
        assertTrue(qrisRoot.containsKey(58)); // Country
        assertTrue(qrisRoot.containsKey(59)); // Merchant Name
        assertTrue(qrisRoot.containsKey(60)); // Merchant City
        assertTrue(qrisRoot.containsKey(61)); // Postal Code
        assertTrue(qrisRoot.containsKey(62)); // Additional Data
        assertTrue(qrisRoot.containsKey(63)); // CRC
    }
    
    @Test
    @DisplayName("Should verify Currency '360' matches Country 'ID'")
    void testCurrencyCountryMatch() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        String currency = qrisRoot.get(53).getValue();
        String country = qrisRoot.get(58).getValue();
        
        assertEquals("ID", country, "Country should be 'ID'");
        assertEquals("360", currency, "Currency should be '360' for Indonesia");
    }
    
    @Test
    @DisplayName("Should have templateMap parsed for Merchant Account templates")
    void testTemplateMapsAreParsed() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Check ID 26
        if (qrisRoot.containsKey(26)) {
            assertNotNull(qrisRoot.get(26).getTemplateMap(), 
                "ID 26 template should be parsed");
            assertFalse(qrisRoot.get(26).getTemplateMap().isEmpty(), 
                "ID 26 template should not be empty");
        }
        
        // Check ID 51
        if (qrisRoot.containsKey(51)) {
            assertNotNull(qrisRoot.get(51).getTemplateMap(), 
                "ID 51 template should be parsed");
            assertFalse(qrisRoot.get(51).getTemplateMap().isEmpty(), 
                "ID 51 template should not be empty");
        }
        
        // Check ID 62
        if (qrisRoot.containsKey(62)) {
            assertNotNull(qrisRoot.get(62).getTemplateMap(), 
                "ID 62 template should be parsed");
        }
    }
    
    @Test
    @DisplayName("Should not be detected as TRANSFER type")
    void testNotTransferType() {
        QrisPayload payload = parser.parse(MPM_PAYMENT_QR);
        
        assertNotEquals(QrisType.TRANSFER, payload.getQrisType(), 
            "Should NOT be TRANSFER type");
        assertEquals(QrisType.MPM_PAYMENT, payload.getQrisType(), 
            "Should be MPM_PAYMENT type");
    }
}
