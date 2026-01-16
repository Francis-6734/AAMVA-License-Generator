package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.util.Locale;

/**
 * Italian driving licence (Patente di Guida) template.
 * Follows EU directive standards with Italian-specific elements.
 */
public class ItalyTemplate extends EUTemplate {

    private static final Color ITALY_GREEN = new Color(0, 146, 70);
    private static final Color ITALY_RED = new Color(206, 43, 55);
    private static final Color ITALY_WHITE = Color.WHITE;

    public ItalyTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                         SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "ITALY";
    }

    @Override
    public Color getPrimaryColor() {
        return ITALY_GREEN;
    }

    @Override
    public Color getSecondaryColor() {
        return ITALY_RED;
    }

    @Override
    public Locale getLocale() {
        return Locale.ITALY;
    }

    @Override
    public String getLocalizedDocumentName() {
        return "PATENTE DI GUIDA";
    }

    @Override
    protected String getDefaultIssuingAuthority() {
        return "REPUBBLICA ITALIANA";
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // EU flag with "I" for Italy
        drawEUFlag(g2d, 20, 20, 100, 70);
        drawCountryCode(g2d, "I");

        // Country name
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(ITALY_GREEN);
        g2d.drawString("REPUBBLICA ITALIANA", 140, 50);

        // Document type
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(getLocalizedDocumentName(), 140, 80);
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Nome";
            case "lastName" -> "Cognome";
            case "dob" -> "Data di nascita";
            case "address" -> "Indirizzo";
            case "city" -> "Comune";
            case "licenseNumber" -> "Numero";
            case "issueDate" -> "Data di rilascio";
            case "expiryDate" -> "Data di scadenza";
            case "class" -> "Categoria";
            case "categories" -> "Categorie";
            default -> super.getLocalizedLabel(key);
        };
    }

    @Override
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Italian emblem watermark (simplified)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(ITALY_GREEN);
        g2d.drawString("IT", 700, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
