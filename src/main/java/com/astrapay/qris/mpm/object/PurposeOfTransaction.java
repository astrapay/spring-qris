package com.astrapay.qris.mpm.object;

/**
 * Enum untuk Purpose of Transaction pada QRIS Transfer.
 * <p>
 * Purpose of Transaction adalah nilai yang terdapat pada tag 08 di dalam 
 * Additional Data Field Template (tag 62). Nilai ini menentukan jenis transaksi transfer.
 * </p>
 * 
 * <p><b>Referensi:</b> QRIS Spesifikasi 4.2 - Transfer Account Information</p>
 * 
 * @author Arthur Purnama
 */
public enum PurposeOfTransaction {
    
    /**
     * BOOK - Booking/Overbooking (On-Us Transfer).
     * <p>
     * Transaksi transfer antar rekening dalam satu bank yang sama (internal transfer).
     * PJP Pengirim WAJIB memverifikasi bahwa transaksi merupakan overbooking.
     * </p>
     * 
     * <p><b>Use Case:</b> Transfer dari rekening BCA ke rekening BCA lainnya</p>
     */
    BOOK("BOOK", "Booking/Overbooking", "Transfer internal dalam satu bank"),
    
    /**
     * DMCT - Debit Merchant Credit Transfer.
     * <p>
     * Transaksi transfer antar rekening di bank yang berbeda melalui switching/jaringan ATM Bersama.
     * Debit dilakukan pada rekening pengirim dan credit pada rekening penerima.
     * </p>
     * 
     * <p><b>Use Case:</b> Transfer dari rekening BCA ke rekening Mandiri</p>
     */
    DMCT("DMCT", "Debit Merchant Credit Transfer", "Transfer antar bank melalui switching"),
    
    /**
     * XBCT - Cross Border Credit Transfer.
     * <p>
     * Transaksi transfer lintas negara/internasional.
     * Digunakan untuk transfer ke rekening di negara lain.
     * </p>
     * 
     * <p><b>Use Case:</b> Transfer dari rekening Indonesia ke rekening di luar negeri</p>
     */
    XBCT("XBCT", "Cross Border Credit Transfer", "Transfer lintas negara");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    PurposeOfTransaction(String code, String displayName, String description) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
    }
    
    /**
     * Mendapatkan kode Purpose of Transaction (BOOK/DMCT/XBCT).
     * 
     * @return Kode purpose yang digunakan di QR text
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Mendapatkan display name dari Purpose of Transaction.
     * 
     * @return Display name untuk purpose
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Mendapatkan deskripsi dari Purpose of Transaction.
     * 
     * @return Deskripsi purpose
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Parse string code menjadi enum PurposeOfTransaction.
     * 
     * @param code Kode purpose (BOOK/DMCT/XBCT)
     * @return PurposeOfTransaction enum, atau null jika tidak ditemukan
     */
    public static PurposeOfTransaction fromCode(String code) {
        if (code == null) {
            return null;
        }
        
        for (PurposeOfTransaction purpose : values()) {
            if (purpose.code.equals(code)) {
                return purpose;
            }
        }
        
        return null;
    }
    
    /**
     * Validasi apakah code adalah purpose yang valid.
     * 
     * @param code Kode yang akan divalidasi
     * @return true jika code adalah BOOK/DMCT/XBCT, false jika tidak
     */
    public static boolean isValid(String code) {
        return fromCode(code) != null;
    }
}
