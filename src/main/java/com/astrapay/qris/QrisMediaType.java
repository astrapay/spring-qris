package com.astrapay.qris;

import org.springframework.http.MediaType;

/**
 * @author Arthur Purnama
 */
public class QrisMediaType extends MediaType {

    /**
     * constructor
     * @param type type
     */
    public QrisMediaType(String type) {
        super(type);
    }

    /**
     * Public constant media type for {@code application/qris}.
     */
    public static final org.springframework.http.MediaType APPLICATION_QRIS = MediaType.valueOf(QrisMediaType.APPLICATION_QRIS_VALUE);

    /**
     * A String equivalent of {@link QrisMediaType#APPLICATION_QRIS}.
     */
    public static final String APPLICATION_QRIS_VALUE = "application/qris";


}
