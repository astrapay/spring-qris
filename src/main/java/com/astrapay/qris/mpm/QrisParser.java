package com.astrapay.qris.mpm;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.object.QrisPayload;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <b>4.8</b> Data objects—Additional Data Field Template (ID "62")
 * <b>4.9</b> Data objects—Merchant Information—Language Template (ID "64")
 */
public class QrisParser {

    /**
     *
     * @param qris qris
     * @return payload
     */
    public QrisPayload parse(String qris) {
        QrisPayload qrisPayload = new QrisPayload();
        qrisPayload.setPayload(qris);
        parse(qrisPayload);
        return qrisPayload;
    }

    /**
     *
     * @param payload payload
     */
    public void parse(QrisPayload payload) {
        Map<Integer, QrisDataObject> qrisMap = new LinkedHashMap<>();
        parseRoot(payload.getPayload(), qrisMap);
        parseMerchantAccountInformationTemplate(qrisMap);
        parseMerchantDomesticRepository(qrisMap);
        parseAdditionalDataFieldTemplate(qrisMap);
        parseMerchantInformationLanguageTemplate(qrisMap);
        if(qrisMap.containsKey(62)){
            parseProprietaryDataTemplate(qrisMap.get(62).getTemplateMap());
        }
        payload.setQrisRoot(qrisMap);
    }

    private void parseMerchantAccountInformationTemplate(Map<Integer, QrisDataObject> qrisMap) {
        for (int i = 26; i <= 45; i++) {
            parseTemplate(qrisMap, i);
        }
    }

    private void parseMerchantDomesticRepository(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, 51);
    }

    private void parseAdditionalDataFieldTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, 62);
    }

    private void parseMerchantInformationLanguageTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, 64);
    }

    private void parseProprietaryDataTemplate(Map<Integer, QrisDataObject> qrisMap) {
        parseTemplate(qrisMap, 99);
    }

    private void parseTemplate(Map<Integer, QrisDataObject> qrisMap, int i) {
        if (qrisMap.containsKey(i)) {
            Map<Integer, QrisDataObject> map = new LinkedHashMap<>();
            QrisDataObject object = qrisMap.get(i);
            parser(object.getValue(), map);
            object.setTemplateMap(map);
        }
    }


    private void parseRoot(String qris, Map<Integer, QrisDataObject> qrisMap) {
        parser(qris, qrisMap);
    }

    /**
     * <b>4.3.1.2</b> Hanya boleh terdapat satu data object dengan ID spesifik di bawah root QR Code dan hanya boleh terdapat satu ID spesifik dalam template-nya.
     */
    private void parser(String qris, Map<Integer, QrisDataObject> qrisMap) {
        String length;//6238 0115220121 0026830150 715ASTRAPA Y2100176
        for (int i = 0 ; i < qris.length(); i = i + 4 + Integer.parseInt(length)) {
            String id = qris.substring(i, i + 2);
            length = qris.substring(i + 2, i + 4);
            String value = qris.substring(i + 4, i + 4 + Integer.parseInt(length));
            QrisDataObject qrisDataObject = new QrisDataObject(id, length, value);
            qrisMap.merge(qrisDataObject.getIntId(), qrisDataObject, (v1, v2) -> {
                throw new IllegalArgumentException("Duplicate key '" + qrisDataObject.getIntId() + "'.");
            });
        }
    }
}
