package com.astrapay.qris;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QrisHexConverter {

    final int EVEN_LENGTH_CHECK = 2;
    final int ZERO = 0;
    int NO_SUBTAG = 0;
    int SUFFIX_INDEX = 1;
    final String UNEVEN_COMPRESSED_NUMERIC_SUFFIX = "F";

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
    public String encode(byte[] value, byte tag, byte subtag) throws IOException {
        var value_byteArray = new ByteArrayOutputStream();
        //TAG INDICATOR
        value_byteArray.write(tag);
        //SUBTAG INDICATOR
        if(subtag != NO_SUBTAG){
            value_byteArray.write(subtag);
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



}
