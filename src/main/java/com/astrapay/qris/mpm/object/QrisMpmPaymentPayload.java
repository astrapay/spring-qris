package com.astrapay.qris.mpm.object;

import com.astrapay.qris.mpm.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Concrete implementation dari {@link QrisPayload} untuk QRIS MPM Payment (Merchant Presented Mode).
 * <p>
 * Class ini merepresentasikan QRIS yang digunakan untuk pembayaran ke merchant, 
 * dengan validasi khusus yang sesuai dengan spesifikasi QRIS MPM Payment.
 * </p>
 * 
 * <p><b>Karakteristik MPM Payment:</b></p>
 * <ul>
 *     <li>Wajib memiliki Merchant Category Code (ID 52) - 4 digit ISO 18245</li>
 *     <li>Wajib memiliki minimal satu Merchant Account Information (ID 26-45 atau ID 51)</li>
 *     <li>Transaction Currency (ID 53) mandatory - "360" untuk IDR</li>
 *     <li>Country Code (ID 58) mandatory - "ID" untuk Indonesia</li>
 *     <li>Merchant Name (ID 59) mandatory - maksimal 25 karakter</li>
 *     <li>Merchant City (ID 60) mandatory - maksimal 15 karakter</li>
 *     <li>Postal Code (ID 61) mandatory jika Country Code "ID"</li>
 *     <li>Transaction Amount (ID 54) conditional - tergantung static/dynamic QR</li>
 *     <li>Point of Initiation Method (ID 01) optional - "11" static / "12" dynamic</li>
 * </ul>
 * 
 * <p><b>Referensi Spesifikasi:</b></p>
 * <ul>
 *     <li><b>4.7.3.1</b> - Setidaknya satu Merchant Account Information dari "02"-"51" harus ditampilkan</li>
 *     <li><b>4.7.6.1</b> - Merchant Category Code harus sesuai ISO 18245</li>
 *     <li><b>4.7.7.1</b> - Transaction Currency harus sesuai ISO 4217</li>
 *     <li><b>4.7.13.1</b> - Merchant Name wajib untuk identifikasi merchant</li>
 *     <li><b>4.7.14.1</b> - Merchant City wajib untuk lokasi merchant</li>
 * </ul>
 * 
 * <p><b>Contoh QR Text MPM Payment:</b></p>
 * <pre>
 * 00020101021126...52044829530336054...5802ID5907ANTONIO6007JAKARTA61051031062386304CCEF
 * </pre>
 * 
 * @author Arthur Purnama
 * @see QrisPayload
 * @see QrisType#MPM_PAYMENT
 */
@NoArgsConstructor
@CheckSum
public class QrisMpmPaymentPayload extends QrisPayload {
    
    // Override qrisRoot field with MPM Payment specific validations
    @PayloadFormatIndicatorFirstPosition
    @CRCLastPosition
    @PayloadFormatIndicatorValue
    @MandatoryField
    @MandatoryField(id = 52)  // Merchant Category Code
    @MandatoryField(id = 53)  // Transaction Currency
    @MandatoryField(id = 58)  // Country Code
    @MandatoryField(id = 59)  // Merchant Name
    @MandatoryField(id = 60)  // Merchant City
    @MandatoryField(id = 63)  // CRC
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
    @CharLength(from=64, to=99, min=1, max=99)
    @MerchantAccountInformation2To45Exist
    @PointOfInitiationMethodValue
    @MerchantAccountInformationExist
    @TransactionCurrency
    @TransactionAmount(id = 54)
    @TipValueIndicator
    @TransactionAmount(id = 57)
    @TipValuePercentage
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
    public QrisMpmPaymentPayload(String payload) {
        this.setPayload(payload);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @return {@link QrisType#MPM_PAYMENT}
     */
    @Override
    public QrisType getQrisType() {
        return QrisType.MPM_PAYMENT;
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
