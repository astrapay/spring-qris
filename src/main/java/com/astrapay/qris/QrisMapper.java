package com.astrapay.qris;

import com.astrapay.qris.object.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Arthur Purnama
 */
public class QrisMapper {

    /**
     * map
     * @param payload payload
     * @return Qris
     */
    public Qris map(Map<Integer, QrisDataObject> payload) {
        Qris object = new Qris();
        object.setPayloadFormatIndicator(payload.get(0).getValue());
        object.setPointOfInitiationMethod(Integer.valueOf(payload.get(1).getValue()));
        mapMerchantAccountInformation(payload, object);
        mapDomesticCentralRepository(payload, object);
        object.setMerchantCategoryCode(Integer.valueOf(payload.get(52).getValue()));
        for(Currency currency : Currency.getAvailableCurrencies()) {
            if(currency.getNumericCodeAsString().equals(payload.get(53).getValue())) {
                object.setTransactionCurrency(currency);
                break;
            }
        }
        if(payload.containsKey(54)) {
            object.setTransactionAmount(Double.valueOf(payload.get(54).getValue()));
        }
        mapTip(payload, object);
        mapCountryCode(payload, object);
        object.setMerchantName(payload.get(59).getValue());
        object.setMerchantCity(payload.get(60).getValue());
        if(payload.containsKey(61)) {
            object.setPostalCode(Integer.valueOf(payload.get(61).getValue()));
        }
        mapAdditionalData(payload, object);
        mapMerchantInformationLanguage(payload, object);
        object.setCrc(payload.get(63).getValue());
        return object;
    }

    public Map<Integer,Object> convertObjectToMap(Object o) throws IllegalAccessException {
        Map<Integer,Object> qrisDataObjectMap = new HashMap<>();
        Field[] allFields = o.getClass().getDeclaredFields();
        for (int i = 0; i < allFields.length; i++) {
            Field field = allFields[i];
            field.setAccessible(true);
            Object value = field.get(o);
            qrisDataObjectMap.put(i, value);
        }
        return qrisDataObjectMap;
    }

    public String mapToString(Qris qris) {
         String payloadFormatIndicator = qris.getPayloadFormatIndicatorAsString()   ;
         String pointOfInitiationmethod = qris.getPointOfInitiationMethodAsString();
         String merchantAccountInformation = qris.getMerchantAccountInformationAsStringAsString(qris.getMerchantAccountInformationDomestics().get(26), "26");
         String domesticRepository = qris.getMerchantAccountInformationAsStringAsString(qris.getDomesticCentralRepository(),"51");
         String mcc = qris.getMerchantCategoryCodeAsString();
         String currency = qris.getCurrencyCodeAsString();
         String tansactionAmount = qris.getTransactionAmountAsString();
         String tip = qris.getTipAsString();
         String countryCode = qris.getCountryCodeAsString();
         String merchantName =qris.getMerchantNameAsString();
         String merchantCity = qris.getMerchantCityAsString();
         String postalCode = qris.getPostalCodeAsString();
         String additionalData = qris.getAdditionalDataAsString();
//         String billingId = null;
         String crc = qris.getCrcAsString();

        return payloadFormatIndicator +
                pointOfInitiationmethod +
                merchantAccountInformation +
                domesticRepository +
                mcc +
                currency +
                tansactionAmount +
                tip +
                countryCode +
                merchantName +
                merchantCity +
                postalCode +
                additionalData +
//                billingId +
                crc;
    }

    private void mapMerchantInformationLanguage(Map<Integer, QrisDataObject> payload, Qris object) {
        if(payload.containsKey(64)){
            MerchantInformationLanguage merchantInformationLanguage = new MerchantInformationLanguage();
            String languagePreference = payload.get(64).getTemplateMap().get(0).getValue();
            for(Locale locale : Locale.getAvailableLocales()){
                if(languagePreference.equals(locale.getISO3Language())){
                    merchantInformationLanguage.setLanguagePreference(locale);
                }
            }
            merchantInformationLanguage.setMerchantNameAlternateLanguage(payload.get(64).getTemplateMap().get(1).getValue());
            merchantInformationLanguage.setMerchantCityAlternateLanguage(payload.get(64).getTemplateMap().get(2).getValue());
            object.setMerchantInformationLanguage(merchantInformationLanguage);
        }
    }

    private void mapAdditionalData(Map<Integer, QrisDataObject> payload, Qris object) {
        if(payload.containsKey(62)){
            AdditionalData additionalData = new AdditionalData();
            Map<Integer, String> dataObjects = new LinkedHashMap<>();
            for(int i = 1; i<=8; i++){
                if(payload.get(62).getTemplateMap().containsKey(i)){
                    dataObjects.put(i, payload.get(62).getTemplateMap().get(i).getValue());
                }
            }
            additionalData.setDataObjects(dataObjects);
            additionalData.setValue(payload.get(62).getValue());
            if(payload.get(62).getTemplateMap().containsKey(9)){
                additionalData.setConsumerDataRequest(payload.get(62).getTemplateMap().get(9).getValue());
            }
            if(payload.get(62).getTemplateMap().containsKey(99)){
                ProprietaryDomestic proprietaryDomestic = new ProprietaryDomestic();
                proprietaryDomestic.setProprietary(payload.get(62).getTemplateMap().get(99).getTemplateMap().get(0).getValue());
                proprietaryDomestic.setProprietary(payload.get(62).getTemplateMap().get(99).getTemplateMap().get(1).getValue());
                additionalData.setProprietaryDomestic(proprietaryDomestic);
            }
            object.setAdditionalData(additionalData);
        }
    }

    private void mapCountryCode(Map<Integer, QrisDataObject> payload, Qris object) {
        for(Locale locale : Locale.getAvailableLocales()) {
            if(locale.getCountry().equals(payload.get(58).getValue())) {
                object.setCountryCode(locale);
            }
        }
    }

    private void mapTip(Map<Integer, QrisDataObject> payload, Qris object) {
        Tip tip = new Tip();
        if(payload.containsKey(55)) {
            tip.setIndicator(payload.get(55).getValue());
            if(payload.containsKey(56)){
                tip.setFixed(Double.valueOf(payload.get(56).getValue()));
            }
            if(payload.containsKey(57)){
                tip.setPercentage(Double.valueOf(payload.get(57).getValue()));
            }
        }
        object.setTip(tip);
    }

    private void mapDomesticCentralRepository(Map<Integer, QrisDataObject> payload, Qris object) {
        if(payload.containsKey(51)){
            Map<Integer, QrisDataObject> merchantAccountInformationMap = payload.get(51).getTemplateMap();
            MerchantAccountInformation merchantAccountInformation = new MerchantAccountInformation();
            merchantAccountInformation.setGloballyUniqueIdentifier(merchantAccountInformationMap.get(0).getValue());
            merchantAccountInformation.setMerchantId(merchantAccountInformationMap.get(2).getValue());
            merchantAccountInformation.setCriteria(MerchantCriteria.valueOf(merchantAccountInformationMap.get(3).getValue()));
            object.setDomesticCentralRepository(merchantAccountInformation);
        }
    }

    private void mapMerchantAccountInformation(Map<Integer, QrisDataObject> payload, Qris object) {
        for(int i=26; i<=45; i++){
            if(payload.containsKey(i)){
                Map<Integer, QrisDataObject> merchantAccountInformationMap = payload.get(i).getTemplateMap();
                MerchantAccountInformation merchantAccountInformation = new MerchantAccountInformation();
                merchantAccountInformation.setGloballyUniqueIdentifier(merchantAccountInformationMap.get(0).getValue());
                merchantAccountInformation.setPersonalAccountNumber(merchantAccountInformationMap.get(1).getValue());
                merchantAccountInformation.setMerchantId(merchantAccountInformationMap.get(2).getValue());
                merchantAccountInformation.setCriteria(MerchantCriteria.valueOf(merchantAccountInformationMap.get(3).getValue()));
                if(object.getMerchantAccountInformationDomestics() == null){
                    object.setMerchantAccountInformationDomestics(new LinkedHashMap<>());
                }
                object.getMerchantAccountInformationDomestics().put(i, merchantAccountInformation);
            }
        }
    }
}
