package com.astrapay.qris;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QrisMediaTypeTest {

    @Test
    void testConstructor() {
        String type = "qris";
        QrisMediaType qrisMediaType = new QrisMediaType(type);

        assertNotNull(qrisMediaType);
    }

    @Test
    void testApplicationQrisValue() {
        assertEquals("application/qris", QrisMediaType.APPLICATION_QRIS_VALUE);
    }

    @Test
    void testApplicationQris() {
        MediaType expectedMediaType = MediaType.valueOf(QrisMediaType.APPLICATION_QRIS_VALUE);
        assertNotNull(QrisMediaType.APPLICATION_QRIS);
        assertEquals(QrisMediaType.APPLICATION_QRIS, expectedMediaType);
    }
}
