package com.astrapay.qris.mpm.object;

import lombok.*;

/**
 * @author Arthur Purnama
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tip {

    String indicator;
    @Builder.Default
    Double fixed = 0.0;
    @Builder.Default
    Double percentage = 0.0;
}
