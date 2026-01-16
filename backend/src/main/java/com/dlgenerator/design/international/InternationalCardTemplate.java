package com.dlgenerator.design.international;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;
import com.dlgenerator.model.SubJurisdiction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Base class for all international license templates.
 * Extends the AAMVA-compliant CardTemplate with international-specific features.
 */
public abstract class InternationalCardTemplate extends CardTemplate {

    public enum BarcodeType {
        PDF417,     // US, Canada, EU
        QRCODE,     // Brazil
        CODE_128,   // Some regions
        NONE        // Japan and others
    }

    protected SubJurisdiction subJurisdiction;

    public InternationalCardTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                                     SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    public InternationalCardTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                                     SecurityFeatureGenerator securityGenerator,
                                     SubJurisdiction subJurisdiction) {
        super(renderer, imageProcessor, securityGenerator);
        this.subJurisdiction = subJurisdiction;
    }

    // Abstract methods for international templates
    public abstract Locale getLocale();
    public abstract String getLocalizedDocumentName();
    public abstract BarcodeType getBarcodeType();

    /**
     * Get date formatter based on locale
     */
    public DateTimeFormatter getLocalDateFormat() {
        String country = getLocale().getCountry();
        return switch (country) {
            case "JP" -> DateTimeFormatter.ofPattern("yyyy/MM/dd");
            case "DE", "AT", "CH" -> DateTimeFormatter.ofPattern("dd.MM.yyyy");
            case "FR", "ES", "IT", "NL", "BE" -> DateTimeFormatter.ofPattern("dd/MM/yyyy");
            case "AU", "GB", "IN" -> DateTimeFormatter.ofPattern("dd/MM/yyyy");
            case "BR", "PT" -> DateTimeFormatter.ofPattern("dd/MM/yyyy");
            case "MX" -> DateTimeFormatter.ofPattern("dd/MM/yyyy");
            default -> DateTimeFormatter.ofPattern("MM/dd/yyyy");
        };
    }

    /**
     * Override date formatting for international formats
     */
    @Override
    protected String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return date.format(getLocalDateFormat());
        } catch (Exception e) {
            return dateStr;
        }
    }

    /**
     * Format date to Japanese era calendar (Reiwa, Heisei, etc.)
     */
    protected String formatJapaneseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            LocalDate date = LocalDate.parse(dateStr);
            JapaneseDate japaneseDate = JapaneseDate.from(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Gy年M月d日", Locale.JAPAN)
                .withChronology(JapaneseChronology.INSTANCE);
            return japaneseDate.format(formatter);
        } catch (Exception e) {
            return dateStr;
        }
    }

    /**
     * Convert height from inches to centimeters for metric countries
     */
    protected String formatHeight(String heightInches) {
        if (heightInches == null || heightInches.isEmpty()) return "";
        try {
            int inches = Integer.parseInt(heightInches.replaceAll("[^0-9]", ""));
            int cm = (int) Math.round(inches * 2.54);
            return cm + " cm";
        } catch (NumberFormatException e) {
            return heightInches;
        }
    }

    /**
     * Convert weight from pounds to kilograms for metric countries
     */
    protected String formatWeight(String weightPounds) {
        if (weightPounds == null || weightPounds.isEmpty()) return "";
        try {
            int lbs = Integer.parseInt(weightPounds.replaceAll("[^0-9]", ""));
            int kg = (int) Math.round(lbs * 0.453592);
            return kg + " kg";
        } catch (NumberFormatException e) {
            return weightPounds;
        }
    }

    /**
     * Draw EU flag (12 stars in a circle on blue background)
     */
    protected void drawEUFlag(Graphics2D g2d, int x, int y, int width, int height) {
        Color EU_BLUE = new Color(0, 51, 153);

        g2d.setColor(EU_BLUE);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.YELLOW);
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = Math.min(width, height) / 3;

        for (int i = 0; i < 12; i++) {
            double angle = (i * 30 - 90) * Math.PI / 180;
            int starX = centerX + (int) (radius * Math.cos(angle));
            int starY = centerY + (int) (radius * Math.sin(angle));
            renderer.drawStar(g2d, starX, starY, 5, 2, 5, Color.YELLOW);
        }
    }

    /**
     * Draw EU license category grid on the back of the card
     */
    protected void drawEUCategoryGrid(Graphics2D g2d, LicenseData data, int startX, int startY) {
        String[] categories = {"AM", "A1", "A2", "A", "B1", "B", "C1", "C", "D1", "D", "BE", "C1E", "CE", "D1E", "DE"};
        String activeCategories = data.getLicenseCategories() != null ? data.getLicenseCategories() : "B";

        g2d.setFont(new Font("Arial", Font.BOLD, 10));

        int colWidth = 55;
        int rowHeight = 25;
        int cols = 5;

        for (int i = 0; i < categories.length; i++) {
            int col = i % cols;
            int row = i / cols;
            int x = startX + col * colWidth;
            int y = startY + row * rowHeight;

            // Draw cell border
            g2d.setColor(new Color(100, 100, 100));
            g2d.drawRect(x, y, colWidth - 2, rowHeight - 2);

            // Check if category is active
            boolean isActive = activeCategories.contains(categories[i]);

            if (isActive) {
                g2d.setColor(new Color(0, 100, 0));
            } else {
                g2d.setColor(new Color(150, 150, 150));
            }

            g2d.drawString(categories[i], x + 5, y + 16);

            if (isActive) {
                // Draw checkmark or date
                g2d.setFont(new Font("Arial", Font.PLAIN, 8));
                g2d.drawString("✓", x + 35, y + 16);
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
            }
        }
    }

    /**
     * Draw regional security features (to be overridden by specific templates)
     */
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Default: no additional security features
        // Override in specific templates
    }

    /**
     * Draw localized field labels
     */
    protected void drawLocalizedField(Graphics2D g2d, String labelKey, String value, int x, int y) {
        String label = getLocalizedLabel(labelKey);
        drawDataField(g2d, label, value, x, y);
    }

    /**
     * Get localized label text (override in specific templates)
     */
    protected String getLocalizedLabel(String key) {
        // Default to English labels
        return switch (key) {
            case "firstName" -> "First Name";
            case "lastName" -> "Last Name";
            case "dob" -> "Date of Birth";
            case "address" -> "Address";
            case "city" -> "City";
            case "licenseNumber" -> "License No.";
            case "issueDate" -> "Issue Date";
            case "expiryDate" -> "Expiry Date";
            case "class" -> "Class";
            case "categories" -> "Categories";
            default -> key;
        };
    }

    /**
     * Draw country flag (to be implemented by specific templates)
     */
    protected void drawCountryFlag(Graphics2D g2d, int x, int y, int width, int height) {
        // Override in specific templates
    }

    /**
     * Get sub-jurisdiction name for display
     */
    protected String getSubJurisdictionDisplay() {
        if (subJurisdiction != null) {
            return subJurisdiction.getName();
        }
        return "";
    }

    /**
     * Override back card generation to use appropriate barcode type
     */
    @Override
    public BufferedImage generateBack(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // 1. Background
        drawBackBackground(g2d);

        // 2. Header
        drawBackHeader(g2d);

        // 3. Barcode based on type
        if (getBarcodeType() == BarcodeType.PDF417 && data.getBarcodeBase64() != null) {
            BufferedImage barcode = imageProcessor.decodeBase64Image(data.getBarcodeBase64());
            if (barcode != null) {
                drawPDF417Barcode(g2d, barcode);
            }
        } else if (getBarcodeType() == BarcodeType.QRCODE) {
            // QR code will be drawn by specific template (e.g., Brazil)
            drawQRCodePlaceholder(g2d, data);
        }
        // NONE type: no barcode

        // 4. Magnetic stripe (optional for some countries)
        if (hasMagneticStripe()) {
            drawMagneticStripe(g2d);
        }

        // 5. License class and restrictions
        drawLicenseClassDescription(g2d, data);
        drawEndorsements(g2d, data);
        drawRestrictions(g2d, data);

        // 6. Compliance text
        drawComplianceText(g2d);

        // 7. Security features
        drawBackSecurityFeatures(g2d);

        g2d.dispose();
        return card;
    }

    /**
     * Whether this license type has a magnetic stripe
     */
    protected boolean hasMagneticStripe() {
        return true; // Most licenses have magnetic stripe
    }

    /**
     * Draw QR code placeholder (for Brazil template to override)
     */
    protected void drawQRCodePlaceholder(Graphics2D g2d, LicenseData data) {
        // Override in BrazilTemplate
    }
}
