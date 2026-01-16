package com.dlgenerator.service;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.design.TemplateFactory;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CardDesignService {
    
    private final TemplateFactory templateFactory;
    private final ImageProcessor imageProcessor;
    
    public CardResponse generateCard(LicenseRequest request, LicenseResponse license) throws IOException {
        CardTemplate template;
        String formatIdentifier;
        SubJurisdiction subJurisdiction = null;

        // Parse sub-jurisdiction if provided
        if (request.getSubJurisdiction() != null && !request.getSubJurisdiction().isEmpty()) {
            subJurisdiction = SubJurisdiction.findByEnumName(request.getSubJurisdiction());
            if (subJurisdiction == null) {
                subJurisdiction = SubJurisdiction.findByCode(request.getSubJurisdiction());
            }
        }

        if (request.getState() != null) {
            template = templateFactory.getTemplateForState(request.getState());
            formatIdentifier = "USA-" + request.getState().name();
        } else if (request.getCountry() != null) {
            template = templateFactory.getTemplateForCountry(request.getCountry(), subJurisdiction);
            formatIdentifier = request.getCountry().getCountryCode();
            if (subJurisdiction != null) {
                formatIdentifier += "-" + subJurisdiction.getCode();
            }
        } else {
            throw new IllegalArgumentException("Either state or country must be specified");
        }

        LicenseData licenseData = buildLicenseData(license, request, subJurisdiction);

        BufferedImage frontCard = template.generateFront(licenseData);
        BufferedImage backCard = template.generateBack(licenseData);

        String frontBase64 = imageProcessor.encodeImageToBase64(frontCard);
        String backBase64 = imageProcessor.encodeImageToBase64(backCard);

        return CardResponse.builder()
            .licenseData(license)
            .frontCardBase64(frontBase64)
            .backCardBase64(backBase64)
            .cardFormat(formatIdentifier)
            .build();
    }
    
    private LicenseData buildLicenseData(LicenseResponse license, LicenseRequest request, SubJurisdiction subJurisdiction) {
        LicenseData.LicenseDataBuilder builder = LicenseData.builder()
            .licenseNumber(license.getLicenseNumber())
            .firstName(license.getFirstName())
            .lastName(license.getLastName())
            .middleName(license.getMiddleName())
            .dob(license.getDob())
            .state(license.getState())
            .country(license.getCountry())
            .gender(license.getGender())
            .address(license.getAddress())
            .city(license.getCity())
            .zipCode(license.getZipCode())
            .eyeColor(license.getEyeColor())
            .hairColor(license.getHairColor())
            .height(license.getHeight())
            .weight(license.getWeight())
            .licenseClass(license.getLicenseClass())
            .issueDate(license.getIssueDate())
            .expirationDate(license.getExpirationDate())
            .restrictions(license.getRestrictions())
            .endorsements(license.getEndorsements())
            .organDonor(license.getOrganDonor())
            .veteran(license.getVeteran())
            .documentDiscriminator(license.getDocumentDiscriminator())
            .photoBase64(license.getPhotoBase64())
            .barcodeBase64(license.getBarcodeBase64());

        // Add international-specific fields
        if (subJurisdiction != null) {
            builder.subJurisdictionCode(subJurisdiction.getCode())
                   .subJurisdictionName(subJurisdiction.getName());
        }

        // Add request-specific fields for international licenses
        if (request != null) {
            if (request.getLicenseCategories() != null) {
                builder.licenseCategories(request.getLicenseCategories());
            }
            if (request.getColorCode() != null) {
                builder.colorCode(request.getColorCode());
            }
            if (request.getNationalId() != null) {
                builder.cpfNumber(request.getNationalId()); // Used for Brazil CPF
            }
            if (request.getLocale() != null) {
                builder.locale(request.getLocale());
            }
        }

        return builder.build();
    }
}