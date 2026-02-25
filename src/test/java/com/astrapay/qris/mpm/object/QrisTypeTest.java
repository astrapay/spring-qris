package com.astrapay.qris.mpm.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test untuk QrisType enum.
 * <p>
 * Test ini memvalidasi:
 * <ul>
 *     <li>Display name untuk setiap tipe QRIS</li>
 *     <li>Deskripsi untuk setiap tipe QRIS</li>
 * </ul>
 * </p>
 */
class QrisTypeTest {

    @Test
    void testMpmPaymentDisplayName() {
        assertEquals("Merchant Payment", QrisType.MPM_PAYMENT.getDisplayName(),
            "MPM_PAYMENT display name should be 'Merchant Payment'");
    }

    @Test
    void testMpmPaymentDescription() {
        assertEquals("QRIS untuk pembayaran ke merchant", QrisType.MPM_PAYMENT.getDescription(),
            "MPM_PAYMENT description should match");
    }

    @Test
    void testTransferDisplayName() {
        assertEquals("TTS Transfer", QrisType.MPM_TRANSFER.getDisplayName(),
            "MPM_TRANSFER display name should be 'TTS Transfer'");
    }

    @Test
    void testTransferDescription() {
        assertEquals("QRIS untuk transfer", QrisType.MPM_TRANSFER.getDescription(),
            "TRANSFER description should match");
    }

    @Test
    void testCashInDisplayName() {
        assertEquals("TTS Cash In", QrisType.MPM_CASH_IN.getDisplayName(),
            "MPM_CASH_IN display name should be 'TTS Cash In'");
    }

    @Test
    void testCashInDescription() {
        assertEquals("QRIS untuk setor tunai di ATM", QrisType.MPM_CASH_IN.getDescription(),
            "MPM_CASH_IN description should match");
    }

    @Test
    void testCashOutDisplayName() {
        assertEquals("TTS Cash Out", QrisType.MPM_CASH_OUT.getDisplayName(),
            "MPM_CASH_OUT display name should be 'TTS Cash Out'");
    }

    @Test
    void testCashOutDescription() {
        assertEquals("QRIS untuk tarik tunai di ATM", QrisType.MPM_CASH_OUT.getDescription(),
            "MPM_CASH_OUT description should match");
    }

    @Test
    void testUnknownDisplayName() {
        assertEquals("Unknown", QrisType.UNKNOWN.getDisplayName(),
            "UNKNOWN display name should be 'Unknown'");
    }

    @Test
    void testUnknownDescription() {
        assertEquals("QRIS dengan tipe yang tidak dikenali", QrisType.UNKNOWN.getDescription(),
            "UNKNOWN description should match");
    }

    @Test
    void testAllEnumValues() {
        QrisType[] values = QrisType.values();
        
        assertEquals(5, values.length, "Should have 5 enum values");
        
        // Verify all enum constants exist
        assertNotNull(QrisType.MPM_PAYMENT, "MPM_PAYMENT should exist");
        assertNotNull(QrisType.MPM_TRANSFER, "MPM_TRANSFER should exist");
        assertNotNull(QrisType.MPM_CASH_IN, "MPM_CASH_IN should exist");
        assertNotNull(QrisType.MPM_CASH_OUT, "MPM_CASH_OUT should exist");
        assertNotNull(QrisType.UNKNOWN, "UNKNOWN should exist");
    }

    @Test
    void testAllEnumValuesHaveDisplayName() {
        for (QrisType type : QrisType.values()) {
            assertNotNull(type.getDisplayName(), 
                "Display name should not be null for " + type.name());
            assertFalse(type.getDisplayName().isEmpty(), 
                "Display name should not be empty for " + type.name());
        }
    }

    @Test
    void testAllEnumValuesHaveDescription() {
        for (QrisType type : QrisType.values()) {
            assertNotNull(type.getDescription(), 
                "Description should not be null for " + type.name());
            assertFalse(type.getDescription().isEmpty(), 
                "Description should not be empty for " + type.name());
        }
    }
}
