package com.astrapay.qris.mpm.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Transfer Account Information (ID 40) untuk QRIS MPM Transfer.
 *
 * <p>Class ini merepresentasikan informasi akun penerima transfer yang digunakan
 * dalam transaksi QRIS Transfer (berbeda dengan MPM Payment yang menggunakan Merchant Account Information).</p>
 *
 * <p><b>Struktur ID 40:</b></p>
 * <ul>
 *   <li>Sub-tag 00: Reverse Domain (wajib, var up to 32 chars)</li>
 *   <li>Sub-tag 01: Customer PAN (wajib, 16-19 chars)</li>
 *   <li>Sub-tag 02: Beneficiary ID (wajib, var up to 15 chars)</li>
 *   <li>Sub-tag 04: Bank Identifier Code/BIC (opsional, 8-11 chars)</li>
 * </ul>
 *
 * <p><b>Contoh:</b></p>
 * <pre>
 * TransferAccountInformation transferInfo = TransferAccountInformation.builder()
 *     .reverseDomain("ID.CO.PJSPNAME1.WWW")
 *     .customerPan("0118936023451234567890")
 *     .beneficiaryId("15KLMNO1234512345")
 *     .bankIdentifierCode("CENAIDJA")  // Optional
 *     .build();
 * </pre>
 *
 * @see MerchantAccountInformation
 * @see Qris#toStringTransfer()
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferAccountInformation {

    /**
     * Reverse Domain (Sub-tag 00).
     * Format: Domain terbalik, contoh: ID.CO.PJSPNAME1.WWW
     * Panjang: Variable, maksimal 32 karakter
     * Wajib untuk QRIS Transfer
     */
    private String reverseDomain;

    /**
     * Customer PAN / Personal Account Number (Sub-tag 01).
     * Nomor rekening atau identitas akun penerima transfer.
     * Panjang: 16-19 karakter numerik
     * Wajib untuk QRIS Transfer
     */
    private String customerPan;

    /**
     * Beneficiary ID (Sub-tag 02).
     * Identitas unik penerima transfer dari penyedia jasa.
     * Format: Alphanumeric
     * Panjang: Variable, maksimal 15 karakter
     * Wajib untuk QRIS Transfer
     */
    private String beneficiaryId;

    /**
     * Bank Identifier Code / BIC (Sub-tag 04).
     * Kode identifikasi bank penerima (SWIFT code atau kode bank lokal).
     * Format: Alphanumeric
     * Panjang: 8-11 karakter
     * Opsional untuk QRIS Transfer
     *
     * <p><b>Contoh:</b> CENAIDJA (Bank Central Asia)</p>
     */
    private String bankIdentifierCode;

}
