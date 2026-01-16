package com.dlgenerator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenseResponse {
    // Core information
    private String licenseNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dob;
    private String state;
    private String country;
    private String gender;

    // Address
    private String address;
    private String addressLine2;
    private String city;
    private String zipCode;

    // Physical description
    private String eyeColor;
    private String hairColor;
    private String height;
    private String weight;

    // License details
    private String licenseClass;
    private String issueDate;
    private String expirationDate;
    private String restrictions;
    private String endorsements;

    // Additional fields
    private Boolean organDonor;
    private Boolean veteran;
    private String documentDiscriminator;

    // Media
    private String photoBase64;
    private String signatureBase64;
    private String barcodeBase64;
    private String barcodeData;

    // Validation
    private boolean isValid;
}