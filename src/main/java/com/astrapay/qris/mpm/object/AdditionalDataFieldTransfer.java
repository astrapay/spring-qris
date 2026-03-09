package com.astrapay.qris.mpm.object;

import lombok.*;

/**
 * Additional Data Field Template (ID 62) untuk QRIS MPM Transfer.
 * 
 * <p>Class ini merepresentasikan struktur Additional Data Field yang spesifik untuk 
 * transaksi QRIS Transfer dengan field-field yang berbeda dari Payment.</p>
 * 
 * <p><b>Struktur ID 62 untuk Transfer (nested structure):</b></p>
 * <pre>
 * Tag 62 (Additional Data Field Template)
 *   ├─ Tag 08: Purpose of Transaction (direct child, wajib, 4 chars)
 *   └─ Tag 99: Unique per generated (direct child, wajib, var up to 74 chars)
 *        ├─ Tag 00: Default Value (nested in 99, wajib, 2 chars, always "00")
 *        └─ Tag 01: Unique Data (nested in 99, wajib, var 8-64 chars)
 * </pre>
 * 
 * <p><b>PENTING:</b> Tag 99 adalah hasil encoding otomatis dari tag 00 dan tag 01!</p>
 * <p>Tag 99 = "99" + length + (encoded_tag_00 + encoded_tag_01)</p>
 * 
 * <p><b>Contoh encoding:</b></p>
 * <pre>
 * Purpose: DMCT, defaultValue: "00", uniqueData: "a1d64af7470ce64d"
 * 
 * Tag 00: 00 + 02 + 00             = "000200" (6 chars)
 * Tag 01: 01 + 16 + a1d64af7470ce64d = "0116a1d64af7470ce64d" (20 chars)
 * Tag 99: 99 + 26 + (000200 + 0116a1d64af7470ce64d) = "99260002000116a1d64af7470ce64d"
 *         ↑    ↑    ↑──────────────────────────────┐
 *       Tag  Len    Content (tag 00 + tag 01)
 * 
 * Output: 62 + 38 + (08 + 04 + DMCT + 99 + 26 + (00 + 02 + 00 + 01 + 16 + a1d64af7470ce64d))
 *         ↑    ↑     ↑    ↑    ↑      ↑    ↑     ↑    ↑    ↑    ↑    ↑    ↑
 *       Tag62 Len  Tag08 Len Value  Tag99 Len  Tag00 Len Val Tag01 Len Value
 *                                             └─────────────┬──────────────┘
 *                                              Nested inside tag 99
 * </pre>
 * 
 * <p><b>Contoh penggunaan:</b></p>
 * <pre>
 * AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
 *     .purposeOfTransaction(PurposeOfTransaction.DMCT)
 *     .defaultValue("00")
 *     .uniqueData("a1d64af7470ce64d")
 *     .build();
 * 
 * String value = additionalData.toString(); // Generate string in QRIS format
 * // Result: 62380804DMCT99260002000116a1d64af7470ce64d
 * </pre>
 * 
 * @see AdditionalData
 * @see PurposeOfTransaction
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalDataFieldTransfer {

    /**
     * Purpose of Transaction (Sub-tag 08).
     * <p>
     * Menentukan jenis transaksi transfer:
     * <ul>
     *   <li><b>BOOK</b>: Booking/Overbooking (On-Us Transfer) - transfer dalam satu bank</li>
     *   <li><b>DMCT</b>: Debit Merchant Credit Transfer - transfer antar bank</li>
     *   <li><b>XBCT</b>: Cross Border Credit Transfer - transfer lintas negara</li>
     *   <li><b>CT</b>: Credit Transfer - transfer umum</li>
     * </ul>
     * </p>
     * Panjang: 4 karakter (fixed)
     * Wajib untuk QRIS Transfer
     */
    private PurposeOfTransaction purposeOfTransaction;



    /**
     * Default Value (Sub-tag 00).
     * <p>
     * Nilai default yang selalu "00" untuk QRIS Transfer.
     * Field ini digunakan untuk kompatibilitas format.
     * </p>
     * Panjang: 2 karakter (fixed)
     * Nilai: Always "00"
     * Wajib untuk QRIS Transfer
     */
    @Builder.Default
    private String defaultValue = "00";

    /**
     * Unique Data (Sub-tag 01).
     * <p>
     * Data unik tambahan yang terkait dengan transaksi, bisa berupa:
     * <ul>
     *   <li>Transaction ID</li>
     *   <li>Session ID</li>
     *   <li>Trace number</li>
     *   <li>Hash/signature</li>
     * </ul>
     * </p>
     * Panjang: Variable, 8-64 karakter
     * Format: Alphanumeric special
     * Wajib untuk QRIS Transfer
     * 
     * <p><b>Contoh:</b> "0116a1d64af7470ce64d" (hex string)</p>
     */
    private String uniqueData;

    /**
     * Generate QRIS formatted string untuk Additional Data Field Template (ID 62).
     * 
     * <p>Struktur nested yang dihasilkan:</p>
     * <pre>
     * Tag 62 (Additional Data)
     *   ├─ Tag 08: Purpose of Transaction (direct child)
     *   └─ Tag 99: Unique per generated (direct child, auto-encoded from tag 00 + 01)
     *        ├─ Tag 00: Default Value (child of 99)
     *        └─ Tag 01: Unique Data (child of 99)
     * </pre>
     * 
     * <p><b>PENTING:</b> Tag 99 adalah hasil encoding otomatis:</p>
     * <p>Tag 99 = "99" + length + (encoded_tag_00 + encoded_tag_01)</p>
     * <p>Format: 62+len+(08+04+code + 99+len+(00+02+val + 01+len+data))</p>
     * 
     * @return String dalam format QRIS: tag + length + value
     */
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        // Sub-tag 08: Purpose of Transaction (Direct child of tag 62, Mandatory, 4 chars)
        if (purposeOfTransaction != null) {
            String tagPurpose = "08";
            String purposeCode = purposeOfTransaction.getCode();
            String lengthPurpose = String.format("%02d", purposeCode.length());
            content.append(tagPurpose).append(lengthPurpose).append(purposeCode);
        }

        // Sub-tag 99: Unique per generated (Direct child of tag 62)
        // Tag 99 adalah hasil encoding dari tag 00 dan tag 01
        // Format: 99 + length + (00+02+defaultVal + 01+len+uniqueData)
        StringBuilder tag99Content = new StringBuilder();
        
        // Sub-tag 00: Default Value (Child of tag 99, Mandatory, 2 chars)
        if (defaultValue != null && !defaultValue.isEmpty()) {
            String tagDefault = "00";
            String lengthDefault = String.format("%02d", defaultValue.length());
            tag99Content.append(tagDefault).append(lengthDefault).append(defaultValue);
        }
        
        // Sub-tag 01: Unique Data (Child of tag 99, Mandatory, 8-64 chars)
        if (uniqueData != null && !uniqueData.isEmpty()) {
            String tagUniqueData = "01";
            String lengthUniqueData = String.format("%02d", uniqueData.length());
            tag99Content.append(tagUniqueData).append(lengthUniqueData).append(uniqueData);
        }
        
        // Encode tag 99 jika ada content
        if (tag99Content.length() > 0) {
            String tag99 = "99";
            String tag99ContentStr = tag99Content.toString();
            String length99 = String.format("%02d", tag99ContentStr.length());
            content.append(tag99).append(length99).append(tag99ContentStr);
        }

        String contentStr = content.toString();
        if (contentStr.isEmpty()) {
            return "";
        }

        // Tag 62: Additional Data Field Template
        String tag = "62";
        String length = String.format("%02d", contentStr.length());
        return tag + length + contentStr;
    }

    /**
     * Generate nilai mentah (raw value) tanpa tag dan length untuk ID 62.
     * Berguna untuk parsing atau testing.
     * 
     * <p>Format: 08+04+code + 99+len+(00+02+default + 01+len+data)</p>
     * <p>Note: Tag 99 adalah encoding otomatis dari tag 00 + tag 01</p>
     * 
     * @return Raw value string dengan nested structure
     */
    public String getValue() {
        StringBuilder content = new StringBuilder();

        // Sub-tag 08: Purpose of Transaction (Direct child of 62)
        if (purposeOfTransaction != null) {
            String purposeCode = purposeOfTransaction.getCode();
            content.append("08").append(String.format("%02d", purposeCode.length())).append(purposeCode);
        }

        // Sub-tag 99: Unique per generated (Direct child of 62, auto-encoded from tag 00 + 01)
        StringBuilder tag99Content = new StringBuilder();
        
        // Sub-tag 00: Default Value (Child of 99)
        if (defaultValue != null && !defaultValue.isEmpty()) {
            tag99Content.append("00").append(String.format("%02d", defaultValue.length())).append(defaultValue);
        }
        
        // Sub-tag 01: Unique Data (Child of 99)
        if (uniqueData != null && !uniqueData.isEmpty()) {
            tag99Content.append("01").append(String.format("%02d", uniqueData.length())).append(uniqueData);
        }
        
        // Encode tag 99 jika ada content
        if (tag99Content.length() > 0) {
            String tag99ContentStr = tag99Content.toString();
            content.append("99").append(String.format("%02d", tag99ContentStr.length())).append(tag99ContentStr);
        }

        return content.toString();
    }
}
