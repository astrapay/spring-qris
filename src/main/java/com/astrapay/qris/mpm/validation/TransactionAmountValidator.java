package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.validation.constraints.TransactionAmount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class TransactionAmountValidator implements ConstraintValidator<TransactionAmount, Map<Integer, QrisDataObject>> {

    private int id;

    @Override
    public void initialize(TransactionAmount constraintAnnotation) {
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if (value.get(this.id) != null && isValidAmount(value.get(this.id).getValue())) {
            double amount = Double.parseDouble(value.get(this.id).getValue());
            return amount > 0;
        }
        return true;
    }

    private boolean isValidAmount(String str) {
        // Regex to check valid amount.
        String regex = "^(([1-9]\\d{0,2}(\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d{0,2})?$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
