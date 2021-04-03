package id.purnama.qris.validation.constraints;

import id.purnama.qris.validation.ProprietaryDomesticDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.8.1.4</b> Jika ID “99” diisi, maka template mengacu pada Tabel 4.8 dengan panjang karakter yang akan tidak lebih dari 91 karakter.
 */
@Documented
@Constraint(validatedBy = {ProprietaryDomesticDataValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface ProprietaryDomesticData {

    String message() default "Jika ID “99” diisi, maka template mengacu pada Tabel 4.8 dengan panjang karakter yang akan tidak lebih dari 91 karakter.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
