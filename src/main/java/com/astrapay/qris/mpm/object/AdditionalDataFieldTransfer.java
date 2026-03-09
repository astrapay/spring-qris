package com.astrapay.qris.mpm.object;

import lombok.*;

/**
 * Additional Data Field Template (ID 62) untuk QRIS MPM Transfer.
 * 
 * <p>Class ini merepresentasikan struktur Additional Data Field yang spesifik untuk 
 * transaksi QRIS Transfer dengan field-field yang berbeda dari Payment.</p>
 * 
 * <p><b>Struktur ID 62 untuk Transfer:</b></p>
 * <ul>
 *   <li>Sub-tag 08: Purpose of Transaction (wajib, 4 chars) - BOOK/DMCT/XBCT/CT</li>
 *   <li>Sub-tag 99: Unique per generated (wajib, var up to 74 chars)</li>
 *   <li>Sub-tag 00: Default Value (wajib, 2 chars, always "00")</li>
 *   <li>Sub-tag 01: Unique Data (wajib, var 8-64 chars)</li>
 * </ul>
 * 
 * <p><b>Contoh penggunaan:</b></p>
 * <pre>
 * AdditionalDataFieldTransfer additionalData = AdditionalDataFieldTransfer.builder()
 *     .purposeOfTransaction(PurposeOfTransaction.DMCT)
 *     .uniquePerGenerated("00020001257660407400001769742270682")
 *     .defaultValue("00")
 *     .uniqueData("0116a1d64af7470ce64d")
 *     .build();
 * 
 * String value = additionalData.toString(); // Generate string in QRIS format
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
     * Unique per generated / Transaction Reference (Sub-tag 99).
     * <p>
     * Nomor referensi unik yang di-generate untuk setiap transaksi transfer.
     * Biasanya kombinasi dari timestamp, sequence number, atau UUID yang di-truncate.
     * </p>
     * Panjang: Variable, maksimal 74 karakter
     * Format: Alphanumeric special
     * Wajib untuk QRIS Transfer
     * 
     * <p><b>Contoh:</b> "00020001257660407400001769742270682"</p>
     */
    private String uniquePerGenerated;

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
     * <p>Format output: 62 + length + (08+04+purposeCode + 99+len+uniqueGen + 00+02+defaultVal + 01+len+uniqueData)</p>
     * 
     * @return String dalam format QRIS: tag + length + value
     */
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        // Sub-tag 08: Purpose of Transaction (Mandatory, 4 chars)
        if (purposeOfTransaction != null) {
            String tagPurpose = "08";
            String purposeCode = purposeOfTransaction.getCode();
            String lengthPurpose = String.format("%02d", purposeCode.length());
            content.append(tagPurpose).append(lengthPurpose).append(purposeCode);
        }

        // Sub-tag 99: Unique per generated (Mandatory, var up to 74 chars)
        if (uniquePerGenerated != null && !uniquePerGenerated.isEmpty()) {
            String tagUnique = "99";
            String lengthUnique = String.format("%02d", uniquePerGenerated.length());
            content.append(tagUnique).append(lengthUnique).append(uniquePerGenerated);
        }

        // Sub-tag 00: Default Value (Mandatory, 2 chars)
        if (defaultValue != null && !defaultValue.isEmpty()) {
            String tagDefault = "00";
            String lengthDefault = String.format("%02d", defaultValue.length());
            content.append(tagDefault).append(lengthDefault).append(defaultValue);
        }

        // Sub-tag 01: Unique Data (Mandatory, 8-64 chars)
        if (uniqueData != null && !uniqueData.isEmpty()) {
            String tagUniqueData = "01";
            String lengthUniqueData = String.format("%02d", uniqueData.length());
            content.append(tagUniqueData).append(lengthUniqueData).append(uniqueData);
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
     * @return Raw value string: 08+04+code+99+len+unique+00+02+default+01+len+data
     */
    public String getValue() {
        StringBuilder content = new StringBuilder();

        if (purposeOfTransaction != null) {
            String purposeCode = purposeOfTransaction.getCode();
            content.append("08").append(String.format("%02d", purposeCode.length())).append(purposeCode);
        }

        if (uniquePerGenerated != null && !uniquePerGenerated.isEmpty()) {
            content.append("99").append(String.format("%02d", uniquePerGenerated.length())).append(uniquePerGenerated);
        }

        if (defaultValue != null && !defaultValue.isEmpty()) {
            content.append("00").append(String.format("%02d", defaultValue.length())).append(defaultValue);
        }

        if (uniqueData != null && !uniqueData.isEmpty()) {
            content.append("01").append(String.format("%02d", uniqueData.length())).append(uniqueData);
        }

        return content.toString();
    }
}
