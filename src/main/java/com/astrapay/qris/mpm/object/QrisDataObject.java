package com.astrapay.qris.mpm.object;

import com.astrapay.qris.mpm.validation.constraints.*;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;


/**
 * <b>3.2 Organisasi Data</b>
 * <p>
 * Data yang terdapat dalam QR Code diatur sebagai berikut:<br/>
 * Setiap <i>data object</i> dibuat dalam tiga <i>field</i>. <i>Field</i> pertama berisi <i>identifier (ID)</i> di mana <i>data object</i> dapat menjadi referensi. <i>Field</i> selanjutnya merupakan panjang karakter yang mengidentifikasi jumlah karakter dalam <i>field</i> ketiga, yaitu <i>Value data object</i>. <i>Data object</i> dapat direpresentasikan sebagai ID-Panjang karakter-<i>Value data object</i>.
 * </p>
 * <b>4.4.1.1</b> Panjang karakter wajib sama dengan jumlah karakter dalam Value field. <br/>
 * <b>4.7.5</b> Merchant Account Information Template (ID "26" - "45")<br/>
 * <b>4.7.5.1</b> Merchant Account Information pada template ini wajib digunakan untuk acquirer domestik.<br/>
 * <b>4.7.5.2</b> Jika ditampilkan, maka template Merchant Account Information berisi data object sesuai Tabel 4.3 dibawah ini.<br/>
 * Tabel 4.3: Data object turunan Merchant Account Information Template (ID "26" - "45")
 * <table>
 *     <caption>Tabel 4.3: Data object turunan Merchant Account Information Template (ID 26 - 45)</caption>
 *     <tr><th>ID</th><th>Definisi</th><th>Format</th><th>Panjang Karakter</th><th>Ketersediaan</th><th>Keterangan</th></tr>
 *      <tr>
 *          <td>“00”</td><td>Globally Unique Identifier</td><td>ans</td><td>var. up to "32"</td><td>M/td>
 *          <td><ul><li>reverse domain name</li><li>acquiring domain</li><li>Contoh: com.acquiring.www</li></ul></td>
 *      </tr>
 *      <tr>
 *          <td>“01”</td><td>PAN</td><td>N</td><td>var. up to "19"</td><td>M</td>
 *          <td><ul><li>Personal Account Number (PAN) merchant</li><li>menggunakan National Numbering System (NNS) pada 8 digit pertama.</li></ul></td>
 *      </tr>
 *      <tr>
 *          <td>“02”</td><td>ID</td><td>ans/td><td>var. up to "15"</td><td>M</td>
 *          <td><ul><li>Merchant ID disediakan oleh acquirer</li><li>*exclude “|” pipe karakter</li></ul></td>
 *      </tr>
 *      <tr>
 *          <td>“03”</td><td>Criteria</td><td>ans/td><td>3</td><td>M</td>
 *          <td><ul><li>Kriteria mengacu pada Tabel 4.6</li><li>*exclude “|” pipe karakter</li></ul></td>
 *      </tr>
 * </table>
 * <b>4.7.5.3</b> Reverse Domain pada ID “26”-“45” dengan sub ID “00” harus memiliki nilai default “00” atau dapat berisi informasi reverse domain-nya.
 */
@LengthValue
@MerchantAccountInformationMandatoryField(from = 26, to = 45)
@MerchantAccountInformationMandatoryField(from = 26, to = 45, id=1)
@MerchantAccountInformationMandatoryField(from = 26, to = 45, id=2)
@MerchantAccountInformationMandatoryField(from = 26, to = 45, id=3)
@MerchantAccountInformationMandatoryField(from = 51, to = 51)
@MerchantAccountInformationMandatoryField(from = 51, to = 51, id=2)
@MerchantAccountInformationCharLength(from = 26, to = 45, max = 32)
@MerchantAccountInformationCharLength(from = 26, to = 45, id=1, max = 19)
@MerchantAccountInformationCharLength(from = 26, to = 45, id=2, max = 15)
@MerchantAccountInformationCharLength(from = 26, to = 45, id=3, min=3, max = 3)
@MerchantAccountInformationCharLength(from = 51, to = 51, max = 32)
@MerchantAccountInformationCharLength(from = 51, to = 51, id=2, max = 15)
@MerchantAccountInformationPanIsNumber
//@NationalNumberingSystem
@MerchantAccountInformationReverseDomain
@MerchantAccountInformationCriteria
@AdditionalDataField
@AdditionalDataFieldAsterisk
@AdditionalConsumerDataRequest
@AdditionalDataFieldCharLength(from = 1, to = 8, min = 1, max = 25)
@AdditionalDataFieldCharLength(from = 9, to = 9, min = 3, max = 3)
@AdditionalDataFieldCharLength(from = 99, to = 99, min = 1, max = 95)
@ProprietaryDomesticData
@ProprietaryDataTemplate
@LanguagePreferance
@MerchantNameAlternateLanguage
@Getter
@Setter
@RequiredArgsConstructor
@Valid
public class QrisDataObject {


    /**
     * <b>3.2</b> ID merupakan kode dua digit angka dari “00” hingga “99”. <br/>
     * <b>4.3.1.1</b> Sebuah ID wajib memiliki dua digit Value numerik dan wajib memiliki Value "00" hingga "99".
     */
    @NonNull
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]{2,2}$", message = "Wrong Input")
    private String id;

    /**
     * <b>3.2</b> Panjang karakter merupakan kode dua digit angka dari “01” hingga “99”.<br/>
     * <b>4.4.1.2</b> Panjang karakter wajib dikodekan dengan dua digit Value numerik dan wajib memiliki Value “01” hingga “99”.
     */
    @NonNull
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]{2,2}$", message = "Wrong Input")
    private String length;

    /**
     * <b>3.2</b> Value data object memiliki panjang minimum satu karakter dan maksimum 99 karakter.
     */
    @NonNull
    @NotEmpty
    private String value;

    /**
     * <b>4.3.1.2</b> Hanya boleh terdapat satu data object dengan ID spesifik di bawah root QR Code dan hanya boleh terdapat satu ID spesifik dalam template-nya.
     */
    @Valid
    private Map<Integer, QrisDataObject> templateMap;

    /**
     *
     * @return Integer
     */
    public Integer getIntId(){
        return Integer.valueOf(this.id);
    }

    /**
     *
     * @return Integer
     */
    public Integer getIntLength(){
        return Integer.valueOf(this.length);
    }
}
