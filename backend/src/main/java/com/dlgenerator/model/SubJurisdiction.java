package com.dlgenerator.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sub-jurisdictions for countries with state/province-based licensing systems
 */
public enum SubJurisdiction {
    // Australia States and Territories
    AU_NSW("NSW", "New South Wales", "AU", "^\\d{8}$"),
    AU_VIC("VIC", "Victoria", "AU", "^\\d{9,10}$"),
    AU_QLD("QLD", "Queensland", "AU", "^\\d{8,9}$"),
    AU_WA("WA", "Western Australia", "AU", "^\\d{7}$"),
    AU_SA("SA", "South Australia", "AU", "^[A-Z]\\d{5}$"),
    AU_TAS("TAS", "Tasmania", "AU", "^\\d{6,8}$"),
    AU_ACT("ACT", "Australian Capital Territory", "AU", "^\\d{6}$"),
    AU_NT("NT", "Northern Territory", "AU", "^\\d{5,8}$"),

    // Canada Provinces and Territories
    CA_ON("ON", "Ontario", "CA", "^[A-Z]\\d{4}-\\d{5}-\\d{5}$"),
    CA_BC("BC", "British Columbia", "CA", "^\\d{7}$"),
    CA_AB("AB", "Alberta", "CA", "^\\d{6,9}$"),
    CA_QC("QC", "Quebec", "CA", "^[A-Z]\\d{4}-\\d{6}-\\d{2}$"),
    CA_MB("MB", "Manitoba", "CA", "^[A-Z]{2}\\d{6}$"),
    CA_SK("SK", "Saskatchewan", "CA", "^\\d{8}$"),
    CA_NS("NS", "Nova Scotia", "CA", "^[A-Z]{5}\\d{9}$"),
    CA_NB("NB", "New Brunswick", "CA", "^\\d{7}$"),
    CA_NL("NL", "Newfoundland and Labrador", "CA", "^[A-Z]\\d{9}$"),
    CA_PE("PE", "Prince Edward Island", "CA", "^\\d{5,6}$"),
    CA_YT("YT", "Yukon", "CA", "^\\d{6}$"),
    CA_NT("NT", "Northwest Territories", "CA", "^\\d{6}$"),
    CA_NU("NU", "Nunavut", "CA", "^\\d{6}$"),

    // India States (Major)
    IN_MH("MH", "Maharashtra", "IN", "^MH-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_DL("DL", "Delhi", "IN", "^DL-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_KA("KA", "Karnataka", "IN", "^KA-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_TN("TN", "Tamil Nadu", "IN", "^TN-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_UP("UP", "Uttar Pradesh", "IN", "^UP-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_GJ("GJ", "Gujarat", "IN", "^GJ-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_RJ("RJ", "Rajasthan", "IN", "^RJ-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_WB("WB", "West Bengal", "IN", "^WB-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_AP("AP", "Andhra Pradesh", "IN", "^AP-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_TS("TS", "Telangana", "IN", "^TS-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_KL("KL", "Kerala", "IN", "^KL-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_MP("MP", "Madhya Pradesh", "IN", "^MP-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_PB("PB", "Punjab", "IN", "^PB-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_HR("HR", "Haryana", "IN", "^HR-?\\d{2}-?\\d{4}-?\\d{7}$"),
    IN_BR("BR", "Bihar", "IN", "^BR-?\\d{2}-?\\d{4}-?\\d{7}$"),

    // Mexico States (Major)
    MX_CDMX("CDMX", "Ciudad de Mexico", "MX", "^[A-Z]{2,4}\\d{8,10}$"),
    MX_JAL("JAL", "Jalisco", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_NL("NL", "Nuevo Leon", "MX", "^[A-Z]{2}\\d{8,10}$"),
    MX_BC("BC", "Baja California", "MX", "^[A-Z]{2}\\d{8,10}$"),
    MX_GTO("GTO", "Guanajuato", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_VER("VER", "Veracruz", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_PUE("PUE", "Puebla", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_QRO("QRO", "Queretaro", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_SON("SON", "Sonora", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_CHIH("CHIH", "Chihuahua", "MX", "^[A-Z]{2,4}\\d{8,10}$"),
    MX_MEX("MEX", "Estado de Mexico", "MX", "^[A-Z]{2,3}\\d{8,10}$"),
    MX_YUC("YUC", "Yucatan", "MX", "^[A-Z]{2,3}\\d{8,10}$"),

    // Brazil States (Major)
    BR_SP("SP", "Sao Paulo", "BR", "^\\d{11}$"),
    BR_RJ("RJ", "Rio de Janeiro", "BR", "^\\d{11}$"),
    BR_MG("MG", "Minas Gerais", "BR", "^\\d{11}$"),
    BR_RS("RS", "Rio Grande do Sul", "BR", "^\\d{11}$"),
    BR_PR("PR", "Parana", "BR", "^\\d{11}$"),
    BR_BA("BA", "Bahia", "BR", "^\\d{11}$"),
    BR_SC("SC", "Santa Catarina", "BR", "^\\d{11}$"),
    BR_PE("PE", "Pernambuco", "BR", "^\\d{11}$"),
    BR_CE("CE", "Ceara", "BR", "^\\d{11}$"),
    BR_GO("GO", "Goias", "BR", "^\\d{11}$"),
    BR_DF("DF", "Distrito Federal", "BR", "^\\d{11}$"),
    BR_PA("PA", "Para", "BR", "^\\d{11}$");

    private final String code;
    private final String name;
    private final String countryCode;
    private final String pattern;

    SubJurisdiction(String code, String name, String countryCode, String pattern) {
        this.code = code;
        this.name = name;
        this.countryCode = countryCode;
        this.pattern = pattern;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean validate(String licenseNumber) {
        if (pattern == null || licenseNumber == null) return true;
        return licenseNumber.matches(pattern);
    }

    /**
     * Get all sub-jurisdictions for a specific country code
     */
    public static List<SubJurisdiction> getByCountry(String countryCode) {
        return Arrays.stream(values())
            .filter(sj -> sj.countryCode.equals(countryCode))
            .collect(Collectors.toList());
    }

    /**
     * Find sub-jurisdiction by its code (e.g., "NSW", "ON", "MH")
     */
    public static SubJurisdiction findByCode(String code) {
        return Arrays.stream(values())
            .filter(sj -> sj.code.equals(code))
            .findFirst()
            .orElse(null);
    }

    /**
     * Find sub-jurisdiction by enum name (e.g., "AU_NSW", "CA_ON")
     */
    public static SubJurisdiction findByEnumName(String enumName) {
        try {
            return valueOf(enumName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
