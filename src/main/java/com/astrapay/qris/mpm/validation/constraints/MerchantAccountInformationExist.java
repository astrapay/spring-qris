package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MerchantAccountInformationExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.3.1</b> Setidaknya satu data object Merchant Account Information dari "02" - "51" harus ditampilkan.
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformationExistValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface MerchantAccountInformationExist {

        /**
     *
     * @return String
     */
    String message() default "Setidaknya satu data object Merchant Account Information dari 02 - 51 harus ditampilkan.";

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
