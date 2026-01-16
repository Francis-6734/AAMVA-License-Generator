package com.dlgenerator.model;

import java.util.regex.Pattern;

/**
 * Complete enumeration of all US states, territories, and DC with their license number formats
 * Based on actual DMV standards for each jurisdiction
 */
public enum StateFormat {
    // US States with validation patterns (All 50 states + DC)
    AL("Alabama", "636033", "^\\d{1,8}$"),
    AK("Alaska", "636059", "^\\d{1,7}$"),
    AZ("Arizona", "636026", "^[A-Z]\\d{8}$"),
    AR("Arkansas", "636021", "^\\d{4,9}$"),
    CA("California", "636014", "^[A-Z]\\d{7}$"),
    CO("Colorado", "636020", "^\\d{9}$|^[A-Z]{2}\\d{3,6}$|^\\d{2}-\\d{3}-\\d{4}$"),
    CT("Connecticut", "636006", "^\\d{9}$"),
    DE("Delaware", "636011", "^\\d{1,7}$"),
    FL("Florida", "636010", "^[A-Z]\\d{12}$|^[A-Z]-\\d{3}-\\d{3}-\\d{3}-\\d{3}$"),
    GA("Georgia", "636055", "^\\d{7,9}$"),
    HI("Hawaii", "636047", "^[A-Z]\\d{8}$|^\\d{9}$"),
    ID("Idaho", "636050", "^[A-Z]{2}\\d{6}[A-Z]$|^\\d{9}$"),
    IL("Illinois", "636035", "^[A-Z]\\d{11}$|^[A-Z]\\d{12}$"),
    IN("Indiana", "636037", "^\\d{10}$|^\\d{9}$"),
    IA("Iowa", "636018", "^\\d{9}$|^\\d{3}[A-Z]{2}\\d{4}$"),
    KS("Kansas", "636022", "^[A-Z]\\d{8}$|^K\\d{8}$|^\\d{9}$"),
    KY("Kentucky", "636046", "^[A-Z]\\d{8,9}$|^\\d{9}$"),
    LA("Louisiana", "636007", "^\\d{1,9}$"),
    ME("Maine", "636041", "^\\d{7}$|^\\d{7}[A-Z]$|^\\d{8}$"),
    MD("Maryland", "636003", "^[A-Z]-\\d{3}-\\d{3}-\\d{3}-\\d{3}$"),
    MA("Massachusetts", "636002", "^[A-Z]\\d{8}$|^S\\d{8}$"),
    MI("Michigan", "636032", "^[A-Z]\\d{12}$|^[A-Z]\\d{10}$"),
    MN("Minnesota", "636038", "^[A-Z]\\d{12}$"),
    MS("Mississippi", "636051", "^\\d{9}$"),
    MO("Missouri", "636030", "^[A-Z]\\d{5,9}$|^\\d{8,9}$|^\\d{9}[A-Z]$"),
    MT("Montana", "636008", "^[A-Z]\\d{12}$|^\\d{13}$|^\\d{9}$"),
    NE("Nebraska", "636054", "^[A-Z]\\d{8}$"),
    NV("Nevada", "636049", "^\\d{9,12}$|^X\\d{8}$"),
    NH("New Hampshire", "636039", "^\\d{2}[A-Z]{3}\\d{5}$"),
    NJ("New Jersey", "636036", "^[A-Z]\\d{14}$"),
    NM("New Mexico", "636009", "^\\d{8,9}$"),
    NY("New York", "636001", "^\\d{8,9}$|^[A-Z]\\d{18}$|^[A-Z]\\d{7}$|^[A-Z]{8}$"),
    NC("North Carolina", "636004", "^\\d{1,12}$"),
    ND("North Dakota", "636034", "^[A-Z]{3}\\d{6}$|^\\d{9}$"),
    OH("Ohio", "636023", "^[A-Z]{2}\\d{6}$|^[A-Z]\\d{4,8}$"),
    OK("Oklahoma", "636058", "^[A-Z]\\d{9}$|^\\d{9}$"),
    OR("Oregon", "636029", "^\\d{1,9}$|^[A-Z]\\d{6}$"),
    PA("Pennsylvania", "636025", "^\\d{8}$"),
    RI("Rhode Island", "636052", "^\\d{7}$|^V\\d{6}$"),
    SC("South Carolina", "636005", "^\\d{5,11}$"),
    SD("South Dakota", "636042", "^\\d{6,10}$|^\\d{12}$"),
    TN("Tennessee", "636053", "^\\d{7,9}$"),
    TX("Texas", "636015", "^\\d{7,8}$"),
    UT("Utah", "636040", "^\\d{4,10}$"),
    VT("Vermont", "636024", "^\\d{8}$|^\\d{7}[A-Z]$"),
    VA("Virginia", "636000", "^[A-Z]\\d{8,11}$|^\\d{9}$"),
    WA("Washington", "636045", "^[A-Z]{5}\\*{2}[A-Z0-9]{3}[A-Z0-9]{2}$|^WDL[A-Z0-9]{9}$"),
    WV("West Virginia", "636061", "^[A-Z]\\d{6}$|^\\d{7}$"),
    WI("Wisconsin", "636048", "^[A-Z]\\d{13}$"),
    WY("Wyoming", "636060", "^\\d{9,10}$"),
    DC("District of Columbia", "636043", "^\\d{7}$|^\\d{9}$"),

    // US Territories
    AS("American Samoa", "636062", "^\\d{9}$"),
    GU("Guam", "636063", "^\\d{9}$"),
    MP("Northern Mariana Islands", "636064", "^\\d{9}$"),
    PR("Puerto Rico", "636065", "^\\d{9}$|^\\d{7}$"),
    VI("U.S. Virgin Islands", "636066", "^\\d{9}$");

    private final String stateName;
    private final String aamvaCode;
    private final String pattern;

    StateFormat(String stateName, String aamvaCode, String pattern) {
        this.stateName = stateName;
        this.aamvaCode = aamvaCode;
        this.pattern = pattern;
    }

    public String getStateName() {
        return stateName;
    }

    public String getAamvaCode() {
        return aamvaCode;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean validate(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(pattern, licenseNumber);
    }
}