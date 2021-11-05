package com.astrapay.qris;

import java.nio.charset.StandardCharsets;

public class QrisCheckSum {
    public String generateChecksum(String payload) {
        int checksum = 0xffff;
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
        checksum &= 0xffff;
        return String.format("%04X", checksum);
    }
}
