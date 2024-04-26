package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TipValuePercentageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.11.1</b> Tip Value Percentage wajib ditampilkan hanya jika data object Tip Indicator (ID "55") menampilkan Value "03" dengan Value yang digunakan berkisar antara “00.01” dan “99.99” untuk merepresentasikan persentase 0.01% hingga 99.99%.
 */
@Documented
@Constraint(validatedBy = {TipValuePercentageValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface TipValuePercentage {

    /**
     *
     * @return String
     */
    String message() default "Value yang digunakan berkisar antara 00.01 dan 99.99";

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
