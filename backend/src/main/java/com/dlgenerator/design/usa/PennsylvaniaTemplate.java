package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

/**
 * Pennsylvania Driver's License Card Template
 * Design based on PennDOT standards
 */
public class PennsylvaniaTemplate extends CardTemplate {

    // Pennsylvania Colors
    private static final Color PA_BLUE = new Color(0, 51, 160);
    private static final Color PA_GOLD = new Color(255, 184, 28);
    private static final Color PA_WHITE = new Color(255, 255, 255);
    private static final Color PA_CREAM = new Color(248, 246, 240);

    public PennsylvaniaTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "PENNSYLVANIA";
    }

    @Override
    public Color getPrimaryColor() {
        return PA_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return PA_GOLD;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Gradient background
        renderer.drawGradientBackground(g2d, PA_CREAM, new Color(235, 240, 250), width, height);

        // Gold accent stripe at bottom
        g2d.setColor(new Color(PA_GOLD.getRed(), PA_GOLD.getGreen(), PA_GOLD.getBlue(), 80));
        g2d.fillRect(0, height - 30, width, 30);

        // Keystone watermark
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        drawKeystone(g2d, width - 180, height - 200, 80, 90);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Guilloche pattern
        securityGenerator.drawGuillochePattern(g2d, 0, 0, width, height, PA_GOLD);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Blue header with gold accent
        GradientPaint headerGradient = new GradientPaint(
            0, 0, PA_BLUE,
            CardRenderer.CARD_WIDTH, 0, new Color(0, 71, 190)
        );
        g2d.setPaint(headerGradient);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 150);

        // Gold stripe
        g2d.setColor(PA_GOLD);
        g2d.fillRect(0, 145, CardRenderer.CARD_WIDTH, 5);

        // Keystone symbol
        drawKeystone(g2d, 70, 80, 80, 90);

        // "PENNSYLVANIA" text
        g2d.setColor(PA_WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("PENNSYLVANIA", 200, 75);

        // "DRIVER'S LICENSE" text
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER'S LICENSE", 200, 115);
    }

    private void drawKeystone(Graphics2D g2d, int x, int y, int width, int height) {
        // Pennsylvania keystone shape
        Polygon keystone = new Polygon();
        keystone.addPoint(x + width / 2, y);  // Top center
        keystone.addPoint(x + width, y + height / 3);  // Top right
        keystone.addPoint(x + width, y + height);  // Bottom right
        keystone.addPoint(x, y + height);  // Bottom left
        keystone.addPoint(x, y + height / 3);  // Top left

        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();

        g2d.setColor(PA_GOLD);
        g2d.fill(keystone);

        g2d.setColor(PA_BLUE);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(keystone);

        // "PA" text in keystone
        g2d.setColor(PA_BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, (int) (height * 0.4)));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "PA";
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + height / 2 + fm.getAscent() / 2;
        g2d.drawString(text, textX, textY);

        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }
}
