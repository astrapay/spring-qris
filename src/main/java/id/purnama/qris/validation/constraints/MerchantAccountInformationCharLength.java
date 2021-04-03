package id.purnama.qris.validation.constraints;

import id.purnama.qris.validation.MerchantAccountInformationCharLengthValidator;

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

    String message() default "Merchant Account Information field \"{id}\" have min:{min} and max{max} characters long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int from() default 0;

    int to() default 0;

    int id() default 0;

    int min() default 0;

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

        MerchantAccountInformationCharLength[] value();
    }
}
