package com.astrapay.qris.object;

import lombok.*;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Qris {

    private String payloadFormatIndicator;
    private Integer pointOfInitiationMethod;
    private Map<Integer, MerchantAccountInformation> merchantAccountInformationDomestics;
    private MerchantAccountInformation domesticCentralRepository;
    private Integer merchantCategoryCode;
    private Currency transactionCurrency;
    @Builder.Default
    private Double transactionAmount = 0.0;
    private Tip tip;
    private Locale countryCode;
    private String merchantName;
    private String merchantCity;
    private String postalCode;
    private AdditionalData additionalData;
    private MerchantInformationLanguage merchantInformationLanguage;
    private String crc;



    private String getPayloadFormatIndicatorAsString(){
        return Optional.ofNullable(this.payloadFormatIndicator).map(data-> {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            String defaultTag = "00";
            return defaultTag +
                    String.format("%02d", data.length()) +
                    data;
        }).orElse("");
    }

    private String getPointOfInitiationMethodAsString(){
       return Optional.ofNullable(this.pointOfInitiationMethod).map(data-> {
           DecimalFormat df = new DecimalFormat();
           df.setMaximumFractionDigits(2);
           String defaultTag = "01";
           return defaultTag +
                   String.format("%02d", this.pointOfInitiationMethod.toString().length()) +
                   this.pointOfInitiationMethod;
       }).orElse("");
    }

    private  String getMerchantAccountInformationAsStringAsString(MerchantAccountInformation merchantAccountInformation, String tag){
        return Optional.ofNullable(merchantAccountInformation).map(information-> {
            String globalUniquIdentifier = Optional.ofNullable(information.getGloballyUniqueIdentifier()).map(data -> {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                String tagIdentifier = "00";
                String lengthIdentifier = String.format("%02d",(data.length()));
                return tagIdentifier + lengthIdentifier + data;
            }).orElse("");


            String pan = Optional.ofNullable(information.getPersonalAccountNumber()).map(data-> {
                String tagPan = "01";
                String tagLengthPan =  String.format("%02d",(information.getPersonalAccountNumber().length()));
                String valuePan = information.getPersonalAccountNumber();
                return tagPan + tagLengthPan + valuePan;
            }).orElse("");


            String merchantId = Optional.ofNullable(information.getMerchantId()).map(data-> {
                String tagMerchantId = "02";
                String tagLengthMerchantId = String.format("%02d",(information.getMerchantId().length()));
                String valueMerchantId = information.getMerchantId();
                return tagMerchantId + tagLengthMerchantId + valueMerchantId;
            }).orElse("");

            String merchantQriteria = Optional.ofNullable(information.getCriteria()).map(data-> {
                String tagMerchantCriteria = "03";
                String tagLengthMerchantQriteria = String.format("%02d",(data.toString().length()));
                return tagMerchantCriteria + tagLengthMerchantQriteria + data;
            }).orElse("");


            int length =  globalUniquIdentifier.length()+pan.length()+merchantId.length()+merchantQriteria.length();
            String valueMerchantAccountInformation = globalUniquIdentifier + pan + merchantId + merchantQriteria;

            return tag + String.format("%02d", length) + valueMerchantAccountInformation;
        }).orElse("");

    }

    private  String getMerchantCategoryCodeAsString(){
        return Optional.ofNullable(this.merchantCategoryCode).map(data-> {
            String tagMerchantCategory = "52";
            String formattedMerchantCategoryCode = String.format("%04d",data);
            String tagLengthMerchantQriteria = String.format("%02d",(formattedMerchantCategoryCode.length()));
            return tagMerchantCategory + tagLengthMerchantQriteria + formattedMerchantCategoryCode;
        }).orElse("");
    }

    private  String getCurrencyCodeAsString(){
        return Optional.ofNullable(this.transactionCurrency).map(data-> {
            String tagCurrencyCode = "53";
            String tagLengthCurrencyCode = String.format("%02d",(data.getNumericCodeAsString().length()));
            return tagCurrencyCode + tagLengthCurrencyCode + data.getNumericCodeAsString();
        }).orElse("");
    }

    private  String getTransactionAmountAsString(){
        return Optional.ofNullable(this.transactionAmount).map(data-> {
            if(data>0){
                String tagTransactionAmount = "54";
                    String tagTransactionAmountLength = String.format("%02d",(String.valueOf(this.transactionAmount.intValue()).length()));
                    return tagTransactionAmount + tagTransactionAmountLength + this.transactionAmount.intValue();
            }else {
                return "";
            }
        }).orElse("");
    }
    private String getTipAsString(){
        return Optional.ofNullable(this.tip).map(data-> {
            String currencyCode = "";
            if(data.indicator!=null){
                String tagCurrencyCode = "55";
                String tagLengthCurrencyCode = String.format("%02d",(this.tip.indicator.length()));
                String tipPercentage = "";
                String tipFixed = "";
                if(data.percentage>0){
                    String tipTag = "57";
                    tipPercentage=tipTag+String.format("%02d",(data.getPercentage().toString().length()))+data.getPercentage().toString();
                }

                if( data.fixed>0){
                    String tipTag = "56";
                    tipFixed=tipTag+String.format("%02d",(String.valueOf(data.getFixed().intValue()).length()))+ data.getFixed().intValue();
                }

                currencyCode=tagCurrencyCode + tagLengthCurrencyCode + this.tip.indicator+tipPercentage+tipFixed;
            }
            return currencyCode;
        }).orElse("");
    }

    private String getCountryCodeAsString(){
     return Optional.ofNullable(this.countryCode).map(data-> {
         String tagCountryCode = "58";
         String tagLengthCountryCodeLength = String.format("%02d",(data.getCountry().length()));
         return tagCountryCode + tagLengthCountryCodeLength + data.getCountry();
     }).orElse("");
    }

    private String getMerchantNameAsString(){
        return Optional.ofNullable(this.merchantName).map(data-> {
            String tagMerchantName = "59";
            String tagMerchantNameLength = String.format("%02d",(data.length()));
            return tagMerchantName + tagMerchantNameLength + data;
        }).orElse("");

    }

    private String getMerchantCityAsString(){
        return Optional.ofNullable(this.merchantCity).map(data-> {
            String tagMerchantCity = "60";
            String tagMerchantCityLength = String.format("%02d",(this.merchantCity.length()));
            return tagMerchantCity + tagMerchantCityLength + this.getMerchantCity();
        }).orElse("");

    }

    private String getPostalCodeAsString(){
        return Optional.ofNullable(this.postalCode).map(data-> {
            String tagCurrencyCode = "61";
            String tagLengthCurrencyCode = String.format("%02d",(this.postalCode.toString().length()));
            return tagCurrencyCode + tagLengthCurrencyCode + this.postalCode;
        }).orElse("");
    }

    private String getAdditionalDataAsString(){
        return Optional.ofNullable(this.additionalData).map(data-> {
            String tagAdditionalData = "62";
            String tagAdditionalDataLength = String.format("%02d",(this.additionalData.getValue().length()));
            return tagAdditionalData + tagAdditionalDataLength + additionalData.getValue();
        }).orElse("");

    }

    private String getCrcAsString(){
      return Optional.ofNullable(this.crc).map(data-> {
          String tagCrc = "63";
          String tagCrcLength = String.format("%02d",(this.crc.length()));
          return tagCrc + tagCrcLength + this.crc;
      }).orElse("");
    }

    @Override
    public String toString() {
        return this.getPayloadFormatIndicatorAsString() +
                this.getPointOfInitiationMethodAsString() +
                this.getMerchantAccountInformationAsStringAsString(this.getMerchantAccountInformationDomestics().get(26), "26") +
                this.getMerchantAccountInformationAsStringAsString(this.getDomesticCentralRepository(),"51") +
                this.getMerchantCategoryCodeAsString() +
                this.getCurrencyCodeAsString() +
                this.getTransactionAmountAsString() +
                this.getTipAsString() +
                this.getCountryCodeAsString() +
                this.getMerchantNameAsString() +
                this.getMerchantCityAsString() +
                this.getPostalCodeAsString() +
                this.getAdditionalDataAsString() +
                this.getCrcAsString();
    }

}
