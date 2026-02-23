package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TransferMerchantCategoryCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validasi Merchant Category Code untuk QRIS MPM Transfer.
 * <p>
 * Sesuai spesifikasi 4.2 Verifikasi Data – QRIS MPM Transaksi Transfer:
 * </p>
 * <ul>
 *     <li>MCC yang diperbolehkan untuk QRIS MPM Transfer: <b>4829</b> (Transfer)</li>
 *     <li>Jika MCC selain nilai tersebut, transaksi TIDAK DAPAT diproses</li>
 * </ul>
 * 
 */
@Documented
@Constraint(validatedBy = {TransferMerchantCategoryCodeValidator.class})
@Target({FIELD, TYPE})
@Retention(RUNTIME)
public @interface TransferMerchantCategoryCode {

    /**
     * Pesan error default.
     * 
     * @return pesan error
     */
    String message() default "Merchant Category Code (ID 52) untuk Transfer wajib bernilai '4829'";

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
