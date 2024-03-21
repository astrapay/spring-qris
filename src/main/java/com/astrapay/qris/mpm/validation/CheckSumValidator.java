package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.CheckSum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

/**
 * <b>4.7.16</b> CRC (ID "63")<br/>
 * <b>4.7.16.1</b> Checksum wajib dihitung sesuai dengan [ISO/IEC 13239] menggunakan polynomial '1021' (hex) dan initial value 'FFFF' (hex). Data yang dihitung adalah seluruh data object termasuk ID, panjang karakter, Value, serta ID dan Panjang karakter dari CRC sendiri (tidak termasuk Value dari CRC).<br/>
 * <b>4.7.16.2</b> Penghitungan checksum menghasilkan nilai 2-byte hexadecimal yang wajib ditulis dalam 4-character Alphanumeric Special dimana nilainya akan dikonversikan sebagai bagian dari karakter Alphanumeric Special.<br/>
 */
public class CheckSumValidator implements ConstraintValidator<CheckSum, QrisPayload> {

    @Override
    public boolean isValid(QrisPayload value, ConstraintValidatorContext context) {
        String crcCheckSum = generateChecksum(value.getPayload().substring(0, value.getPayload().length()-4));
        return crcCheckSum.equals(value.getQrisRoot().get(63).getValue());
    }


    private String generateChecksum(String payload) {
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
