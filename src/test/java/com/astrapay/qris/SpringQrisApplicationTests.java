package com.astrapay.qris;

import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.QrisPayload;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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
//        testsWithErrors(0, qris);

    }

//    @Test
//    void qrisTest() {
//        List<String> stringList = new LinkedList<>();
//        stringList.add("00020101021226610014COM.GO-JEK.WWW01189360091432343267810210G2343267810303UBE51440014ID.CO.QRIS.WWW0215ID20190143816000303UBE5204581253033605802ID5910Starbucks 6013JAKARTA PUSAT610510210540839000.006239502801202104130502044NMWVA6P4CID0703A0363049A04");
//        stringList.add("00020101021126630014ID.SPINPAY.WWW011893600816343100062402121314310006240303UMI51440014ID.CO.QRIS.WWW0215ID10200384314890303UMI5204839853033605802ID5904SPIN6013Jakarta Pusat61051034062140103***0703A0163045CDD");
//        stringList.add("00020101021226530012COM.DOKU.WWW0118936008990000002475020424750303URE51440014ID.CO.QRIS.WWW0215ID10200392934820303URE5204539953033605404100055020256035005802ID5911GUREUM SHOP6013JAKARTA PUSAT61051064062070703A016304FD26");
//        stringList.add("0002010102122654000200011893600014300061643802150008850006164380303UKE5204541153033605405495005802ID5913OMBE KOFIE-HO6013JAKARTA UTARA6105142406259010611093205121100131109320708AG20521199170002000107DINAMIS63049414");
//        stringList.add("00020101021226660014ID.LINKAJA.WWW011893600911002711446402151902170711446450303UBE51450015ID.OR.GPNQR.WWW02150000000000000000303UBE520454995802ID5920SPBU SNTRA BISNIS AR6001-6101-621801143414210-7584075303360550201540630000063040FA0");
//        stringList.add("00020101021226620016COM.ASTRAPAY.WWW011893600822321000001802092100000180303UBE520450455303360540445805802ID5914Yokke Merchant6015Jakarta Selatan610512440626001152104140025420150715ASTRAPAY0000100981802092100000180301163040E7E");
//        stringList.add("00020101021126610014COM.GO-JEK.WWW01189360091435804770920210G5804770920303URE51440014ID.CO.QRIS.WWW0215ID20200182437880303URE5204839853033605802ID5925Lazis Amalia Astra PT Int6013JAKARTA UTARA61051433062070703A946304E083");
//        stringList.add("00020101021226630014ID.SPINPAY.WWW011893600816319100102402121314310103170303URE51430014ID.CO.QRIS.WWW0214ID1314310103170303URE520472985303360540411355802ID5908MNC LIFE6007JAKARTA61052020362390707TID00656024607cc759c07a8847bf7b4def63040741");
//        for (String qris : stringList) {
//            testsWithErrors(0, qris);
//        }
//    }

//    @Test
//    void qrisTestWithErrors() {
//        testsWithErrors(2, "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000030502092100003050303UBE5204111153033605802ID5922AHASS Hayati Pemuda 356007Sumbawa61058431362240703A01981302092100003056304ABEF");
//    }

//    private void testsWithErrors(int expected, String qris) {
//        QrisPayload parse = qrisParser.parse(qris);
//        Set<ConstraintViolation<QrisPayload>> constraintViolationSet = validator.validate(parse);
//        assertEquals(expected, constraintViolationSet.size());
//    }

//    @Test
//    private void testsWithErrorsXXX(int expected, String qris) {
//        QrisPayload parse = qrisParser.parseCoba(qris);
//        Set<ConstraintViolation<QrisPayload>> constraintViolationSet = validator.validate(parse);
//        assertEquals(expected, constraintViolationSet.size());
//    }

//    @Test
//    void qrisTestWithErrors1() {
//        testsWithErrors(2, "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000031302092100003130303UBE5204551153033605802ID5926AHASS Hayati Sungai Rumbai6011DHARMASRAYA61052768462240703A0198130209210000313630437A8");
//    }
//
//    @Test
//    void qrisTestWithErrors2() {
//        testsWithErrors(2, "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000030702092100003070303UBE5204111153033605802ID5924AHASS Hayati Lubuk Buaya6007Sumbawa61058431362240703A019813020921000030763043CAA");
//    }
//
//    @Test
//    void qrisTestWithErrors3() {
//        testsWithErrors(3, "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000031302092100003130303UBE5204111153033605802ID5926AHASS Hayati Sungai Rumbai6007Sumbawa61058431362240703A019813020921000031363046015");
//    }
//
//    @Test
//    void qrisTestWithErrors4() {
//        testsWithErrors(4, "00020101021126640018ID.CO.ASTRAPAY.WWW011893600822321000022702092100002270303UBE520311153033605802ID5926INFAQ/SEDEKAH AMANAH ASTRA6007Sumbawa61058431362290708ASTRAPAY981302092100002276304C9C2");
//    }
}
