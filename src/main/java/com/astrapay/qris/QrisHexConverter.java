package com.astrapay.qris;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class QrisHexConverter {

    final int EVEN_LENGTH_CHECK = 2;
    final int ZERO = 0;
    int SUFFIX_INDEX = 1;
    final String UNEVEN_COMPRESSED_NUMERIC_SUFFIX = "F";

    int FIRST_TAG_HEX_INDEX = 0;
    int SECOND_TAG_HEX_INDEX = 1;
    int RADIX = 16;
    int SHIFT_NUMBER = 4;

    public byte[] convertByteOrNumberToArrayByte(String numberOrByte) throws DecoderException {
        //acceptable string is a string that represent byte or number
        return Hex.decodeHex(numberOrByte);
    }

    public byte[] convertCompressedNumericToArrayByte(String compressedNumeric) throws DecoderException {
        //acceptable string is a string that represent byte or number

        return Hex.decodeHex(compressedNumeric.length() % EVEN_LENGTH_CHECK == ZERO ? compressedNumeric : compressedNumeric + UNEVEN_COMPRESSED_NUMERIC_SUFFIX);
    }

    public byte[] convertAlphaNumericToArrayByte(String alphaNumeric) {
        return alphaNumeric.getBytes();
    }


    //if you want to directly encode all TLV information into HEX
    public String encode(byte[] value, byte[] tags) throws IOException {
        var value_byteArray = new ByteArrayOutputStream();
        //TAG INDICATOR
        value_byteArray.write(tags[FIRST_TAG_HEX_INDEX]);
        //SUBTAG INDICATOR
        if(tags.length > SECOND_TAG_HEX_INDEX) {
            value_byteArray.write(tags[SECOND_TAG_HEX_INDEX]);
        }
        //LENGTH INDICATOR
        value_byteArray.write(value.length);
        //VALUE
        value_byteArray.write(value);
        return Hex.encodeHexString(value_byteArray.toByteArray(), false);
    }

    //if you want to encode byte[] type of data into HEX
    public String encode(byte[] value) throws IOException {
        var value_byteArray = new ByteArrayOutputStream();
        value_byteArray.write(value);
        return Hex.encodeHexString(value_byteArray.toByteArray(), false);
    }

    //if you want to encode ByteArrayOutputStream type of data into HEX
    public String encode(ByteArrayOutputStream byteArrayOutputStream) {
        return Hex.encodeHexString(byteArrayOutputStream.toByteArray(), false);
    }

    public byte[] encodeToByte(byte[] value, byte[] tags) throws IOException {
        var value_byteArray = new ByteArrayOutputStream();
        //TAG INDICATOR
        value_byteArray.write(tags[FIRST_TAG_HEX_INDEX]);
        //SUBTAG INDICATOR
        if(tags.length > SECOND_TAG_HEX_INDEX){
            value_byteArray.write(tags[SECOND_TAG_HEX_INDEX]);
        }
        //LENGTH INDICATOR
        value_byteArray.write(value.length);
        //VALUE
        value_byteArray.write(value);
        return value_byteArray.toByteArray();

    }

    public String encodeToBase64(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    public String encodeToBase64(byte[] value, byte[] tags) throws IOException {
        var valueInByteArray = encodeToByte(value, tags);
        return Base64.getEncoder().encodeToString(valueInByteArray);
    }

    public byte[] decodeFromBase64(String value) {
        return Base64.getDecoder().decode(value);
    }

    public String convertAlphaNumericHexToString (String hexString) throws DecoderException {
        return new String(Hex.decodeHex(hexString));
    }
    public String convertAlphaNumericHexToString (String hexString, int index) throws DecoderException {
        return new String(Hex.decodeHex(hexString.substring(index)));
    }

    public String convertByteOrNumberHexToString (String hexString, int index) {
        return hexString.substring(index);
    }

    public String convertCompressedNumericHexToString (String hexString, int index) {
        if (hexString.endsWith(UNEVEN_COMPRESSED_NUMERIC_SUFFIX)) {
            return hexString.substring(index, hexString.length() - SUFFIX_INDEX);
        }
        return hexString.substring(index);
    }


    public byte[] hexStringToByteArray(String s) {
    byte[] byteArray = new byte[s.length() / EVEN_LENGTH_CHECK];
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        for (int i = ZERO; i < s.length(); i += EVEN_LENGTH_CHECK) {
            buffer.put((byte) ((Character.digit(s.charAt(i), RADIX) << SHIFT_NUMBER)
                    + Character.digit(s.charAt(i + SUFFIX_INDEX), RADIX)));
        }
        return byteArray;
    }


}
