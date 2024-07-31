package com.astrapay.qris;

import com.astrapay.qris.mpm.QrisMapper;
import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QrisMapperTest {

  @InjectMocks
  private QrisMapper qrisMapper;

  @Test
  void mapToStringQrisWithTipPercentageTest() {
    generateQrisToString("00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE520458125303360540650000055020357030.75802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO6304265F");
    generateQrisToString("00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE52045812530336054065000005802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO63048A85");
    generateQrisToString("00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE52045812530336054061000005502015802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO6304E237");
    generateQrisToString("00020101021226640018ID.CO.ASTRAPAY.WWW011893600822321000045502092100004550303UBE51440014ID.CO.QRIS.WWW0215ID20210719803990303UBE520475385303360540745480515802ID5916AUTO2000 Bintaro6009Tangerang610515118626001152110050107770150715ASTRAPAY210502198180209210000455030116304E3CF");
    generateQrisToString("00020101021126570011ID.DANA.WWW011893600915300060605702090006060570303UMI51440014ID.CO.QRIS.WWW0215ID10190046990790303UMI5204481453033605802ID5912Saifullah.id6014Kota Pontianak61057812463045F72");
//    generateQrisToString("00020101021226610014COM.GO-JEK.WWW01189360091432343267810210G2343267810303UBE51440014ID.CO.QRIS.WWW0215ID20190143816000303UBE5204581253033605802ID5910Starbucks 6013JAKARTA PUSAT610510210540839000.006239502801202104130502044NMWVA6P4CID0703A0363049A04"); // error amount
    generateQrisToString("00020101021126630014ID.SPINPAY.WWW011893600816343100062402121314310006240303UMI51440014ID.CO.QRIS.WWW0215ID10200384314890303UMI5204839853033605802ID5904SPIN6013Jakarta Pusat61051034062140103***0703A0163045CDD");
    generateQrisToString("00020101021226530012COM.DOKU.WWW0118936008990000002475020424750303URE51440014ID.CO.QRIS.WWW0215ID10200392934820303URE5204539953033605404100055020256035005802ID5911GUREUM SHOP6013JAKARTA PUSAT61051064062070703A016304FD26");
    generateQrisToString("0002010102122654000200011893600014300061643802150008850006164380303UKE5204541153033605405495005802ID5913OMBE KOFIE-HO6013JAKARTA UTARA6105142406259010611093205121100131109320708AG20521199170002000107DINAMIS63049414");
// generateQrisToString("00020101021226660014ID.LINKAJA.WWW011893600911002711446402151902170711446450303UBE51450015ID.OR.GPNQR.WWW02150000000000000000303UBE520454995802ID5920SPBU SNTRA BISNIS AR6001-6101-621801143414210-7584075303360550201540630000063040FA0"); // error
    generateQrisToString("00020101021226620016COM.ASTRAPAY.WWW011893600822321000001802092100000180303UBE520450455303360540445805802ID5914Yokke Merchant6015Jakarta Selatan610512440626001152104140025420150715ASTRAPAY0000100981802092100000180301163040E7E");
    generateQrisToString("00020101021126610014COM.GO-JEK.WWW01189360091435804770920210G5804770920303URE51440014ID.CO.QRIS.WWW0215ID20200182437880303URE5204839853033605802ID5925Lazis Amalia Astra PT Int6013JAKARTA UTARA61051433062070703A946304E083");
    generateQrisToString("00020101021226630014ID.SPINPAY.WWW011893600816319100102402121314310103170303URE51430014ID.CO.QRIS.WWW0214ID1314310103170303URE520472985303360540411355802ID5908MNC LIFE6007JAKARTA61052020362390707TID00656024607cc759c07a8847bf7b4def63040741");
    generateQrisToString("00020101021126610014COM.GO-JEK.WWW01189360091430438058080210G7238689620303UMI51440014ID.CO.QRIS.WWW0215ID10190847190020303UMI5204581253033605802ID5909Bluebird36015Bandung deket r61054025762090705PT20063044CAF");
    generateQrisToString("00020101021126670018ID.CO.EXAMPLE2.WWW01159360001404567890215MIDCONTOH1234560303UMI51370014ID.CO.QRIS.WWW0215ID10191234567815204123453033605802ID5914NamaMerchantC16009NamaKota16110123456789062070703K196304B22E");
    generateQrisToString("00020101021126650013ID.CO.BCA.WWW011893600014000128766702150008850012876670303UKE51440014ID.CO.QRIS.WWW0215ID10210818616060303UKE5204074253033605802ID5909GREEN PAW6007TANGSEL61051532362070703A0163046D3F");
  }

  private void generateQrisToString(String payloadString) {
    QrisParser qrisParser = new QrisParser();
    QrisPayload qrisPayload = qrisParser.parse(payloadString);
    Qris qris = qrisMapper.map(qrisPayload.getQrisRoot());
    assertEquals(payloadString,qris.toString());
  }

  @Test
  void testMapMerchantInformationLanguage() throws Exception {
    // Setup the payload map with mock data
    Map<Integer, QrisDataObject> payload = new HashMap<>();
    Map<Integer, QrisDataObject> templateMap = new HashMap<>();

    // Populate the template map with mock data
    templateMap.put(0, new QrisDataObject("00", "3", "en_IN")); // Language Preference
    templateMap.put(1, new QrisDataObject("01", "3", "Alt Merchant Name")); // Alternate Merchant Name
    templateMap.put(2, new QrisDataObject("02", "3", "Alt Merchant City")); // Alternate Merchant City

    // Create and populate the QrisDataObject for key 64
    QrisDataObject languageDataObject = new QrisDataObject("64", "3", "lang");
    languageDataObject.setTemplateMap(templateMap);
    payload.put(64, languageDataObject);

    // Create a new Qris object
    Qris qrisObject = new Qris();

    // Use reflection to access the private method
    Method method = QrisMapper.class.getDeclaredMethod("mapMerchantInformationLanguage", Map.class, Qris.class);
    method.setAccessible(true);
    method.invoke(qrisMapper, payload, qrisObject);

    // Verify the results
    MerchantInformationLanguage result = qrisObject.getMerchantInformationLanguage();
    assertNotNull(result, "MerchantInformationLanguage should not be null");

    // Check the alternate merchant name and city
    assertEquals("Alt Merchant Name", result.getMerchantNameAlternateLanguage(), "MerchantNameAlternateLanguage should match the expected value");
    assertEquals("Alt Merchant City", result.getMerchantCityAlternateLanguage(), "MerchantCityAlternateLanguage should match the expected value");
  }

  @Test
  void testMapAdditionalData() throws Exception {
    // Setup the payload map with mock data
    Map<Integer, QrisDataObject> payload = new LinkedHashMap<>();
    Map<Integer, QrisDataObject> templateMap = new LinkedHashMap<>();

    // Populate the template map with mock data
    templateMap.put(1, new QrisDataObject("01", "3", "Value1"));
    templateMap.put(2, new QrisDataObject("02", "3", "Value2"));
    templateMap.put(3, new QrisDataObject("03", "3", "Value3"));
    templateMap.put(4, new QrisDataObject("04", "3", "Value4"));
    templateMap.put(5, new QrisDataObject("05", "3", "Value5"));
    templateMap.put(6, new QrisDataObject("06", "3", "Value6"));
    templateMap.put(7, new QrisDataObject("07", "3", "Value7"));
    templateMap.put(8, new QrisDataObject("08", "3", "Value8"));
    templateMap.put(9, new QrisDataObject("09", "3", "ConsumerRequest"));

    // Create and populate the QrisDataObject for key 62
    QrisDataObject additionalDataObject = new QrisDataObject("62", "99", "AdditionalDataValue");
    additionalDataObject.setTemplateMap(templateMap);
    payload.put(62, additionalDataObject);

    // Create a new Qris object
    Qris qrisObject = new Qris();

    // Use reflection to access the private method
    Method method = QrisMapper.class.getDeclaredMethod("mapAdditionalData", Map.class, Qris.class);
    method.setAccessible(true);
    method.invoke(qrisMapper, payload, qrisObject);

    // Verify the results
    AdditionalData result = qrisObject.getAdditionalData();
    assertNotNull(result, "AdditionalData should not be null");

    // Check data objects
    Map<Integer, String> expectedDataObjects = new LinkedHashMap<>();
    for (int i = 1; i <= 8; i++) {
      expectedDataObjects.put(i, "Value" + i);
    }
    assertEquals(expectedDataObjects, result.getDataObjects(), "DataObjects should match the expected values");

    // Check additional data value
    assertEquals("AdditionalDataValue", result.getValue(), "AdditionalData value should match");

    // Check consumer data request
    assertEquals("ConsumerRequest", result.getConsumerDataRequest(), "ConsumerDataRequest should match");

  }

}
