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
 * Australian Driver Licence template.
 * Supports all states and territories with state-specific designs.
 */
public class AustraliaTemplate extends InternationalCardTemplate {

    // State colors
    private static final Color NSW_BLUE = new Color(0, 47, 108);
    private static final Color VIC_BLUE = new Color(0, 50, 95);
    private static final Color QLD_MAROON = new Color(115, 0, 46);
    private static final Color WA_BLACK = new Color(0, 0, 0);
    private static final Color SA_RED = new Color(204, 0, 0);
    private static final Color TAS_GREEN = new Color(0, 86, 63);
    private static final Color ACT_BLUE = new Color(0, 33, 71);
    private static final Color NT_ORANGE = new Color(198, 83, 37);

    public AustraliaTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                             SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    public AustraliaTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                             SecurityFeatureGenerator securityGenerator,
                             SubJurisdiction subJurisdiction) {
        super(renderer, imageProcessor, securityGenerator, subJurisdiction);
    }

    @Override
    public String getTemplateName() {
        if (subJurisdiction != null) {
            return subJurisdiction.getName().toUpperCase();
        }
        return "AUSTRALIA";
    }

    @Override
    public Color getPrimaryColor() {
        if (subJurisdiction == null) return NSW_BLUE;
        return switch (subJurisdiction) {
            case AU_NSW -> NSW_BLUE;
            case AU_VIC -> VIC_BLUE;
            case AU_QLD -> QLD_MAROON;
            case AU_WA -> WA_BLACK;
            case AU_SA -> SA_RED;
            case AU_TAS -> TAS_GREEN;
            case AU_ACT -> ACT_BLUE;
            case AU_NT -> NT_ORANGE;
            default -> NSW_BLUE;
        };
    }

    @Override
    public Color getSecondaryColor() {
        return new Color(200, 200, 200);
    }

    @Override
    public Locale getLocale() {
        return new Locale("en", "AU");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "DRIVER LICENCE";
    }

    @Override
    public BarcodeType getBarcodeType() {
        // Australian licenses typically don't have visible barcodes
        // Some states use RFID chips instead
        if (subJurisdiction != null) {
            return switch (subJurisdiction) {
                case AU_QLD, AU_VIC -> BarcodeType.NONE; // RFID chip
                default -> BarcodeType.PDF417;
            };
        }
        return BarcodeType.PDF417;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Light gradient background
        renderer.drawGradientBackground(g2d,
            new Color(255, 255, 255),
            new Color(240, 240, 245),
            width, height);

        // State-specific design elements
        drawStateDesign(g2d, width, height);
    }

    private void drawStateDesign(Graphics2D g2d, int width, int height) {
        // Vertical color bar on left side
        g2d.setColor(getPrimaryColor());
        g2d.fillRect(0, 0, 15, height);

        // Top header bar
        g2d.fillRect(0, 0, width, 100);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // State name and document type
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 28));

        String stateName = subJurisdiction != null ? subJurisdiction.getName() : "AUSTRALIA";
        g2d.drawString(stateName.toUpperCase(), 80, 45);

        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(getLocalizedDocumentName(), 80, 75);

        // State coat of arms placeholder
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillOval(820, 15, 70, 70);
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
        drawAustralianFields(g2d, data);

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

    private void drawAustralianFields(Graphics2D g2d, LicenseData data) {
        int x = DATA_START_X;
        int y = DATA_START_Y;

        // License number
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("LICENCE NO", x, y - 5);

        g2d.setFont(new Font("Courier New", Font.BOLD, 22));
        g2d.setColor(Color.BLACK);
        g2d.drawString(data.getLicenseNumber(), x, y + 20);
        y += 50;

        // Full name
        drawDataField(g2d, "SURNAME", data.getLastName() != null ? data.getLastName().toUpperCase() : "", x, y);
        y += DATA_LINE_HEIGHT;

        String givenNames = data.getFirstName() != null ? data.getFirstName().toUpperCase() : "";
        if (data.getMiddleName() != null) {
            givenNames += " " + data.getMiddleName().toUpperCase();
        }
        drawDataField(g2d, "GIVEN NAMES", givenNames, x, y);
        y += DATA_LINE_HEIGHT;

        // Address
        String address = data.getAddress() != null ? data.getAddress().toUpperCase() : "";
        drawDataField(g2d, "ADDRESS", address, x, y);
        y += DATA_LINE_HEIGHT;

        String cityLine = "";
        if (data.getCity() != null) cityLine += data.getCity().toUpperCase();
        if (subJurisdiction != null) cityLine += " " + subJurisdiction.getCode();
        if (data.getZipCode() != null) cityLine += " " + data.getZipCode();
        g2d.setFont(DATA_FONT);
        g2d.setColor(Color.BLACK);
        g2d.drawString(cityLine, x, y + 20);
        y += DATA_LINE_HEIGHT + 10;

        // DOB
        drawDataField(g2d, "DATE OF BIRTH", formatDate(data.getDob()), x, y);
        y += DATA_LINE_HEIGHT;

        // Physical
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(80, 80, 80));

        drawCompactField(g2d, "SEX", data.getGender(), x, y);
        drawCompactField(g2d, "HEIGHT", formatHeight(data.getHeight()), x + 100, y);
        y += 35;

        // Expiry
        drawDataField(g2d, "EXPIRY DATE", formatDate(data.getExpirationDate()), x, y);

        // Class
        drawDataField(g2d, "CLASS", data.getLicenseClass() != null ? data.getLicenseClass() : "C", x + 200, y);

        // Card number (separate from license number)
        y += DATA_LINE_HEIGHT + 20;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("CARD NO: " + (data.getDocumentDiscriminator() != null ? data.getDocumentDiscriminator() : ""), x, y);
    }

    @Override
    protected void drawBackHeader(Graphics2D g2d) {
        g2d.setColor(getPrimaryColor());
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 60);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String headerText = (subJurisdiction != null ? subJurisdiction.getName() : "AUSTRALIA") + " DRIVER LICENCE";
        g2d.drawString(headerText, 50, 40);
    }

    @Override
    protected boolean hasMagneticStripe() {
        // Some Australian licenses don't have magnetic stripe
        if (subJurisdiction != null) {
            return switch (subJurisdiction) {
                case AU_QLD, AU_VIC -> false;
                default -> true;
            };
        }
        return true;
    }
}
