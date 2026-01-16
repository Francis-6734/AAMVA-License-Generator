package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.util.Locale;

/**
 * German driving licence (Führerschein) template.
 * Follows EU directive standards with German-specific elements.
 */
public class GermanyTemplate extends EUTemplate {

    private static final Color GERMANY_BLACK = new Color(0, 0, 0);
    private static final Color GERMANY_RED = new Color(221, 0, 0);
    private static final Color GERMANY_GOLD = new Color(255, 206, 0);

    public GermanyTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                           SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "GERMANY";
    }

    @Override
    public Color getPrimaryColor() {
        return GERMANY_BLACK;
    }

    @Override
    public Color getSecondaryColor() {
        return GERMANY_RED;
    }

    @Override
    public Locale getLocale() {
        return Locale.GERMANY;
    }

    @Override
    public String getLocalizedDocumentName() {
        return "FÜHRERSCHEIN";
    }

    @Override
    protected String getDefaultIssuingAuthority() {
        return "BUNDESREPUBLIK DEUTSCHLAND";
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // EU flag with "D" for Germany
        drawEUFlag(g2d, 20, 20, 100, 70);
        drawCountryCode(g2d, "D");

        // Country name
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(GERMANY_BLACK);
        g2d.drawString("BUNDESREPUBLIK DEUTSCHLAND", 140, 50);

        // Document type
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(getLocalizedDocumentName(), 140, 80);
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Vorname";
            case "lastName" -> "Name";
            case "dob" -> "Geburtsdatum";
            case "address" -> "Anschrift";
            case "city" -> "Wohnort";
            case "licenseNumber" -> "Nr.";
            case "issueDate" -> "Ausstellungsdatum";
            case "expiryDate" -> "Gültig bis";
            case "class" -> "Klasse";
            case "categories" -> "Klassen";
            default -> super.getLocalizedLabel(key);
        };
    }

    @Override
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // German eagle watermark (simplified representation)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(GERMANY_BLACK);
        g2d.drawString("DE", 700, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
