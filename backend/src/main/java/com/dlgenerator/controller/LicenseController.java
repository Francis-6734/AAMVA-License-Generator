package com.dlgenerator.controller;

import com.dlgenerator.model.CardResponse;
import com.dlgenerator.model.LicenseRequest;
import com.dlgenerator.model.LicenseResponse;
import com.dlgenerator.service.CardDesignService;
import com.dlgenerator.service.LicenseGeneratorService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/license")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LicenseController {
    
    private final LicenseGeneratorService licenseGeneratorService;
    private final CardDesignService cardDesignService;
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("Health check endpoint called");
        return ResponseEntity.ok("Driver's License Generator API is running!");
    }
    
    @PostMapping("/generate")
    public ResponseEntity<LicenseResponse> generateLicense(@Valid @RequestBody LicenseRequest request) {
        try {
            log.info("Generating license for: {} {}", request.getFirstName(), request.getLastName());
            LicenseResponse response = licenseGeneratorService.generateLicense(request);
            log.info("License generated successfully: {}", response.getLicenseNumber());
            return ResponseEntity.ok(response);
        } catch (WriterException | IOException e) {
            log.error("Error generating license", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/generate/card")
    public ResponseEntity<CardResponse> generateCard(@Valid @RequestBody LicenseRequest request) {
        try {
            log.info("Generating card design for: {} {}", request.getFirstName(), request.getLastName());
            
            LicenseResponse license = licenseGeneratorService.generateLicense(request);
            CardResponse card = cardDesignService.generateCard(request, license);
            
            log.info("Card generated successfully with format: {}", card.getCardFormat());
            return ResponseEntity.ok(card);
        } catch (WriterException | IOException e) {
            log.error("Error generating card", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}