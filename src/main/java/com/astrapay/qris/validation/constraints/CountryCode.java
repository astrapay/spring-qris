package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.CountryCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.12</b> Country Code (ID "58")<br />
 * <b>4.7.12.1</b> Country Code wajib mengandung Value yang didefinisikan oleh [ISO 3166-1 alpha 2]. Sebagai contoh, Indonesia memiliki Value data object "ID".
 */
@Documented
@Constraint(validatedBy = {CountryCodeValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface CountryCode {

        /**
     *
     * @return String
     */
    String message() default "Country Code wajib mengandung Value yang didefinisikan oleh [ISO 3166-1 alpha 2].";

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
