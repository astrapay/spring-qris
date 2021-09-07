package com.astrapay.qris.validation.constraints;

import com.astrapay.qris.validation.TransactionAmountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>4.7.8</b> Transaction Amount (ID "54") <br />
 * <b>4.7.8.1</b> Jika ditampilkan, Transaction Amount Valuenya tidak boleh “0”, nilai yang digunakan harus digit “0” hingga ”9” dan tidak bernilai negatif. Apabila ada nilai desimal, karakter “.” (titik) digunakan sebagai pemisah antara nilai bulat dengan nilai desimalnya. Karakter “.” (titik) juga dapat digunakan tanpa nilai desimal. Karakter “.” (titik) termasuk penghitungan panjang karakter data object.<br />
 * Jumlah digit setelah tanda desimal harus sesuai dengan eksponen mata uang yang terkait dengan kode mata uang yang didefinisikan dalam [ISO 4217]. Tidak boleh menambahkan karakter lain (misalnya, tidak ada “ “ (spasi) atau “,” (koma) untuk memisahkan ribuan).<br />
 * Sebagai contoh untuk nominal transaksi Rp 1000,00
 * <ul>
 *     <li>Transaction Amounts yang sesuai: "1000.00", "1000" dan "1000."</li>
 *     <li>Transaction Amounts yang tidak sesuai: "1000,00", “1.000” dan "1 000".</li>
 * </ul>
 * <b>4.7.8.2</b> Transaction Amount tidak tersedia pada QR Code jika konsumen diharuskan untuk memasukan nominal transaksinya sendiri di dalam aplikasi mobile.
 */
@Documented
@Constraint(validatedBy = {TransactionAmountValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(TransactionAmount.List.class)
public @interface TransactionAmount {

    /**
     *
     * @return String
     */
    String message() default "Transaction Amount Valuenya tidak boleh 0, nilai yang digunakan harus digit 0 hingga 9 dan tidak bernilai negatif.";

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
    int id() default 0;

    /**
     * Defines several {@link TransactionAmount} annotations on the same element.
     *
     * @see TransactionAmount
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         *
         * @return TransactionAmount
         */
        TransactionAmount[] value();
    }
}
