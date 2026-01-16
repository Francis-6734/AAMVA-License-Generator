package com.dlgenerator.service;

import com.dlgenerator.model.*;
import com.dlgenerator.util.ComprehensiveLicenseNumberGenerator;
import com.dlgenerator.util.PDF417Generator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Enhanced License Generator Service with full AAMVA DL/ID-2020 compliance
 * Supports all 50 US states + DC + territories + international countries
 */
@Service
@RequiredArgsConstructor
public class LicenseGeneratorService {

    private final ComprehensiveLicenseNumberGenerator numberGenerator;
    private final PDF417Generator barcodeGenerator;
    private final LicenseValidationService validationService;
    private final Random random = new Random();

    /**
     * Generate comprehensive license data with full AAMVA compliance
     */
    public LicenseResponse generateLicense(LicenseRequest request) throws WriterException, IOException {
        // Build complete license data
        LicenseData licenseData = buildLicenseData(request);

        // Get jurisdiction AAMVA code
        String aamvaCode = getAAMVACode(request);

        // Generate AAMVA-compliant barcode data
        String aamvaData = barcodeGenerator.generateAAMVAData(licenseData, aamvaCode);
        String barcodeBase64 = barcodeGenerator.generateAAMVABarcode(aamvaData);

        // Set barcode in license data
        licenseData.setBarcodeBase64(barcodeBase64);
        licenseData.setBarcodeData(aamvaData);

        // Validate license number
        boolean isValid = validateLicense(licenseData, request);

        // Build and return response
        return buildLicenseResponse(licenseData, isValid);
    }

    /**
     * Build complete LicenseData object from request with all AAMVA fields populated
     */
    private LicenseData buildLicenseData(LicenseRequest request) {
        // Generate license number
        String licenseNumber = generateLicenseNumber(request);

        // Calculate dates
        LocalDate issueDate = LocalDate.now();
        LocalDate expirationDate = calculateExpirationDate(issueDate, request);
        LocalDate dateOfBirth = LocalDate.parse(request.getDob());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Calculate age-related dates
        String under18Until = calculateAgeDate(dateOfBirth, 18, formatter);
        String under19Until = calculateAgeDate(dateOfBirth, 19, formatter);
        String under21Until = calculateAgeDate(dateOfBirth, 21, formatter);

        // Determine state/country
        String state = request.getState() != null ? request.getState().name() : null;
        String country = request.getCountry() != null ? request.getCountry().getCountryName() : "USA";

        // Generate document discriminator (unique identifier)
        String documentDiscriminator = generateDocumentDiscriminator();

        // Get jurisdiction code
        String jurisdictionCode = getJurisdictionCode(request);

        // Determine compliance type
        String complianceType = request.getComplianceType() != null ? request.getComplianceType() : "F";

        // Build comprehensive license data
        return LicenseData.builder()
                // Core personal information
                .licenseNumber(licenseNumber)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .dob(request.getDob())

                // Location
                .state(state)
                .country(country)
                .city(request.getCity())
                .address(request.getAddress())
                .addressLine2(request.getAddressLine2())
                .zipCode(request.getZipCode() != null ? request.getZipCode() : "00000")

                // Physical description
                .gender(request.getGender())
                .eyeColor(request.getEyeColor() != null ? request.getEyeColor() : "BRO")
                .hairColor(request.getHairColor() != null ? request.getHairColor() : "BRO")
                .height(request.getHeight())
                .weight(request.getWeight())

                // License details
                .licenseClass(request.getLicenseClass() != null ? request.getLicenseClass() : "C")
                .issueDate(issueDate.format(formatter))
                .expirationDate(expirationDate.format(formatter))
                .restrictions(request.getRestrictions())
                .endorsements(request.getEndorsements())

                // AAMVA compliance fields
                .customerId(licenseNumber) // Same as license number for most states
                .documentDiscriminator(documentDiscriminator)
                .jurisdictionCode(jurisdictionCode)
                .inventoryControlNumber(generateInventoryControlNumber())

                // Name truncation indicators
                .familyNameTruncation(request.getLastName().length() > 35 ? "T" : "N")
                .firstNameTruncation(request.getFirstName().length() > 35 ? "T" : "N")
                .middleNameTruncation(request.getMiddleName() != null && request.getMiddleName().length() > 35 ? "T" : "N")

                // Additional personal information
                .nameSuffix(request.getNameSuffix())
                .aliasFirstName(request.getAliasFirstName())
                .aliasLastName(request.getAliasLastName())
                .aliasSuffix(request.getAliasSuffix())

                // Compliance and status
                .complianceType(complianceType)
                .cardRevisionDate(issueDate.format(DateTimeFormatter.ofPattern("MMddyyyy")))
                .organDonor(request.getOrganDonor())
                .veteran(request.getVeteran())

                // Age-related dates
                .under18Until(under18Until)
                .under19Until(under19Until)
                .under21Until(under21Until)

                // Document classifications
                .restrictionCodes(request.getRestrictions())
                .endorsementCodes(request.getEndorsements())

                // Medical and special
                .medicalIndicator(request.getMedicalIndicator())
                .raceEthnicity(request.getRaceEthnicity())
                .nonResidentIndicator("N")

                // Permit information
                .permitClassificationCode(request.getPermitClassificationCode())

                // Media
                .photoBase64(request.getPhotoBase64())
                .signatureBase64(request.getSignatureBase64())

                .build();
    }

    /**
     * Generate license number based on jurisdiction
     */
    private String generateLicenseNumber(LicenseRequest request) {
        if (request.getState() != null) {
            return numberGenerator.generateForState(
                    request.getState(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDob()
            );
        } else if (request.getCountry() != null) {
            return numberGenerator.generateForCountry(
                    request.getCountry(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDob()
            );
        } else {
            throw new IllegalArgumentException("Either state or country must be specified");
        }
    }

    /**
     * Calculate expiration date based on jurisdiction rules (typically 4-8 years)
     */
    private LocalDate calculateExpirationDate(LocalDate issueDate, LicenseRequest request) {
        // Most states use 4-8 year validity periods
        // Default to 4 years for now, can be customized per state
        return issueDate.plusYears(4);
    }

    /**
     * Calculate date when person reaches a specific age
     */
    private String calculateAgeDate(LocalDate dob, int targetAge, DateTimeFormatter formatter) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = dob.plusYears(targetAge);

        // Only return if in the future
        if (targetDate.isAfter(today)) {
            return targetDate.format(formatter);
        }
        return null;
    }

    /**
     * Generate unique document discriminator (8-character alphanumeric)
     */
    private String generateDocumentDiscriminator() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Generate inventory control number
     */
    private String generateInventoryControlNumber() {
        return String.format("%010d", random.nextInt(1000000000));
    }

    /**
     * Get AAMVA code for jurisdiction
     */
    private String getAAMVACode(LicenseRequest request) {
        if (request.getState() != null) {
            return request.getState().getAamvaCode();
        } else if (request.getCountry() != null) {
            // International codes would need to be defined in CountryFormat
            return "636999"; // Default international code
        }
        return "636999";
    }

    /**
     * Get jurisdiction code for document
     */
    private String getJurisdictionCode(LicenseRequest request) {
        if (request.getState() != null) {
            return request.getState().name();
        } else if (request.getCountry() != null) {
            return request.getCountry().getCountryCode();
        }
        return "USA";
    }

    /**
     * Validate generated license number
     */
    private boolean validateLicense(LicenseData licenseData, LicenseRequest request) {
        if (request.getState() != null) {
            return validationService.validateState(licenseData.getLicenseNumber(), request.getState());
        } else if (request.getCountry() != null) {
            return validationService.validateCountry(licenseData.getLicenseNumber(), request.getCountry());
        }
        return false;
    }

    /**
     * Build LicenseResponse from LicenseData
     */
    private LicenseResponse buildLicenseResponse(LicenseData licenseData, boolean isValid) {
        return LicenseResponse.builder()
                .licenseNumber(licenseData.getLicenseNumber())
                .firstName(licenseData.getFirstName())
                .lastName(licenseData.getLastName())
                .middleName(licenseData.getMiddleName())
                .dob(licenseData.getDob())
                .state(licenseData.getState())
                .country(licenseData.getCountry())
                .gender(licenseData.getGender())
                .address(licenseData.getAddress())
                .addressLine2(licenseData.getAddressLine2())
                .city(licenseData.getCity())
                .zipCode(licenseData.getZipCode())
                .eyeColor(licenseData.getEyeColor())
                .hairColor(licenseData.getHairColor())
                .height(licenseData.getHeight())
                .weight(licenseData.getWeight())
                .licenseClass(licenseData.getLicenseClass())
                .issueDate(licenseData.getIssueDate())
                .expirationDate(licenseData.getExpirationDate())
                .restrictions(licenseData.getRestrictions())
                .endorsements(licenseData.getEndorsements())
                .organDonor(licenseData.getOrganDonor())
                .veteran(licenseData.getVeteran())
                .photoBase64(licenseData.getPhotoBase64())
                .signatureBase64(licenseData.getSignatureBase64())
                .barcodeBase64(licenseData.getBarcodeBase64())
                .barcodeData(licenseData.getBarcodeData())
                .isValid(isValid)
                .build();
    }
}