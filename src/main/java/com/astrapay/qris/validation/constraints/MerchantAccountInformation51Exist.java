package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.MerchantAccountInformation51ExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.7</b> Merchant Account Information Template (ID “51”) ID “51” wajib ditampilkan jika Value dari Point of Initiation Method “11”.<br />
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformation51ExistValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface MerchantAccountInformation51Exist {

        /**
     *
     * @return String
     */
    String message() default "ID 51 wajib ditampilkan jika Value dari Point of Initiation Method 11.";

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
