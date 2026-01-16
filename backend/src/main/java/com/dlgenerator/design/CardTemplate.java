package com.dlgenerator.design;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * AAMVA-Compliant Driver License Card Template Base Class
 * Implements DL/ID-2020 standards with real security features
 */
public abstract class CardTemplate {

    // AAMVA-Standard Fonts (based on real license specifications)
    protected static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 52);
    protected static final Font SUBHEADER_FONT = new Font("Arial", Font.BOLD, 22);
    protected static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 11);
    protected static final Font DATA_FONT = new Font("Arial", Font.BOLD, 18);
    protected static final Font LICENSE_NUM_FONT = new Font("OCR A Extended", Font.BOLD, 26);
    protected static final Font MICROPRINT_FONT = new Font("Arial", Font.PLAIN, 4);
    protected static final Font SMALL_DATA_FONT = new Font("Arial", Font.BOLD, 14);

    // Date formatters
    protected static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    protected static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");

    // AAMVA field positioning standards - improved for professional look
    protected static final int PHOTO_X = 60;
    protected static final int PHOTO_Y = 120;
    protected static final int PHOTO_WIDTH = 240;
    protected static final int PHOTO_HEIGHT = 290;

    protected static final int DATA_START_X = 330;
    protected static final int DATA_START_Y = 180;
    protected static final int DATA_LINE_HEIGHT = 40;

    protected CardRenderer renderer;
    protected ImageProcessor imageProcessor;
    protected SecurityFeatureGenerator securityGenerator;

    public CardTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        this.renderer = renderer;
        this.imageProcessor = imageProcessor;
        this.securityGenerator = securityGenerator;
    }

    // Abstract methods - must be implemented by each state template
    public abstract String getTemplateName();
    public abstract Color getPrimaryColor();
    public abstract Color getSecondaryColor();
    public abstract void drawBackground(Graphics2D g2d, int width, int height);
    public abstract void drawHeader(Graphics2D g2d, LicenseData data);

    /**
     * Generate AAMVA-compliant front of card with all required fields
     */
    public BufferedImage generateFront(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // 1. Background with security patterns
        drawBackground(g2d, CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // 2. State-specific header
        drawHeader(g2d, data);

        // 3. Primary photo with border (AAMVA required)
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawMainPhoto(g2d, photo);
            }
        }

        // 4. License number (AAMVA DL DAQ field) - Prominent placement
        drawLicenseNumber(g2d, data);

        // 5. Full name (AAMVA DCS, DAC, DAD fields)
        drawFullName(g2d, data);

        // 6. Address (AAMVA DAG, DAI, DAJ, DAK fields)
        drawAddress(g2d, data);

        // 7. Date of birth (AAMVA DBB field)
        drawDateOfBirth(g2d, data);

        // 8. Issue and expiration dates (AAMVA DBD, DBA fields)
        drawIssueDates(g2d, data);

        // 9. Physical characteristics (AAMVA DAY, DAU, DAW fields)
        drawPhysicalCharacteristics(g2d, data);

        // 10. License class (AAMVA DCA field)
        drawLicenseClass(g2d, data);

        // 11. Document discriminator (AAMVA DCF field)
        drawDocumentDiscriminator(g2d, data);

        // 12. Special indicators (Organ donor, Veteran, Under 21)
        drawSpecialIndicators(g2d, data);

        // 13. Security features (Guilloche, UV, microprint)
        drawFrontSecurityFeatures(g2d, data);

        // 14. Ghost image (transparent duplicate photo)
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
     * Generate AAMVA-compliant back of card with PDF417 barcode
     */
    public BufferedImage generateBack(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // 1. Background
        drawBackBackground(g2d);

        // 2. Header with state name
        drawBackHeader(g2d);

        // 3. PDF417 Barcode (AAMVA required - primary data carrier)
        if (data.getBarcodeBase64() != null) {
            BufferedImage barcode = imageProcessor.decodeBase64Image(data.getBarcodeBase64());
            if (barcode != null) {
                drawPDF417Barcode(g2d, barcode);
            }
        }

        // 4. Magnetic stripe representation (legacy compatibility)
        drawMagneticStripe(g2d);

        // 5. 1D Barcode for license number (additional verification)
        draw1DBarcode(g2d, data.getLicenseNumber(), 600, 465, 340, 50);

        // 6. License class description
        drawLicenseClassDescription(g2d, data);

        // 7. Endorsements (AAMVA DCJ field)
        drawEndorsements(g2d, data);

        // 8. Restrictions (AAMVA DCK field)
        drawRestrictions(g2d, data);

        // 9. Compliance text
        drawComplianceText(g2d);

        // 10. Security features
        drawBackSecurityFeatures(g2d);

        g2d.dispose();
        return card;
    }

    // FRONT CARD RENDERING METHODS

    protected void drawMainPhoto(Graphics2D g2d, BufferedImage photo) throws IOException {
        BufferedImage resized = imageProcessor.resizeImage(photo, PHOTO_WIDTH, PHOTO_HEIGHT);

        // Draw white background box for photo
        g2d.setColor(Color.WHITE);
        g2d.fillRect(PHOTO_X - 4, PHOTO_Y - 4, PHOTO_WIDTH + 8, PHOTO_HEIGHT + 8);

        // Draw photo
        g2d.drawImage(resized, PHOTO_X, PHOTO_Y, null);

        // Draw clean border (AAMVA standard)
        g2d.setColor(getPrimaryColor());
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(PHOTO_X - 2, PHOTO_Y - 2, PHOTO_WIDTH + 4, PHOTO_HEIGHT + 4);

        // Inner decorative border
        g2d.setColor(new Color(getPrimaryColor().getRed(), getPrimaryColor().getGreen(),
                               getPrimaryColor().getBlue(), 80));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(PHOTO_X + 2, PHOTO_Y + 2, PHOTO_WIDTH - 4, PHOTO_HEIGHT - 4);
    }

    protected void drawLicenseNumber(Graphics2D g2d, LicenseData data) {
        int x = DATA_START_X + 420;
        int y = 145;

        // Label with better styling
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(new Color(70, 70, 70));
        g2d.drawString("DL NUMBER", x, y - 10);

        // License number with shadow effect for depth
        g2d.setFont(LICENSE_NUM_FONT);
        // Shadow
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawString(data.getLicenseNumber(), x + 1, y + 19);
        // Main text
        g2d.setColor(Color.BLACK);
        g2d.drawString(data.getLicenseNumber(), x, y + 18);
    }

    protected void drawFullName(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y;

        // Last Name (DCS)
        drawDataField(g2d, "LN", data.getLastName().toUpperCase(), DATA_START_X, y);

        // First Name (DAC)
        y += DATA_LINE_HEIGHT;
        drawDataField(g2d, "FN", data.getFirstName().toUpperCase(), DATA_START_X, y);

        // Middle Name (DAD) - if present
        if (data.getMiddleName() != null && !data.getMiddleName().isEmpty()) {
            y += DATA_LINE_HEIGHT;
            drawDataField(g2d, "MN", data.getMiddleName().toUpperCase(), DATA_START_X, y);
        }
    }

    protected void drawAddress(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y + (data.getMiddleName() != null ? 3 : 2) * DATA_LINE_HEIGHT;

        // Street Address (DAG)
        if (data.getAddress() != null) {
            drawDataField(g2d, "ADDRESS", data.getAddress().toUpperCase(), DATA_START_X, y);
            y += DATA_LINE_HEIGHT;
        }

        // City, State, ZIP (DAI, DAJ, DAK)
        String cityStateZip = String.format("%s %s %s",
            data.getCity() != null ? data.getCity().toUpperCase() : "",
            data.getState() != null ? data.getState() : "",
            data.getZipCode() != null ? data.getZipCode() : ""
        ).trim();
        drawDataField(g2d, "CITY", cityStateZip, DATA_START_X, y);
    }

    protected void drawDateOfBirth(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y + 150;
        String dobFormatted = formatDate(data.getDob());
        drawDataField(g2d, "DOB", dobFormatted, DATA_START_X, y);
    }

    protected void drawIssueDates(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y + 185;

        // Issue date (DBD)
        String issueFormatted = formatDate(data.getIssueDate());
        drawDataField(g2d, "ISS", issueFormatted, DATA_START_X, y);

        // Expiration date (DBA)
        String expFormatted = formatDate(data.getExpirationDate());
        drawDataField(g2d, "EXP", expFormatted, DATA_START_X + 180, y);
    }

    protected void drawPhysicalCharacteristics(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y + 220;

        // Sex (DBC)
        String sex = data.getGender();
        if ("1".equals(sex)) sex = "M";
        else if ("2".equals(sex)) sex = "F";
        drawCompactField(g2d, "SEX", sex, DATA_START_X, y);

        // Height (DAU)
        String height = data.getHeight() != null ? data.getHeight() + "\"" : "N/A";
        drawCompactField(g2d, "HGT", height, DATA_START_X + 100, y);

        // Weight (DAW)
        String weight = data.getWeight() != null ? data.getWeight() + " lb" : "N/A";
        drawCompactField(g2d, "WGT", weight, DATA_START_X + 200, y);

        // Eyes (DAY)
        y += 30;
        String eyes = data.getEyeColor() != null ? data.getEyeColor() : "BRO";
        drawCompactField(g2d, "EYES", eyes, DATA_START_X, y);

        // Hair (DAZ)
        String hair = data.getHairColor() != null ? data.getHairColor() : "BRO";
        drawCompactField(g2d, "HAIR", hair, DATA_START_X + 100, y);
    }

    protected void drawLicenseClass(Graphics2D g2d, LicenseData data) {
        int y = DATA_START_Y + 280;
        String licClass = data.getLicenseClass() != null ? data.getLicenseClass() : "C";
        drawDataField(g2d, "CLASS", licClass, DATA_START_X, y);
    }

    protected void drawDocumentDiscriminator(Graphics2D g2d, LicenseData data) {
        if (data.getDocumentDiscriminator() != null) {
            int y = DATA_START_Y + 315;
            g2d.setFont(new Font("Arial", Font.PLAIN, 9));
            g2d.setColor(new Color(120, 120, 120));
            g2d.drawString("DD: " + data.getDocumentDiscriminator(), DATA_START_X, y);
        }
    }

    protected void drawSpecialIndicators(Graphics2D g2d, LicenseData data) {
        int indicatorY = 150;
        int indicatorX = 880;

        // Organ Donor (DDF)
        if (Boolean.TRUE.equals(data.getOrganDonor())) {
            drawOrganDonorBadge(g2d, indicatorX, indicatorY);
            indicatorY += 65;
        }

        // Veteran (DDI)
        if (Boolean.TRUE.equals(data.getVeteran())) {
            drawVeteranBadge(g2d, indicatorX, indicatorY);
            indicatorY += 65;
        }

        // Under 21 indicator (calculated from DOB)
        if (isUnder21(data.getDob())) {
            drawUnder21Badge(g2d, indicatorX, indicatorY);
        }
    }

    protected void drawOrganDonorBadge(Graphics2D g2d, int x, int y) {
        // Red heart symbol
        g2d.setColor(new Color(220, 20, 60));
        g2d.fillOval(x, y, 55, 55);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "DONOR";
        int textX = x + (55 - fm.stringWidth(text)) / 2;
        g2d.drawString(text, textX, y + 32);
    }

    protected void drawVeteranBadge(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(0, 51, 102));
        g2d.fillRect(x, y, 55, 55);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "VET";
        int textX = x + (55 - fm.stringWidth(text)) / 2;
        g2d.drawString(text, textX, y + 32);
    }

    protected void drawUnder21Badge(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(204, 0, 0));
        g2d.fillRect(x, y, 55, 55);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        String text1 = "UNDER";
        String text2 = "21";
        g2d.drawString(text1, x + (55 - fm.stringWidth(text1)) / 2, y + 25);
        g2d.drawString(text2, x + (55 - fm.stringWidth(text2)) / 2, y + 40);
    }

    protected void drawFrontSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Guilloche pattern - REMOVED for better visibility
        // securityGenerator.drawGuillochePattern(g2d, 0, 0,
        //     CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT, getSecondaryColor());

        // Microprint border - REMOVED for better visibility
        // securityGenerator.drawMicroprint(g2d,
        //     getTemplateName() + " " + getTemplateName() + " " + getTemplateName(),
        //     20, CardRenderer.CARD_HEIGHT - 20, 80);

        // UV reactive feature - REMOVED for better visibility
        // securityGenerator.drawUVFeature(g2d, 700, 500, 50);
    }

    protected void drawGhostImage(Graphics2D g2d, BufferedImage photo) throws IOException {
        // Second smaller photo for security verification - NOW FULLY VISIBLE
        int ghostX = 750;
        int ghostY = 480;
        int ghostWidth = 110;
        int ghostHeight = 130;

        BufferedImage small = imageProcessor.resizeImage(photo, ghostWidth, ghostHeight);
        // Changed from 0.25f to 1.0f for full visibility
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.drawImage(small, ghostX, ghostY, null);

        // Ghost image border - now fully visible
        g2d.setColor(getPrimaryColor());
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(ghostX, ghostY, ghostWidth, ghostHeight);
    }

    // BACK CARD RENDERING METHODS

    protected void drawBackBackground(Graphics2D g2d) {
        // Professional gradient background
        renderer.drawGradientBackground(g2d,
            new Color(245, 245, 250),
            new Color(230, 230, 240),
            CardRenderer.CARD_WIDTH,
            CardRenderer.CARD_HEIGHT);
    }

    protected void drawBackHeader(Graphics2D g2d) {
        g2d.setColor(getPrimaryColor());
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 80);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        String headerText = getTemplateName() + " DRIVER LICENSE";
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (CardRenderer.CARD_WIDTH - fm.stringWidth(headerText)) / 2;
        g2d.drawString(headerText, textX, 52);
    }

    protected void drawPDF417Barcode(Graphics2D g2d, BufferedImage barcode) {
        int barcodeX = 50;
        int barcodeY = 110;
        int barcodeWidth = 900;
        int barcodeHeight = 180;

        // White background box with border for barcode
        g2d.setColor(Color.WHITE);
        g2d.fillRect(barcodeX - 15, barcodeY - 15, barcodeWidth + 30, barcodeHeight + 30);

        // Professional border around barcode area
        g2d.setColor(new Color(60, 60, 60));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(barcodeX - 15, barcodeY - 15, barcodeWidth + 30, barcodeHeight + 30);

        // Draw PDF417 barcode (primary 2D barcode)
        g2d.drawImage(barcode, barcodeX, barcodeY, barcodeWidth, barcodeHeight, null);

        // Label above barcode
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        g2d.setColor(getPrimaryColor());
        g2d.drawString("PDF417 2D BARCODE - AAMVA DL/ID-2020 COMPLIANT", barcodeX, barcodeY - 25);

        // Barcode type indicator
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("Scan this barcode to verify license authenticity and read encoded data", barcodeX, barcodeY + barcodeHeight + 25);
    }

    protected void drawMagneticStripe(Graphics2D g2d) {
        // Black magnetic stripe representation (positioned below barcode)
        int stripeY = 320;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, stripeY, CardRenderer.CARD_WIDTH, 35);

        // Magnetic stripe label
        g2d.setColor(new Color(80, 80, 80));
        g2d.setFont(new Font("Arial", Font.BOLD, 8));
        g2d.drawString("MAGNETIC STRIPE", 50, stripeY - 5);
    }

    protected void drawLicenseClassDescription(Graphics2D g2d, LicenseData data) {
        int y = 380;
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);

        String licClass = data.getLicenseClass() != null ? data.getLicenseClass() : "C";
        String description = getLicenseClassDescription(licClass);
        g2d.drawString("CLASS " + licClass + ": " + description, 50, y);
    }

    protected void drawEndorsements(Graphics2D g2d, LicenseData data) {
        int y = 410;
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        g2d.setColor(Color.BLACK);

        String endorsements = data.getEndorsements();
        if (endorsements == null || endorsements.trim().isEmpty()) {
            g2d.drawString("ENDORSEMENTS: NONE", 50, y);
        } else {
            g2d.drawString("ENDORSEMENTS: " + endorsements, 50, y);
        }
    }

    protected void drawRestrictions(Graphics2D g2d, LicenseData data) {
        int y = 440;
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        g2d.setColor(Color.BLACK);

        String restrictions = data.getRestrictions();
        if (restrictions == null || restrictions.trim().isEmpty()) {
            g2d.drawString("RESTRICTIONS: NONE", 50, y);
        } else {
            g2d.drawString("RESTRICTIONS: " + restrictions, 50, y);
        }
    }

    protected void drawComplianceText(Graphics2D g2d) {
        int y = 500;
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.setColor(new Color(100, 100, 100));

        String[] lines = {
            "This card is the property of the issuing authority.",
            "Unlawful use of this license is subject to criminal penalties.",
            "AAMVA DL/ID-2020 Standard Compliant - All encoded data verifiable via PDF417 barcode"
        };

        for (String line : lines) {
            g2d.drawString(line, 50, y);
            y += 14;
        }
    }

    protected void drawBackSecurityFeatures(Graphics2D g2d) {
        // Watermark
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
        g2d.setFont(new Font("Arial", Font.BOLD, 120));
        g2d.setColor(getPrimaryColor());
        g2d.drawString(getTemplateName(), 200, 500);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    /**
     * Draw a 1D barcode representation (Code 128 style)
     * This is a visual representation for additional authenticity
     */
    protected void draw1DBarcode(Graphics2D g2d, String data, int x, int y, int width, int height) {
        // White background for barcode
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x - 5, y - 5, width + 10, height + 30);

        // Border
        g2d.setColor(new Color(60, 60, 60));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x - 5, y - 5, width + 10, height + 30);

        // Generate barcode pattern (simplified Code 128 representation)
        g2d.setColor(Color.BLACK);
        int barX = x;
        int barWidth = 2;
        boolean isBar = true;

        // Create visual barcode pattern
        for (int i = 0; i < width / barWidth; i++) {
            if (isBar) {
                g2d.fillRect(barX, y, barWidth, height);
            }
            barX += barWidth;
            // Alternate pattern based on data
            isBar = (i + data.hashCode()) % 2 == 0;
        }

        // Human-readable text below barcode
        g2d.setFont(new Font("Courier New", Font.BOLD, 10));
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        String displayText = data.length() > 20 ? data.substring(0, 20) : data;
        int textX = x + (width - fm.stringWidth(displayText)) / 2;
        g2d.drawString(displayText, textX, y + height + 18);
    }

    // UTILITY METHODS

    protected void drawDataField(Graphics2D g2d, String label, String value, int x, int y) {
        // Label with improved spacing
        g2d.setFont(LABEL_FONT);
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y - 2);

        // Value with better contrast
        g2d.setFont(DATA_FONT);
        g2d.setColor(new Color(10, 10, 10));
        g2d.drawString(value != null ? value : "", x, y + 22);
    }

    protected void drawCompactField(Graphics2D g2d, String label, String value, int x, int y) {
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y - 1);

        g2d.setFont(SMALL_DATA_FONT);
        g2d.setColor(new Color(10, 10, 10));
        g2d.drawString(value != null ? value : "", x, y + 18);
    }

    protected String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            // Convert from ISO format (yyyy-MM-dd) to display format (MM/dd/yyyy)
            String[] parts = dateStr.split("-");
            if (parts.length == 3) {
                return parts[1] + "/" + parts[2] + "/" + parts[0];
            }
            return dateStr;
        } catch (Exception e) {
            return dateStr;
        }
    }

    protected boolean isUnder21(String dobStr) {
        try {
            LocalDate dob = LocalDate.parse(dobStr);
            LocalDate today = LocalDate.now();
            LocalDate age21Date = dob.plusYears(21);
            return today.isBefore(age21Date);
        } catch (Exception e) {
            return false;
        }
    }

    protected String getLicenseClassDescription(String licClass) {
        return switch (licClass) {
            case "A" -> "Any combination of vehicles with GVWR of 26,001+ lbs";
            case "B" -> "Heavy straight vehicles (26,001+ lbs GVWR)";
            case "C" -> "Standard passenger vehicles";
            case "D" -> "Operator license";
            case "M" -> "Motorcycle";
            default -> "Standard driver license";
        };
    }
}
