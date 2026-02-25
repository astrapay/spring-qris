package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TransferCurrencyCountryCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validasi kesesuaian Transaction Currency dan Country Code untuk QRIS Transfer.
 * <p>
 * Sesuai spesifikasi 4.2 Verifikasi Data – QRIS MPM Transaksi Transfer:
 * </p>
 * <ul>
 *     <li>Jika Country Code (ID "58") = "ID", maka Transaction Currency (ID "53") WAJIB = "360"</li>
 *     <li>Jika kondisi ini tidak terpenuhi, transaksi TIDAK BOLEH diteruskan</li>
 * </ul>
 * 
 */
@Documented
@Constraint(validatedBy = {TransferCurrencyCountryCodeValidator.class})
@Target({FIELD, TYPE})
@Retention(RUNTIME)
public @interface TransferCurrencyCountryCode {

    /**
     * Pesan error default.
     * 
     * @return pesan error
     */
    String message() default "Untuk Country Code 'ID', Transaction Currency wajib '360' (IDR)";

    /**
     * Validation groups.
     * 
     * @return array groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload untuk extensibility.
     * 
     * @return array payload
     */
    Class<? extends Payload>[] payload() default {};
}
