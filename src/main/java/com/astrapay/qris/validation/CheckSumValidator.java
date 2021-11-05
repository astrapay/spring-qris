package com.astrapay.qris.validation;

import com.astrapay.qris.QrisCheckSum;
import com.astrapay.qris.object.QrisPayload;
import com.astrapay.qris.validation.constraints.CheckSum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

/**
 * <b>4.7.16</b> CRC (ID "63")<br/>
 * <b>4.7.16.1</b> Checksum wajib dihitung sesuai dengan [ISO/IEC 13239] menggunakan polynomial '1021' (hex) dan initial value 'FFFF' (hex). Data yang dihitung adalah seluruh data object termasuk ID, panjang karakter, Value, serta ID dan Panjang karakter dari CRC sendiri (tidak termasuk Value dari CRC).<br/>
 * <b>4.7.16.2</b> Penghitungan checksum menghasilkan nilai 2-byte hexadecimal yang wajib ditulis dalam 4-character Alphanumeric Special dimana nilainya akan dikonversikan sebagai bagian dari karakter Alphanumeric Special.<br/>
 */
public class CheckSumValidator implements ConstraintValidator<CheckSum, QrisPayload> {

    @Autowired
    private QrisCheckSum qrisCheckSum;

    @Override
    public boolean isValid(QrisPayload value, ConstraintValidatorContext context) {
        String crcCheckSum = qrisCheckSum.generateChecksum(value.getPayload().substring(0, value.getPayload().length()-4));
        return crcCheckSum.equals(value.getQrisRoot().get(63).getValue());
    }



}