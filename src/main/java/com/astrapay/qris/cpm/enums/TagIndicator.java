package com.astrapay.qris.cpm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TagIndicator {
    ADF_NAME(new Byte[]{(byte) 0x4f}, "4F", DataType.BYTE),
    APPLICATION_LABEL(new Byte[]{(byte) 0x50}, "50", DataType.ALPHA_NUMERIC_SPECIAL),
    APP_PAN(new Byte[]{(byte) 0x5a}, "5A", DataType.COMPRESSED_NUMERIC),
    APPLICATION_VERSION_NUMBER(new Byte[]{(byte) 0x9f, (byte) 0x08}, "9F08", DataType.BYTE),
    APPLICATION_TEMPLATE(new Byte[]{(byte) 0x61}, "61", DataType.BYTE),
    APPLICATION_CRYPTOGRAM(new Byte[]{(byte) 0x9f, (byte) 0x26}, "9F26", DataType.BYTE),
    APPLICATION_TRANSACTION_COUNTER(new Byte[]{(byte) 0x9f, (byte) 0x36}, "9F36", DataType.BYTE),
    APPLICATION_INTERCHANGE_PROFILE(new Byte[]{(byte) 0x82}, "82", DataType.BYTE),
    APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE(new Byte[]{(byte) 0x63}, "63", DataType.BYTE),
    CARDHOLDER_NAME(new Byte[]{(byte) 0x5f, (byte) 0x20}, "5F20", DataType.ALPHA_NUMERIC_SPECIAL),
    LANGUAGE_PREFERENCE(new Byte[]{(byte) 0x5f, (byte) 0x2d}, "5F2D", DataType.ALPHA_NUMERIC),
    ISSUER_URL(new Byte[]{(byte) 0x5f, (byte) 0x50}, "5F50", DataType.ALPHA_NUMERIC_SPECIAL),
    LAST_4_DIGIT_PAN(new Byte[]{(byte) 0x9f, (byte) 0x25}, "9F25", DataType.NUMERIC),
    TRACK_2_EQUIVALENT_DATA(new Byte[]{(byte) 0x57}, "57", DataType.BYTE),
    COMMON_DATA_TEMPLATE(new Byte[]{(byte) 0x62}, "62", DataType.BYTE),
    COMMON_DATA_TRANSPARENT_TEMPLATE(new Byte[]{(byte) 0x64}, "64", DataType.BYTE),
    TOKEN_REQUESTOR_ID(new Byte[]{(byte) 0x9f, (byte) 0x19}, "9F19", DataType.NUMERIC),
    PAYLOAD_FORMAT_INDICATOR(new Byte[]{(byte) 0x85}, "85", DataType.ALPHA_NUMERIC),
    PAYMENT_ACCOUNT_REFERENCE(new Byte[]{(byte) 0x9f, (byte) 0x24}, "9F24", DataType.ALPHA_NUMERIC),
    CRYPTOGRAM_INFORMATION_DATA(new Byte[]{(byte) 0x9f, (byte) 0x27}, "9F27", DataType.BYTE),
    ISSUER_APPLICATION_DATA(new Byte[]{(byte) 0x9f, (byte) 0x10}, "9F10", DataType.BYTE),
    UNPREDICTABLE_NUMBER(new Byte[]{(byte) 0x9f, (byte) 0x37}, "9F37", DataType.BYTE),
    ISSUER_QRIS_DATA(new Byte[]{(byte) 0x9f, (byte) 0x74}, "9F74", DataType.ALPHA_NUMERIC);

    final Byte[] byteTag;
    final String value;

    final DataType dataType;

    public static DataType getDataType(String input) {
        for (TagIndicator tag : TagIndicator.values()) {
            if (input.startsWith(tag.value)) {
                return tag.dataType;
            }
        }
        return DataType.UNKNOWN;
    }
}



