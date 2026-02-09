package com.astrapay.qris.mpm.validation.constraints;

import com.astrapay.qris.mpm.validation.PurposeOfTransactionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom validation annotation untuk memvalidasi Purpose of Transaction pada QRIS Transfer.
 * <p>
 * Annotation ini memvalidasi bahwa Additional Data Field Template (ID "62") memiliki
 * Purpose of Transaction (tag "08") dengan value yang valid untuk QRIS Transfer.
 * </p>
 * 
 * <p><b>Valid Purpose Values:</b></p>
 * <ul>
 *     <li><b>BOOK</b> - Transfer/Booking
 *         <br/>Digunakan untuk transfer antar rekening atau booking pembayaran</li>
 *     <li><b>DMCT</b> - Debit Merchant Credit Transfer
 *         <br/>Digunakan untuk pembayaran ke merchant dengan credit transfer</li>
 *     <li><b>XBCT</b> - Cross Border Credit Transfer
 *         <br/>Digunakan untuk transfer lintas negara (cross border)</li>
 * </ul>
 * 
 * <p><b>Struktur Data:</b></p>
 * <pre>
 * Root QR:
 *   ├─ ID "62" (Additional Data Field Template)
 *   │    ├─ tag "08" (Purpose of Transaction) ← VALIDATED HERE
 *   │    │    └─ value: "BOOK" | "DMCT" | "XBCT"
 *   │    ├─ tag "99" (Unique per Generated)
 *   │    └─ tag "00" (Default Value)
 * </pre>
 * 
 * <p><b>Validasi yang dilakukan:</b></p>
 * <ol>
 *     <li>Tag 62 (Additional Data) wajib ada pada Transfer payload</li>
 *     <li>Sub-tag 08 (Purpose of Transaction) wajib ada di dalam tag 62</li>
 *     <li>Value dari tag 08 harus salah satu dari: BOOK, DMCT, atau XBCT</li>
 *     <li>Value tidak boleh kosong atau null</li>
 *     <li>Value harus match exact (case-sensitive)</li>
 * </ol>
 * 
 * <p><b>Type Detection Logic:</b></p>
 * <p>
 * Purpose of Transaction digunakan sebagai identifier untuk mendeteksi apakah QR text
 * merupakan Transfer atau MPM Payment:
 * </p>
 * <pre>
 * if (tag 62 contains tag 08 with value BOOK/DMCT/XBCT) {
 *     return TRANSFER;
 * } else {
 *     return MPM_PAYMENT;
 * }
 * </pre>
 * 
 * <p><b>Contoh penggunaan:</b></p>
 * <pre>
 * &#64;PurposeOfTransactionValid
 * public class QrisTransferPayload extends QrisPayload {
 *     // class implementation
 * }
 * </pre>
 * 
 * <p><b>Contoh QR Text dengan Purpose:</b></p>
 * <pre>
 * ...62380804DMCT99041234...
 * 
 * Breakdown tag 62:
 * - 6238: ID "62", length 38
 * - 0804: tag "08", length 04
 * - DMCT: Purpose value
 * - 9904: tag "99", length 04
 * - 1234: Unique per Generated value
 * </pre>
 * 
 * <p><b>Referensi:</b></p>
 * <ul>
 *     <li>Tabel 3.20 - Data Object QRIS MPM Transfer</li>
 *     <li>Section 5 - Data Mapping ISO8583</li>
 *     <li>DE 57-tag 08 - Purpose of Transaction</li>
 * </ul>
 * 
 * @author Arthur Purnama
 * @see QrisTransferPayload
 * @see PurposeOfTransactionValidator
 * @see QrisType#TRANSFER
 */
@Documented
@Constraint(validatedBy = {PurposeOfTransactionValidator.class})
@Target({TYPE, FIELD})
@Retention(RUNTIME)
public @interface PurposeOfTransactionValid {

    /**
     * Error message yang akan ditampilkan jika validasi gagal.
     * 
     * @return Default error message
     */
    String message() default "Purpose of Transaction (tag 62->08) is missing or invalid. " +
            "Must be one of: BOOK, DMCT, XBCT";

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
