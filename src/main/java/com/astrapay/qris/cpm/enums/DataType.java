package com.astrapay.qris.cpm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataType {
    NUMERIC,
    BYTE,
    COMPRESSED_NUMERIC,
    ALPHA_NUMERIC_SPECIAL,
    ALPHA_NUMERIC,
    UNKNOWN

}
