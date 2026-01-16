package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

/**
 * Illinois Driver's License Card Template
 * Design based on Illinois Secretary of State standards
 */
public class IllinoisTemplate extends CardTemplate {

    // Illinois Colors
    private static final Color IL_BLUE = new Color(0, 43, 127);
    private static final Color IL_ORANGE = new Color(233, 131, 0);
    private static final Color IL_WHITE = new Color(255, 255, 255);
    private static final Color IL_CREAM = new Color(250, 248, 243);
    private static final Color IL_RED = new Color(204, 0, 0);

    public IllinoisTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "ILLINOIS";
    }

    @Override
    public Color getPrimaryColor() {
        return IL_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return IL_ORANGE;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Gradient from cream to light blue
        renderer.drawGradientBackground(g2d, IL_CREAM, new Color(235, 245, 255), width, height);

        // Abraham Lincoln silhouette watermark
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
        drawLincolnSilhouette(g2d, width - 150, height - 280);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Guilloche pattern
        securityGenerator.drawGuillochePattern(g2d, 0, 0, width, height, IL_ORANGE);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Blue header with orange accent
        GradientPaint headerGradient = new GradientPaint(
            0, 0, IL_BLUE,
            CardRenderer.CARD_WIDTH, 0, new Color(0, 63, 157)
        );
        g2d.setPaint(headerGradient);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 160);

        // Orange accent stripe
        g2d.setColor(IL_ORANGE);
        g2d.fillRect(0, 155, CardRenderer.CARD_WIDTH, 5);

        // "ILLINOIS" text
        g2d.setColor(IL_WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("ILLINOIS", 200, 80);

        // "DRIVER'S LICENSE" text
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER'S LICENSE", 200, 125);

        // Organ Donor indicator
        if (Boolean.TRUE.equals(data.getOrganDonor())) {
            drawOrganDonorIndicator(g2d, 920, 150);
        }
    }

    private void drawLincolnSilhouette(Graphics2D g2d, int x, int y) {
        Color originalColor = g2d.getColor();
        g2d.setColor(new Color(IL_BLUE.getRed(), IL_BLUE.getGreen(), IL_BLUE.getBlue(), 100));

        // Simplified Lincoln profile silhouette
        // Hat
        g2d.fillRect(x, y, 60, 15);
        g2d.fillRect(x + 15, y - 35, 30, 35);

        // Head and beard
        int[] xPoints = {x + 15, x + 45, x + 50, x + 45, x + 20, x + 10};
        int[] yPoints = {y, y + 10, y + 40, y + 80, y + 75, y + 40};
        g2d.fillPolygon(xPoints, yPoints, 6);

        g2d.setColor(originalColor);
    }

    private void drawOrganDonorIndicator(Graphics2D g2d, int x, int y) {
        Color originalColor = g2d.getColor();

        // Red circle with white "DONOR" text
        g2d.setColor(IL_RED);
        g2d.fillOval(x, y, 70, 70);

        g2d.setColor(IL_WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("DONOR", x + 10, y + 45);

        g2d.setColor(originalColor);
    }
}
