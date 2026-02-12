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
        assertEquals("Transfer", QrisType.TRANSFER.getDisplayName(),
            "TRANSFER display name should be 'Transfer'");
    }

    @Test
    void testTransferDescription() {
        assertEquals("QRIS untuk transfer", QrisType.TRANSFER.getDescription(),
            "TRANSFER description should match");
    }

    @Test
    void testTuntasDisplayName() {
        assertEquals("Tuntas/Cash Withdrawal", QrisType.TUNTAS.getDisplayName(),
            "TUNTAS display name should be 'Tuntas/Cash Withdrawal'");
    }

    @Test
    void testTuntasDescription() {
        assertEquals("QRIS untuk penarikan tunai di ATM", QrisType.TUNTAS.getDescription(),
            "TUNTAS description should match");
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
        
        assertEquals(4, values.length, "Should have 4 enum values");
        
        // Verify all enum constants exist
        assertNotNull(QrisType.MPM_PAYMENT, "MPM_PAYMENT should exist");
        assertNotNull(QrisType.TRANSFER, "TRANSFER should exist");
        assertNotNull(QrisType.TUNTAS, "TUNTAS should exist");
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
