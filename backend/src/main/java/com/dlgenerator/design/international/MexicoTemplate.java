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
 * Mexican Driving Licence (Licencia de Conducir) template.
 * Supports federal and state-level licenses.
 */
public class MexicoTemplate extends InternationalCardTemplate {

    private static final Color MEXICO_GREEN = new Color(0, 104, 71);
    private static final Color MEXICO_RED = new Color(206, 17, 38);
    private static final Color MEXICO_WHITE = Color.WHITE;

    public MexicoTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                          SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    public MexicoTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                          SecurityFeatureGenerator securityGenerator,
                          SubJurisdiction subJurisdiction) {
        super(renderer, imageProcessor, securityGenerator, subJurisdiction);
    }

    @Override
    public String getTemplateName() {
        if (subJurisdiction != null) {
            return subJurisdiction.getName().toUpperCase();
        }
        return "MEXICO";
    }

    @Override
    public Color getPrimaryColor() {
        return MEXICO_GREEN;
    }

    @Override
    public Color getSecondaryColor() {
        return MEXICO_RED;
    }

    @Override
    public Locale getLocale() {
        return new Locale("es", "MX");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "LICENCIA DE CONDUCIR";
    }

    @Override
    public BarcodeType getBarcodeType() {
        return BarcodeType.PDF417;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Light background with subtle pattern
        renderer.drawGradientBackground(g2d,
            new Color(255, 255, 255),
            new Color(245, 248, 245),
            width, height);

        // Mexican flag colors at top
        int stripeWidth = width / 3;
        g2d.setColor(MEXICO_GREEN);
        g2d.fillRect(0, 0, stripeWidth, 10);
        g2d.setColor(MEXICO_WHITE);
        g2d.fillRect(stripeWidth, 0, stripeWidth, 10);
        g2d.setColor(MEXICO_RED);
        g2d.fillRect(stripeWidth * 2, 0, stripeWidth + 1, 10);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        int y = 40;

        // Mexican coat of arms (simplified - eagle)
        drawMexicanEagle(g2d, 50, 55, 50);

        // Government text
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(MEXICO_GREEN);
        g2d.drawString("ESTADOS UNIDOS MEXICANOS", 120, y);

        // State name
        String stateName = subJurisdiction != null ? subJurisdiction.getName() : "FEDERAL";
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("GOBIERNO DEL ESTADO DE " + stateName.toUpperCase(), 120, y + 20);

        // Document title
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(MEXICO_GREEN);
        g2d.drawString(getLocalizedDocumentName(), 120, y + 55);
    }

    private void drawMexicanEagle(Graphics2D g2d, int x, int y, int size) {
        // Simplified eagle representation
        g2d.setColor(MEXICO_GREEN);
        g2d.fillOval(x - size/2, y - size/2, size, size);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("MX", x - 12, y + 7);
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
        drawMexicanFields(g2d, data);

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

    private void drawMexicanFields(Graphics2D g2d, LicenseData data) {
        int x = DATA_START_X;
        int y = DATA_START_Y - 10;

        // License number
        drawSpanishField(g2d, "NÚMERO DE LICENCIA", data.getLicenseNumber(), x, y, true);
        y += 50;

        // Name
        String fullName = (data.getFirstName() != null ? data.getFirstName() : "") + " " +
                         (data.getLastName() != null ? data.getLastName() : "");
        drawSpanishField(g2d, "NOMBRE COMPLETO", fullName.toUpperCase(), x, y, false);
        y += DATA_LINE_HEIGHT;

        // Address
        String address = data.getAddress() != null ? data.getAddress() : "";
        if (data.getCity() != null) address += ", " + data.getCity();
        if (subJurisdiction != null) address += ", " + subJurisdiction.getCode();
        drawSpanishField(g2d, "DOMICILIO", address.toUpperCase(), x, y, false);
        y += DATA_LINE_HEIGHT;

        // CURP (Mexican ID - using middle name as placeholder)
        String curp = data.getMiddleName() != null ? data.getMiddleName().toUpperCase() : "XXXX000000XXXXXX00";
        drawSpanishField(g2d, "CURP", curp, x, y, false);
        y += DATA_LINE_HEIGHT;

        // Date of Birth
        drawSpanishField(g2d, "FECHA DE NACIMIENTO", formatDate(data.getDob()), x, y, false);
        y += DATA_LINE_HEIGHT;

        // Physical characteristics
        g2d.setFont(new Font("Arial", Font.BOLD, 9));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("SEXO", x, y);
        g2d.drawString("ESTATURA", x + 80, y);
        g2d.drawString("TIPO SANGRE", x + 180, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString(data.getGender() != null ? (data.getGender().equals("M") ? "M" : "F") : "M", x, y + 16);
        g2d.drawString(formatHeight(data.getHeight()), x + 80, y + 16);
        g2d.drawString("O+", x + 180, y + 16); // Placeholder
        y += 40;

        // Validity dates
        g2d.setFont(new Font("Arial", Font.BOLD, 9));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("FECHA EXPEDICIÓN", x, y);
        g2d.drawString("VIGENCIA", x + 180, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString(formatDate(data.getIssueDate()), x, y + 16);
        g2d.drawString(formatDate(data.getExpirationDate()), x + 180, y + 16);
        y += 40;

        // License type
        drawSpanishField(g2d, "TIPO DE LICENCIA", data.getLicenseClass() != null ? data.getLicenseClass() : "A", x, y, false);
    }

    private void drawSpanishField(Graphics2D g2d, String label, String value, int x, int y, boolean large) {
        g2d.setFont(new Font("Arial", Font.BOLD, 9));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y);

        if (large) {
            g2d.setFont(new Font("Courier New", Font.BOLD, 20));
        } else {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString(value != null ? value : "", x, y + 18);
    }

    @Override
    protected void drawBackHeader(Graphics2D g2d) {
        // Mexican flag stripe
        int stripeWidth = CardRenderer.CARD_WIDTH / 3;
        g2d.setColor(MEXICO_GREEN);
        g2d.fillRect(0, 0, stripeWidth, 10);
        g2d.setColor(MEXICO_WHITE);
        g2d.fillRect(stripeWidth, 0, stripeWidth, 10);
        g2d.setColor(MEXICO_RED);
        g2d.fillRect(stripeWidth * 2, 0, stripeWidth + 1, 10);

        // Header
        g2d.setColor(MEXICO_GREEN);
        g2d.fillRect(0, 10, CardRenderer.CARD_WIDTH, 60);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.drawString("LICENCIA DE CONDUCIR - MÉXICO", 50, 50);
    }

    @Override
    protected void drawComplianceText(Graphics2D g2d) {
        int y = 500;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(100, 100, 100));

        String[] lines = {
            "Esta licencia es expedida conforme a la Ley de Tránsito.",
            "This license is issued in accordance with Traffic Law.",
            "Secretaría de Movilidad / Secretaría de Comunicaciones y Transportes"
        };

        for (String line : lines) {
            g2d.drawString(line, 50, y);
            y += 14;
        }
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Nombre";
            case "lastName" -> "Apellidos";
            case "dob" -> "Fecha de Nacimiento";
            case "address" -> "Domicilio";
            case "city" -> "Ciudad";
            case "licenseNumber" -> "Número";
            case "issueDate" -> "Fecha de Expedición";
            case "expiryDate" -> "Vigencia";
            case "class" -> "Tipo";
            default -> super.getLocalizedLabel(key);
        };
    }
}
