package com.astrapay.qris;

import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.QrisPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QrisHttpMessageConverterTest {

    private QrisHttpMessageConverter<QrisPayload> converter;

    @Mock
    private QrisParser qrisParser;

    @Mock
    private HttpInputMessage inputMessage;

    @Mock
    private HttpOutputMessage outputMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new QrisHttpMessageConverter<>();
        converter.setQrisParser(qrisParser);
    }

    @Test
    void testConstructor() {
        assertEquals(QrisMediaType.APPLICATION_QRIS, converter.getSupportedMediaTypes().get(0));
    }

    @Test
    void testSupports() {
        assertTrue(converter.supports(QrisPayload.class));
    }

//    @Test
//    void testReadInternal() throws IOException {
//        String mockPayload = "mockPayload";
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(mockPayload.getBytes(StandardCharsets.UTF_8));
//        when(inputMessage.getBody()).thenReturn(inputStream);
//        QrisPayload mockQrisPayload = new QrisPayload();
//        when(qrisParser.parse(mockPayload)).thenReturn(mockQrisPayload);
//
//        QrisPayload result = converter.readInternal(QrisPayload.class, inputMessage);
//
//        verify(qrisParser, times(1)).parse(mockPayload);
//        assertEquals(mockQrisPayload, result);
//    }

    @Test
    void testWriteInternal() throws IOException {
        QrisPayload mockQrisPayload = new QrisPayload();
        mockQrisPayload.setPayload("mockPayload");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(outputMessage.getBody()).thenReturn(outputStream);

        converter.writeInternal(mockQrisPayload, outputMessage);

        assertEquals("mockPayload", outputStream.toString(StandardCharsets.UTF_8.name()));
    }
}
