package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TransferPointOfInitiationMethodValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validasi Point of Initiation Method untuk QRIS MPM Transfer.
 * <p>
 * Sesuai spesifikasi 4.2 Verifikasi Data – QRIS MPM Transaksi Transfer:
 * </p>
 * <ul>
 *     <li>Point of Initiation Method (ID "01") wajib ada</li>
 *     <li>Nilai WAJIB = "12" (Dynamic QR)</li>
 *     <li>Jika nilai bukan "12", transaksi TIDAK BOLEH diteruskan</li>
 * </ul>
 * 
 */
@Documented
@Constraint(validatedBy = {TransferPointOfInitiationMethodValidator.class})
@Target({FIELD, TYPE})
@Retention(RUNTIME)
public @interface TransferPointOfInitiationMethod {

    /**
     * Pesan error default.
     * 
     * @return pesan error
     */
    String message() default "Point of Initiation Method (ID 01) untuk Transfer wajib bernilai '12'";

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
