package com.astrapay.qris.mpm.object;

/**
 * Enum untuk merepresentasikan tipe QRIS berdasarkan use case.
 * <p>
 * QRIS memiliki berbagai tipe transaksi yang memiliki struktur data dan validasi berbeda:
 * <ul>
 *     <li><b>MPM_PAYMENT</b>: QRIS untuk pembayaran merchant (Merchant Presented Mode).
 *         Menggunakan Merchant Account Information (ID 26-45 atau 51) dan Merchant Category Code (ID 52).</li>
 *     <li><b>TRANSFER</b>: QRIS untuk transfer antar rekening atau pembayaran tuntas.
 *         Ditandai dengan Transfer Account Information (ID 40) dan Purpose of Transaction (tag 62->08) 
 *         yang berisi "BOOK", "DMCT", atau "XBCT".</li>
 *     <li><b>TUNTAS</b>: QRIS untuk penarikan tunai di ATM (future implementation).
 *         QR Code akan di-scan pada mesin ATM untuk melakukan penarikan tunai.</li>
 * </ul>
 * </p>
 * 
 */
public enum QrisType {
    
    /**
     * QRIS untuk pembayaran merchant (Merchant Presented Mode).
     * <p>
     * Karakteristik:
     * <ul>
     *     <li>Wajib memiliki Merchant Category Code (ID 52)</li>
     *     <li>Wajib memiliki Merchant Account Information (ID 26-45 atau ID 51)</li>
     *     <li>Merchant Name (ID 59) dan Merchant City (ID 60) mandatory</li>
     *     <li>Transaction Amount (ID 54) optional untuk static QR, mandatory untuk dynamic QR</li>
     * </ul>
     * </p>
     */
    MPM_PAYMENT("Merchant Payment", "QRIS untuk pembayaran ke merchant"),
    
    /**
     * QRIS untuk transfer antar rekening atau pembayaran tuntas.
     * <p>
     * Karakteristik:
     * <ul>
     *     <li>Wajib memiliki Transfer Account Information (ID 40) dengan sub-tag:
     *         <ul>
     *             <li>00: Reverse Domain</li>
     *             <li>01: Customer PAN (16-19 digit)</li>
     *             <li>02: Beneficiary ID</li>
     *             <li>04: Bank Identifier Code (BIC) - optional</li>
     *         </ul>
     *     </li>
     *     <li>Wajib memiliki Additional Data (ID 62) dengan Purpose of Transaction (tag 08) 
     *         yang berisi "BOOK" (booking/transfer), "DMCT" (debit merchant credit transfer), 
     *         atau "XBCT" (cross border credit transfer)</li>
     *     <li>Beneficiary Name (ID 59) dan Beneficiary City (ID 60) mandatory</li>
     *     <li>Tidak memiliki Merchant Category Code (ID 52)</li>
     * </ul>
     * </p>
     */
    TRANSFER("Transfer", "QRIS untuk transfer"),
    
    /**
     * QRIS untuk penarikan tunai di ATM (future implementation).
     * <p>
     * Karakteristik:
     * <ul>
     *     <li>QR Code akan di-generate untuk di-scan pada mesin ATM</li>
     *     <li>Spesifikasi dan validasi akan ditentukan kemudian</li>
     * </ul>
     * </p>
     */
    TUNTAS("Tuntas/Cash Withdrawal", "QRIS untuk penarikan tunai di ATM"),
    
    /**
     * QRIS dengan tipe yang tidak dikenali atau invalid.
     * <p>
     * Karakteristik:
     * <ul>
     *     <li>Memiliki tag 62 (Additional Data) dengan sub-tag 08 (Purpose of Transaction)</li>
     *     <li>Nilai Purpose of Transaction tidak sesuai dengan nilai yang valid (BOOK/DMCT/XBCT)</li>
     *     <li>Tidak dapat diproses sebagai Transfer atau MPM Payment</li>
     *     <li>Payload akan ditolak oleh validator</li>
     * </ul>
     * </p>
     */
    UNKNOWN("Unknown", "QRIS dengan tipe yang tidak dikenali");
    
    private final String displayName;
    private final String description;
    
    QrisType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    /**
     * Mendapatkan display name dari tipe QRIS.
     * 
     * @return Display name untuk tipe QRIS
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Mendapatkan deskripsi dari tipe QRIS.
     * 
     * @return Deskripsi tipe QRIS
     */
    public String getDescription() {
        return description;
    }
}
