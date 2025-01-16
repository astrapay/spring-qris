package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.CRCLastPositionCustomValidator;
import com.astrapay.qris.mpm.validation.CRCLastPositionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>3.2 Organisasi Data</b>
 * CRC (ID "63") adalah data object terakhir di bawah root.<br/>
 * <b>4.6.1.2</b> CRC (ID "63") harus menjadi urutan terakhir data object dalam QR Code.
 */
@Documented
@Constraint(validatedBy = {CRCLastPositionCustomValidator.class})
@Target({ElementType.METHOD, FIELD})
@Retention(RUNTIME)
public @interface CRCCustom {

    /**
     *
     * @return String
     */
    String message() default "CRC custom";

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
