package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.util.Locale;

/**
 * French driving licence (Permis de Conduire) template.
 * Follows EU directive standards with French-specific elements.
 */
public class FranceTemplate extends EUTemplate {

    private static final Color FRANCE_BLUE = new Color(0, 85, 164);
    private static final Color FRANCE_RED = new Color(239, 65, 53);
    private static final Color FRANCE_WHITE = Color.WHITE;

    public FranceTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                          SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "FRANCE";
    }

    @Override
    public Color getPrimaryColor() {
        return FRANCE_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return FRANCE_RED;
    }

    @Override
    public Locale getLocale() {
        return Locale.FRANCE;
    }

    @Override
    public String getLocalizedDocumentName() {
        return "PERMIS DE CONDUIRE";
    }

    @Override
    protected String getDefaultIssuingAuthority() {
        return "RÉPUBLIQUE FRANÇAISE";
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // EU flag with "F" for France
        drawEUFlag(g2d, 20, 20, 100, 70);
        drawCountryCode(g2d, "F");

        // Country name
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(FRANCE_BLUE);
        g2d.drawString("RÉPUBLIQUE FRANÇAISE", 140, 50);

        // Document type
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(getLocalizedDocumentName(), 140, 80);
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Prénom";
            case "lastName" -> "Nom";
            case "dob" -> "Date de naissance";
            case "address" -> "Adresse";
            case "city" -> "Ville";
            case "licenseNumber" -> "Numéro";
            case "issueDate" -> "Date de délivrance";
            case "expiryDate" -> "Date d'expiration";
            case "class" -> "Catégorie";
            case "categories" -> "Catégories";
            default -> super.getLocalizedLabel(key);
        };
    }

    @Override
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Marianne symbol watermark (simplified)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(FRANCE_BLUE);
        g2d.drawString("RF", 700, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
