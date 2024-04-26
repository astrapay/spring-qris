package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.AdditionalDataFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.8</b> Data objects—Additional Data Field Template (ID "62")
 * <b>4.8.1.1</b> Jika ditampilkan, minimal berisi satu data object.
 */
@Documented
@Constraint(validatedBy = {AdditionalDataFieldValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface AdditionalDataField {

        /**
     *
     * @return String
     */
    String message() default "Data objects—Additional Data Field Template (ID 62) jika ditampilkan, minimal berisi satu data object.";

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
