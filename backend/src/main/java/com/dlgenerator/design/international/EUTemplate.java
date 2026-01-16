package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Base class for all EU member state license templates.
 * Implements EU driving licence directive standards.
 */
public abstract class EUTemplate extends InternationalCardTemplate {

    // EU standard colors
    protected static final Color EU_BLUE = new Color(0, 51, 153);
    protected static final Color EU_YELLOW = new Color(255, 204, 0);
    protected static final Color EU_PINK = new Color(255, 192, 203);
    protected static final Color EU_PINK_DARK = new Color(255, 160, 180);

    public EUTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                      SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public BarcodeType getBarcodeType() {
        return BarcodeType.PDF417;
    }

    /**
     * Draw standard EU pink gradient background
     */
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        renderer.drawGradientBackground(g2d, EU_PINK, EU_PINK_DARK, width, height);
    }

    /**
     * Generate EU-compliant front of card
     */
    @Override
    public BufferedImage generateFront(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // 1. Pink background (EU standard)
        drawBackground(g2d, CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // 2. EU flag
        drawEUFlag(g2d, 20, 20, 100, 70);

        // 3. Country-specific header
        drawHeader(g2d, data);

        // 4. Photo
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawMainPhoto(g2d, photo);
            }
        }

        // 5. EU field numbers with data
        drawEUFields(g2d, data);

        // 6. Security features
        drawFrontSecurityFeatures(g2d, data);

        // 7. Ghost image
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawGhostImage(g2d, photo);
            }
        }

        g2d.dispose();
        return card;
    }

    /**
     * Draw EU-standard numbered fields
     * Fields are numbered 1-9 as per EU directive
     */
    protected void drawEUFields(Graphics2D g2d, LicenseData data) {
        int x = DATA_START_X;
        int y = DATA_START_Y;
        int lineHeight = 35;

        // Field 1: Last name
        drawEUField(g2d, "1.", data.getLastName() != null ? data.getLastName().toUpperCase() : "", x, y);
        y += lineHeight;

        // Field 2: First name(s)
        String firstName = data.getFirstName() != null ? data.getFirstName().toUpperCase() : "";
        if (data.getMiddleName() != null && !data.getMiddleName().isEmpty()) {
            firstName += " " + data.getMiddleName().toUpperCase();
        }
        drawEUField(g2d, "2.", firstName, x, y);
        y += lineHeight;

        // Field 3: Date and place of birth
        String dob = formatDate(data.getDob());
        drawEUField(g2d, "3.", dob, x, y);
        y += lineHeight;

        // Field 4a: Issue date
        drawEUField(g2d, "4a.", formatDate(data.getIssueDate()), x, y);

        // Field 4b: Expiry date
        drawEUField(g2d, "4b.", formatDate(data.getExpirationDate()), x + 200, y);
        y += lineHeight;

        // Field 4c: Issuing authority
        String authority = data.getIssuingAuthority() != null ? data.getIssuingAuthority() : getDefaultIssuingAuthority();
        drawEUField(g2d, "4c.", authority, x, y);
        y += lineHeight;

        // Field 5: License number
        drawEUField(g2d, "5.", data.getLicenseNumber(), x, y);
        y += lineHeight;

        // Field 7: Signature (placeholder text)
        g2d.setFont(new Font("Arial", Font.ITALIC, 10));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("7. Signature", x, y + 10);
        y += lineHeight;

        // Field 8: Address
        String address = data.getAddress() != null ? data.getAddress() : "";
        if (data.getCity() != null) {
            address += ", " + data.getCity();
        }
        if (data.getZipCode() != null) {
            address += " " + data.getZipCode();
        }
        drawEUField(g2d, "8.", address.toUpperCase(), x, y);
    }

    /**
     * Draw a single EU-style field with number prefix
     */
    protected void drawEUField(Graphics2D g2d, String fieldNum, String value, int x, int y) {
        // Field number (small, gray)
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString(fieldNum, x - 25, y + 15);

        // Value (bold, black)
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString(value != null ? value : "", x, y + 15);
    }

    /**
     * Get default issuing authority name (override in specific country templates)
     */
    protected abstract String getDefaultIssuingAuthority();

    /**
     * Draw country identifier text on EU flag area
     */
    protected void drawCountryCode(Graphics2D g2d, String code) {
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.WHITE);
        int x = 45 + (3 - code.length()) * 5; // Center based on code length
        g2d.drawString(code, x, 110);
    }

    /**
     * Override back card for EU-specific layout with category grid
     */
    @Override
    public BufferedImage generateBack(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Background
        drawBackBackground(g2d);

        // Header
        drawBackHeader(g2d);

        // EU Category grid (Fields 9-12)
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("9. CATEGORIES  10. ISSUE DATE  11. EXPIRY DATE  12. RESTRICTIONS", 50, 110);
        drawEUCategoryGrid(g2d, data, 50, 130);

        // Barcode
        if (data.getBarcodeBase64() != null) {
            BufferedImage barcode = imageProcessor.decodeBase64Image(data.getBarcodeBase64());
            if (barcode != null) {
                g2d.drawImage(barcode, 50, 350, 800, 150, null);
            }
        }

        // Compliance text
        drawEUComplianceText(g2d);

        // Security features
        drawBackSecurityFeatures(g2d);

        g2d.dispose();
        return card;
    }

    /**
     * Draw EU-specific compliance text
     */
    protected void drawEUComplianceText(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));

        String[] lines = {
            "This driving licence is issued in accordance with the provisions of",
            "European Union Directive 2006/126/EC.",
            "Valid in all EU/EEA member states."
        };

        int y = 530;
        for (String line : lines) {
            g2d.drawString(line, 50, y);
            y += 14;
        }
    }

    @Override
    protected boolean hasMagneticStripe() {
        return false; // EU licenses typically don't have magnetic stripe
    }
}
