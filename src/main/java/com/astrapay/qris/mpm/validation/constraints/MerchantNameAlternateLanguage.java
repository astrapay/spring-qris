package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MerchantNameAlternateLanguageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.9.3</b> Merchant Name—Alternate Language (ID "01")
 * <b>4.9.3.1</b> Merchant Name—Alternate Language wajib ditampilkan. Merchant Name—Alternate Language direkomendasikan merupakan indikasi dari nama toko atau merchant dalam bahasa alternatif
 */
@Documented
@Constraint(validatedBy = {MerchantNameAlternateLanguageValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface MerchantNameAlternateLanguage {

        /**
     *
     * @return String
     */
    String message() default "Merchant Name—Alternate Language wajib ditampilkan.";

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
