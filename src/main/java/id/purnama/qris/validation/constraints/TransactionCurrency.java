package id.purnama.qris.validation.constraints;

import id.purnama.qris.validation.TransactionCurrencyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.7</b> Transaction Currency (ID "53")<br />
 * <b>4.7.7.1</b> Transaction Currency harus mengacu pada [ISO 4217] dan merupakan 3 digit angka yang merepresentasikan mata uang . Indonesia Rupiah direpresentasikan oleh Value "360". Value tersebut wajib digunakan jika Value ID “58” adalah “ID” (Indonesia) <br />
 */
@Documented
@Constraint(validatedBy = {TransactionCurrencyValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface TransactionCurrency {

    String message() default "Transaction Currency harus mengacu pada [ISO 4217] dan merupakan 3 digit angka yang merepresentasikan mata uang";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
