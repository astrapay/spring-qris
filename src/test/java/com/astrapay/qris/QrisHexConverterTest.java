package com.astrapay.qris;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.astrapay.qris.cpm.enums.TagIndicator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class QrisHexConverterTest {
    @InjectMocks
    QrisHexConverter converter;

    @Test
    void testConvertStringWithCompressedNumericValue() throws DecoderException, IOException {
        // Test case 1
        byte[] valueInByte = converter.convertCompressedNumericToArrayByte("9360123411234567899");
        var expectedOutput = "5A0A9360123411234567899F";
        assertEquals(expectedOutput, converter.encode(valueInByte, APP_PAN.getByteTag()));
    }

    @Test
    void testConvertStringWithByteOrNumberValue() throws DecoderException, IOException {

        byte[] adfNameByte = converter.convertByteOrNumberToArrayByte("A0000006022020");
        var expectedOutput2 = "4F07A0000006022020";
        assertEquals(expectedOutput2, converter.encode(adfNameByte, ADF_NAME.getByteTag()));

        byte[] last4DigitPanByte = converter.convertByteOrNumberToArrayByte("7899");
        var expectedOutput = "9F25027899";
        assertEquals(expectedOutput, converter.encode(last4DigitPanByte, LAST_4_DIGIT_PAN.getByteTag()));
    }

    @Test
    void testConvertStringWithAlphaNumericValue() throws IOException {

        byte[] valueInByte = converter.convertAlphaNumericToArrayByte("QRISCPM");
        var expectedOutput = "50075152495343504D";
        assertEquals(expectedOutput, converter.encode(valueInByte, APPLICATION_LABEL.getByteTag()));

        byte[] cardHolderNameByte = converter.convertAlphaNumericToArrayByte("Riki Derian");
        expectedOutput = "5F200B52696B692044657269616E";
        assertEquals(expectedOutput, converter.encode(cardHolderNameByte, CARDHOLDER_NAME.getByteTag()));

        byte[] languageByte = converter.convertAlphaNumericToArrayByte("iden");
        expectedOutput = "5F2D046964656E";
        assertEquals(expectedOutput, converter.encode(languageByte, LANGUAGE_PREFERENCE.getByteTag()));

        byte[] issuerUrlByte = converter.convertAlphaNumericToArrayByte("riki.derian@qriscpm.com");
        expectedOutput = "5F501772696B692E64657269616E407172697363706D2E636F6D";
        assertEquals(expectedOutput, converter.encode(issuerUrlByte, ISSUER_URL.getByteTag()));
    }

    @Test
    void testEncodeWithByteValue() throws DecoderException, IOException {
        //an example of implementation to straight forward converting String value into hex
        byte[] value = converter.convertByteOrNumberToArrayByte("A0000006022020");
        var expectedOutput = "A0000006022020";
        assertEquals(expectedOutput, converter.encode(value));
    }

    @Test
    void testEncodeWithByteArrayOutputStream() throws DecoderException, IOException {
        //an example of implementation to convert ByteArrayOutputStream data type into HEX
        //this scenario for example if you want to build the ByteArrayOutputStream outside and
        //pass it into the encoder
        byte[] value = converter.convertByteOrNumberToArrayByte("A0000006022020");
        var expectedOutput = "4F07A0000006022020";
        var byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(ADF_NAME.getByteTag()[0]);
        byteArrayOutputStream.write(value.length);
        byteArrayOutputStream.write(value);
        assertEquals(expectedOutput, converter.encode(byteArrayOutputStream));
    }

    @Test
    void testEncodeToTlvFormattedString() throws IOException {
        //an example of implementation if you want to directly convert a tag data to HEX and input
        //all TLV information here
        byte[] issuerUrlByte = converter.convertAlphaNumericToArrayByte("riki.derian@qriscpm.com");
        var expectedOutput = "5F501772696B692E64657269616E407172697363706D2E636F6D";
        assertEquals(expectedOutput, converter.encode(issuerUrlByte, ISSUER_URL.getByteTag()));
    }

    @Test
    void testConvertAlphaNumericHexIntoString() throws DecoderException {
        var hexString = "5152495343504D";
        var index = 0;
        var expectedOutput = "QRISCPM";
        assertEquals(expectedOutput, converter.convertAlphaNumericHexToString(hexString, index));
    }

    @Test
    void testConvertCompressedNumericHexToString() {
        var hexString = "9360123411234567899F";
        var index = 0;
        var expectedOutput = "9360123411234567899";
        assertEquals(expectedOutput, converter.convertCompressedNumericHexToString(hexString, index));
    }

    @Test
    void testByteHexToString() {
        var hexString = "A0000006022020";
        var index = 0;
        var expectedOutput = "A0000006022020";
        assertEquals(expectedOutput, converter.convertByteOrNumberHexToString(hexString, index));
    }

    @Test
    void testNumberHexToString() {
        var hexString = "7899";
        var index = 0;
        var expectedOutput = "7899";
        assertEquals(expectedOutput, converter.convertByteOrNumberHexToString(hexString, index));
    }

    @Test
    void testTag61With4DigitLength() throws DecoderException, IOException {
        // Test case 1
        byte[] value = converter.convertByteOrNumberToArrayByte("4F07A000000602202050075152495343504D5A0A9360123411234567899F5F200B52696B692044657269616E5F2D046964656E5F501772696B692E64657269616E407172697363706D2E636F6D9F25027899633F9F");
        var expectedOutput = "6181934F07A000000602202050075152495343504D5A0A9360123411234567899F5F200B52696B692044657269616E5F2D046964656E5F501772696B692E64657269616E407172697363706D2E636F6D9F25027899633F9F";
        var byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(APPLICATION_TEMPLATE.getByteTag());
        byteArrayOutputStream.write((byte)0x81);
        byteArrayOutputStream.write((byte)0x93);
        byteArrayOutputStream.write(value);
        assertEquals(expectedOutput, converter.encode(byteArrayOutputStream));
    }

    @Test
    void testTag63() throws DecoderException, IOException {
        byte[] valueInByte = converter.convertByteOrNumberToArrayByte("9F743C313233343536373839303132333435363738393031323334353637383930313233343536373839303132333435363738393031323334353637383930");
        var expectedOutput = "633F9F743C313233343536373839303132333435363738393031323334353637383930313233343536373839303132333435363738393031323334353637383930";
        assertEquals(expectedOutput, converter.encode(valueInByte, APPLICATION_SPECIFIC_TRANSPARENT_TEMPLATE.getByteTag()));
    }

    @Test
    void testEncodeToBase64() throws DecoderException, IOException {
        var value = converter.convertAlphaNumericToArrayByte("CPV01");
        var expectedOutput = "hQVDUFYwMQ==";
        assertEquals(expectedOutput, converter.encodeToBase64(value, PAYLOAD_FORMAT_INDICATOR.getByteTag()));
    }

//    @Test
//    void testConcatEncodeToBase64() throws IOException {
//        var value3 = converter.convertAlphaNumericToArrayByte("CPV01");
//        var value = converter.convertAlphaNumericToArrayByte("A0000006022020");
//        var value2 = converter.convertAlphaNumericToArrayByte("QRISCPM");
//        byte[] valueArray3 = converter.encodeToByte(value3, PAYLOAD_FORMAT_INDICATOR.getByteT;
//        byte[] valueArray = converter.encodeToByte(value, ADF_NAME.getByteT;
//        byte[] valueArray2 = converter.encodeToByte(value2, APPLICATION_LABEL.getByteT;
//        //combined the two byte array
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byteArrayOutputStream.write(valueArray3);
//        byteArrayOutputStream.write(APPLICATION_TEMPLATE.getByteTag());
//        byteArrayOutputStream.write((byte)0x81);
//        byteArrayOutputStream.write((byte)0x93);
//        byteArrayOutputStream.write
//        byteArrayOutputStream.write(valueArray);
//        byteArrayOutputStream.write(valueArray2);
//        var base64 = converter.encodeToBase64(byteArrayOutputStream.toByteArray());
//        var expectedOutput = "NEYwN0EwMDAwMDYwMjIwMjBQNFJJS0lTQ1BNOjUwMDc1MTI0OTUzNDU2Nzg5OUY";
//        assertEquals(expectedOutput, base64);
//    }
}

