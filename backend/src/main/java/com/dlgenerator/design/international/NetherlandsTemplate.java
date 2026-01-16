package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.util.Locale;

/**
 * Dutch driving licence (Rijbewijs) template.
 * Follows EU directive standards with Dutch-specific elements.
 */
public class NetherlandsTemplate extends EUTemplate {

    private static final Color NL_RED = new Color(174, 28, 40);
    private static final Color NL_BLUE = new Color(33, 70, 139);
    private static final Color NL_ORANGE = new Color(255, 79, 0);

    public NetherlandsTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                               SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "NETHERLANDS";
    }

    @Override
    public Color getPrimaryColor() {
        return NL_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return NL_ORANGE;
    }

    @Override
    public Locale getLocale() {
        return new Locale("nl", "NL");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "RIJBEWIJS";
    }

    @Override
    protected String getDefaultIssuingAuthority() {
        return "KONINKRIJK DER NEDERLANDEN";
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // EU flag with "NL" for Netherlands
        drawEUFlag(g2d, 20, 20, 100, 70);
        drawCountryCode(g2d, "NL");

        // Country name
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(NL_BLUE);
        g2d.drawString("KONINKRIJK DER NEDERLANDEN", 140, 50);

        // Document type
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(getLocalizedDocumentName(), 140, 80);
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Voornaam";
            case "lastName" -> "Achternaam";
            case "dob" -> "Geboortedatum";
            case "address" -> "Adres";
            case "city" -> "Woonplaats";
            case "licenseNumber" -> "Nummer";
            case "issueDate" -> "Afgiftedatum";
            case "expiryDate" -> "Vervaldatum";
            case "class" -> "Categorie";
            case "categories" -> "Categorieën";
            default -> super.getLocalizedLabel(key);
        };
    }

    @Override
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Dutch lion watermark (simplified)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(NL_ORANGE);
        g2d.drawString("NL", 700, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
