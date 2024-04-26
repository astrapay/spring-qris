package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.CpmMandatoryFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CpmMandatoryFieldValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(CpmMandatoryField.List.class)
public @interface CpmMandatoryField {

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
     * @return String
     */
    String id() default "0";

    /**
     * Defines several {@link CpmMandatoryField} annotations on the same element.
     *
     * @see CpmMandatoryField
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return MerchantAccountInformationMandatoryField
         */
        CpmMandatoryField[] value();
    }
}
