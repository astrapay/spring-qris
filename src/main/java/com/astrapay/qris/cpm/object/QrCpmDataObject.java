package com.astrapay.qris.cpm.object;

import lombok.*;

import javax.validation.Valid;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QrCpmDataObject {
    private String tag;
    private String length;
    private String value;
    private Map<String, QrCpmDataObject> templateMap;
}
