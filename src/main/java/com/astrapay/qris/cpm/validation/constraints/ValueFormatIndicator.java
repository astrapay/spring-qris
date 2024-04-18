package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.ValueFormatIndicatorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ValueFormatIndicatorValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValueFormatIndicator {

    /**
     *
     * @return String
     */
    String message() default "Value format indicator value is not CPV01";

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
     *
     * @return String
     */
    String stringValue() default "CPV01";

    /**
     *
     * @return hex Value of CPV01
     */
    String hexValue() default "4350563031";

    String id() default "85";
}
