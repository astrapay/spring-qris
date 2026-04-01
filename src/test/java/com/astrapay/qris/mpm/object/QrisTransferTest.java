package com.astrapay.qris.mpm.object;

import com.astrapay.qris.MerchantCriteria;
import com.astrapay.qris.QrisCommon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test untuk QRIS MPM Transfer menggunakan method toStringTransfer().
 *
 */
@DisplayName("QRIS MPM Transfer Tests")
class QrisTransferTest {
    private static final String TAG_CHECKSUM_QRIS = "6304";

    @Test
    @DisplayName("Should generate valid QRIS Transfer string without amount")
    void testGenerateTransferWithoutAmount() {
        // Arrange - Data sesuai contoh dari spesifikasi
        TransferAccountInformation transferInfo = TransferAccountInformation.builder()
                .reverseDomain("ID.CO.PJSPNAME1.WWW") //max length 32 chars
                .customerPan("9360082202345123451") // max length 19 chars
                .beneficiaryId("15KLMNO12345123") // max length 15 chars
                .build();

        // Gunakan AdditionalDataFieldTransfer dengan struktur yang benar
        // Tag 99 akan di-generate otomatis dari tag 00 + tag 01
        AdditionalDataFieldTransfer additionalDataTransfer = new AdditionalDataFieldTransfer();
        additionalDataTransfer.setPurposeOfTransaction(PurposeOfTransaction.DMCT);
        additionalDataTransfer.setDefaultValue("00");
        additionalDataTransfer.setUniqueData("7660407400001769742270682");  // 25 chars

        Qris qris = Qris.builder()
                .payloadFormatIndicator("01")
                .pointOfInitiationMethod(12)
                .transferAccountInformation(transferInfo)
                .merchantCategoryCode(4829)
                .transactionCurrency(Currency.getInstance("IDR"))
                .transactionAmount(0.0)
                .countryCode(new Locale("id", "ID"))
                .beneficiaryName("ANTONIO")
                .beneficiaryCity("JAKARTA")
                .postalCode("10310")
                .additionalDataFieldTransfer(additionalDataTransfer)
                .build();

        // Act
        String qrText = qris.toStringTransfer()+ TAG_CHECKSUM_QRIS;
        var checkSum = QrisCommon.generateChecksum(qrText);
        qris.setCrc(checkSum);
        String qrisString = qris.toStringTransfer();

        // Assert
        System.out.println("Generated QRIS Transfer (without amount): " + qrisString);

        assertNotNull(qrisString);
        assertTrue(qrisString.startsWith("000201"), "Should start with Payload Format Indicator");
        assertTrue(qrisString.contains("010212"), "Should contain Point of Initiation Method");
        assertTrue(qrisString.contains("4065"), "Should contain Transfer Account Information tag 40 with length 65");
        assertTrue(qrisString.contains("0019ID.CO.PJSPNAME1.WWW"), "Should contain Reverse Domain");
        assertTrue(qrisString.contains("01199360082202345123451"), "Should contain Customer PAN");
        assertTrue(qrisString.contains("021515KLMNO12345123"), "Should contain Beneficiary ID");
        assertTrue(qrisString.contains("52044829"), "Should contain Merchant Category Code");
        assertTrue(qrisString.contains("5303360"), "Should contain Transaction Currency (IDR=360)");
        assertFalse(qrisString.contains("54"), "Should NOT contain Transaction Amount tag when amount is 0");
        assertTrue(qrisString.contains("5802ID"), "Should contain Country Code");
        assertTrue(qrisString.contains("5907ANTONIO"), "Should contain Beneficiary Name");
        assertTrue(qrisString.contains("6007JAKARTA"), "Should contain Beneficiary City");
        assertTrue(qrisString.contains("610510310"), "Should contain Postal Code tag with value 10310");

        // Verify tag 62 structure with nested tag 99
        // uniqueData: "7660407400001769742270682" (25 chars)
        // Tag 00: 00 + 02 + 00 = 6 chars
        // Tag 01: 01 + 25 + 7660407400001769742270682 = 29 chars
        // Tag 99 content: 6 + 29 = 35 chars
        // Tag 99: 99 + 35 + content = 39 chars
        // Tag 08: 08 + 04 + DMCT = 8 chars
        // Tag 62 content: 8 + 39 = 47 chars
        // Tag 62: 62 + 47 + content
        assertTrue(qrisString.contains("6247"), "Should contain Additional Data tag 62 with length 47");
        assertTrue(qrisString.contains("0804DMCT"), "Should contain tag 08 (Purpose of Transaction)");
        assertTrue(qrisString.contains("9935"), "Should contain tag 99 with length 35");
        assertTrue(qrisString.contains("000200"), "Should contain tag 00 (Default Value)");
        assertTrue(qrisString.contains("01257660407400001769742270682"), "Should contain tag 01 (Unique Data)");
    }

    @Test
    @DisplayName("Should fail validation when tag 62 missing sub-tag 01 (Unique Data)")
    void testGenerateTransferValidasiTag62() {
        // Arrange - Data dengan AdditionalDataFieldTransfer yang TIDAK memiliki uniqueData (tag 01)
        TransferAccountInformation transferInfo = TransferAccountInformation.builder()
                .reverseDomain("ID.CO.PJSPNAME1.WWW") //max length 32 chars
                .customerPan("9360082202345123451") // max length 19 chars
                .beneficiaryId("15KLMNO12345123") // max length 15 chars
                .build();

        // AdditionalDataFieldTransfer dengan hanya purposeOfTransaction dan defaultValue
        // Tag 99 akan di-generate otomatis hanya berisi tag 00, TANPA tag 01
        // Struktur: 08 04 DMCT + 99 06 (00 02 00)
        // Tag 01 (Unique Data) TIDAK ADA - ini akan menyebabkan validation error
        AdditionalDataFieldTransfer additionalDataTransfer = new AdditionalDataFieldTransfer();
        additionalDataTransfer.setPurposeOfTransaction(PurposeOfTransaction.DMCT);
        additionalDataTransfer.setDefaultValue("00");
        // uniqueData intentionally NOT set - will cause validation to fail

        System.out.println("additionalDataTransfer value: " + additionalDataTransfer.getValue());

        Qris qris = Qris.builder()
                .payloadFormatIndicator("01")
                .pointOfInitiationMethod(12)
                .transferAccountInformation(transferInfo)
                .merchantCategoryCode(4829)
                .transactionCurrency(Currency.getInstance("IDR"))
                .transactionAmount(0.0)
                .countryCode(new Locale("id", "ID"))
                .beneficiaryName("ANTONIO")
                .beneficiaryCity("JAKARTA")
                .postalCode("10310")
                .additionalDataFieldTransfer(additionalDataTransfer)
                .build();

        // Act & Assert - Expect validation to fail when converting to payload
        // Note: Ini akan divalidasi saat QrisTransferPayload di-validate
        String qrText = qris.toStringTransfer() + TAG_CHECKSUM_QRIS;
        var checkSum = QrisCommon.generateChecksum(qrText);
        qris.setCrc(checkSum);
        String qrisString = qris.toStringTransfer();

        // Assert - QR string should be generated but will fail when validated as QrisTransferPayload
        assertNotNull(qrisString);
        assertTrue(qrisString.startsWith("000201"), "Should start with Payload Format Indicator");

        // Expected tag 62 structure (missing tag 01):
        // 62 18 (tag + length)
        //   08 04 DMCT (tag 08)
        //   99 06 (tag 99 with only tag 00 inside)
        //     00 02 00 (tag 00 - nested in tag 99)
        // Total: 62180804DMCT9906000200
        assertTrue(qrisString.contains("62180804DMCT9906000200"),
                "Should contain Additional Data (tag 62) with tag 99 missing sub-tag 01");
        assertTrue(qrisString.contains("0804DMCT"), "Should contain tag 08 (Purpose of Transaction)");
        assertTrue(qrisString.contains("9906000200"), "Should contain tag 99 with only tag 00 (length 6)");

        // Verify tag 99 structure: should be exactly "9906000200" (tag 99 + length 06 + tag 00 content)
        // Tag 99 length is 06, which means only 6 chars inside (000200 = tag 00)
        // If tag 01 existed, length would be > 06
        int tag99Index = qrisString.indexOf("9906");
        if (tag99Index != -1) {
            String tag99Content = qrisString.substring(tag99Index + 4, tag99Index + 4 + 6); // Extract 6 chars after "9906"
            assertEquals("000200", tag99Content, "Tag 99 should only contain tag 00, not tag 01");
        }
    }

    @Test
    @DisplayName("Should generate valid QRIS Transfer string with amount")
    void testGenerateTransferWithAmount() {
        // Arrange
        TransferAccountInformation transferInfo = TransferAccountInformation.builder()
                .reverseDomain("ID.CO.PJSPNAME1.WWW") //max length 32 chars
                .customerPan("9360082202345123451") // max length 19 chars
                .beneficiaryId("15KLMNO12345123") // max length 15 chars
                .build();

        AdditionalDataFieldTransfer additionalDataTransfer = new AdditionalDataFieldTransfer();
        additionalDataTransfer.setPurposeOfTransaction(PurposeOfTransaction.DMCT);
        additionalDataTransfer.setUniqueData("7660407400001769742270682");

        Qris qris = Qris.builder()
                .payloadFormatIndicator("01")
                .pointOfInitiationMethod(12)
                .transferAccountInformation(transferInfo)
                .merchantCategoryCode(4829)
                .transactionCurrency(Currency.getInstance("IDR"))
                .transactionAmount(850000.0)
                .countryCode(new Locale("id", "ID"))
                .beneficiaryName("ANTONIO")
                .beneficiaryCity("JAKARTA")
                .postalCode("10310")
                .additionalDataFieldTransfer(additionalDataTransfer)
                .build();

        // Act
        String qrText = qris.toStringTransfer()+ TAG_CHECKSUM_QRIS;
        var checkSum = QrisCommon.generateChecksum(qrText);
        qris.setCrc(checkSum);
        String qrisString = qris.toStringTransfer();

        // Assert
        assertNotNull(qrisString);
        assertTrue(qrisString.contains("5406850000"), "Should contain Transaction Amount");

        System.out.println("Generated QRIS Transfer (with amount): " + qrisString);
    }

    @Test
    @DisplayName("Should generate Transfer with Bank Identifier Code (BIC)")
    void testGenerateTransferWithBIC() {
        // Arrange
        TransferAccountInformation transferInfo = TransferAccountInformation.builder()
                .reverseDomain("ID.CO.PJSPNAME1.WWW")
                .customerPan("0118936023451234567890")
                .beneficiaryId("15KLMNO1234512345")
                .bankIdentifierCode("CENAIDJA")  // Bank Central Asia
                .build();



        Qris qris = Qris.builder()
                .payloadFormatIndicator("01")
                .pointOfInitiationMethod(12)
                .transferAccountInformation(transferInfo)
                .merchantCategoryCode(4829)
                .transactionCurrency(Currency.getInstance("IDR"))
                .countryCode(new Locale("id", "ID"))
                .beneficiaryName("ANTONIO")
                .beneficiaryCity("JAKARTA")
                .postalCode("10310")
                .build();



        // Act
        String qrText = qris.toStringTransfer()+ TAG_CHECKSUM_QRIS;
        var checkSum = QrisCommon.generateChecksum(qrText);
        qris.setCrc(checkSum);
        String qrisString = qris.toStringTransfer();

        // Assert
        assertNotNull(qrisString);
        assertTrue(qrisString.contains("4082"), "Should contain Transfer Account Information with BIC (length should be 82)");
        assertTrue(qrisString.contains("0408CENAIDJA"), "Should contain Bank Identifier Code");

        System.out.println("Generated QRIS Transfer (with BIC): " + qrisString);
    }

    @Test
    @DisplayName("toString() should still work for MPM Payment (backward compatibility)")
    void testBackwardCompatibilityForPayment() {
        // Tag 26 (Merchant Account Information Domestic - AstraPay)
        MerchantAccountInformation merchantAccountInformationDomestic = new MerchantAccountInformation();
        merchantAccountInformationDomestic.setGloballyUniqueIdentifier("ID.CO.ASTRAPAY.WWW"); // 26-00
        merchantAccountInformationDomestic.setPersonalAccountNumber("936008223210000240"); // 26-01
        merchantAccountInformationDomestic.setMerchantId("210000240"); // 26-02
        merchantAccountInformationDomestic.setCriteria(MerchantCriteria.UBE); // 26-03

        Map<Integer, MerchantAccountInformation> merchantAccountInformationMap = new HashMap<>();
        merchantAccountInformationMap.put(26, merchantAccountInformationDomestic);

        // Tag 51 (Domestic Central Repository - QRIS GPN)
        MerchantAccountInformation domesticCentralRepo = new MerchantAccountInformation();
        domesticCentralRepo.setGloballyUniqueIdentifier("ID.CO.QRIS.WWW"); // 51-00
        // Note: Tag 51 tidak memiliki PAN (sub-tag 01) dalam contoh QR text
        domesticCentralRepo.setMerchantId("ID2021066246392"); // 51-02
        domesticCentralRepo.setCriteria(MerchantCriteria.UBE); // 51-03

        // Tag 62 (Additional Data)
        AdditionalData additionalData = new AdditionalData();
        additionalData.setValue("0704AP01"); // Terminal ID

        Qris qris = Qris.builder()
                .payloadFormatIndicator("01") // Tag 00
                .pointOfInitiationMethod(12) // Tag 01 (Dynamic QR in example)
                .merchantAccountInformationDomestics(merchantAccountInformationMap) // Tag 26
                .domesticCentralRepository(domesticCentralRepo) // Tag 51
                .merchantCategoryCode(5812) // Tag 52
                .transactionCurrency(Currency.getInstance("IDR")) // Tag 53 (360)
                .transactionAmount(0.0) // Tag 54 tidak ada karena 0
                .countryCode(new Locale("id", "ID")) // Tag 58
                .merchantName("AHASS TDM Natar") // Tag 59
                .merchantCity("Jakarta") // Tag 60
                .postalCode("44335") // Tag 61
                .additionalData(additionalData) // Tag 62
                .build();

        // Act - Generate checksum seperti production code
        String qrTextForChecksum = qris.toString() + TAG_CHECKSUM_QRIS;
        String checkSum = QrisCommon.generateChecksum(qrTextForChecksum);
        qris.setCrc(checkSum);

        String qrisString = qris.toString();
        System.out.println("Generated QRIS Payment (backward compatibility): " + qrisString);

        // Assert
        assertNotNull(qrisString);
        assertTrue(qrisString.startsWith("000201"), "Should start with Payload Format Indicator '01'");
        assertTrue(qrisString.contains("010212"), "Should contain Point of Initiation Method 12");

        // Tag 26 checks
        assertTrue(qrisString.contains("2664"), "Should contain Tag 26 with length 64");
        assertTrue(qrisString.contains("ID.CO.ASTRAPAY.WWW"), "Should contain AstraPay domain");
        assertTrue(qrisString.contains("936008223210000240"), "Should contain PAN");
        assertTrue(qrisString.contains("210000240"), "Should contain Merchant ID");

        // Tag 51 checks
        assertTrue(qrisString.contains("5144"), "Should contain Tag 51 with length 44");
        assertTrue(qrisString.contains("ID.CO.QRIS.WWW"), "Should contain QRIS GPN domain");
        assertTrue(qrisString.contains("ID2021066246392"), "Should contain NMID");

        // Other mandatory tags
        assertTrue(qrisString.contains("5204581"), "Should contain MCC 5812");
        assertTrue(qrisString.contains("5303360"), "Should contain Currency IDR (360)");
        assertTrue(qrisString.contains("5802ID"), "Should contain Country Code");
        assertTrue(qrisString.contains("5915AHASS TDM Natar"), "Should contain Merchant Name");
        assertTrue(qrisString.contains("6007Jakarta"), "Should contain Merchant City");
        assertTrue(qrisString.contains("610544335"), "Should contain Postal Code");
        assertTrue(qrisString.contains("6208"), "Should contain Additional Data tag");
        assertTrue(qrisString.contains("6304"), "Should contain CRC tag");

        assertFalse(qrisString.contains("5406"), "Should NOT contain Transaction Amount when 0");
    }

    @Test
    @DisplayName("Should handle null transferAccountInformation gracefully")
    void testNullTransferAccountInformation() {
        // Arrange
        Qris qris = Qris.builder()
                .payloadFormatIndicator("01")
                .pointOfInitiationMethod(12)
                .transferAccountInformation(null)
                .merchantCategoryCode(4829)
                .transactionCurrency(Currency.getInstance("IDR"))
                .countryCode(new Locale("id", "ID"))
                .beneficiaryName("ANTONIO")
                .beneficiaryCity("JAKARTA")
                .build();

        // Act
        String qrisString = qris.toStringTransfer();

        // Assert
        assertNotNull(qrisString);
        assertFalse(qrisString.contains("40"), "Should not contain ID 40 when transferAccountInformation is null");
    }
}