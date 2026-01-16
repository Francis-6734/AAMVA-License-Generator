package com.dlgenerator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LicenseRequest {

    // Required Fields
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Date of birth is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must be YYYY-MM-DD")
    private String dob;

    private StateFormat state;  // For US licenses

    private CountryFormat country; // For international licenses

    // Setter that accepts string from frontend for country (maps countryFormat -> country)
    public void setCountryFormat(String countryFormat) {
        if (countryFormat != null && !countryFormat.isEmpty() && !"USA".equals(countryFormat)) {
            try {
                this.country = CountryFormat.valueOf(countryFormat);
            } catch (IllegalArgumentException e) {
                // Invalid country format, leave as null
            }
        }
    }

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^[MF9]$", message = "Gender must be M, F, or 9 (not specified)")
    private String gender;

    // Address Fields
    private String address;
    private String addressLine2;
    private String city;
    private String zipCode;

    // Physical Description
    @Pattern(regexp = "^(BLK|BLU|BRO|GRY|GRN|HAZ|MAR|PNK|DIC|UNK)?$", message = "Invalid eye color code")
    private String eyeColor;

    @Pattern(regexp = "^(BAL|BLK|BLN|BRO|GRY|RED|SDY|WHI|UNK)?$", message = "Invalid hair color code")
    private String hairColor;

    private String height;  // In inches (e.g., "68") or cm
    private String weight;  // In pounds (e.g., "180") or kg

    // License Classification
    private String licenseClass;

    private String restrictions;    // Comma-separated restriction codes
    private String endorsements;    // Comma-separated endorsement codes

    // Additional Personal Information
    private String nameSuffix;      // Jr, Sr, III, IV, etc.
    private String aliasFirstName;
    private String aliasLastName;
    private String aliasSuffix;

    // Jurisdiction-Specific Optional Fields
    private Boolean organDonor;     // Organ donor indicator
    private Boolean veteran;        // Veteran indicator

    // Social Security (rarely used)
    private String socialSecurityNumber;

    // Document-specific
    private String complianceType;  // F = Fully Compliant, N = Non-Compliant

    // Medical and Special
    private String medicalIndicator;
    private String raceEthnicity;

    // Permit Information
    private String permitClassificationCode;

    // Media
    private String photoBase64;     // Base64 encoded photo
    private String signatureBase64; // Base64 encoded signature (optional)

    // International License Fields
    private String subJurisdiction;     // Sub-jurisdiction code (e.g., "AU_NSW", "CA_ON")
    private String licenseCategories;   // EU-style categories (e.g., "A,B,C1")
    private String nationalId;          // Country-specific national ID (e.g., CPF for Brazil)
    private String colorCode;           // Japan license color (GREEN, BLUE, GOLD)
    private String locale;              // Language/locale preference (e.g., "de", "fr", "ja")
}