package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.util.Locale;

/**
 * Spanish driving licence (Permiso de Conducción) template.
 * Follows EU directive standards with Spanish-specific elements.
 */
public class SpainTemplate extends EUTemplate {

    private static final Color SPAIN_RED = new Color(198, 11, 30);
    private static final Color SPAIN_YELLOW = new Color(255, 196, 0);

    public SpainTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                         SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "SPAIN";
    }

    @Override
    public Color getPrimaryColor() {
        return SPAIN_RED;
    }

    @Override
    public Color getSecondaryColor() {
        return SPAIN_YELLOW;
    }

    @Override
    public Locale getLocale() {
        return new Locale("es", "ES");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "PERMISO DE CONDUCCIÓN";
    }

    @Override
    protected String getDefaultIssuingAuthority() {
        return "REINO DE ESPAÑA";
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // EU flag with "E" for Spain
        drawEUFlag(g2d, 20, 20, 100, 70);
        drawCountryCode(g2d, "E");

        // Country name
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.setColor(SPAIN_RED);
        g2d.drawString("REINO DE ESPAÑA", 140, 50);

        // Document type
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(getLocalizedDocumentName(), 140, 80);
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Nombre";
            case "lastName" -> "Apellidos";
            case "dob" -> "Fecha de nacimiento";
            case "address" -> "Domicilio";
            case "city" -> "Localidad";
            case "licenseNumber" -> "Número";
            case "issueDate" -> "Fecha de expedición";
            case "expiryDate" -> "Fecha de caducidad";
            case "class" -> "Clase";
            case "categories" -> "Categorías";
            default -> super.getLocalizedLabel(key);
        };
    }

    @Override
    protected void drawRegionalSecurityFeatures(Graphics2D g2d, LicenseData data) {
        // Spanish coat of arms watermark (simplified)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(SPAIN_RED);
        g2d.drawString("ES", 700, 400);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
