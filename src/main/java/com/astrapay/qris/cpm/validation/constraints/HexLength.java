package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.HexLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {HexLengthValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(HexLength.List.class)
public @interface HexLength {

    String message() default "Field {id} should have min:{min} and max:{max} characters long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String id() default "";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    /**
     * Defines several {@link HexLength} annotations on the same element.
     *
     * @see HexLength
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return MerchantAccountInformationMandatoryField
         */
        HexLength[] value();
    }
}
