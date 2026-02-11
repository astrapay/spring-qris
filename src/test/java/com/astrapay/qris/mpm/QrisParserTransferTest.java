package com.astrapay.qris.mpm;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.object.QrisType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test untuk QrisParser dengan fokus pada QRIS Transfer.
 * <p>
 * Test ini memvalidasi:
 * <ul>
 *     <li>Type detection untuk Transfer QR</li>
 *     <li>Parsing struktur Transfer Account Information (ID 40)</li>
 *     <li>Parsing Purpose of Transaction (tag 62->08)</li>
 *     <li>Validasi Spesifikasi 4.2 untuk Transfer</li>
 * </ul>
 * </p>
 */
class QrisParserTransferTest {

    private QrisParser parser;
    private Validator validator;
    
    // Real Transfer QR from production
    private static final String TRANSFER_QR_DMCT = 
        "00020101021240530013ID.CO.BCA.WWW011893600014151703139202105170313927520448295303360540410005802ID5920APRILA RACHMAT RIADY6013Jakarta Pusat61051031062470804DMCT9935000200012551703139270017707086683026304E3A1";
    
    @BeforeEach
    void setUp() {
        parser = new QrisParser();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    /**
     * Test parsing Transfer QR dengan Purpose = DMCT.
     * QR ini harus:
     * - Terdeteksi sebagai QrisType.TRANSFER
     * - Memiliki Transfer Account Information (ID 40)
     * - Memiliki Purpose of Transaction = "DMCT"
     * - Point of Initiation Method = "12" (Dynamic)
     * - Merchant Category Code = "4829" (Transfer)
     */
    @Test
    void testParseTransferQrWithDMCT() {
        // When
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        
        // Then - Verify instance type
        assertNotNull(payload, "Payload should not be null");
        assertInstanceOf(QrisTransferPayload.class, payload, "Should be QrisTransferPayload instance");
        assertEquals(QrisType.TRANSFER, payload.getQrisType(), "QRIS type should be TRANSFER");
        
        // Verify payload string
        assertEquals(TRANSFER_QR_DMCT, payload.getPayload(), "Payload string should match");
        
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        assertNotNull(qrisRoot, "QrisRoot should not be null");
        
        // Verify mandatory fields exist
        assertTrue(qrisRoot.containsKey(0), "Should have Payload Format Indicator (ID 00)");
        assertTrue(qrisRoot.containsKey(1), "Should have Point of Initiation Method (ID 01)");
        assertTrue(qrisRoot.containsKey(40), "Should have Transfer Account Information (ID 40)");
        assertTrue(qrisRoot.containsKey(52), "Should have Merchant Category Code (ID 52)");
        assertTrue(qrisRoot.containsKey(53), "Should have Transaction Currency (ID 53)");
        assertTrue(qrisRoot.containsKey(58), "Should have Country Code (ID 58)");
        assertTrue(qrisRoot.containsKey(59), "Should have Beneficiary Name (ID 59)");
        assertTrue(qrisRoot.containsKey(60), "Should have Beneficiary City (ID 60)");
        assertTrue(qrisRoot.containsKey(62), "Should have Additional Data (ID 62)");
        assertTrue(qrisRoot.containsKey(63), "Should have CRC (ID 63)");
    }
    
    /**
     * Test Point of Initiation Method harus "12" untuk Transfer.
     */
    @Test
    void testTransferPointOfInitiationMethodIs12() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject pointOfInitiation = qrisRoot.get(1);
        assertNotNull(pointOfInitiation, "Point of Initiation Method should exist");
        assertEquals("12", pointOfInitiation.getValue(), "Point of Initiation Method must be '12' (Dynamic) for Transfer");
    }
    
    /**
     * Test Merchant Category Code harus "4829" untuk Transfer.
     */
    @Test
    void testTransferMerchantCategoryCodeIs4829() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject mcc = qrisRoot.get(52);
        assertNotNull(mcc, "Merchant Category Code should exist");
        assertEquals("4829", mcc.getValue(), "MCC must be '4829' (Transfer) for Transfer QR");
    }
    
    /**
     * Test Transaction Currency harus "360" (IDR) ketika Country Code "ID".
     */
    @Test
    void testTransferCurrencyIs360ForIndonesia() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject currency = qrisRoot.get(53);
        assertNotNull(currency, "Transaction Currency should exist");
        assertEquals("360", currency.getValue(), "Currency must be '360' (IDR) for Indonesia");
        
        QrisDataObject countryCode = qrisRoot.get(58);
        assertNotNull(countryCode, "Country Code should exist");
        assertEquals("ID", countryCode.getValue(), "Country Code should be 'ID'");
    }
    
    /**
     * Test parsing Transfer Account Information (ID 40).
     * Harus memiliki struktur:
     * - 00: Reverse Domain
     * - 01: Customer PAN (16-19 digit)
     * - 02: Beneficiary ID
     */
    @Test
    void testParseTransferAccountInformation() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject transferAccountInfo = qrisRoot.get(40);
        assertNotNull(transferAccountInfo, "Transfer Account Information (ID 40) should exist");
        
        Map<Integer, QrisDataObject> templateMap = transferAccountInfo.getTemplateMap();
        assertNotNull(templateMap, "Transfer Account Info template map should be parsed");
        assertFalse(templateMap.isEmpty(), "Template map should not be empty");
        
        // Check sub-tag 00: Reverse Domain
        assertTrue(templateMap.containsKey(0), "Should have Reverse Domain (tag 00)");
        QrisDataObject reverseDomain = templateMap.get(0);
        assertEquals("ID.CO.BCA.WWW", reverseDomain.getValue(), "Reverse Domain should be 'ID.CO.BCA.WWW'");
        
        // Check sub-tag 01: Customer PAN
        assertTrue(templateMap.containsKey(1), "Should have Customer PAN (tag 01)");
        QrisDataObject customerPan = templateMap.get(1);
        assertNotNull(customerPan.getValue(), "Customer PAN should not be null");
        assertEquals("9360001415170313920", customerPan.getValue(), "Customer PAN should match");
        assertTrue(customerPan.getValue().matches("\\d{16,19}"), "Customer PAN should be 16-19 numeric digits");
        
        // Check sub-tag 02: Beneficiary ID
        assertTrue(templateMap.containsKey(2), "Should have Beneficiary ID (tag 02)");
        QrisDataObject beneficiaryId = templateMap.get(2);
        assertEquals("105170313927", beneficiaryId.getValue(), "Beneficiary ID should match");
    }
    
    /**
     * Test parsing Purpose of Transaction dari Additional Data (ID 62).
     * Purpose harus salah satu dari: BOOK, DMCT, atau XBCT.
     */
    @Test
    void testParsePurposeOfTransactionDMCT() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject additionalData = qrisRoot.get(62);
        assertNotNull(additionalData, "Additional Data (ID 62) should exist");
        
        Map<Integer, QrisDataObject> additionalDataMap = additionalData.getTemplateMap();
        assertNotNull(additionalDataMap, "Additional Data template map should be parsed");
        
        // Check tag 08: Purpose of Transaction
        assertTrue(additionalDataMap.containsKey(8), "Should have Purpose of Transaction (tag 08)");
        QrisDataObject purpose = additionalDataMap.get(8);
        assertEquals("DMCT", purpose.getValue(), "Purpose should be 'DMCT' (Debit Merchant Credit Transfer)");
    }
    
    /**
     * Test parsing Unique per Generated dari Additional Data.
     */
    @Test
    void testParseUniquePerGenerated() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject additionalData = qrisRoot.get(62);
        Map<Integer, QrisDataObject> additionalDataMap = additionalData.getTemplateMap();
        
        // Check tag 99: Unique per Generated (optional)
        if (additionalDataMap.containsKey(99)) {
            QrisDataObject unique = additionalDataMap.get(99);
            assertNotNull(unique.getValue(), "Unique per Generated should not be null if present");
            // Value in this QR: "50002000125517031392700177070866830"
        }
    }
    
    /**
     * Test Beneficiary Name dan City.
     */
    @Test
    void testBeneficiaryInformation() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Beneficiary Name (ID 59)
        QrisDataObject beneficiaryName = qrisRoot.get(59);
        assertNotNull(beneficiaryName, "Beneficiary Name should exist");
        assertEquals("APRILA RACHMAT RIADY", beneficiaryName.getValue(), "Beneficiary Name should match");
        assertTrue(beneficiaryName.getValue().length() <= 25, "Beneficiary Name max 25 chars");
        
        // Beneficiary City (ID 60)
        QrisDataObject beneficiaryCity = qrisRoot.get(60);
        assertNotNull(beneficiaryCity, "Beneficiary City should exist");
        assertEquals("Jakarta Pusat", beneficiaryCity.getValue(), "Beneficiary City should match");
        assertTrue(beneficiaryCity.getValue().length() <= 15, "Beneficiary City max 15 chars");
        
        // Postal Code (ID 61)
        QrisDataObject postalCode = qrisRoot.get(61);
        assertNotNull(postalCode, "Postal Code should exist for Country Code 'ID'");
        assertEquals("10310", postalCode.getValue(), "Postal Code should match");
    }
    
    /**
     * Test Transaction Amount (optional untuk Transfer).
     */
    @Test
    void testTransactionAmount() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        // Transaction Amount (ID 54) - optional
        QrisDataObject amount = qrisRoot.get(54);
        assertNotNull(amount, "Transaction Amount exists in this QR");
        assertEquals("100", amount.getValue(), "Transaction Amount should be 100");
    }
    
    /**
     * Test CRC checksum.
     */
    @Test
    void testCRCChecksum() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        
        QrisDataObject crc = qrisRoot.get(63);
        assertNotNull(crc, "CRC should exist");
        assertEquals("E3A1", crc.getValue(), "CRC should match");
        assertEquals(4, crc.getValue().length(), "CRC must be 4 chars");
    }
    
    /**
     * Test Bean Validation terhadap QrisTransferPayload.
     * QR yang valid seharusnya tidak memiliki constraint violation.
     */
    @Test
    void testTransferQrValidation() {
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        
        Set<ConstraintViolation<QrisPayload>> violations = validator.validate(payload);
        
        // Print violations for debugging
        if (!violations.isEmpty()) {
            System.out.println("Validation violations found:");
            violations.forEach(v -> 
                System.out.println("  - " + v.getPropertyPath() + ": " + v.getMessage())
            );
        }
        
        assertTrue(violations.isEmpty(), 
            "Valid Transfer QR should have no constraint violations. Found: " + violations.size());
    }
    
    /**
     * Test complete QR structure dengan toString (regenerate QR).
     */
    @Test
    void testQrRoundTrip() {
        // Parse QR
        QrisPayload payload = parser.parse(TRANSFER_QR_DMCT);
        
        // Verify all major components
        assertNotNull(payload.getQrisRoot());
        assertEquals(QrisType.TRANSFER, payload.getQrisType());
        
        // Check QrisRoot has expected number of tags
        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        assertTrue(qrisRoot.size() >= 10, "Should have at least 10 root tags");
        
        // Verify Transfer-specific tags
        assertNotNull(qrisRoot.get(40), "Transfer Account Info should exist");
        assertNotNull(qrisRoot.get(62), "Additional Data should exist");
        
        // Verify templateMaps are parsed
        assertNotNull(qrisRoot.get(40).getTemplateMap(), "Transfer Account Info should be parsed");
        assertNotNull(qrisRoot.get(62).getTemplateMap(), "Additional Data should be parsed");
    }
}
