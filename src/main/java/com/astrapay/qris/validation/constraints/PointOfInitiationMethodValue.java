package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.PointOfInitiationMethodValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.2.1</b> Jika tersedia, maka Point of Initiation Method harus berisi Value "11" atau "12".<br/>
 * Data Object ini mengidentifikasikan teknologi yang digunakan dalam QR Code, apakah datanya statis atau dinamis. Value lainnya diperuntukkan dalam penggunaan yang belum didefinisikan. <br/>
 * Point of Initiation Method memiliki Value "11" untuk QR Code statis dan Value "12" untuk QR Code dinamis:
 * <ul>
 *     <li>Value "11" digunakan saat QR Code yang sama ditampilkan pada setiap transaksi.</li>
 *     <li>Value "12" digunakan saat QR Code baru dibuat dan ditampilkan untuk tiap-tiap transaksi.</li>
 * </ul>
 */
@Documented
@Constraint(validatedBy = {PointOfInitiationMethodValueValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface PointOfInitiationMethodValue {

        /**
     *
     * @return String
     */
    String message() default "Point of Initiation Method harus berisi Value 11 atau 12.";

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
