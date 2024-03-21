package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.LanguagePreferenceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.9.2</b> Language Preference (ID "00")
 * <b>4.9.2.1</b> Language Preference wajib berisi dua karakter alfabet yang didefinisikan oleh [ISO 639]. Value pada data object Language Preference (ID "00") harus sesuai dengan Merchant Name—Alternate Language dan Merchant City—Alternate Language.
 */
@Documented
@Constraint(validatedBy = {LanguagePreferenceValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface LanguagePreferance {

        /**
     *
     * @return String
     */
    String message() default "Language Preference wajib berisi dua karakter alfabet yang didefinisikan oleh [ISO 639].";

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
