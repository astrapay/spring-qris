package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.Track2EquivalentDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {Track2EquivalentDataValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Track2EquivalentData {
    /**
     *
     * @return String
     */
    String message() default "Hex value for this field is not in valid format";

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

    String id() default "57";
}
