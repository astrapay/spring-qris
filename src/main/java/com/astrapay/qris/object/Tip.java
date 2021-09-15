package com.astrapay.qris.object;

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

    @Builder.Default
    String indicator = "0";
    @Builder.Default
    Double fixed = 0.0;
    @Builder.Default
    Double percentage = 0.0;
}
