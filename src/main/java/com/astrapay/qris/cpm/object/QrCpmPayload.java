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

    @CpmMandatoryField
    @CpmMandatoryField(id = "85")
    @CpmMandatoryField(id = "61")
    @CpmMandatoryField(id = "4F")
    @CpmMandatoryField(id = "50")
    @CpmMandatoryField(id = "63")
    @CpmMandatoryField(id = "9F74")
    @CpmConditionalField(id = "57")
    @HexLength(id = "85", min = 5, max = 5)
    @HexLength(id = "4F", min = 5, max = 16)
    @HexLength(id = "50", min = 1, max = 16)
    @HexLength(id = "57", max = 19)
    @HexLength(id = "5A", min = 1, max = 10)
    @HexLength(id = "5F20", min = 2, max = 26)
    @HexLength(id = "5F2D", min = 2, max = 8)
    @HexLength(id = "9F08", min = 2, max = 2)
    @HexLength(id = "9F25", min = 4, max = 4)
    @HexLength(id = "9F74", min = 8, max = 64)
    @HexLength(id = "9F19", min = 11, max = 11)
    @HexLength(id = "9F24", min = 29, max = 29)
    @HexLength(id = "9F26", min = 8, max = 8)
    @HexLength(id = "9F27", min = 1, max = 1)
    @HexLength(id = "9F10", min = 1, max = 32)
    @HexLength(id = "9F36", min = 2, max = 2)
    @HexLength(id = "9F37", min = 4, max = 4)
    @HexLength(id = "82", min = 2, max = 2)
    @Track2EquivalentData
    @ApplicationDefinitionFileName
    @ApplicationLabel
    private Map<String, QrCpmDataObject> qrisRoot;
}
