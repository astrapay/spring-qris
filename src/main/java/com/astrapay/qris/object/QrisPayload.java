package com.astrapay.qris.object;

import com.astrapay.qris.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author Arthur Purnama
 */
@CheckSum
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QrisPayload {

    /**
     * <b>4.1 Payload</b>
     * <p>Panjang karakter dari konten QR Code sebaiknya tidak melebihi 512 karakter. Harap diperhatikan apabila menggunakan karakter Unicode karena akan berdampak pada sisa kapasitas payload.</p>
     */
    @Size(max = 512)
    private String payload;

    /**
     * * <b>3.2 Organisasi Data</b>
     * <p>Payload Format Indicator (ID "00") adalah data object pertama di bawah root. CRC (ID "63") adalah data object terakhir di bawah root.</p>
     * <b>Tabel 3.6 Data objects di bawah root QR Code</b>
     * <table>
     *     <tr><td>Nama</td><td>ID</td><td>Format</td><td>Panjang</td><td>Karakter</td><td>Ketersediaan</td><td>Keterangan</td></tr>
     *     <tr><td>Payload Format Indicator</td><td>"00"</td><td></td><td>"02"</td><td>M</td><td>-</td></tr>
     *     <tr><td>Point of Initiation Method</td><td>"01"</td><td>N</td><td>"02"</td><td>O</td><td>-</td></tr>
     *     <tr><td>Merchant Account Information</td><td>"02"- "45"</td><td>ans</td><td>var. up to "99"</td><td>C</td><td>Wajib ditampilkan minimal satu merchant account information jika ID 51 tidak diisi.</td></tr>
     *     <tr><td>Merchant Account Information Domestic Central Repository</td><td>“51”</td><td>ans</td><td>var. up to "99"</td><td>C</td><td>Konten data object mengacu pada Tabel 4.5</td></tr>
     *     <tr><td>Merchant Category Code</td><td>"52"</td><td>N</td><td>"04"</td><td>M</td><td>Konten mengacu pada ISO 18245</td></tr>
     *     <tr><td>Transaction Currency</td><td>"53"</td><td>N</td><td>"03"</td><td>M</td><td>Konten mengacu pada ISO 4217. Kode mata uang untuk Rupiah Indonesia: ‘360’</td></tr>
     *     <tr><td>Transaction Amount</td><td>"54"</td><td>ans</td><td>var. up to "13"</td><td>C</td><td>Tidak tersedia jika aplikasi mobile meminta konsumen memasukan nominal transaksi atau sebaliknya.</td></tr>
     *     <tr><td>Tip Indicator</td><td>"55"</td><td>N</td><td>"02"</td><td>O</td><td>-</td></tr>
     *     <tr><td>Tip Value of Fixed</td><td>"56"</td><td>ans</td><td>var. up to "13"</td><td>C</td><td>Ketersediaan data object dari Tip Indicator.</td></tr>
     *     <tr><td>Tip Value of Percentage</td><td>"57"</td><td>ans</td><td>var. up to "05"</td><td>C</td><td>Ketersediaan data object dari Tip Indicator.</td></tr>
     *     <tr><td>Country Code</td><td>"58"</td><td>ans</td><td>"02"</td><td>M</td><td>Merujuk ISO 3166-1 alpha 2. Country Code Indonesia: ‘ID ,<br/> *exclude “|” pipe karakter</td></tr>
     *     <tr><td>Merchant Name</td><td>"59"</td><td>ans</td><td>var. up to "25"</td><td>M</td><td>- ,<br/> *exclude “|” pipe karakter</td></tr>
     *     <tr><td>Merchant City</td><td>"60"</td><td>ans</td><td>var. up to "15"</td><td>M</td><td>- ,<br/> *exclude “|” pipe karakter</td></tr>
     *     <tr><td>Postal Code</td><td>"61"</td><td>ans</td><td>var. up to "10"</td><td>O</td><td>Jika Value ID “58” adalah “ID” (Indonesia) maka data postal code menjadi [M] – Mandatory <br/> *exclude “|” pipe karakter</td></tr>
     *     <tr><td>Additional Data Field Template</td><td>"62"</td><td>S</td><td>var. up to "99"</td><td>O</td><td>Data tambahan yang dimuat dalam QR Code,<br/>Untuk daftar dari data object dapat dilihat pada Tabel 3.7</td></tr>
     *     <tr><td>Merchant Information— Language Template</td><td>"64"</td><td>S</td><td>var. up to "99"</td><td>O</td><td>Merchant Information— Language Template memuat informasi merchant dalam bahasa alternatif yang dapat menggunakan karakter khusus. Informasi ada di root QR Code. <br/>Daftar data object dapat dilihat pada Tabel 3.8</td></tr>
     *     <tr><td>RFU for EMVCo</td><td>"65"- "79"</td><td>S</td><td>var. up to "99"</td><td>O</td><td>Data object diperuntukkan bagi EMVCo</td></tr>
     *     <tr><td>Unreserved Templates</td><td>"80"- "99"</td><td>S</td><td>var. up to "99"</td><td>O</td><td>Belum ditetapkan peruntukannya</td></tr>
     *     <tr><td>CRC</td><td>"63"</td><td>ans</td><td>"04"</td><td>M</td><td>Merujuk ISO/IEC 13239 dan menggunakan polynomial '1021' (hex) dan initial Value 'FFFF' (hex)</td></tr>
     * </table>
     * <b>4.3.1.2</b> Hanya boleh terdapat satu data object dengan ID spesifik di bawah root QR Code dan hanya boleh terdapat satu ID spesifik dalam template-nya.
     * <p>
     * <b>4.7.2.1</b> Jika tersedia, maka Point of Initiation Method harus berisi Value "11" atau "12".<br/>
     * Data Object ini mengidentifikasikan teknologi yang digunakan dalam QR Code, apakah datanya statis atau dinamis. Value lainnya diperuntukkan dalam penggunaan yang belum didefinisikan. <br/>
     * Point of Initiation Method memiliki Value "11" untuk QR Code statis dan Value "12" untuk QR Code dinamis:
     *     <ul>
     *         <li>Value "11" digunakan saat QR Code yang sama ditampilkan pada setiap transaksi.</li>
     *         <li>Value "12" digunakan saat QR Code baru dibuat dan ditampilkan untuk tiap-tiap transaksi.</li>
     *     </ul>
     * </p>
     * <b>4.6.1.1</b> Payload Format Indicator (ID "00") harus menjadi urutan pertama data object dalam QR Code.<br/>
     * <b>4.6.1.2</b> CRC (ID "63") harus menjadi urutan terakhir data object dalam QR Code. Seluruh turunan data object root dapat ditempatkan di urutan lain. Data object dengan template seperti Additional Data Field Template (ID "62") atau Merchant Information—Language Template (ID "64"), dapat ditempatkan di urutan manapun di bawah template-nya.<br/>
     * <b>4.7.1.1</b> Payload Format Indicator wajib memiliki Value “01”. Value lainnya diperuntukkan dalam penggunaan yang belum didefinisikan.<br />
     * <b>4.7.3.1</b> Setidaknya satu data object Merchant Account Information dari "02" - "51" harus ditampilkan.<br />
     * <b>4.7.6</b> Merchant Category Code (ID "52")<br  />
     * <b>4.7.6.1</b> Merchant Category Code (MCC) harus memuat informasi MCC yang didefinisikan oleh [ISO 18245]. <br />
     * <b>4.7.7</b> Merchant Account Information Template (ID “51”) ID “51” wajib ditampilkan jika Value dari Point of Initiation Method “11”.<br />
     * <b>4.7.7</b> Transaction Currency (ID "53")<br />
     * <b>4.7.7.1</b> Transaction Currency harus mengacu pada [ISO 4217] dan merupakan 3 digit angka yang merepresentasikan mata uang . Indonesia Rupiah direpresentasikan oleh Value "360". Value tersebut wajib digunakan jika Value ID “58” adalah “ID” (Indonesia) <br />
     * <b>4.7.13</b> Merchant Name (ID "59")<br />
     * <b>4.7.13.1</b> Merchant Name wajib ditampilkan untuk mengidentifikasi nama merchant yang dapat dikenali oleh konsumen.<br />
     * <b>4.7.14</b> Merchant City (ID "60")<br />
     * <b>4.7.14.1</b> Merchant City wajib ditampilkan untuk mengindikasikan kota lokasi toko atau merchant beroperasi.<br />
     */
    @MandatoryField
    @MandatoryField(id = 52)
    @MandatoryField(id = 53)
    @MandatoryField(id = 58)
    @MandatoryField(id = 59)
    @MandatoryField(id = 60)
    @MandatoryField(id = 60)
    @MandatoryField(id = 63)
    @CharLength(min=2, max=2)
    @CharLength(from=1, to=1, min=2, max=2)
    @CharLength(from=2, to=51, min=1, max=99)
    @CharLength(from=52, to=52, min=4, max=4)
    @CharLength(from=53, to=53, min=3, max=3)
    @CharLength(from=54, to=54, min=1, max=13)
    @CharLength(from=55, to=55, min=2, max=2)
    @CharLength(from=56, to=56, min=1, max=13)
    @CharLength(from=57, to=57, min=1, max=5)
    @CharLength(from=58, to=58, min=2, max=2)
    @CharLength(from=59, to=59, min=1, max=25)
    @CharLength(from=60, to=60, min=1, max=15)
    @CharLength(from=61, to=61, min=1, max=10)
    @CharLength(from=62, to=62, min=1, max=99)
    @CharLength(from=63, to=63, min=4, max=4)
    @CharLength(from=64, to=99, min=1, max=99)
    @MerchantAccountInformation2To45Exist
    @PayloadFormatIndicatorFirstPosition
    @CRCLastPosition
    @PointOfInitiationMethodValue
    @PayloadFormatIndicatorValue
    @MerchantAccountInformationExist
    @MerchantAccountInformation51Exist
    @MerchantCategoryCode
    @TransactionCurrency
    @TransactionAmount(id = 54)
    @TipValueIndicator
    @TransactionAmount(id = 56)
    @TransactionAmount(id = 57)
    @TipValuePercentage
    @CountryCode
    @IdNotNull(id = 59)
    @IdNotNull(id = 60)
    @PostalCode
    @Valid
    private Map<Integer, QrisDataObject> qrisRoot;
}
