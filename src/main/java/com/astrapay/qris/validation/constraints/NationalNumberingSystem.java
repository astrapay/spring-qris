package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.NationalNumberingSystemValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.5.4</b> Merchant PAN yang mengacu pada ID “26”-“45” dengan sub ID “01” menandakan merchant yang melakukan transaksi, panjang karakter dari Value tersebut mencapai 19 digit.<br/>
 * <b>4.7.5.5</b> Delapan digit pertama merupakan NNS yang digunakan di Indonesia.
 */
@Documented
@Constraint(validatedBy = {NationalNumberingSystemValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface NationalNumberingSystem {

    /**
     *
     * @return String
     */
    String message() default "Delapan digit pertama Merchant PAN merupakan NNS yang digunakan di Indonesia.";

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
