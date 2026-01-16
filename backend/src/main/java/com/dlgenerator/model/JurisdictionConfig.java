package com.dlgenerator.model;

import lombok.Builder;
import lombok.Data;
import java.util.*;

/**
 * Comprehensive jurisdiction configuration for AAMVA compliance
 * Includes all US states, territories, and international countries
 */
@Data
@Builder
public class JurisdictionConfig {
    private String code;
    private String name;
    private String aamvaCode; // AAMVA Issuer Identification Number (IIN)
    private String countryCode; // ISO 3166-1 alpha-3
    private String documentName;
    private boolean requiresMiddleName;
    private boolean requiresOrganDonor;
    private boolean requiresVeteran;
    private boolean requiresRestrictions;
    private boolean requiresEndorsements;
    private List<String> availableClasses;
    private List<String> availableRestrictions;
    private List<String> availableEndorsements;
    private Map<String, String> eyeColorCodes;
    private Map<String, String> hairColorCodes;

    /**
     * Get all US state jurisdictions with AAMVA compliance data
     */
    public static Map<String, JurisdictionConfig> getUSJurisdictions() {
        Map<String, JurisdictionConfig> jurisdictions = new HashMap<>();

        // Alabama
        jurisdictions.put("AL", JurisdictionConfig.builder()
                .code("AL").name("Alabama").aamvaCode("636033").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .availableRestrictions(Arrays.asList("B", "C", "D", "E", "L", "M", "N", "O"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Alaska
        jurisdictions.put("AK", JurisdictionConfig.builder()
                .code("AK").name("Alaska").aamvaCode("636059").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Arizona
        jurisdictions.put("AZ", JurisdictionConfig.builder()
                .code("AZ").name("Arizona").aamvaCode("636026").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Arkansas
        jurisdictions.put("AR", JurisdictionConfig.builder()
                .code("AR").name("Arkansas").aamvaCode("636021").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // California
        jurisdictions.put("CA", JurisdictionConfig.builder()
                .code("CA").name("California").aamvaCode("636014").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "M"))
                .availableRestrictions(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Colorado
        jurisdictions.put("CO", JurisdictionConfig.builder()
                .code("CO").name("Colorado").aamvaCode("636020").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Connecticut
        jurisdictions.put("CT", JurisdictionConfig.builder()
                .code("CT").name("Connecticut").aamvaCode("636006").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Delaware
        jurisdictions.put("DE", JurisdictionConfig.builder()
                .code("DE").name("Delaware").aamvaCode("636011").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Florida
        jurisdictions.put("FL", JurisdictionConfig.builder()
                .code("FL").name("Florida").aamvaCode("636010").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "K", "L", "M", "N", "O", "P"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Georgia
        jurisdictions.put("GA", JurisdictionConfig.builder()
                .code("GA").name("Georgia").aamvaCode("636055").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "F", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Hawaii
        jurisdictions.put("HI", JurisdictionConfig.builder()
                .code("HI").name("Hawaii").aamvaCode("636047").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("1", "2", "3", "4"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Idaho
        jurisdictions.put("ID", JurisdictionConfig.builder()
                .code("ID").name("Idaho").aamvaCode("636050").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Illinois
        jurisdictions.put("IL", JurisdictionConfig.builder()
                .code("IL").name("Illinois").aamvaCode("636035").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "L", "M"))
                .availableRestrictions(Arrays.asList("B", "C", "D", "E", "F", "H", "K", "L", "M", "N", "P", "S", "Y", "Z"))
                .availableEndorsements(Arrays.asList("H", "L", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Indiana
        jurisdictions.put("IN", JurisdictionConfig.builder()
                .code("IN").name("Indiana").aamvaCode("636037").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Iowa
        jurisdictions.put("IA", JurisdictionConfig.builder()
                .code("IA").name("Iowa").aamvaCode("636018").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Kansas
        jurisdictions.put("KS", JurisdictionConfig.builder()
                .code("KS").name("Kansas").aamvaCode("636022").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Kentucky
        jurisdictions.put("KY", JurisdictionConfig.builder()
                .code("KY").name("Kentucky").aamvaCode("636046").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Louisiana
        jurisdictions.put("LA", JurisdictionConfig.builder()
                .code("LA").name("Louisiana").aamvaCode("636007").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Maine
        jurisdictions.put("ME", JurisdictionConfig.builder()
                .code("ME").name("Maine").aamvaCode("636041").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Maryland
        jurisdictions.put("MD", JurisdictionConfig.builder()
                .code("MD").name("Maryland").aamvaCode("636003").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Massachusetts
        jurisdictions.put("MA", JurisdictionConfig.builder()
                .code("MA").name("Massachusetts").aamvaCode("636002").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Michigan
        jurisdictions.put("MI", JurisdictionConfig.builder()
                .code("MI").name("Michigan").aamvaCode("636032").countryCode("USA")
                .documentName("Operator's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Minnesota
        jurisdictions.put("MN", JurisdictionConfig.builder()
                .code("MN").name("Minnesota").aamvaCode("636038").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Mississippi
        jurisdictions.put("MS", JurisdictionConfig.builder()
                .code("MS").name("Mississippi").aamvaCode("636051").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M", "R"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Missouri
        jurisdictions.put("MO", JurisdictionConfig.builder()
                .code("MO").name("Missouri").aamvaCode("636030").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "F", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Montana
        jurisdictions.put("MT", JurisdictionConfig.builder()
                .code("MT").name("Montana").aamvaCode("636008").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Nebraska
        jurisdictions.put("NE", JurisdictionConfig.builder()
                .code("NE").name("Nebraska").aamvaCode("636054").countryCode("USA")
                .documentName("Operator's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M", "O"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Nevada
        jurisdictions.put("NV", JurisdictionConfig.builder()
                .code("NV").name("Nevada").aamvaCode("636049").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "J", "K", "L", "M", "N", "O", "P", "V", "Y", "Z"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // New Hampshire
        jurisdictions.put("NH", JurisdictionConfig.builder()
                .code("NH").name("New Hampshire").aamvaCode("636039").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // New Jersey
        jurisdictions.put("NJ", JurisdictionConfig.builder()
                .code("NJ").name("New Jersey").aamvaCode("636036").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // New Mexico
        jurisdictions.put("NM", JurisdictionConfig.builder()
                .code("NM").name("New Mexico").aamvaCode("636009").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // New York
        jurisdictions.put("NY", JurisdictionConfig.builder()
                .code("NY").name("New York").aamvaCode("636001").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "M"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "K", "L", "M", "N", "O", "P", "V"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // North Carolina
        jurisdictions.put("NC", JurisdictionConfig.builder()
                .code("NC").name("North Carolina").aamvaCode("636004").countryCode("USA")
                .documentName("Drivers License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // North Dakota
        jurisdictions.put("ND", JurisdictionConfig.builder()
                .code("ND").name("North Dakota").aamvaCode("636034").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Ohio
        jurisdictions.put("OH", JurisdictionConfig.builder()
                .code("OH").name("Ohio").aamvaCode("636023").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "M"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "I", "K", "L", "M", "N", "O", "W", "X", "Y"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Oklahoma
        jurisdictions.put("OK", JurisdictionConfig.builder()
                .code("OK").name("Oklahoma").aamvaCode("636058").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Oregon
        jurisdictions.put("OR", JurisdictionConfig.builder()
                .code("OR").name("Oregon").aamvaCode("636029").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Pennsylvania
        jurisdictions.put("PA", JurisdictionConfig.builder()
                .code("PA").name("Pennsylvania").aamvaCode("636025").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "M"))
                .availableRestrictions(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Rhode Island
        jurisdictions.put("RI", JurisdictionConfig.builder()
                .code("RI").name("Rhode Island").aamvaCode("636052").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // South Carolina
        jurisdictions.put("SC", JurisdictionConfig.builder()
                .code("SC").name("South Carolina").aamvaCode("636005").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // South Dakota
        jurisdictions.put("SD", JurisdictionConfig.builder()
                .code("SD").name("South Dakota").aamvaCode("636042").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Tennessee
        jurisdictions.put("TN", JurisdictionConfig.builder()
                .code("TN").name("Tennessee").aamvaCode("636053").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Texas
        jurisdictions.put("TX", JurisdictionConfig.builder()
                .code("TX").name("Texas").aamvaCode("636015").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "M"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"))
                .availableEndorsements(Arrays.asList("H", "L", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Utah
        jurisdictions.put("UT", JurisdictionConfig.builder()
                .code("UT").name("Utah").aamvaCode("636040").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Vermont
        jurisdictions.put("VT", JurisdictionConfig.builder()
                .code("VT").name("Vermont").aamvaCode("636024").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Virginia
        jurisdictions.put("VA", JurisdictionConfig.builder()
                .code("VA").name("Virginia").aamvaCode("636000").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .requiresRestrictions(true).requiresEndorsements(true)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .availableRestrictions(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "I", "J", "K", "L", "M", "N", "O", "P", "S", "T", "V", "W", "X", "Y", "Z"))
                .availableEndorsements(Arrays.asList("H", "N", "P", "S", "T", "X"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Washington
        jurisdictions.put("WA", JurisdictionConfig.builder()
                .code("WA").name("Washington").aamvaCode("636045").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(true)
                .availableClasses(Arrays.asList("A", "B", "C"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // West Virginia
        jurisdictions.put("WV", JurisdictionConfig.builder()
                .code("WV").name("West Virginia").aamvaCode("636061").countryCode("USA")
                .documentName("Driver's License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Wisconsin
        jurisdictions.put("WI", JurisdictionConfig.builder()
                .code("WI").name("Wisconsin").aamvaCode("636048").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // Wyoming
        jurisdictions.put("WY", JurisdictionConfig.builder()
                .code("WY").name("Wyoming").aamvaCode("636060").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        // District of Columbia
        jurisdictions.put("DC", JurisdictionConfig.builder()
                .code("DC").name("District of Columbia").aamvaCode("636043").countryCode("USA")
                .documentName("Driver License")
                .requiresMiddleName(false).requiresOrganDonor(true).requiresVeteran(false)
                .availableClasses(Arrays.asList("A", "B", "C", "D", "E", "M"))
                .eyeColorCodes(getStandardEyeColors())
                .hairColorCodes(getStandardHairColors())
                .build());

        return jurisdictions;
    }

    private static Map<String, String> getStandardEyeColors() {
        Map<String, String> colors = new HashMap<>();
        colors.put("BLK", "Black");
        colors.put("BLU", "Blue");
        colors.put("BRO", "Brown");
        colors.put("GRY", "Gray");
        colors.put("GRN", "Green");
        colors.put("HAZ", "Hazel");
        colors.put("MAR", "Maroon");
        colors.put("PNK", "Pink");
        colors.put("DIC", "Dichromatic");
        colors.put("UNK", "Unknown");
        return colors;
    }

    private static Map<String, String> getStandardHairColors() {
        Map<String, String> colors = new HashMap<>();
        colors.put("BAL", "Bald");
        colors.put("BLK", "Black");
        colors.put("BLN", "Blond");
        colors.put("BRO", "Brown");
        colors.put("GRY", "Gray");
        colors.put("RED", "Red/Auburn");
        colors.put("SDY", "Sandy");
        colors.put("WHI", "White");
        colors.put("UNK", "Unknown");
        return colors;
    }
}
