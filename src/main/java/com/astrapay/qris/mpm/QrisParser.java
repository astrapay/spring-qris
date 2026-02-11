package com.astrapay.qris.mpm;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.object.QrisMpmPaymentPayload;
import com.astrapay.qris.mpm.object.QrisTransferPayload;
import com.astrapay.qris.mpm.object.QrisType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parser untuk mengubah QR text string menjadi {@link QrisPayload} object dengan type detection.
 * <p>
 * Parser ini melakukan:
 * <ul>
 *     <li>Type detection untuk menentukan apakah QR text adalah MPM Payment atau Transfer</li>
 *     <li>Parsing QR text menjadi struktur data object dengan ID-Length-Value format</li>
 *     <li>Parsing template data objects (Merchant Account, Additional Data, dll)</li>
 *     <li>Return concrete payload instance sesuai tipe yang terdeteksi</li>
 * </ul>
 * </p>
 * 
 * <p><b>Type Detection Logic:</b></p>
 * <pre>
 * 1. Parse QR text untuk mendapatkan tag 62 (Additional Data)
 * 2. Jika tag 62 ada, parse sub-tags nya
 * 3. Jika tag 08 (Purpose) ditemukan dan berisi BOOK/DMCT/XBCT → TRANSFER
 * 4. Selain itu → MPM_PAYMENT (default)
 * </pre>
 * 
 * <p><b>4.8</b> Data objects—Additional Data Field Template (ID "62")</p>
 * <p><b>4.9</b> Data objects—Merchant Information—Language Template (ID "64")</p>
 * 
 * @author Arthur Purnama
 * @see QrisPayload
 * @see QrisMpmPaymentPayload
 * @see QrisTransferPayload
 * @see QrisType
 */
public class QrisParser {

    // Tag IDs
    private static final int TAG_ID_ADDITIONAL_DATA = 62;
    private static final int TAG_ID_PURPOSE_OF_TRANSACTION = 8;
    private static final int TAG_ID_TRANSFER_ACCOUNT_INFO = 40;
    private static final int TAG_ID_MERCHANT_DOMESTIC_REPO = 51;
    private static final int TAG_ID_MERCHANT_INFO_LANGUAGE = 64;
    private static final int TAG_ID_PROPRIETARY_DATA = 99;
    private static final int TAG_ID_MERCHANT_ACCOUNT_START = 26;
    private static final int TAG_ID_MERCHANT_ACCOUNT_END = 45;
    
    // Purpose of Transaction Values
    private static final String PURPOSE_BOOK = "BOOK";
    private static final String PURPOSE_DMCT = "DMCT";
    private static final String PURPOSE_XBCT = "XBCT";
    
    // Parser constants
    private static final int ID_LENGTH = 2;
    private static final int ID_PLUS_LENGTH = 4;
    
    // Error messages
    private static final String ERROR_DUPLICATE_KEY = "Duplicate key '%d'.";
    private static final String ERROR_TUNTAS_NOT_IMPLEMENTED = "QRIS Tuntas belum diimplementasikan";
    private static final String ERROR_UNKNOWN_TYPE = "QRIS dengan tipe UNKNOWN tidak dapat diproses";

    /**
     * Parse QR text string menjadi QrisPayload dengan automatic type detection.
     * <p>
     * Method ini akan:
     * <ol>
     *     <li>Detect tipe QRIS dari QR text (Transfer atau MPM Payment)</li>
     *     <li>Create instance QrisPayload yang sesuai (QrisTransferPayload atau QrisMpmPaymentPayload)</li>
     *     <li>Parse QR text menjadi data objects</li>
     *     <li>Return payload dengan validasi yang sesuai dengan tipe-nya</li>
     * </ol>
     * </p>
     * 
     * <p><b>Type Detection:</b></p>
     * <p>
     * Parser akan memeriksa tag 62 (Additional Data) → tag 08 (Purpose of Transaction).
     * Jika berisi "BOOK", "DMCT", atau "XBCT" maka akan di-treat sebagai Transfer QRIS.
     * </p>
     * 
     * @param qris QR text string yang akan di-parse (max 512 chars)
     * @return QrisPayload (QrisMpmPaymentPayload atau QrisTransferPayload) tergantung type detection
     * @throws IllegalArgumentException jika QR text invalid atau duplicate ID ditemukan
     */
    public QrisPayload parse(String qris) {
        // Detect QRIS type first
        QrisType qrisType = detectQrisType(qris);
        
        // Create appropriate payload instance based on type
        QrisPayload qrisPayload = createPayloadByType(qrisType);
        qrisPayload.setPayload(qris);
        
        // Parse the payload
        parse(qrisPayload);
        
        return qrisPayload;
    }
    
    /**
     * Detect tipe QRIS dari QR text string.
     * <p>
     * Detection logic:
     * <ul>
     *     <li>Parse tag 62 (Additional Data) untuk mencari tag 08 (Purpose of Transaction)</li>
     *     <li>Jika tag 08 berisi "BOOK", "DMCT", atau "XBCT" → TRANSFER</li>
     *     <li>Jika tag 08 ada tapi nilai tidak sesuai → UNKNOWN</li>
     *     <li>Jika tidak ditemukan tag 08 → MPM_PAYMENT (default)</li>
     * </ul>
     * </p>
     * 
     * @param qris QR text string
     * @return QrisType (TRANSFER, UNKNOWN, atau MPM_PAYMENT)
     */
    private QrisType detectQrisType(String qris) {
        try {
            // Parse root to get tag 62
            Map<Integer, QrisDataObject> tempMap = new LinkedHashMap<>();
            parseRoot(qris, tempMap);
            
            // Check if tag 62 (Additional Data) exists
            if (tempMap.containsKey(TAG_ID_ADDITIONAL_DATA)) {
                QrisDataObject additionalData = tempMap.get(TAG_ID_ADDITIONAL_DATA);
                
                // Parse sub-tags of tag 62
                Map<Integer, QrisDataObject> tag62Map = new LinkedHashMap<>();
                parser(additionalData.getValue(), tag62Map);
                
                // Check if tag 08 (Purpose of Transaction) exists
                if (tag62Map.containsKey(TAG_ID_PURPOSE_OF_TRANSACTION)) {
                    String purposeValue = tag62Map.get(TAG_ID_PURPOSE_OF_TRANSACTION).getValue();
                    
                    // Check if purpose contains BOOK, DMCT, or XBCT
                    if (purposeValue != null) {
                        if (purposeValue.contains(PURPOSE_BOOK) || 
                            purposeValue.contains(PURPOSE_DMCT) || 
                            purposeValue.contains(PURPOSE_XBCT)) {
                            return QrisType.TRANSFER;
                        } else {
                            // Tag 08 exists but value is invalid
                            return QrisType.UNKNOWN;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // If parsing fails during detection, default to MPM_PAYMENT
            // The actual parse() will handle the validation errors
        }
        
        return QrisType.MPM_PAYMENT; // Default
    }
    
    /**
     * Create payload instance berdasarkan tipe QRIS.
     * 
     * @param type Tipe QRIS (TRANSFER, MPM_PAYMENT, TUNTAS, atau UNKNOWN)
     * @return Instance QrisPayload yang sesuai
     * @throws UnsupportedOperationException jika tipe TUNTAS atau UNKNOWN
     */
    private QrisPayload createPayloadByType(QrisType type) {
        switch (type) {
            case TRANSFER:
                return new QrisTransferPayload();
            case MPM_PAYMENT:
                return new QrisMpmPaymentPayload();
            case TUNTAS:
                // Future implementation
                throw new UnsupportedOperationException(ERROR_TUNTAS_NOT_IMPLEMENTED);
            case UNKNOWN:
                // Cannot process QRIS with unknown type
                throw new UnsupportedOperationException(ERROR_UNKNOWN_TYPE);
            default:
                return new QrisMpmPaymentPayload();
        }
    }

    /**
     * Parse QrisPayload object (in-place parsing).
     * <p>
     * Method ini melakukan parsing terhadap QrisPayload yang sudah ada,
     * mengisi qrisRoot map dengan data objects yang ter-parse.
     * </p>
     * 
     * <p><b>Parsing Steps:</b></p>
     * <ol>
     *     <li>Parse root data objects dari payload string</li>
     *     <li>Parse Merchant Account Information templates (ID 26-45)</li>
     *     <li>Parse Merchant Domestic Repository (ID 51)</li>
     *     <li>Parse Additional Data Field Template (ID 62)</li>
     *     <li>Parse Merchant Information Language Template (ID 64)</li>
     *     <li>Parse Proprietary Data Template (tag 99 dalam ID 62) jika ada</li>
     * </ol>
     * 
     * @param payload QrisPayload object yang akan di-parse (harus sudah memiliki payload string)
     */
    public void parse(QrisPayload payload) {
        Map<Integer, QrisDataObject> qrisMap = new LinkedHashMap<>();
        parseRoot(payload.getPayload(), qrisMap);
        parseMerchantAccountInformationTemplate(qrisMap);
        parseTransferAccountInformation(qrisMap);  // Parse ID 40 for Transfer
        parseMerchantDomesticRepository(qrisMap);
        parseAdditionalDataFieldTemplate(qrisMap);
        parseMerchantInformationLanguageTemplate(qrisMap);
        if(qrisMap.containsKey(TAG_ID_ADDITIONAL_DATA)){
            parseProprietaryDataTemplate(qrisMap.get(TAG_ID_ADDITIONAL_DATA).getTemplateMap());
        }
        payload.setQrisRoot(qrisMap);
    }

    /**
     * Parse Merchant Account Information templates (ID 26-45).
     * <p>Digunakan untuk MPM Payment QRIS.</p>
     */
    private void parseMerchantAccountInformationTemplate(Map<Integer, QrisDataObject> qrisMap) {
        for (int i = TAG_ID_MERCHANT_ACCOUNT_START; i <= TAG_ID_MERCHANT_ACCOUNT_END; i++) {
            parseTemplate(qrisMap, i);
        }
    }

    /**
     * Parse Transfer Account Information (ID 40).
     * <p>
     * Digunakan untuk Transfer QRIS. Template ini berisi:
     * <ul>
     *     <li>00: Reverse Domain</li>
     *     <li>01: Customer PAN</li>
     *     <li>02: Beneficiary ID</li>
     *     <li>04: Bank Identifier Code (optional)</li>
     * </ul>
     * </p>
     */
    private void parseTransferAccountInformation(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, TAG_ID_TRANSFER_ACCOUNT_INFO);
    }

    /**
     * Parse Merchant Domestic Repository (ID 51).
     * <p>Digunakan untuk MPM Payment QRIS static (Point of Initiation Method "11").</p>
     */
    private void parseMerchantDomesticRepository(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, TAG_ID_MERCHANT_DOMESTIC_REPO);
    }

    /**
     * Parse Additional Data Field Template (ID 62).
     * <p>
     * Template ini berisi:
     * <ul>
     *     <li>08: Purpose of Transaction (untuk Transfer: BOOK/DMCT/XBCT)</li>
     *     <li>99: Unique per Generated</li>
     *     <li>00: Default Value</li>
     *     <li>01: Unique Data</li>
     * </ul>
     * </p>
     */
    private void parseAdditionalDataFieldTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, TAG_ID_ADDITIONAL_DATA);
    }

    private void parseMerchantInformationLanguageTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, TAG_ID_MERCHANT_INFO_LANGUAGE);
    }

    private void parseProprietaryDataTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, TAG_ID_PROPRIETARY_DATA);
    }

    private void parseTemplate(Map<Integer, QrisDataObject> qrisMap, int i) {
        if (qrisMap.containsKey(i)) {
            Map<Integer, QrisDataObject> map = new LinkedHashMap<>();
            QrisDataObject object = qrisMap.get(i);
            parser(object.getValue(), map);
            object.setTemplateMap(map);
        }
    }


    private void parseRoot(String qris, Map<Integer, QrisDataObject> qrisMap) {
        parser(qris, qrisMap);
    }

    /**
     * <b>4.3.1.2</b> Hanya boleh terdapat satu data object dengan ID spesifik di bawah root QR Code dan hanya boleh terdapat satu ID spesifik dalam template-nya.
     */
    private void parser(String qris, Map<Integer, QrisDataObject> qrisMap) {
        String length;//6238 0115220121 0026830150 715ASTRAPA Y2100176
        for (int i = 0 ; i < qris.length(); i = i + ID_PLUS_LENGTH + Integer.parseInt(length)) {
            String id = qris.substring(i, i + ID_LENGTH);
            length = qris.substring(i + ID_LENGTH, i + ID_PLUS_LENGTH);
            String value = qris.substring(i + ID_PLUS_LENGTH, i + ID_PLUS_LENGTH + Integer.parseInt(length));
            QrisDataObject qrisDataObject = new QrisDataObject(id, length, value);
            qrisMap.merge(qrisDataObject.getIntId(), qrisDataObject, (v1, v2) -> {
                throw new IllegalArgumentException(String.format(ERROR_DUPLICATE_KEY, qrisDataObject.getIntId()));
            });
        }
    }
}
