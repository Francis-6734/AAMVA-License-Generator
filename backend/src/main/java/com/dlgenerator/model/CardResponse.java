package com.dlgenerator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardResponse {
    private LicenseResponse licenseData;
    private String frontCardBase64;  // Front of card as Base64
    private String backCardBase64;   // Back of card as Base64
    private String cardFormat;       // Format identifier (USA-CA, UK, etc.)
}