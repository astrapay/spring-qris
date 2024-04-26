package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.IdNotNullValidator;

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
@Constraint(validatedBy = {IdNotNullValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(IdNotNull.List.class)
public @interface IdNotNull {

    /**
     *
     * @return String
     */
    String message() default "ID: {id} tidak boleh kosong.";

    /**
     *
     * @return class
     */
    Class<?>[] groups() default {};

    /**
     *
     * @return int
     */
    int id() default 0;

    /**
     *
     * @return class
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link IdNotNull} annotations on the same element.
     *
     * @see IdNotNull
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return IdNotNull
         */
        IdNotNull[] value();
    }
}
