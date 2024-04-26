package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MerchantAccountInformationPanIsNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Arthur Purnama
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformationPanIsNumberValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface MerchantAccountInformationPanIsNumber {

        /**
     *
     * @return String
     */
    String message() default "PAN should be a number";

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

    /**
     * @return id
     */
    int id() default 0;

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max() default Integer.MAX_VALUE;
}
