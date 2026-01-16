package com.dlgenerator.service;

import com.dlgenerator.model.StateFormat;
import com.dlgenerator.model.CountryFormat;
import org.springframework.stereotype.Service;

@Service
public class LicenseValidationService {
    
    public boolean validateState(String licenseNumber, StateFormat state) {
        if (licenseNumber == null || state == null) {
            return false;
        }
        return state.validate(licenseNumber);
    }
    
    public boolean validateCountry(String licenseNumber, CountryFormat country) {
        if (licenseNumber == null || country == null) {
            return false;
        }
        return country.validate(licenseNumber);
    }
}