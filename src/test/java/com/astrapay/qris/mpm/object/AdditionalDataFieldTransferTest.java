package com.astrapay.qris.mpm.object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test untuk AdditionalDataFieldTransfer dengan nested structure.
 * Memverifikasi bahwa tag 00 dan 01 di-encode sebagai child dari tag 99.
 * Tag 99 adalah hasil encoding otomatis dari tag 00 + tag 01.
 */
@DisplayName("AdditionalDataFieldTransfer Nested Structure Tests")
class AdditionalDataFieldTransferTest {

    @Test
    @DisplayName("Should encode tag 00 and 01 as nested children of tag 99")
    void testNestedStructure() {
        // Arrange
        AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
                .purposeOfTransaction(PurposeOfTransaction.DMCT)
                .defaultValue("00")            // 2 chars -> encoded as "000200"
                .uniqueData("ABC123")          // 6 chars -> encoded as "0106ABC123"
                .build();

        // Act
        String result = additionalData.toString();
        System.out.println("Generated QRIS string: " + result);

        // Assert structure
        assertNotNull(result);
        assertTrue(result.startsWith("62"), "Should start with tag 62");
        
        // Expected breakdown:
        // Tag 08: "0804DMCT" (8 chars)
        // Tag 99: "99" + length + (tag 00 + tag 01)
        //   - Tag 00: "000200" (6 chars)
        //   - Tag 01: "0106ABC123" (10 chars)
        //   Total in tag 99: 6 + 10 = 16 chars
        // So tag 99 = "9916" + "000200" + "0106ABC123" (20 chars)
        // Tag 62 length = 8 + 20 = 28
        
        
        
        // Verify nested structure: tag 99 content should be "000200" + "0106ABC123"
        String expected99Content = "000200" + "0106ABC123";
        assertTrue(result.contains("9916" + expected99Content), 
            "Tag 99 should contain nested structure: tag00 + tag01");
        
        // Calculate expected tag 62 length
        String tag08Content = "0804DMCT"; // 8 chars
        String tag99Content = "9916" + expected99Content; // 4 + 16 = 20 chars
        int expectedLength = tag08Content.length() + tag99Content.length(); // 8 + 20 = 28
        String expectedResult = "62" + String.format("%02d", expectedLength) + tag08Content + tag99Content;
        
        assertTrue(result.contains(tag08Content), "Should contain tag 08 with Purpose DMCT");
        assertTrue(result.contains("9916"), "Tag 99 should have length 16 (6+10)");
        assertEquals(expectedResult, result, "Complete structure should match expected nested format");
        
        // Expected: 62280804DMCT99160002000106ABC123
    }

    @Test
    @DisplayName("Should handle longer uniqueData value")
    void testLongerUniqueData() {
        // Arrange
        AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
                .purposeOfTransaction(PurposeOfTransaction.BOOK)
                .defaultValue("00")                     // 2 chars -> "000200" (6 chars)
                .uniqueData("a1d64af7470ce64d")        // 16 chars -> "0116a1d64af7470ce64d" (20 chars)
                .build();

        // Act
        String result = additionalData.toString();
        System.out.println("Generated QRIS string (long): " + result);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("0804BOOK"), "Should contain tag 08 with Purpose BOOK");
        
        // Tag 99 content: tag 00 (6 chars) + tag 01 (20 chars) = 26 chars
        // Tag 99: "9926" + content (30 chars total)
        assertTrue(result.contains("9926"), "Tag 99 should have length 26");
        
        // Verify tag 00 and 01 are inside tag 99
        String expected99Content = "000200" + "0116a1d64af7470ce64d";
        assertTrue(result.contains(expected99Content), 
            "Tag 99 should contain all nested elements in correct order");
        
        // Complete expected: 62380804BOOK99260002000116a1d64af7470ce64d
        String expectedResult = "62380804BOOK99260002000116a1d64af7470ce64d";
        assertEquals(expectedResult, result, "Complete structure should match");
    }

    @Test
    @DisplayName("getValue() should return raw value with nested structure")
    void testGetValueWithNestedStructure() {
        // Arrange
        AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
                .purposeOfTransaction(PurposeOfTransaction.DMCT)
                .defaultValue("00")
                .uniqueData("ABC123")
                .build();

        // Act
        String value = additionalData.getValue();
        System.out.println("Raw value: " + value);

        // Assert
        // Expected: "0804DMCT" + "9916" + "000200" + "0106ABC123"
        String expectedValue = "0804DMCT" + "9916" + "000200" + "0106ABC123";
        assertEquals(expectedValue, value, "getValue() should return correct nested structure");
    }

    @Test
    @DisplayName("Should handle empty uniqueData gracefully")
    void testEmptyUniqueData() {
        // Arrange - tag 99 should not be included if no uniqueData
        AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
                .purposeOfTransaction(PurposeOfTransaction.DMCT)
                .defaultValue("00")
                .uniqueData("")  // empty
                .build();

        // Act
        String result = additionalData.toString();

        // Assert - if uniqueData is empty, tag 99 should still contain tag 00
        assertNotNull(result);
        assertTrue(result.contains("0804DMCT"), "Should contain tag 08");
        assertTrue(result.contains("9906"), "Should contain tag 99 with length 6 (tag 00 only)");
        assertTrue(result.contains("000200"), "Should contain tag 00");
        assertFalse(result.contains("01"), "Tag 01 should not appear when uniqueData is empty");
        
        // Expected: 62180804DMCT9906000200
        // Tag 08: 0804DMCT (8 chars)
        // Tag 99: 9906000200 (10 chars)
        // Total: 18 chars -> 6218
        String expectedResult = "62180804DMCT9906000200";
        assertEquals(expectedResult, result, "Should match expected format with only tag 00");
    }
}
