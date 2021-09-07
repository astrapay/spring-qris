package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.PayloadFormatIndicatorValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.1.1</b> Payload Format Indicator wajib memiliki Value “01”.
 */
@Documented
@Constraint(validatedBy = {PayloadFormatIndicatorValueValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface PayloadFormatIndicatorValue {

        /**
     *
     * @return String
     */
    String message() default "Payload Format Indicator wajib memiliki Value 01.";

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
