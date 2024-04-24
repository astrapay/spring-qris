package com.astrapay.qris.cpm.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QrCpmPayload {
    // object to represent payload (unparsed, string format) and do validations
    private String payloadBase64;
    private String payloadHex;
    private Map<String, QrCpmDataObject> qrisRoot;
}
