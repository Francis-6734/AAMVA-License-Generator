package com.dlgenerator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenseData {
    // Personal Information (Required)
    private String licenseNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dob;

    // Location
    private String state;
    private String country;
    private String city;
    private String address;
    private String addressLine2;
    private String zipCode;

    // Physical Description
    private String gender;
    private String eyeColor;
    private String hairColor;
    private String height;
    private String weight;

    // License Details
    private String licenseClass;
    private String issueDate;
    private String expirationDate;
    private String restrictions;
    private String endorsements;

    // AAMVA Compliance Fields
    private String customerId;              // Customer ID Number (may differ from license number)
    private String documentDiscriminator;   // Unique document identifier
    private String jurisdictionCode;        // AAMVA Issuer Identification Number
    private String inventoryControlNumber;  // Internal tracking number

    // Name Truncation Indicators
    private String familyNameTruncation;    // T = Truncated, N = Not truncated
    private String firstNameTruncation;
    private String middleNameTruncation;

    // Additional Personal Information
    private String nameSuffix;              // Jr, Sr, III, etc.
    private String aliasFirstName;          // AKA first name
    private String aliasLastName;           // AKA last name
    private String aliasSuffix;             // AKA suffix

    // Compliance and Status
    private String complianceType;          // F = Fully Compliant, N = Non-Compliant
    private String cardRevisionDate;        // Date of card design revision
    private Boolean organDonor;             // Organ donor indicator
    private Boolean veteran;                // Veteran indicator

    // Age-related Dates
    private String under18Until;            // Date when person turns 18
    private String under19Until;
    private String under21Until;

    // Document Classifications
    private String vehicleClass;            // Vehicle class description
    private String endorsementCodes;        // Endorsement codes (comma-separated)
    private String restrictionCodes;        // Restriction codes (comma-separated)

    // Endorsement/Restriction Descriptions
    private String jurisdictionVehicleClassDescription;
    private String jurisdictionEndorsementDescription;
    private String jurisdictionRestrictionDescription;

    // Additional Classifications
    private String standardVehicleClassification;   // Standard vehicle classification
    private String standardEndorsementCode;         // Standard endorsement code
    private String standardRestrictionCode;         // Standard restriction code

    // Hazmat and Special
    private String hazmatEndorsementExpiry; // Hazmat endorsement expiration date
    private String limitedDurationIndicator; // Y or N - limited duration document
    private String weightRange;             // Weight range classification (for privacy)
    private String raceEthnicity;           // Optional race/ethnicity

    // Medical and Special Conditions
    private String medicalIndicator;        // Medical condition codes
    private String nonResidentIndicator;    // Y or N
    private String uniqueCustomerId;        // Unique customer identifier across state systems

    // Additional Optional Fields
    private String socialSecurityNumber;    // Rarely used, optional
    private String placeOfBirth;            // City/state of birth
    private String auditInformation;        // Audit or tracking info
    private String federalCommercialVehicleCodes; // Federal commercial vehicle codes

    // Permit Information
    private String permitClassificationCode; // Permit classification
    private String permitExpiryDate;        // Permit expiration date
    private String numberOfDuplicates;      // Number of duplicate cards issued

    // Media
    private String photoBase64;
    private String signatureBase64;
    private String barcodeBase64;
    private String barcodeData;             // Raw AAMVA barcode data string

    // International License Fields
    private String subJurisdictionCode;     // Sub-jurisdiction code (e.g., "NSW", "ON")
    private String subJurisdictionName;     // Full name (e.g., "New South Wales", "Ontario")
    private String licenseCategories;       // EU-style categories (e.g., "A,B,C1,D")
    private String issuingAuthority;        // Local issuing authority name
    private String locale;                  // Language/locale code

    // Japan-specific fields
    private String colorCode;               // GREEN, BLUE, GOLD for Japan license types
    private String eraYear;                 // Japanese era calendar year

    // Brazil-specific fields
    private String cpfNumber;               // Brazilian CPF (tax ID)
    private String cnhRegistro;             // CNH registration number

    // EU-specific fields
    private String euCategoryValidUntil;    // Category validity dates
}