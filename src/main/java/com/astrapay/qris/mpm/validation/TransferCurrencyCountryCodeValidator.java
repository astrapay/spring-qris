package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferCurrencyCountryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator untuk Country Code dan Transaction Currency pada QRIS Transfer.
 * <p>
 * Validasi: Country Code (ID 58) = "ID" → Transaction Currency (ID 53) wajib "360"
 * </p>
 * 
 * <p><b>Referensi:</b> Spesifikasi 4.2 - Verifikasi Data QRIS MPM Transaksi Transfer, Poin 4</p>
 */
public class TransferCurrencyCountryCodeValidator implements ConstraintValidator<TransferCurrencyCountryCode, QrisPayload> {

    private static final int COUNTRY_CODE_ID = 58;
    private static final int TRANSACTION_CURRENCY_ID = 53;
    private static final String INDONESIA_COUNTRY_CODE = "ID";
    private static final String INDONESIA_CURRENCY_CODE = "360";

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (isNullPayload(payload)) {
            return true;
        }

        String countryCode = getCountryCode(payload.getQrisRoot());
        
        // Jika Country Code tidak ada atau bukan "ID", skip validasi
        if (!isIndonesia(countryCode)) {
            return true;
        }

        // Country Code = "ID" → Transaction Currency wajib "360"
        String currency = getCurrency(payload.getQrisRoot());
        return isIndonesiaCurrency(currency);
    }

    private boolean isNullPayload(QrisPayload payload) {
        return Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot());
    }

    private String getCountryCode(Map<Integer, QrisDataObject> qrisRoot) {
        QrisDataObject countryCode = qrisRoot.get(COUNTRY_CODE_ID);
        return Objects.isNull(countryCode) ? null : countryCode.getValue();
    }

    private String getCurrency(Map<Integer, QrisDataObject> qrisRoot) {
        QrisDataObject currency = qrisRoot.get(TRANSACTION_CURRENCY_ID);
        return Objects.isNull(currency) ? null : currency.getValue();
    }

    private boolean isIndonesia(String countryCode) {
        return Objects.nonNull(countryCode) && 
               INDONESIA_COUNTRY_CODE.equalsIgnoreCase(countryCode);
    }

    private boolean isIndonesiaCurrency(String currency) {
        return Objects.nonNull(currency) && 
               INDONESIA_CURRENCY_CODE.equals(currency);
    }
}
