package com.astrapay.qris.mpm.object;

import com.astrapay.qris.mpm.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Concrete implementation dari {@link QrisPayload} untuk QRIS Transfer.
 * <p>
 * Class ini merepresentasikan QRIS yang digunakan untuk transfer antar rekening atau pembayaran tuntas,
 * dengan validasi khusus yang sesuai dengan spesifikasi QRIS Transfer.
 * </p>
 * 
 * <p><b>4.2 Verifikasi Data – QRIS MPM Transaksi Transfer:</b></p>
 * <ol>
 *     <li><b>Point of Initiation Method (ID "01")</b>
 *         <ul>
 *             <li>Wajib ada</li>
 *             <li>Nilai WAJIB = "12" (Dynamic QR)</li>
 *             <li>Jika nilai bukan "12", transaksi TIDAK BOLEH diteruskan</li>
 *         </ul>
 *     </li>
 *     <li><b>Transfer Account Information (ID "40")</b>
 *         <ul>
 *             <li>PJP Pengirim meneruskan transaksi berdasarkan 8 digit pertama Customer PAN (ID "01") yang merupakan NNS</li>
 *             <li>Jika NNS tidak dikenali, transaksi TIDAK BOLEH diteruskan</li>
 *         </ul>
 *     </li>
 *     <li><b>Merchant Category Code (ID "52")</b>
 *         <ul>
 *             <li>Nilai MCC yang diperbolehkan untuk QRIS MPM Transfer: <b>4829</b> (Transfer)</li>
 *             <li>Jika MCC selain nilai di atas, transaksi TIDAK DAPAT diproses</li>
 *         </ul>
 *     </li>
 *     <li><b>Transaction Currency (ID "53") dan Country Code (ID "58")</b>
 *         <ul>
 *             <li>Jika Country Code (ID "58") = "ID": Transaction Currency (ID "53") WAJIB = "360"</li>
 *             <li>Jika kondisi ini tidak terpenuhi, transaksi TIDAK BOLEH diteruskan</li>
 *         </ul>
 *     </li>
 *     <li><b>Transaction Amount (ID "54")</b>
 *         <ul>
 *             <li>Jika ID "54" ADA: Nominal harus ditampilkan dan TIDAK BOLEH diubah oleh Pengirim dana</li>
 *             <li>Jika ID "54" TIDAK ADA: Aplikasi mobile menampilkan field input nominal</li>
 *             <li>Nilai Transaction Amount WAJIB ditampilkan pada halaman konfirmasi</li>
 *         </ul>
 *     </li>
 *     <li><b>Beneficiary Name (ID "59")</b>
 *         <ul>
 *             <li>Harus ditampilkan saat proses scan QR</li>
 *             <li>Wajib ditampilkan kembali pada halaman konfirmasi aplikasi mobile Pengirim dana</li>
 *         </ul>
 *     </li>
 *     <li><b>Purpose of Transaction (ID "62") Sub-ID "08"</b>
 *         <ul>
 *             <li>Nilai WAJIB sesuai dengan Table 3.23 (BOOK, DMCT, XBCT)</li>
 *             <li>Jika tidak sesuai, transaksi TIDAK BOLEH diteruskan</li>
 *             <li>Jika nilai = "BOOK": PJP Pengirim WAJIB memverifikasi bahwa transaksi merupakan overbooking (on-us transfer)</li>
 *         </ul>
 *     </li>
 *     <li><b>Unique per Generated (ID "62") Sub-ID "99"</b>
 *         <ul>
 *             <li>Jika PJP Pengirim menerima respon bahwa Unique per generated tidak valid, transaksi TIDAK BOLEH diteruskan</li>
 *         </ul>
 *     </li>
 * </ol>
 * 
 * <p><b>Karakteristik Transfer:</b></p>
 * <ul>
 *     <li>Wajib memiliki Transfer Account Information (ID 40) dengan sub-tags:
 *         <ul>
 *             <li>00: Reverse Domain - var up to 32 chars (contoh: ID.CO.PJSPNAME1.WWW)</li>
 *             <li>01: Customer PAN - 16-19 digit (format numeric)</li>
 *             <li>02: Beneficiary ID - var up to 15 chars</li>
 *             <li>04: Bank Identifier Code (BIC) - 8-11 chars (optional)</li>
 *         </ul>
 *     </li>
 *     <li>Wajib memiliki Additional Data (ID 62) dengan Purpose of Transaction (tag 08)
 *         yang berisi salah satu:
 *         <ul>
 *             <li><b>BOOK</b> - Transfer/booking</li>
 *             <li><b>DMCT</b> - Debit Merchant Credit Transfer</li>
 *             <li><b>XBCT</b> - Cross Border Credit Transfer</li>
 *         </ul>
 *     </li>
 *     <li>Transaction Currency (ID 53) mandatory - "360" untuk IDR jika Country Code "ID"</li>
 *     <li>Country Code (ID 58) mandatory - "ID" untuk Indonesia</li>
 *     <li>Beneficiary Name (ID 59) mandatory - maksimal 25 karakter</li>
 *     <li>Beneficiary City (ID 60) mandatory - maksimal 15 karakter</li>
 *     <li>Postal Code (ID 61) conditional - mandatory jika Country Code "ID"</li>
 *     <li>Point of Initiation Method (ID 01) mandatory - WAJIB "12" (Dynamic QR) untuk Transfer</li>
 *     <li>Merchant Category Code (ID 52) jika ada WAJIB "4829" (Transfer)</li>
 * </ul>
 * 
 * <p><b>Referensi Spesifikasi:</b></p>
 * <ul>
 *     <li><b>4.2</b> - Verifikasi Data QRIS MPM Transaksi Transfer</li>
 *     <li><b>Tabel 3.20</b> - QRIS MPM Transfer Data Structure</li>
 *     <li><b>Tabel 3.23</b> - Purpose of Transaction Values</li>
 * </ul>
 * 
 * <p><b>Data Objects Wajib untuk Transfer:</b></p>
 * <ul>
 *     <li>ID "00" - Payload Format Indicator (M) - value "01"</li>
 *     <li>ID "01" - Point of Initiation Method (M) - value "12" (Dynamic, WAJIB untuk Transfer)</li>
 *     <li>ID "40" - Transfer Account Information (M) - var up to 99</li>
 *     <li>ID "52" - Merchant Category Code (C) - jika ada WAJIB "4829"</li>
 *     <li>ID "53" - Transaction Currency (M) - "360" jika Country Code "ID"</li>
 *     <li>ID "54" - Transaction Amount (C) - var up to 13</li>
 *     <li>ID "58" - Country Code (M) - "ID"</li>
 *     <li>ID "59" - Beneficiary Name (M) - var up to 25</li>
 *     <li>ID "60" - Beneficiary City (M) - var up to 15</li>
 *     <li>ID "61" - Postal Code (C) - var up to 10</li>
 *     <li>ID "62" - Additional Data (M) dengan Purpose (tag 08: BOOK/DMCT/XBCT)</li>
 *     <li>ID "63" - CRC (M) - 4 chars</li>
 * </ul>
 * 
 * <p><b>Data Mapping ISO8583:</b></p>
 * <ul>
 *     <li>Customer PAN (tag 40->01) → DE 02 (Primary Account Number)</li>
 *     <li>Beneficiary ID (tag 40->02) → DE 103</li>
 *     <li>Bank Identifier Code (tag 40->04) → DE 48 Tag BC</li>
 *     <li>Merchant Category Code (ID 52) → DE 18</li>
 *     <li>Transaction Currency (ID 53) → DE 49</li>
 *     <li>Transaction Amount (ID 54) → DE 04</li>
 *     <li>Country Code (ID 58) → DE 43 posisi 39-40</li>
 *     <li>Beneficiary Name (ID 59) → DE 43 posisi 1-25</li>
 *     <li>Beneficiary City (ID 60) → DE 43 posisi 26-38</li>
 *     <li>Postal Code (ID 61) → DE 57</li>
 *     <li>Purpose of Transaction (tag 62->08) → DE 57-tag 08</li>
 * </ul>
 * 
 * <p><b>Contoh QR Text Transfer:</b></p>
 * <pre>
 * 00020101024064...0118936023451234567890...5303360540850000.005802ID5907ANTONIO6007JAKARTA
 * 61051031062380804DMCT990496266304CCEF
 * 
 * Breakdown:
 * - 0002: ID "00", length 02
 * - 01: value "01" (Payload Format)
 * - 0102: ID "01", length 02
 * - 12: value "12" (Dynamic QR)
 * - 4064: ID "40", length 64 (Transfer Account)
 * - ...sub-tags 00, 01, 02, 04...
 * - 5303: ID "53", length 03
 * - 360: value "360" (IDR)
 * - 6238: ID "62", length 38 (Additional Data)
 * - 0804: tag "08", length 04
 * - DMCT: Purpose of Transaction
 * </pre>
 * 
 * @see QrisPayload
 * @see QrisType#MPM_TRANSFER
 */
@NoArgsConstructor
@CheckSum
@TransferPointOfInitiationMethod  // Validasi ID 01 = "12"
@TransferMerchantCategoryCode     // Validasi MCC = "4829" jika ada
@TransferCurrencyCountryCode      // Validasi Currency "360" untuk Country "ID"
@TransferAccountInformationValid  // Validasi struktur ID 40
@PurposeOfTransactionValid        // Validasi Purpose: BOOK/DMCT/XBCT
public class QrisTransferPayload extends QrisPayload {
    
    // Override qrisRoot field with Transfer specific validations
    @PayloadFormatIndicatorFirstPosition
    @CRCLastPosition
    @PayloadFormatIndicatorValue
    @MandatoryField(id = 1)   // Point of Initiation Method (WAJIB untuk Transfer)
    @MandatoryField(id = 40)  // Transfer Account Information
    @MandatoryField(id = 53)  // Transaction Currency
    @MandatoryField(id = 58)  // Country Code
    @MandatoryField(id = 59)  // Beneficiary Name
    @MandatoryField(id = 60)  // Beneficiary City
    @MandatoryField(id = 62)  // Additional Data (with Purpose)
    @MandatoryField(id = 63)  // CRC
    @CharLength(from=1, to=1, min=2, max=2)
    @CharLength(from=40, to=40, min=1, max=99)
    @CharLength(from=52, to=52, min=4, max=4)  // MCC jika ada
    @CharLength(from=53, to=53, min=3, max=3)
    @CharLength(from=54, to=54, min=1, max=13)
    @CharLength(from=58, to=58, min=2, max=2)
    @CharLength(from=59, to=59, min=1, max=25)
    @CharLength(from=60, to=60, min=1, max=15)
    @CharLength(from=61, to=61, min=1, max=10)
    @CharLength(from=62, to=62, min=1, max=99)
    @CharLength(from=64, to=99, min=1, max=99)
    @PointOfInitiationMethodValue
    @TransactionCurrency
    @TransactionAmount(id = 54)
    @CountryCode
    @IdNotNull(id = 59)
    @IdNotNull(id = 60)
    @PostalCode
    private Map<Integer, QrisDataObject> qrisRoot;
    
    /**
     * Constructor dengan payload string.
     * 
     * @param payload Raw QR text string yang akan di-parse
     */
    public QrisTransferPayload(String payload) {
        this.setPayload(payload);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @return {@link QrisType#MPM_TRANSFER}
     */
    @Override
    public QrisType getQrisType() {
        return QrisType.MPM_TRANSFER;
    }
    
    @Override
    public Map<Integer, QrisDataObject> getQrisRoot() {
        return qrisRoot;
    }
    
    @Override
    public void setQrisRoot(Map<Integer, QrisDataObject> qrisRoot) {
        this.qrisRoot = qrisRoot;
    }
}
