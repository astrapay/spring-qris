package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.AdditionalConsumerDataRequestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.8.1.3</b> Jika ditampilkan Additional Consumer Data Request (ID "09") wajib berisi kombinasi karakter seperti "A", "M" dan/atau "E", kemudian wajib ada konten yang mencerminkan setiap karakter tersebut. <br/>
 * Jika satu atau beberapa karakter berikut muncul pada Additional Consumer Data Request (ID "09") konsumen harus mengisi seluruh informasi yang diminta dengan lengkap. Setiap karakter yang mewakili permintaan data tambahan, yaitu:
 * <ul>
 *     <li>"A" = Alamat konsumen;</li>
 *     <li>"M" = Nomor telepon konsumen;</li>
 *     <li>"E" = Alamat email konsumen;</li>
 * </ul>
 * hanya diperbolehkan muncul satu kali.
 * Contoh:
 * <ul>
 *     <li>0903AME -> diperbolehkan</li>
 *     <li>0902EA -> diperbolehkan</li>
 *     <li>0902MM -> tidak diperbolehkan</li>
 * </ul>
 */
@Documented
@Constraint(validatedBy = {AdditionalConsumerDataRequestValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface AdditionalConsumerDataRequest {

        /**
     *
     * @return String
     */
    String message() default "Jika ditampilkan Additional Consumer Data Request (ID 09) wajib berisi kombinasi karakter seperti A, M dan/atau E";

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
}
