package com.dlgenerator.controller;

import com.dlgenerator.model.JurisdictionConfig;
import com.dlgenerator.model.StateFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * REST Controller for jurisdiction configuration and metadata
 * Provides dynamic form field requirements to frontend
 */
@RestController
@RequestMapping("/api/jurisdiction")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class JurisdictionController {

    /**
     * Get all US jurisdictions with their configurations
     */
    @GetMapping("/us-states")
    public ResponseEntity<List<Map<String, Object>>> getUSStates() {
        Map<String, JurisdictionConfig> jurisdictions = JurisdictionConfig.getUSJurisdictions();

        List<Map<String, Object>> response = jurisdictions.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> stateInfo = new HashMap<>();
                    stateInfo.put("code", entry.getKey());
                    stateInfo.put("name", entry.getValue().getName());
                    stateInfo.put("aamvaCode", entry.getValue().getAamvaCode());
                    stateInfo.put("documentName", entry.getValue().getDocumentName());
                    stateInfo.put("requiresMiddleName", entry.getValue().isRequiresMiddleName());
                    stateInfo.put("requiresOrganDonor", entry.getValue().isRequiresOrganDonor());
                    stateInfo.put("requiresVeteran", entry.getValue().isRequiresVeteran());
                    stateInfo.put("requiresRestrictions", entry.getValue().isRequiresRestrictions());
                    stateInfo.put("requiresEndorsements", entry.getValue().isRequiresEndorsements());
                    stateInfo.put("availableClasses", entry.getValue().getAvailableClasses());
                    stateInfo.put("availableRestrictions", entry.getValue().getAvailableRestrictions());
                    stateInfo.put("availableEndorsements", entry.getValue().getAvailableEndorsements());
                    return stateInfo;
                })
                .sorted(Comparator.comparing(m -> (String) m.get("name")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Get configuration for a specific US state
     */
    @GetMapping("/us-states/{stateCode}")
    public ResponseEntity<Map<String, Object>> getStateConfig(@PathVariable String stateCode) {
        Map<String, JurisdictionConfig> jurisdictions = JurisdictionConfig.getUSJurisdictions();
        JurisdictionConfig config = jurisdictions.get(stateCode.toUpperCase());

        if (config == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", stateCode.toUpperCase());
        response.put("name", config.getName());
        response.put("aamvaCode", config.getAamvaCode());
        response.put("countryCode", config.getCountryCode());
        response.put("documentName", config.getDocumentName());
        response.put("requiresMiddleName", config.isRequiresMiddleName());
        response.put("requiresOrganDonor", config.isRequiresOrganDonor());
        response.put("requiresVeteran", config.isRequiresVeteran());
        response.put("requiresRestrictions", config.isRequiresRestrictions());
        response.put("requiresEndorsements", config.isRequiresEndorsements());
        response.put("availableClasses", config.getAvailableClasses());
        response.put("availableRestrictions", config.getAvailableRestrictions());
        response.put("availableEndorsements", config.getAvailableEndorsements());
        response.put("eyeColorCodes", config.getEyeColorCodes());
        response.put("hairColorCodes", config.getHairColorCodes());

        return ResponseEntity.ok(response);
    }

    /**
     * Get all available US state codes
     */
    @GetMapping("/us-states/codes")
    public ResponseEntity<List<Map<String, String>>> getStateCodes() {
        List<Map<String, String>> states = Arrays.stream(StateFormat.values())
                .map(state -> {
                    Map<String, String> stateMap = new HashMap<>();
                    stateMap.put("code", state.name());
                    stateMap.put("name", state.getStateName());
                    stateMap.put("aamvaCode", state.getAamvaCode());
                    return stateMap;
                })
                .sorted(Comparator.comparing(m -> m.get("name")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(states);
    }

    /**
     * Get field requirements for dynamic form rendering
     */
    @GetMapping("/field-requirements/{jurisdictionCode}")
    public ResponseEntity<Map<String, Object>> getFieldRequirements(@PathVariable String jurisdictionCode) {
        Map<String, JurisdictionConfig> jurisdictions = JurisdictionConfig.getUSJurisdictions();
        JurisdictionConfig config = jurisdictions.get(jurisdictionCode.toUpperCase());

        if (config == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> requirements = new HashMap<>();

        // Required fields (always present)
        List<String> requiredFields = new ArrayList<>(Arrays.asList(
                "firstName", "lastName", "dob", "gender", "address", "city", "zipCode"
        ));

        // Optional fields based on jurisdiction
        List<String> optionalFields = new ArrayList<>(Arrays.asList(
                "height", "weight", "eyeColor", "hairColor", "licenseClass"
        ));

        if (config.isRequiresMiddleName()) {
            requiredFields.add("middleName");
        } else {
            optionalFields.add("middleName");
        }

        if (config.isRequiresOrganDonor()) {
            optionalFields.add("organDonor");
        }

        if (config.isRequiresVeteran()) {
            optionalFields.add("veteran");
        }

        if (config.isRequiresRestrictions()) {
            optionalFields.add("restrictions");
        }

        if (config.isRequiresEndorsements()) {
            optionalFields.add("endorsements");
        }

        requirements.put("required", requiredFields);
        requirements.put("optional", optionalFields);
        requirements.put("availableClasses", config.getAvailableClasses());
        requirements.put("availableRestrictions", config.getAvailableRestrictions());
        requirements.put("availableEndorsements", config.getAvailableEndorsements());
        requirements.put("eyeColorCodes", config.getEyeColorCodes());
        requirements.put("hairColorCodes", config.getHairColorCodes());

        return ResponseEntity.ok(requirements);
    }

    /**
     * Get AAMVA standard color codes
     */
    @GetMapping("/color-codes")
    public ResponseEntity<Map<String, Object>> getColorCodes() {
        Map<String, Object> colors = new HashMap<>();

        Map<String, String> eyeColors = new HashMap<>();
        eyeColors.put("BLK", "Black");
        eyeColors.put("BLU", "Blue");
        eyeColors.put("BRO", "Brown");
        eyeColors.put("GRY", "Gray");
        eyeColors.put("GRN", "Green");
        eyeColors.put("HAZ", "Hazel");
        eyeColors.put("MAR", "Maroon");
        eyeColors.put("PNK", "Pink");
        eyeColors.put("DIC", "Dichromatic");
        eyeColors.put("UNK", "Unknown");

        Map<String, String> hairColors = new HashMap<>();
        hairColors.put("BAL", "Bald");
        hairColors.put("BLK", "Black");
        hairColors.put("BLN", "Blond");
        hairColors.put("BRO", "Brown");
        hairColors.put("GRY", "Gray");
        hairColors.put("RED", "Red/Auburn");
        hairColors.put("SDY", "Sandy");
        hairColors.put("WHI", "White");
        hairColors.put("UNK", "Unknown");

        colors.put("eyeColors", eyeColors);
        colors.put("hairColors", hairColors);

        return ResponseEntity.ok(colors);
    }

    /**
     * Validate license number format for a jurisdiction
     */
    @PostMapping("/validate-format")
    public ResponseEntity<Map<String, Object>> validateLicenseFormat(
            @RequestBody Map<String, String> request) {

        String licenseNumber = request.get("licenseNumber");
        String stateCode = request.get("stateCode");

        if (licenseNumber == null || stateCode == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            StateFormat state = StateFormat.valueOf(stateCode.toUpperCase());
            boolean isValid = state.validate(licenseNumber);

            Map<String, Object> response = new HashMap<>();
            response.put("valid", isValid);
            response.put("pattern", state.getPattern());
            response.put("message", isValid ?
                    "License number format is valid" :
                    "License number does not match required format");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("valid", false);
            errorResponse.put("message", "Invalid state code");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
