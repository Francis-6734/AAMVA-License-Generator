package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;
import com.dlgenerator.model.SubJurisdiction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

/**
 * Indian Driving Licence template.
 * Supports all states with state-specific RTO codes.
 */
public class IndiaTemplate extends InternationalCardTemplate {

    private static final Color INDIA_SAFFRON = new Color(255, 153, 51);
    private static final Color INDIA_GREEN = new Color(19, 136, 8);
    private static final Color INDIA_BLUE = new Color(0, 0, 128);
    private static final Color ASHOKA_BLUE = new Color(0, 56, 168);

    public IndiaTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                         SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    public IndiaTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                         SecurityFeatureGenerator securityGenerator,
                         SubJurisdiction subJurisdiction) {
        super(renderer, imageProcessor, securityGenerator, subJurisdiction);
    }

    @Override
    public String getTemplateName() {
        if (subJurisdiction != null) {
            return subJurisdiction.getName().toUpperCase();
        }
        return "INDIA";
    }

    @Override
    public Color getPrimaryColor() {
        return ASHOKA_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return INDIA_SAFFRON;
    }

    @Override
    public Locale getLocale() {
        return new Locale("en", "IN");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "DRIVING LICENCE";
    }

    @Override
    public BarcodeType getBarcodeType() {
        return BarcodeType.PDF417;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Light cream/white background
        renderer.drawGradientBackground(g2d,
            new Color(255, 255, 250),
            new Color(245, 245, 240),
            width, height);

        // Tricolor accent at top
        int stripeHeight = 8;
        g2d.setColor(INDIA_SAFFRON);
        g2d.fillRect(0, 0, width, stripeHeight);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, stripeHeight, width, stripeHeight);
        g2d.setColor(INDIA_GREEN);
        g2d.fillRect(0, stripeHeight * 2, width, stripeHeight);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        int y = 50;

        // Ashoka Chakra representation (simplified)
        drawAshokaChakra(g2d, 50, 35, 40);

        // Government text
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(ASHOKA_BLUE);
        g2d.drawString("UNION OF INDIA", 110, y);
        g2d.drawString("भारत संघ", 110, y + 15);

        // State name
        String stateName = subJurisdiction != null ? subJurisdiction.getName() : "INDIA";
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("STATE OF " + stateName.toUpperCase(), 110, y + 40);

        // Document title
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(ASHOKA_BLUE);
        g2d.drawString(getLocalizedDocumentName(), 450, y + 20);

        // Hindi text
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("ड्राइविंग लाइसेंस", 450, y + 45);
    }

    private void drawAshokaChakra(Graphics2D g2d, int x, int y, int radius) {
        // Blue circle
        g2d.setColor(ASHOKA_BLUE);
        g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // White inner circle
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x - radius + 5, y - radius + 5, (radius - 5) * 2, (radius - 5) * 2);

        // Spokes (24 spokes)
        g2d.setColor(ASHOKA_BLUE);
        g2d.setStroke(new BasicStroke(1));
        for (int i = 0; i < 24; i++) {
            double angle = i * 15 * Math.PI / 180;
            int x1 = x + (int)((radius - 10) * Math.cos(angle));
            int y1 = y + (int)((radius - 10) * Math.sin(angle));
            g2d.drawLine(x, y, x1, y1);
        }
    }

    @Override
    public BufferedImage generateFront(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Background
        drawBackground(g2d, CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // Header
        drawHeader(g2d, data);

        // Photo
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawMainPhoto(g2d, photo);
            }
        }

        // License fields
        drawIndianFields(g2d, data);

        // Ghost image
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawGhostImage(g2d, photo);
            }
        }

        g2d.dispose();
        return card;
    }

    private void drawIndianFields(Graphics2D g2d, LicenseData data) {
        int x = DATA_START_X;
        int y = DATA_START_Y - 20;

        // License number (format: STATE-RTO-YEAR-NUMBER)
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("DL NO / डीएल नं", x, y);

        g2d.setFont(new Font("Courier New", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString(data.getLicenseNumber(), x, y + 22);
        y += 50;

        // Name
        drawBilingualField(g2d, "NAME / नाम", data.getLastName() + " " + data.getFirstName(), x, y);
        y += DATA_LINE_HEIGHT;

        // Son/Daughter/Wife of (Guardian name - using middle name as placeholder)
        String guardian = data.getMiddleName() != null ? data.getMiddleName() : "";
        drawBilingualField(g2d, "S/D/W OF / पुत्र/पुत्री/पत्नी", guardian.toUpperCase(), x, y);
        y += DATA_LINE_HEIGHT;

        // Address
        String address = data.getAddress() != null ? data.getAddress() : "";
        if (data.getCity() != null) address += ", " + data.getCity();
        drawBilingualField(g2d, "ADDRESS / पता", address.toUpperCase(), x, y);
        y += DATA_LINE_HEIGHT;

        // Date of Birth
        drawBilingualField(g2d, "DOB / जन्म तिथि", formatDate(data.getDob()), x, y);
        y += DATA_LINE_HEIGHT;

        // Blood Group and Gender on same line
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("BG / रक्त समूह", x, y);
        g2d.drawString("SEX / लिंग", x + 120, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("O+", x, y + 18); // Placeholder blood group
        g2d.drawString(data.getGender() != null ? data.getGender() : "M", x + 120, y + 18);
        y += 40;

        // Validity dates
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("VALID FROM / से मान्य", x, y);
        g2d.drawString("VALID TILL / तक मान्य", x + 180, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString(formatDate(data.getIssueDate()), x, y + 18);
        g2d.drawString(formatDate(data.getExpirationDate()), x + 180, y + 18);
        y += 45;

        // Vehicle class
        drawBilingualField(g2d, "COV / वाहन वर्ग", data.getLicenseClass() != null ? data.getLicenseClass() : "LMV", x, y);

        // RTO info
        y += DATA_LINE_HEIGHT + 10;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));
        String rto = subJurisdiction != null ? "RTO: " + subJurisdiction.getCode() : "";
        g2d.drawString("ISSUING AUTHORITY: " + rto, x, y);
    }

    private void drawBilingualField(Graphics2D g2d, String label, String value, int x, int y) {
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString(value != null ? value.toUpperCase() : "", x, y + 18);
    }

    @Override
    protected void drawBackHeader(Graphics2D g2d) {
        // Tricolor stripe
        int stripeHeight = 8;
        g2d.setColor(INDIA_SAFFRON);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, stripeHeight);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, stripeHeight, CardRenderer.CARD_WIDTH, stripeHeight);
        g2d.setColor(INDIA_GREEN);
        g2d.fillRect(0, stripeHeight * 2, CardRenderer.CARD_WIDTH, stripeHeight);

        // Header bar
        g2d.setColor(ASHOKA_BLUE);
        g2d.fillRect(0, stripeHeight * 3, CardRenderer.CARD_WIDTH, 50);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("DRIVING LICENCE - INDIA", 50, stripeHeight * 3 + 35);
    }

    @Override
    protected void drawComplianceText(Graphics2D g2d) {
        int y = 480;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));

        String[] lines = {
            "This licence is issued under the Motor Vehicles Act, 1988.",
            "इस लाइसेंस को मोटर वाहन अधिनियम, 1988 के तहत जारी किया गया है।",
            "Issued by the Regional Transport Office (RTO)."
        };

        for (String line : lines) {
            g2d.drawString(line, 50, y);
            y += 15;
        }
    }
}
