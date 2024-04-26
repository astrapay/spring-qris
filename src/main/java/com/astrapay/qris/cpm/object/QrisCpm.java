package com.astrapay.qris.cpm.object;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QrisCpm {
    // class to represent QR CPM (used to generate QR)
    // tag 85
    String payloadFormatIndicator;
    // tag 61
    ApplicationTemplate applicationTemplate;
    
}
