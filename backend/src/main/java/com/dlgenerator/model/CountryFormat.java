package com.dlgenerator.model;

import java.util.regex.Pattern;

public enum CountryFormat {
    // International driver's license formats
    USA("United States", "US", "Driver License"),
    UK("United Kingdom", "GB", "Driving Licence", "^[A-Z]{5}\\d{6}[A-Z]{2}\\d[A-Z]{2}$"),
    CANADA("Canada", "CA", "Driver's Licence", "^[A-Z]\\d{4}-\\d{5}-\\d{5}$"),
    GERMANY("Germany", "DE", "Führerschein", "^[A-Z]\\d{2}[A-Z]\\d{6}$"),
    FRANCE("France", "FR", "Permis de Conduire", "^\\d{12}$"),
    AUSTRALIA("Australia", "AU", "Driver Licence", "^\\d{8,10}$"),
    JAPAN("Japan", "JP", "運転免許証", "^\\d{12}$"),
    INDIA("India", "IN", "Driving Licence", "^[A-Z]{2}\\d{13}$"),
    MEXICO("Mexico", "MX", "Licencia de Conducir", "^[A-Z]\\d{8}$"),
    BRAZIL("Brazil", "BR", "Carteira Nacional de Habilitação", "^\\d{11}$"),
    SPAIN("Spain", "ES", "Permiso de Conducción", "^\\d{8}[A-Z]$"),
    ITALY("Italy", "IT", "Patente di Guida", "^[A-Z]{2}\\d{7}[A-Z]$"),
    NETHERLANDS("Netherlands", "NL", "Rijbewijs", "^\\d{10}$"),
    SWEDEN("Sweden", "SE", "Körkort", "^\\d{8}$"),
    NORWAY("Norway", "NO", "Førerkort", "^\\d{8}$");
    
    private final String countryName;
    private final String countryCode;
    private final String documentName;
    private final String validationPattern;
    
    CountryFormat(String countryName, String countryCode, String documentName) {
        this(countryName, countryCode, documentName, null);
    }
    
    CountryFormat(String countryName, String countryCode, String documentName, String pattern) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.documentName = documentName;
        this.validationPattern = pattern;
    }
    
    public String getCountryName() { return countryName; }
    public String getCountryCode() { return countryCode; }
    public String getDocumentName() { return documentName; }
    
    public boolean validate(String licenseNumber) {
        if (validationPattern == null) return true;
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) return false;
        return Pattern.matches(validationPattern, licenseNumber);
    }
}