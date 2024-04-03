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
        assertEquals(expectedOutput, converter.encode(valueInByte, APP_PAN.getByteTag(), APP_PAN.getByteSubTag()));
    }

    @Test
    void testConvertStringWithByteOrNumberValue() throws DecoderException, IOException {

        byte[] adfNameByte = converter.convertByteOrNumberToArrayByte("A0000006022020");
        var expectedOutput2 = "4F07A0000006022020";
        assertEquals(expectedOutput2, converter.encode(adfNameByte, ADF_NAME.getByteTag(), ADF_NAME.getByteSubTag()));

        byte[] last4DigitPanByte = converter.convertByteOrNumberToArrayByte("7899");
        var expectedOutput = "9F25027899";
        assertEquals(expectedOutput, converter.encode(last4DigitPanByte, LAST_4_DIGIT_PAN.getByteTag(), LAST_4_DIGIT_PAN.getByteSubTag()));
    }

    @Test
    void testConvertStringWithAlphaNumericValue() throws IOException {

        byte[] valueInByte = converter.convertAlphaNumericToArrayByte("QRISCPM");
        var expectedOutput = "50075152495343504D";
        assertEquals(expectedOutput, converter.encode(valueInByte, APPLICATION_LABEL.getByteTag(), APPLICATION_LABEL.getByteSubTag()));

        byte[] cardHolderNameByte = converter.convertAlphaNumericToArrayByte("Riki Derian");
        expectedOutput = "5F200B52696B692044657269616E";
        assertEquals(expectedOutput, converter.encode(cardHolderNameByte, CARDHOLDER_NAME.getByteTag(), CARDHOLDER_NAME.getByteSubTag()));

        byte[] languageByte = converter.convertAlphaNumericToArrayByte("iden");
        expectedOutput = "5F2D046964656E";
        assertEquals(expectedOutput, converter.encode(languageByte, LANGUAGE_PREFERENCE.getByteTag(), LANGUAGE_PREFERENCE.getByteSubTag()));

        byte[] issuerUrlByte = converter.convertAlphaNumericToArrayByte("riki.derian@qriscpm.com");
        expectedOutput = "5F501772696B692E64657269616E407172697363706D2E636F6D";
        assertEquals(expectedOutput, converter.encode(issuerUrlByte, ISSUER_URL.getByteTag(), ISSUER_URL.getByteSubTag()));
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
        byteArrayOutputStream.write(ADF_NAME.getByteTag());
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
        assertEquals(expectedOutput, converter.encode(issuerUrlByte, ISSUER_URL.getByteTag(), ISSUER_URL.getByteSubTag()));
    }

}

