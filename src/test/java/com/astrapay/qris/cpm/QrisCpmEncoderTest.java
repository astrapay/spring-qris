package com.astrapay.qris.cpm;

import com.astrapay.qris.QrisHexConverter;
import com.astrapay.qris.cpm.object.ApplicationTemplate;
import com.astrapay.qris.cpm.object.QrisCpm;
import com.astrapay.qris.mpm.object.ApplicationSpecificTransparentTemplate;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)

public class QrisCpmEncoderTest {
    
    @InjectMocks
    QrisCpmEncoder qrisCpmEncoder;
    
    @Spy
    QrisHexConverter qrisHexConverter;
    
    @Test
    void testEncodeToBase64_1() throws IOException {
        ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate = ApplicationSpecificTransparentTemplate.builder()
                .issuerData("123456789012345678901234567890123456789012345678901234567890")
                .build();
        ApplicationTemplate applicationTemplate = ApplicationTemplate.builder()
                .applicationPan("9360123411234567899")
                .cardholderName("Riki Derian")
                .issuerUrl("riki.derian@qriscpm.com")
                .last4DigitsPan("7899")
                .applicationSpecificTransparentTemplate(applicationSpecificTransparentTemplate)
                .build();
        QrisCpm qrisCpm = QrisCpm.builder()
                .applicationTemplate(applicationTemplate)
                .build();
        
        var expectedOutput = "hQVDUFYwMWGBk08HoAAABgIgIFAHUVJJU0NQTVoKk2ASNBEjRWeJn18gC1Jpa2kgRGVyaWFuXy0EaWRlbl9QF3Jpa2kuZGVyaWFuQHFyaXNjcG0uY29tnyUCeJljP590PDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MA==";
        Assertions.assertEquals(expectedOutput, qrisCpmEncoder.encode(qrisCpm));
    }
    
    @Test
    void testEncodeToBase64_2() throws IOException {
        ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate = ApplicationSpecificTransparentTemplate.builder()
                .issuerData("281012029560385043466010")
                .build();
        ApplicationTemplate applicationTemplate = ApplicationTemplate.builder()
                .applicationPan("9360091531624094763")
                .cardholderName("Grace")
                .applicationSpecificTransparentTemplate(applicationSpecificTransparentTemplate)
                .build();
        QrisCpm qrisCpm = QrisCpm.builder()
                .applicationTemplate(applicationTemplate)
                .build();
        
        var expectedOutput = "hQVDUFYwMWFKTwegAAAGAiAgUAdRUklTQ1BNWgqTYAkVMWJAlHY/XyAFR3JhY2VfLQRpZGVuYxufdBgyODEwMTIwMjk1NjAzODUwNDM0NjYwMTA=";
        Assertions.assertEquals(expectedOutput, qrisCpmEncoder.encode(qrisCpm));
    }
    
    @Test
    void testEncodeToBase64_3() throws IOException {
        ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate = ApplicationSpecificTransparentTemplate.builder()
                .issuerData("2810120295603850434")
                .applicationCryptogram("0102030405060708")
                .cryptogramInformationData("80")
                .issuerApplicationData("000102030405060708090A0B0C0D0E0F")
                .applicationTransactionCounter("0001")
                .applicationInterchangeProfile("5980")
                .unpredictableNumber("01020304")
                .build();
        ApplicationTemplate applicationTemplate = ApplicationTemplate.builder()
                .track2EquivalentData("12345678901234523D49112011234567")
                .applicationPan("9360091531624094763")
                .cardholderName("Grace")
                .issuerUrl("6d61696c746f3a6578616d706c6540656d76636f2e636f6d")
                .applicationVersionNumber("0010")
                .tokenRequestorId("099901234567")
                .paymentAccountReference("39393939313233344142434445464748494a4b4c4d4e4f505152535455")
                .last4DigitsPan("4763")
                .applicationSpecificTransparentTemplate(applicationSpecificTransparentTemplate)
                .build();
        QrisCpm qrisCpm = QrisCpm.builder()
                .applicationTemplate(applicationTemplate)
                .build();
        var expectedOutput = "hQVDUFYwMWGCAQxPB6AAAAYCICBQB1FSSVNDUE1XEBI0VniQEjRSPUkRIBEjRWdaCpNgCRUxYkCUdj9fIAVHcmFjZV8tBGlkZW5fUDA2ZDYxNjk2Yzc0NmYzYTY1Nzg2MTZkNzA2YzY1NDA2NTZkNzY2MzZmMmU2MzZmNmSfCAIAEJ8lAkdjnxkGCZkBI0VnnyQ6MzkzOTM5MzkzMTMyMzMzNDQxNDI0MzQ0NDU0NjQ3NDg0OTRhNGI0YzRkNGU0ZjUwNTE1MjUzNTQ1NWNIn3QTMjgxMDEyMDI5NTYwMzg1MDQzNJ8mCAECAwQFBgcInycBgJ8QEAABAgMEBQYHCAkKCwwNDg+fNgIAAYICWYCfNwQBAgME";
        Assertions.assertEquals(expectedOutput, qrisCpmEncoder.encode(qrisCpm));
    }
    
    @Test
    void testEncodeToBase64_exception() throws DecoderException {
        ApplicationSpecificTransparentTemplate applicationSpecificTransparentTemplate = ApplicationSpecificTransparentTemplate.builder()
                .issuerData("123456789012345678901234567890123456789012345678901234567890")
                .build();
        ApplicationTemplate applicationTemplate = ApplicationTemplate.builder()
                .applicationPan("9360123411234567899")
                .cardholderName("Riki Derian")
                .issuerUrl("riki.derian@qriscpm.com")
                .last4DigitsPan("7899")
                .applicationSpecificTransparentTemplate(applicationSpecificTransparentTemplate)
                .build();
        QrisCpm qrisCpm = QrisCpm.builder()
                .applicationTemplate(applicationTemplate)
                .build();
        Mockito.doThrow(new DecoderException()).when(qrisHexConverter).convertCompressedNumericToArrayByte(Mockito.anyString());
        Assertions.assertThrows(IOException.class, () -> qrisCpmEncoder.encode(qrisCpm));
    }
    
    @Test
    void testEncodeToBase64_EmptyQr() {
        Assertions.assertDoesNotThrow(() -> qrisCpmEncoder.encode(new QrisCpm()));
    }
    
    @Test
    void testGetLength_overLimit() {
        byte[] foo = new byte[16777216];
        Assertions.assertThrows(IllegalStateException.class, () -> qrisCpmEncoder.getTagLength(foo));
    }
    
    @Test
    void testGetLength_maxLength() {
        byte[] foo = new byte[16777215];
        byte[] expectedLength = new byte[]{(byte)0x83, (byte)0xFF, (byte)0xFF, (byte)0xFF};
        Assertions.assertArrayEquals(expectedLength, qrisCpmEncoder.getTagLength(foo));
    }
}
