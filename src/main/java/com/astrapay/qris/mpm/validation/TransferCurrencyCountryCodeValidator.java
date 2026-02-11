package com.astrapay.qris.mpm.validation;

import com.astrapay.qris.mpm.object.QrisDataObject;
import com.astrapay.qris.mpm.object.QrisPayload;
import com.astrapay.qris.mpm.validation.constraints.TransferCurrencyCountryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;

/**
 * Validator untuk {@link TransferCurrencyCountryCode}.
 * <p>
 * Memvalidasi kesesuaian Transaction Currency dan Country Code:
 * <ul>
 *     <li>Country Code (ID 58) = "ID" → Transaction Currency (ID 53) wajib "360"</li>
 * </ul>
 * </p>
 * 
 * <p><b>Referensi:</b> Spesifikasi 4.2 - Verifikasi Data QRIS MPM Transaksi Transfer, Poin 4</p>
 * 
 */
public class TransferCurrencyCountryCodeValidator implements ConstraintValidator<TransferCurrencyCountryCode, QrisPayload> {

    private static final int COUNTRY_CODE_ID = 58;
    private static final int TRANSACTION_CURRENCY_ID = 53;
    private static final String INDONESIA_COUNTRY_CODE = "ID";
    private static final String INDONESIA_CURRENCY_CODE = "360";

    @Override
    public boolean isValid(QrisPayload payload, ConstraintValidatorContext context) {
        if (Objects.isNull(payload) || Objects.isNull(payload.getQrisRoot())) {
            return true; // Handled by other validators
        }

        Map<Integer, QrisDataObject> qrisRoot = payload.getQrisRoot();
        QrisDataObject countryCode = qrisRoot.get(COUNTRY_CODE_ID);
        QrisDataObject currency = qrisRoot.get(TRANSACTION_CURRENCY_ID);

        // Jika Country Code tidak ada, skip validasi ini
        if (Objects.isNull(countryCode) || Objects.isNull(countryCode.getValue())) {
            return true;
        }

        String countryValue = countryCode.getValue();

        // Jika Country Code = "ID", Transaction Currency wajib "360"
        if (INDONESIA_COUNTRY_CODE.equalsIgnoreCase(countryValue)) {
            if (Objects.isNull(currency) || Objects.isNull(currency.getValue())) {
                return false;
            }

            String currencyValue = currency.getValue();
            if (!INDONESIA_CURRENCY_CODE.equals(currencyValue)) {
                return false;
            }
        }

        return true;
    }
}
