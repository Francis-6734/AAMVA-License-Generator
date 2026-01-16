package com.dlgenerator.model;

/**
 * Complete AAMVA DL/ID-2020 Field Definitions
 * Based on AAMVA DL/ID Card Design Standard Version 10.0
 */
public enum AAMVAField {
    // File Type (Required)
    FILE_TYPE("DCA", "Jurisdiction-specific vehicle class", false),

    // Identification Number (Required)
    CUSTOMER_ID("DCB", "Customer ID Number / Unique ID", false),

    // Name Fields (Required)
    CUSTOMER_FAMILY_NAME("DCS", "Customer Family Name", true),
    CUSTOMER_FIRST_NAME("DAC", "Customer First Name", true),
    CUSTOMER_MIDDLE_NAME("DAD", "Customer Middle Name(s)", false),

    // Name Truncation Indicators
    FAMILY_NAME_TRUNCATION("DDE", "Family Name Truncation", false),
    FIRST_NAME_TRUNCATION("DDF", "First Name Truncation", false),
    MIDDLE_NAME_TRUNCATION("DDG", "Middle Name Truncation", false),

    // Document Information (Required)
    DOCUMENT_EXPIRATION_DATE("DBA", "Document Expiration Date (MMDDCCYY)", true),
    DOCUMENT_ISSUE_DATE("DBD", "Document Issue Date (MMDDCCYY)", true),
    DATE_OF_BIRTH("DBB", "Date of Birth (MMDDCCYY)", true),

    // Physical Description (Required)
    PHYSICAL_DESCRIPTION_SEX("DBC", "Sex (1=M, 2=F, 9=Not specified)", true),
    PHYSICAL_DESCRIPTION_EYE_COLOR("DAY", "Eye Color", false),
    PHYSICAL_DESCRIPTION_HEIGHT("DAU", "Height (in inches or cm)", false),
    PHYSICAL_DESCRIPTION_WEIGHT("DAW", "Weight (lbs or kg)", false),
    PHYSICAL_DESCRIPTION_HAIR_COLOR("DAZ", "Hair Color", false),

    // Address (Required for some jurisdictions)
    ADDRESS_STREET_1("DAG", "Address Street 1", false),
    ADDRESS_STREET_2("DAH", "Address Street 2", false),
    ADDRESS_CITY("DAI", "Address City", false),
    ADDRESS_STATE("DAJ", "Address Jurisdiction Code", false),
    ADDRESS_POSTAL_CODE("DAK", "Address Postal Code", true),

    // License Information (Required)
    CUSTOMER_ID_NUMBER("DAQ", "Customer ID Number (License/ID Number)", true),
    DOCUMENT_DISCRIMINATOR("DCF", "Document Discriminator", true),
    JURISDICTION_CODE("DCG", "Country Identification", true),

    // Audit Information
    INVENTORY_CONTROL_NUMBER("DCK", "Inventory Control Number", false),

    // Compliance Type
    COMPLIANCE_TYPE("DDA", "Compliance Type", false),
    CARD_REVISION_DATE("DDB", "Card Revision Date (MMDDCCYY)", false),

    // Additional Fields
    HAZMAT_ENDORSEMENT_EXPIRY("DDH", "HAZMAT Endorsement Expiration Date", false),
    LIMITED_DURATION("DDI", "Limited Duration Document Indicator", false),
    WEIGHT_RANGE("DDJ", "Weight Range", false),
    RACE_ETHNICITY("DDK", "Race/Ethnicity", false),
    STANDARD_VEHICLE_CLASSIFICATION("DDL", "Standard Vehicle Classification", false),
    STANDARD_ENDORSEMENT_CODE("DDM", "Standard Endorsement Code", false),
    STANDARD_RESTRICTION_CODE("DDN", "Standard Restriction Code", false),

    // Jurisdiction Specific
    JURISDICTION_SPECIFIC_VEHICLE_CLASS("DDD", "Jurisdiction Specific Vehicle Class Description", false),
    JURISDICTION_SPECIFIC_ENDORSEMENT("DDO", "Jurisdiction Specific Endorsement Code Description", false),
    JURISDICTION_SPECIFIC_RESTRICTION("DDP", "Jurisdiction Specific Restriction Code Description", false),

    // Additional Optional Fields
    UNDER_18_UNTIL("DDC", "Under 18 Until (MMDDCCYY)", false),
    UNDER_19_UNTIL("DDQ", "Under 19 Until (MMDDCCYY)", false),
    UNDER_21_UNTIL("DDR", "Under 21 Until (MMDDCCYY)", false),

    // Organ Donor
    ORGAN_DONOR("DDS", "Organ Donor Indicator", false),

    // Veteran
    VETERAN("DDT", "Veteran Indicator", false),

    // Additional Names
    AKA_FAMILY_NAME("DBN", "Alias/AKA Family Name", false),
    AKA_GIVEN_NAME("DBG", "Alias/AKA Given Name", false),
    AKA_SUFFIX_NAME("DBS", "Alias/AKA Suffix Name", false),

    // Suffix
    NAME_SUFFIX("DCU", "Name Suffix", false),

    // Permit and Classifications
    PERMIT_CLASSIFICATION_CODE("DCI", "Permit Classification Code", false),
    PERMIT_EXPIRY_DATE("DCJ", "Permit Expiry Date", false),
    NUMBER_OF_DUPLICATES("DCL", "Number of Duplicates", false),

    // Medical Indicator
    MEDICAL_INDICATOR("DCM", "Medical Indicator Codes", false),

    // Non-Resident Indicator
    NON_RESIDENT("DCN", "Non-Resident Indicator", false),

    // Unique Customer Identifier
    UNIQUE_CUSTOMER_ID("DCO", "Unique Customer Identifier", false),

    // Social Security Number (Optional, rarely used)
    SOCIAL_SECURITY_NUMBER("DAM", "Social Security Number", false),

    // Place of Birth
    PLACE_OF_BIRTH("DCI", "Place of Birth", false),

    // Audit Information - Additional
    AUDIT_INFORMATION("DCH", "Federal Commercial Vehicle Codes", false),

    // Second Address Line
    SECOND_ADDRESS_LINE("DAO", "Second Address Line", false),

    // Country Name
    COUNTRY_SUBDIVISION("DCO", "Country Subdivision", false);

    private final String code;
    private final String description;
    private final boolean required;

    AAMVAField(String code, String description, boolean required) {
        this.code = code;
        this.description = description;
        this.required = required;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    /**
     * Format field for AAMVA barcode
     * @param value Field value
     * @return Formatted field string with code
     */
    public String format(String value) {
        if (value == null || value.isEmpty()) {
            return required ? code : "";
        }
        return code + value;
    }
}
