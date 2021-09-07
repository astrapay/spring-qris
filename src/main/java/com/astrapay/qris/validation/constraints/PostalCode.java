package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.PostalCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.15</b> Postal Code (ID "61")<br/>
 * Postal Code untuk mengindikasikan kode pos tempat merchant beroperasi. Wajib ditampilkan jika Value ID “58” adalah “ID” (Indonesia).
 */
@Documented
@Constraint(validatedBy = {PostalCodeValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface PostalCode {

        /**
     *
     * @return String
     */
    String message() default "Kode Pos wajib ditampilkan jika Value ID 58 adalah ID (Indonesia).";

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
