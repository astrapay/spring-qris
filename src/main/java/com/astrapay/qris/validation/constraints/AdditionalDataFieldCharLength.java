package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.AdditionalDataFieldCharLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Tabel 3.7 Data objects for Additional Data Field Template (ID "62")
 * <table>
 *     <tr><td>Name</td><td>ID</td><td>Format</td><td>Panjang karakter</td><td>Ketersediaan</td></tr>
 *     <tr><td>Bill Number</td><td>"01"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Mobile Number</td><td>"02"</td><td>ans</td><td>var. up to "25 "</td><td>O</td></tr>
 *     <tr><td>Store Label</td><td>"03"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Loyalty Number</td><td>"04"/td><td>ans/td><td>var. up to "25"/td><td>O</td></tr>
 *     <tr><td>Reference Label</td><td>"05"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Customer Label</td><td>"06"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Terminal label</td><td>"07"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Purpose of Transaction</td><td>"08"</td><td>ans</td><td>var. up to "25"</td><td>O</td></tr>
 *     <tr><td>Additional Consumer Data Request</td><td>"09"</td><td>ans</td><td>var. up to "03"</td><td>O</td></tr>
 *     <tr><td>RFU for EMVCo</td><td>"10"-"49"</td><td>S</td><td>-</td><td>O</td></tr>
 *     <tr></td><td>Payment System specific templates.</td><td>"50"-"98"</td><td>S</td><td>-</td><td>O</td></tr>
 *     <tr><td>Proprietary data</td><td>"99"</td><td>ans</td><td>var. up to "95"</td><td>O</td></tr>
 * </table>
 */
@Documented
@Constraint(validatedBy = {AdditionalDataFieldCharLengthValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
@Repeatable(AdditionalDataFieldCharLength.List.class)
public @interface AdditionalDataFieldCharLength {

        /**
     *
     * @return String
     */
    String message() default "ID 62 field from {from} to {to} have min:{min} and max:{max} characters long.";

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
    int min() default 0;
    /**
     *
     * @return int
     */
    int max() default Integer.MAX_VALUE;

    /**
     * Defines several {@link AdditionalDataFieldCharLength} annotations on the same element.
     *
     * @see AdditionalDataFieldCharLength
     */
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        /**
         *
         * @return MerchantAccountInformationCharLength
         */
        AdditionalDataFieldCharLength[] value();
    }
}
