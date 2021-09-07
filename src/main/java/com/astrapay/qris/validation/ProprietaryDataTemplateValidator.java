package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.ProprietaryDataTemplate;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

/**
 * <b>Tabel 4.8:</b> Data object turunan (ID “99”) dalam (ID “62”) Proprietary data Template
 * <table>
 *     <caption><b>Tabel 4.8:</b> Data object turunan (ID “99”) dalam (ID “62”) Proprietary data Template</caption>
 *     <tr>
 *         <th>ID</th><th>Definisi</th><th>Format</th><th>Panjang Karakter</th><th>Ketersediaan</th><th>Keterangan</th>
 *     </tr>
 *     <tr>
 *         <td>“00”</td><td>Globally Unique Identifier</td><td>ans</td><td>var. up to "32"</td><td>M</td><td>Disarankan merupakan default value=’00’</td>
 *     </tr>
 *     <tr>
 *         <td>“01”</td><td>Proprietary</td><td>ans</td><td>var. up to “81”</td><td>M</td><td></td>
 *     </tr>
 * </table>
 */
@Builder
@NoArgsConstructor
public class ProprietaryDataTemplateValidator implements ConstraintValidator<ProprietaryDataTemplate, QrisDataObject> {

    @Override
    public boolean isValid(QrisDataObject value, ConstraintValidatorContext context) {
        if (value.getIntId().equals(62) && value.getTemplateMap().containsKey(99)) {
            Map<Integer, QrisDataObject> qrisDataObjectMap = value.getTemplateMap().get(99).getTemplateMap();
            try {
                if (qrisDataObjectMap.get(0).getValue().length() > 32) return false;
                if (qrisDataObjectMap.get(1).getValue().length() > 81) return false;
            } catch (NullPointerException ex) {
                return false;
            }
        }
        return true;
    }
}