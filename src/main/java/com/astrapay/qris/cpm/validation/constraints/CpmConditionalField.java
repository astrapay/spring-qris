package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.CpmConditionalFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CpmConditionalFieldValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface CpmConditionalField {

        /**
        *
        * @return String
        */
        String message() default "Whether 5A Tag or 57 Tag must be present, or if both are present, the value of 5A Tag is not equal with 57 PAN value";

        /**
         *
         * @return String
         */
        String id() default "0";

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
         * @return label of tag 5A
         */
        String applicationPanTag() default "5A";

        /**
         *
         * @return field separator label
         */
        String fieldSeparator() default "D";

        /**
         *
         * @return 0 as start index
         */
        int startIndex() default 0;
}
