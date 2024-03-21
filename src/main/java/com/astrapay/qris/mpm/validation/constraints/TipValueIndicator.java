package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TipValueIndicatorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.9</b> Tip Indicator (ID "55")
 * <b>4.7.9.1</b> Jika ditampilkan maka Tip Indicator wajib mengandung Value dari "01", "02" atau "03". Value lainnya belum ditetapkan.
 * <ul>
 *     <li>Value "01" wajib digunakan jika aplikasi mobile meminta konfirmasi konsumen untuk memasukan jumlah tip yang akan dibayarkan ke merchant</li>
 *     <li>Value "02" wajib digunakan untuk mengindikasi akan diisinya Value data object Tip Value Fixed (ID "56").</li>
 *     <li>Value “03” wajib digunakan untuk mengindikasi akan diisinya Value data object Tip Value Percentage (ID “57”).</li>
 * </ul>
 * Catatan: Walaupun Transaction Amount (ID “54”) tidak tersedia pada QR Code maka data object ini boleh ditampilkan.
 */
@Documented
@Constraint(validatedBy = {TipValueIndicatorValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface TipValueIndicator {

        /**
     *
     * @return String
     */
    String message() default "Tip Indicator wajib mengandung Value dari 01, 02 atau 03.";

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
