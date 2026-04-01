package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.AdditionalDataFieldTransferValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>3.12</b> Data objects—Additional Data Field Template (ID "62")
 */
@Documented
@Constraint(validatedBy = {AdditionalDataFieldTransferValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface AdditionalDataFieldTransferChildIsExist {
    /**
     *
     * @return String
     */
    String message() default "Data objects—Additional Data Field Template (ID 62) missing tag 08,99,00 OR 01 for Transfer.";

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