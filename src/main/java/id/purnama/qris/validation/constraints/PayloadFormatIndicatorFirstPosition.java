package id.purnama.qris.validation.constraints;

import id.purnama.qris.validation.PayloadFormatIndicatorFirstPositionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>3.2 Organisasi Data</b>
 * Payload Format Indicator (ID "00") adalah data object pertama di bawah root.<br/>
 * <b>4.6.1.1</b> Payload Format Indicator (ID "00") harus menjadi urutan pertama data object dalam QR Code.
 */
@Documented
@Constraint(validatedBy = {PayloadFormatIndicatorFirstPositionValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface PayloadFormatIndicatorFirstPosition {

    String message() default "Payload Format Indicator (ID \"00\") harus menjadi urutan pertama data object dalam QR Code.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
