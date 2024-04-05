package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.MerchantAccountInformationCharLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.5.4</b> Merchant PAN yang mengacu pada ID “26”-“45” dengan sub ID “01” menandakan merchant yang melakukan transaksi, panjang karakter dari Value tersebut mencapai 19 digit.
 */
@Documented
@Constraint(validatedBy = {MerchantAccountInformationCharLengthValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(MerchantAccountInformationCharLength.List.class)
public @interface MerchantAccountInformationCharLength {

    /**
     *
     * @return String
     */
    String message() default "Merchant Account Information field {id} have min:{min} and max:{max} characters long.";

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
     *
     * @return int
     */
    int min() default 0;
    /**
     *
     * @return int
     */
    int max() default Integer.MAX_VALUE;

    /**
     * Defines several {@link MerchantAccountInformationCharLength} annotations on the same element.
     *
     * @see MerchantAccountInformationCharLength
     */
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        /**
         *
         * @return MerchantAccountInformationCharLength
         */
        MerchantAccountInformationCharLength[] value();
    }
}
