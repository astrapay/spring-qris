package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MandatoryFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Arthur Purnama
 */
@Documented
@Constraint(validatedBy = {MandatoryFieldValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(MandatoryField.List.class)
public @interface MandatoryField {

    /**
     *
     * @return String
     */
    String message() default "Field {id} is mandatory";

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
     * @return int
     */
    int id() default 0;

    /**
     * Defines several {@link MandatoryField} annotations on the same element.
     *
     * @see MandatoryField
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return MerchantAccountInformationMandatoryField
         */
        MandatoryField[] value();
    }
}
