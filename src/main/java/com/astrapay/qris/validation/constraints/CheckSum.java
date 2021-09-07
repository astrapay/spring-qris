package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.CheckSumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.16</b> CRC (ID "63")<br/>
 * <b>4.7.16.1</b> Checksum wajib dihitung sesuai dengan [ISO/IEC 13239] menggunakan polynomial '1021' (hex) dan initial value 'FFFF' (hex). Data yang dihitung adalah seluruh data object termasuk ID, panjang karakter, Value, serta ID dan Panjang karakter dari CRC sendiri (tidak termasuk Value dari CRC).<br/>
 * <b>4.7.16.2</b> Penghitungan checksum menghasilkan nilai 2-byte hexadecimal yang wajib ditulis dalam 4-character Alphanumeric Special dimana nilainya akan dikonversikan sebagai bagian dari karakter Alphanumeric Special.<br/>
 */
@Documented
@Constraint(validatedBy = {CheckSumValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface CheckSum {

        /**
     *
     * @return String
     */
    String message() default "Checksum wajib dihitung sesuai dengan [ISO/IEC 13239]";

    /**
     *
     * @return class
     */
    Class<?>[] groups() default {};

    /**
     *
     * @return class
     */
    Class<? extends Payload>[] payload() default {};
}
