package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.AdditionalDataFieldAsteriskValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.8.1.2</b> Jika ditampilkan, konten dari data object ID "01" - "08" wajib berupa "***" (tiga karakter asterisk) atau Value yang telah didefinisi oleh merchant. Ketersediaan dari "***" (tiga karakter asterisk) mengindikasikan bahwa aplikasi mobile meminta untuk memasukan informasi yang diperlukan.<br />
 */
@Documented
@Constraint(validatedBy = {AdditionalDataFieldAsteriskValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface AdditionalDataFieldAsterisk {

        /**
     *
     * @return String
     */
    String message() default "Jika ditampilkan, konten dari data object ID 01 - 08 wajib berupa '***' (tiga karakter asterisk) atau Value yang telah didefinisi oleh merchant.";

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
