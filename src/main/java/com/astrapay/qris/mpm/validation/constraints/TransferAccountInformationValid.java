package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.TransferAccountInformationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom validation annotation untuk memvalidasi Transfer Account Information (ID "40") pada QRIS Transfer.
 * <p>
 * Annotation ini memvalidasi bahwa Transfer Account Information memiliki struktur yang benar
 * dengan sub-tags yang mandatory sesuai spesifikasi QRIS Transfer:
 * </p>
 * 
 * <p><b>Sub-tags Transfer Account Information (ID "40"):</b></p>
 * <ul>
 *     <li><b>00: Reverse Domain</b> (M) - var up to 32 chars
 *         <br/>Format: reverse domain dari acquiring bank
 *         <br/>Contoh: "ID.CO.PJSPNAME1.WWW"</li>
 *     <li><b>01: Customer PAN</b> (M) - 16-19 digit numeric
 *         <br/>Primary Account Number dari customer/sender
 *         <br/>Menggunakan National Numbering System (NNS) pada 8 digit pertama</li>
 *     <li><b>02: Beneficiary ID</b> (M) - var up to 15 chars
 *         <br/>ID/account number penerima transfer
 *         <br/>Exclude pipe "|" character</li>
 *     <li><b>04: Bank Identifier Code</b> (O) - 8-11 chars
 *         <br/>BIC/SWIFT code bank tujuan (optional)</li>
 * </ul>
 * 
 * <p><b>Validasi yang dilakukan:</b></p>
 * <ol>
 *     <li>Tag 40 wajib ada pada Transfer payload</li>
 *     <li>Sub-tag 00 (Reverse Domain) wajib ada dan tidak kosong</li>
 *     <li>Sub-tag 01 (Customer PAN) wajib ada, numeric, dan panjang 16-19 digit</li>
 *     <li>Sub-tag 02 (Beneficiary ID) wajib ada dan tidak kosong</li>
 *     <li>Sub-tag 04 (BIC) jika ada harus panjang 8-11 karakter</li>
 *     <li>Customer PAN harus menggunakan National Numbering System (8 digit pertama)</li>
 * </ol>
 * 
 * <p><b>Contoh penggunaan:</b></p>
 * <pre>
 * &#64;TransferAccountInformationValid
 * public class QrisTransferPayload extends QrisPayload {
 *     // class implementation
 * }
 * </pre>
 * 
 * <p><b>Referensi:</b></p>
 * <ul>
 *     <li>Tabel 3.20 - Data Object QRIS MPM Transfer</li>
 *     <li>ISO 8583 - DE 02 (Primary Account Number)</li>
 *     <li>ISO 8583 - DE 103 (Beneficiary ID)</li>
 *     <li>ISO 8583 - DE 48 Tag BC (Bank Identifier Code)</li>
 * </ul>
 *
 * @see QrisTransferPayload
 * @see TransferAccountInformationValidator
 */
@Documented
@Constraint(validatedBy = {TransferAccountInformationValidator.class})
@Target({TYPE, FIELD})
@Retention(RUNTIME)
public @interface TransferAccountInformationValid {

    /**
     * Error message yang akan ditampilkan jika validasi gagal.
     * 
     * @return Default error message
     */
    String message() default "Transfer Account Information (ID 40) is invalid or incomplete. " +
            "Required sub-tags: 00 (Reverse Domain), 01 (Customer PAN 16-19 digits), 02 (Beneficiary ID)";

    /**
     * Validation groups untuk conditional validation.
     * 
     * @return Array of validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload untuk metadata validation.
     * 
     * @return Array of payload types
     */
    Class<? extends Payload>[] payload() default {};
}
