package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.LengthValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.4</b> Panjang Karakter Data object <br/>
 * <b>4.4.1.1</b> Panjang karakter wajib sama dengan jumlah karakter dalam Value field.
 */
@Documented
@Constraint(validatedBy = {LengthValueValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface LengthValue {

        /**
     *
     * @return String
     */
    String message() default "Panjang karakter wajib sama dengan jumlah karakter dalam Value field.";

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
