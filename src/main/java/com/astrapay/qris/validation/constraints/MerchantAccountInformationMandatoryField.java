package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.MerchantAccountInformationMandatoryFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Arthur Purnama
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformationMandatoryFieldValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(MerchantAccountInformationMandatoryField.List.class)
public @interface MerchantAccountInformationMandatoryField {

    /**
     *
     * @return String
     */
    String message() default "Merchant Account Information field {id} is mandatory";

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
    int from() default 0;
    /**
     *
     * @return int
     */
    int to() default 0;
    /**
     *
     * @return int
     */
    int id() default 0;

    /**
     * Defines several {@link MerchantAccountInformationMandatoryField} annotations on the same element.
     *
     * @see MerchantAccountInformationMandatoryField
     */
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return MerchantAccountInformationMandatoryField
         */
        MerchantAccountInformationMandatoryField[] value();
    }
}
