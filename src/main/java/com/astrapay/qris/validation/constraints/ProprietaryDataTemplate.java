package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.ProprietaryDataTemplateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>Tabel 4.8:</b> Data object turunan (ID “99”) dalam (ID “62”) Proprietary data Template
 * <table>
 *     <caption><b>Tabel 4.8:</b> Data object turunan (ID “99”) dalam (ID “62”) Proprietary data Template</caption>
 *     <tr>
 *         <th>ID</th><th>Definisi</th><th>Format</th><th>Panjang Karakter</th><th>Ketersediaan</th><th>Keterangan</th>
 *     </tr>
 *     <tr>
 *         <td>“00”</td><td>Globally Unique Identifier</td><td>ans</td><td>var. up to "32"</td><td>M</td><td>Disarankan merupakan default value=’00’</td>
 *     </tr>
 *     <tr>
 *         <td>“01”</td><td>Proprietary</td><td>ans</td><td>var. up to “81”</td><td>M</td><td></td>
 *     </tr>
 * </table>
 */
@Documented
@Constraint(validatedBy = {ProprietaryDataTemplateValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface ProprietaryDataTemplate {

        /**
     *
     * @return String
     */
    String message() default "Proprietary data template tidak sesuai.";

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