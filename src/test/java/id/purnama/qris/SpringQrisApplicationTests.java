package id.purnama.qris;

import id.purnama.qris.object.QrisDataObject;
import id.purnama.qris.object.QrisPayload;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringQrisApplicationTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final QrisParser qrisParser = new QrisParser();

    @Test
    void contextLoads() {
        String qris =
                "00" + "02" + "01" +
                        "01" + "02" + "11" +
                        "26" + "53" + "00" + "12" + "COM.DOKU.WWW" +
                        "01" + "18" + "936008990000002475" +
                        "02" + "04" + "2475" +
                        "03" + "03" + "URE" +
                        "51" + "44" + "00" + "14" + "ID.CO.QRIS.WWW" +
                        "02" + "15" + "ID1020039293482" +
                        "03" + "03" + "URE" +
                        "52" + "04" + "5399" +
                        "53" + "03" + "360" +
                        "58" + "02" + "ID" +
                        "59" + "11" + "GUREUM SHOP" +
                        "60" + "13" + "JAKARTA PUSAT" +
                        "61" + "05" + "10640" +
                        "62" + "07" + "0703A01" +
                        "63" + "04" + "455C";
        QrisPayload parse = qrisParser.parse(qris);
        Set<ConstraintViolation<QrisPayload>> constraintViolationSet = validator.validate(parse);
        assertEquals(0, constraintViolationSet.size());

    }

    @Test
    void qrisTest() {
        List<String> stringList = new LinkedList<>();
        stringList.add("00020101021126630014ID.SPINPAY.WWW011893600816343100062402121314310006240303UMI51440014ID.CO.QRIS.WWW0215ID10200384314890303UMI5204839853033605802ID5904SPIN6013Jakarta Pusat61051034062140103***0703A0163045CDD");
        stringList.add("00020101021226530012COM.DOKU.WWW0118936008990000002475020424750303URE51440014ID.CO.QRIS.WWW0215ID10200392934820303URE5204539953033605404100055020256035005802ID5911GUREUM SHOP6013JAKARTA PUSAT61051064062070703A016304FD26");
        stringList.add("0002010102122654000200011893600014300061643802150008850006164380303UKE5204541153033605405495005802ID5913OMBE KOFIE-HO6013JAKARTA UTARA6105142406259010611093205121100131109320708AG20521199170002000107DINAMIS63049414");
        stringList.add("00020101021226660014ID.LINKAJA.WWW011893600911002711446402151902170711446450303UBE51450015ID.OR.GPNQR.WWW02150000000000000000303UBE520454995802ID5920SPBU SNTRA BISNIS AR6001-6101-621801143414210-7584075303360550201540630000063040FA0");
        stringList.add("00020101021226620016COM.ASTRAPAY.WWW011893600822321000001802092100000180303UBE520450455303360540445805802ID5914Yokke Merchant6015Jakarta Selatan610512440626001152104140025420150715ASTRAPAY0000100981802092100000180301163040E7E");
        stringList.add("00020101021126610014COM.GO-JEK.WWW01189360091435804770920210G5804770920303URE51440014ID.CO.QRIS.WWW0215ID20200182437880303URE5204839853033605802ID5925Lazis Amalia Astra PT Int6013JAKARTA UTARA61051433062070703A946304E083");
        for (String qris : stringList) {
            QrisPayload parse = qrisParser.parse(qris);
            Set<ConstraintViolation<QrisPayload>> constraintViolationSet = validator.validate(parse);
            assertEquals(0, constraintViolationSet.size());
        }
    }

    @Test
    void qrisTestWithErrors() {
        List<String> stringList = new LinkedList<>();
        stringList.add("00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000022702092100002270303UBE520311153033605802ID5926INFAQ/SEDEKAH AMANAH ASTRA6007Sumbawa61058431362290708ASTRAPAY981302092100002276304C9C2");
        for (String qris : stringList) {
            QrisPayload parse = qrisParser.parse(qris);
            Set<ConstraintViolation<QrisPayload>> constraintViolationSet = validator.validate(parse);
            assertEquals(3, constraintViolationSet.size());
        }
    }
}
