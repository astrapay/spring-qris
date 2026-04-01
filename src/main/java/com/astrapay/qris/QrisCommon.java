package com.astrapay.qris;

import java.nio.charset.StandardCharsets;

public final class QrisCommon {

    /**
     * Generate CRC-16 checksum menggunakan polynomial '1021' (hex) dan initial value 'FFFF' (hex).
     *
     * @param payload data yang akan dihitung checksum-nya
     * @return 4-character hexadecimal checksum
     */
    public static String generateChecksum(String payload) {
        int checksum = 0xFFFF;
        int polynomial = 0x1021;
        byte[] data = payload.getBytes(StandardCharsets.UTF_8);
        for (byte b : data) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((checksum >> 15 & 1) == 1);
                checksum <<= 1;
                if (c15 ^ bit) {
                    checksum ^= polynomial;
                }
            }
        }
        checksum &= 0xFFFF;
        return String.format("%04X", checksum);
    }
}
