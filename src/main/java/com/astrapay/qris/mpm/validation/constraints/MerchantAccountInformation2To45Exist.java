package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MerchantAccountInformation2To45ExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ID 2-45 Wajib ditampilkan minimal satu merchant account information jika ID 51 tidak diisi.
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformation2To45ExistValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface MerchantAccountInformation2To45Exist {

        /**
     *
     * @return String
     */
    String message() default "ID 2-45 Wajib ditampilkan minimal satu merchant account information jika ID 51 tidak diisi.";

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
