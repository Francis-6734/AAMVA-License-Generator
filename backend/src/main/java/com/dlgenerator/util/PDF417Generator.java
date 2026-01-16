package com.dlgenerator.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.PDF417Writer;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class PDF417Generator {
    
    /**
     * Generate AAMVA-compliant PDF417 barcode
     */
    public String generateAAMVABarcode(String licenseData) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, 5);
        
        PDF417Writer writer = new PDF417Writer();
        BitMatrix bitMatrix = writer.encode(
            licenseData,
            BarcodeFormat.PDF_417,
            600,
            120,
            hints
        );
        
        BufferedImage image = toBufferedImage(bitMatrix);
        return imageToBase64(image);
    }
    
    /**
     * Convert BitMatrix to BufferedImage
     */
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        
        return image;
    }
    
    /**
     * Convert BufferedImage to Base64
     */
    private String imageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    
    /**
     * Generate AAMVA-compliant PDF417 data string with full DL/ID-2020 compliance
     *
     * @param licenseData Complete license data object
     * @param jurisdictionAAMVACode AAMVA jurisdiction code (e.g., "636014" for CA)
     * @return Properly formatted AAMVA barcode data string
     */
    public String generateAAMVAData(com.dlgenerator.model.LicenseData licenseData, String jurisdictionAAMVACode) {
        StringBuilder aamvaData = new StringBuilder();

        // Compliance Indicator (always starts with @)
        aamvaData.append("@\n");

        // File Header with AAMVA version
        // Format: ANSI XXXXYYZZAA where:
        // XXXX = IIN (Issuer Identification Number)
        // YY = AAMVA Version Number (08 = version 8)
        // ZZ = Jurisdiction Version Number (01)
        // AA = Number of entries (02 = DL + subfile)
        String aamvaVersion = String.format("ANSI %s080102", jurisdictionAAMVACode);
        aamvaData.append(aamvaVersion).append("\n");

        // Subfile Designator for DL (Driver License Data)
        aamvaData.append("DL\n");

        // Jurisdiction-specific vehicle class (DCA) - Required
        appendField(aamvaData, "DCA", licenseData.getLicenseClass());

        // Jurisdiction-specific restriction codes (DCB) - Optional
        if (licenseData.getRestrictionCodes() != null && !licenseData.getRestrictionCodes().isEmpty()) {
            appendField(aamvaData, "DCB", licenseData.getRestrictionCodes());
        }

        // Jurisdiction-specific endorsement codes (DCD) - Optional
        if (licenseData.getEndorsementCodes() != null && !licenseData.getEndorsementCodes().isEmpty()) {
            appendField(aamvaData, "DCD", licenseData.getEndorsementCodes());
        }

        // Document Expiration Date (DBA) - Required (MMDDCCYY)
        appendField(aamvaData, "DBA", formatDateToAAMVA(licenseData.getExpirationDate()));

        // Customer Family Name (DCS) - Required
        appendField(aamvaData, "DCS", licenseData.getLastName());

        // Customer First Name (DAC) - Required
        appendField(aamvaData, "DAC", licenseData.getFirstName());

        // Customer Middle Name (DAD) - Optional
        if (licenseData.getMiddleName() != null && !licenseData.getMiddleName().isEmpty()) {
            appendField(aamvaData, "DAD", licenseData.getMiddleName());
        }

        // Document Issue Date (DBD) - Required (MMDDCCYY)
        appendField(aamvaData, "DBD", formatDateToAAMVA(licenseData.getIssueDate()));

        // Date of Birth (DBB) - Required (MMDDCCYY)
        appendField(aamvaData, "DBB", formatDateToAAMVA(licenseData.getDob()));

        // Physical Description - Sex (DBC) - Required
        String genderCode = convertGenderToAAMVA(licenseData.getGender());
        appendField(aamvaData, "DBC", genderCode);

        // Eye Color (DAY) - Optional
        if (licenseData.getEyeColor() != null && !licenseData.getEyeColor().isEmpty()) {
            appendField(aamvaData, "DAY", licenseData.getEyeColor());
        }

        // Height (DAU) - Optional (in inches, format: XXX)
        if (licenseData.getHeight() != null && !licenseData.getHeight().isEmpty()) {
            String heightFormatted = formatHeight(licenseData.getHeight());
            appendField(aamvaData, "DAU", heightFormatted);
        }

        // Address Street 1 (DAG) - Optional
        if (licenseData.getAddress() != null && !licenseData.getAddress().isEmpty()) {
            appendField(aamvaData, "DAG", licenseData.getAddress());
        }

        // Address Street 2 (DAH) - Optional
        if (licenseData.getAddressLine2() != null && !licenseData.getAddressLine2().isEmpty()) {
            appendField(aamvaData, "DAH", licenseData.getAddressLine2());
        }

        // Address City (DAI) - Optional
        if (licenseData.getCity() != null && !licenseData.getCity().isEmpty()) {
            appendField(aamvaData, "DAI", licenseData.getCity());
        }

        // Address Jurisdiction Code (DAJ) - Optional
        if (licenseData.getState() != null && !licenseData.getState().isEmpty()) {
            appendField(aamvaData, "DAJ", licenseData.getState());
        }

        // Address Postal Code (DAK) - Required
        appendField(aamvaData, "DAK", licenseData.getZipCode() != null ? licenseData.getZipCode() : "00000");

        // Customer ID Number / License Number (DAQ) - Required
        appendField(aamvaData, "DAQ", licenseData.getLicenseNumber());

        // Document Discriminator (DCF) - Required (unique identifier)
        appendField(aamvaData, "DCF", licenseData.getDocumentDiscriminator() != null ?
            licenseData.getDocumentDiscriminator() : generateDocumentDiscriminator());

        // Country Identification (DCG) - Required
        String countryCode = licenseData.getCountry() != null && !licenseData.getCountry().isEmpty() ?
            licenseData.getCountry() : "USA";
        appendField(aamvaData, "DCG", countryCode);

        // Family Name Truncation (DDE) - Optional
        if (licenseData.getFamilyNameTruncation() != null) {
            appendField(aamvaData, "DDE", licenseData.getFamilyNameTruncation());
        } else {
            appendField(aamvaData, "DDE", "N");
        }

        // First Name Truncation (DDF) - Optional
        if (licenseData.getFirstNameTruncation() != null) {
            appendField(aamvaData, "DDF", licenseData.getFirstNameTruncation());
        } else {
            appendField(aamvaData, "DDF", "N");
        }

        // Middle Name Truncation (DDG) - Optional
        if (licenseData.getMiddleNameTruncation() != null) {
            appendField(aamvaData, "DDG", licenseData.getMiddleNameTruncation());
        } else {
            appendField(aamvaData, "DDG", "N");
        }

        // Audit Information (DBA type fields)
        // Compliance Type (DDA) - Optional
        if (licenseData.getComplianceType() != null) {
            appendField(aamvaData, "DDA", licenseData.getComplianceType());
        } else {
            appendField(aamvaData, "DDA", "F"); // F = Fully Compliant
        }

        // Card Revision Date (DDB) - Optional
        if (licenseData.getCardRevisionDate() != null) {
            appendField(aamvaData, "DDB", licenseData.getCardRevisionDate());
        }

        // Under 18 Until (DDC) - Optional
        if (licenseData.getUnder18Until() != null) {
            appendField(aamvaData, "DDC", formatDateToAAMVA(licenseData.getUnder18Until()));
        }

        // Under 19 Until (DDQ) - Optional
        if (licenseData.getUnder19Until() != null) {
            appendField(aamvaData, "DDQ", formatDateToAAMVA(licenseData.getUnder19Until()));
        }

        // Under 21 Until (DDR) - Optional
        if (licenseData.getUnder21Until() != null) {
            appendField(aamvaData, "DDR", formatDateToAAMVA(licenseData.getUnder21Until()));
        }

        // Organ Donor (DDS) - Optional
        if (licenseData.getOrganDonor() != null) {
            appendField(aamvaData, "DDS", licenseData.getOrganDonor() ? "1" : "0");
        }

        // Veteran (DDT) - Optional
        if (licenseData.getVeteran() != null) {
            appendField(aamvaData, "DDT", licenseData.getVeteran() ? "1" : "0");
        }

        // Hair Color (DAZ) - Optional
        if (licenseData.getHairColor() != null && !licenseData.getHairColor().isEmpty()) {
            appendField(aamvaData, "DAZ", licenseData.getHairColor());
        }

        // Weight (DAW) - Optional (in pounds, format: XXX)
        if (licenseData.getWeight() != null && !licenseData.getWeight().isEmpty()) {
            String weightFormatted = formatWeight(licenseData.getWeight());
            appendField(aamvaData, "DAW", weightFormatted);
        }

        // Name Suffix (DCU) - Optional
        if (licenseData.getNameSuffix() != null && !licenseData.getNameSuffix().isEmpty()) {
            appendField(aamvaData, "DCU", licenseData.getNameSuffix());
        }

        // Alias/AKA Family Name (DBN) - Optional
        if (licenseData.getAliasLastName() != null && !licenseData.getAliasLastName().isEmpty()) {
            appendField(aamvaData, "DBN", licenseData.getAliasLastName());
        }

        // Alias/AKA Given Name (DBG) - Optional
        if (licenseData.getAliasFirstName() != null && !licenseData.getAliasFirstName().isEmpty()) {
            appendField(aamvaData, "DBG", licenseData.getAliasFirstName());
        }

        // Alias/AKA Suffix (DBS) - Optional
        if (licenseData.getAliasSuffix() != null && !licenseData.getAliasSuffix().isEmpty()) {
            appendField(aamvaData, "DBS", licenseData.getAliasSuffix());
        }

        // Weight Range (DDJ) - Optional
        if (licenseData.getWeightRange() != null) {
            appendField(aamvaData, "DDJ", licenseData.getWeightRange());
        }

        // Race/Ethnicity (DDK) - Optional
        if (licenseData.getRaceEthnicity() != null) {
            appendField(aamvaData, "DDK", licenseData.getRaceEthnicity());
        }

        // Standard Vehicle Classification (DDL) - Optional
        if (licenseData.getStandardVehicleClassification() != null) {
            appendField(aamvaData, "DDL", licenseData.getStandardVehicleClassification());
        }

        // Standard Endorsement Code (DDM) - Optional
        if (licenseData.getStandardEndorsementCode() != null) {
            appendField(aamvaData, "DDM", licenseData.getStandardEndorsementCode());
        }

        // Standard Restriction Code (DDN) - Optional
        if (licenseData.getStandardRestrictionCode() != null) {
            appendField(aamvaData, "DDN", licenseData.getStandardRestrictionCode());
        }

        // Jurisdiction Vehicle Class Description (DDD) - Optional
        if (licenseData.getJurisdictionVehicleClassDescription() != null) {
            appendField(aamvaData, "DDD", licenseData.getJurisdictionVehicleClassDescription());
        }

        // Jurisdiction Endorsement Code Description (DDO) - Optional
        if (licenseData.getJurisdictionEndorsementDescription() != null) {
            appendField(aamvaData, "DDO", licenseData.getJurisdictionEndorsementDescription());
        }

        // Jurisdiction Restriction Code Description (DDP) - Optional
        if (licenseData.getJurisdictionRestrictionDescription() != null) {
            appendField(aamvaData, "DDP", licenseData.getJurisdictionRestrictionDescription());
        }

        // Hazmat Endorsement Expiration Date (DDH) - Optional
        if (licenseData.getHazmatEndorsementExpiry() != null) {
            appendField(aamvaData, "DDH", formatDateToAAMVA(licenseData.getHazmatEndorsementExpiry()));
        }

        // Limited Duration Document (DDI) - Optional
        if (licenseData.getLimitedDurationIndicator() != null) {
            appendField(aamvaData, "DDI", licenseData.getLimitedDurationIndicator());
        }

        // Inventory Control Number (DCK) - Optional
        if (licenseData.getInventoryControlNumber() != null) {
            appendField(aamvaData, "DCK", licenseData.getInventoryControlNumber());
        }

        // Medical Indicator (DCM) - Optional
        if (licenseData.getMedicalIndicator() != null) {
            appendField(aamvaData, "DCM", licenseData.getMedicalIndicator());
        }

        // Non-Resident Indicator (DCN) - Optional
        if (licenseData.getNonResidentIndicator() != null) {
            appendField(aamvaData, "DCN", licenseData.getNonResidentIndicator());
        }

        // Unique Customer Identifier (DCO) - Optional
        if (licenseData.getUniqueCustomerId() != null) {
            appendField(aamvaData, "DCO", licenseData.getUniqueCustomerId());
        }

        // Permit Classification Code (DCI) - Optional
        if (licenseData.getPermitClassificationCode() != null) {
            appendField(aamvaData, "DCI", licenseData.getPermitClassificationCode());
        }

        // Permit Expiration Date (DCJ) - Optional
        if (licenseData.getPermitExpiryDate() != null) {
            appendField(aamvaData, "DCJ", formatDateToAAMVA(licenseData.getPermitExpiryDate()));
        }

        // Number of Duplicates (DCL) - Optional
        if (licenseData.getNumberOfDuplicates() != null) {
            appendField(aamvaData, "DCL", licenseData.getNumberOfDuplicates());
        }

        // Audit Information / Federal Commercial Vehicle Codes (DCH) - Optional
        if (licenseData.getFederalCommercialVehicleCodes() != null) {
            appendField(aamvaData, "DCH", licenseData.getFederalCommercialVehicleCodes());
        }

        return aamvaData.toString().trim();
    }

    /**
     * Append a field to the AAMVA data string
     */
    private void appendField(StringBuilder builder, String fieldCode, String value) {
        if (value != null && !value.isEmpty()) {
            builder.append(fieldCode).append(value).append("\n");
        }
    }

    /**
     * Format date from YYYY-MM-DD to MMDDCCYY (AAMVA format)
     */
    private String formatDateToAAMVA(String date) {
        if (date == null || date.isEmpty()) {
            return "";
        }
        // Input: YYYY-MM-DD
        // Output: MMDDCCYY
        String[] parts = date.split("-");
        if (parts.length == 3) {
            String year = parts[0];
            String month = parts[1];
            String day = parts[2];
            return month + day + year;
        }
        return "";
    }

    /**
     * Convert gender to AAMVA format
     */
    private String convertGenderToAAMVA(String gender) {
        if (gender == null || gender.isEmpty()) {
            return "9"; // Unknown/Not specified
        }
        return switch (gender.toUpperCase()) {
            case "M", "MALE" -> "1";
            case "F", "FEMALE" -> "2";
            default -> "9"; // Unknown/Not specified
        };
    }

    /**
     * Format height to AAMVA format (3 digits, in inches)
     */
    private String formatHeight(String height) {
        try {
            int heightValue = Integer.parseInt(height.replaceAll("[^0-9]", ""));
            return String.format("%03d", heightValue);
        } catch (NumberFormatException e) {
            return "000";
        }
    }

    /**
     * Format weight to AAMVA format (3 digits, in pounds)
     */
    private String formatWeight(String weight) {
        try {
            int weightValue = Integer.parseInt(weight.replaceAll("[^0-9]", ""));
            return String.format("%03d", weightValue);
        } catch (NumberFormatException e) {
            return "000";
        }
    }

    /**
     * Generate a unique document discriminator
     */
    private String generateDocumentDiscriminator() {
        // Generate a random 8-character alphanumeric string
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder discriminator = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            discriminator.append(chars.charAt(random.nextInt(chars.length())));
        }
        return discriminator.toString();
    }
}