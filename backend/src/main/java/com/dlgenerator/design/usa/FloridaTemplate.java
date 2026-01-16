package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

/**
 * Florida Driver License Card Template
 * Design based on Florida DHSMV standards
 */
public class FloridaTemplate extends CardTemplate {

    // Florida Colors
    private static final Color FL_ORANGE = new Color(255, 102, 0);
    private static final Color FL_GREEN = new Color(0, 128, 0);
    private static final Color FL_BLUE = new Color(0, 102, 204);
    private static final Color FL_YELLOW = new Color(255, 204, 0);
    private static final Color FL_CREAM = new Color(255, 250, 240);

    public FloridaTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "FLORIDA";
    }

    @Override
    public Color getPrimaryColor() {
        return FL_ORANGE;
    }

    @Override
    public Color getSecondaryColor() {
        return FL_BLUE;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Professional gradient background
        GradientPaint mainGradient = new GradientPaint(
            0, 0, FL_CREAM,
            width, height, new Color(255, 248, 235)
        );
        g2d.setPaint(mainGradient);
        g2d.fillRect(0, 0, width, height);

        // Accent overlay - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        // GradientPaint accentGradient = new GradientPaint(
        //     0, 0, FL_ORANGE,
        //     width, height, new Color(255, 255, 255, 0)
        // );
        // g2d.setPaint(accentGradient);
        // g2d.fillRect(0, 0, width, height);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Orange accent stripe at bottom
        g2d.setColor(FL_ORANGE);
        g2d.fillRect(0, height - 40, width, 40);

        // White stripe above orange
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.fillRect(0, height - 45, width, 5);

        // Palm tree silhouettes - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.06f));
        // g2d.setColor(FL_GREEN);
        // drawPalmTreeSilhouette(g2d, 50, 320, 90);
        // drawPalmTreeSilhouette(g2d, width - 120, 380, 110);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Guilloche pattern - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
        // securityGenerator.drawGuillochePattern(g2d, 0, 0, width, height, FL_ORANGE);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Orange gradient header bar
        GradientPaint headerGradient = new GradientPaint(
            0, 0, FL_ORANGE,
            0, 100, new Color(230, 90, 0)
        );
        g2d.setPaint(headerGradient);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 100);

        // Green accent line at top (Florida colors)
        g2d.setColor(FL_GREEN);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 3);

        // Palm tree icon in header - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        // g2d.setColor(FL_GREEN);
        // drawPalmTreeSilhouette(g2d, 50, 30, 60);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // "FLORIDA" text
        g2d.setColor(Color.WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("FLORIDA", 160, 58);

        // "DRIVER LICENSE" text
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER LICENSE", 160, 85);

        // "USA" in top right corner
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("USA", 960, 30);

        // Sunshine State motto indicator
        g2d.setFont(new Font("Arial", Font.ITALIC, 12));
        g2d.setColor(FL_YELLOW);
        g2d.drawString("The Sunshine State", 750, 85);
    }

    private void drawPalmTreeSilhouette(Graphics2D g2d, int x, int y, int height) {
        // Trunk
        g2d.fillRect(x + 15, y, 10, height);

        // Fronds
        Stroke originalStroke = g2d.getStroke();
        for (int i = 0; i < 6; i++) {
            double angle = (Math.PI * 2 / 6) * i - Math.PI / 2;
            int endX = x + 20 + (int) (40 * Math.cos(angle));
            int endY = y + (int) (40 * Math.sin(angle));
            g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(x + 20, y, endX, endY);
        }
        g2d.setStroke(originalStroke);
    }

    private void drawOrganDonorHeart(Graphics2D g2d, int x, int y) {
        // Red heart for organ donor
        g2d.setColor(new Color(220, 20, 60));
        drawHeart(g2d, x, y, 35);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("DONOR", x - 10, y + 50);
    }

    private void drawHeart(Graphics2D g2d, int x, int y, int size) {
        Polygon heart = new Polygon();
        // Simplified heart shape
        heart.addPoint(x, y + size / 4);
        heart.addPoint(x + size / 2, y);
        heart.addPoint(x + size, y + size / 4);
        heart.addPoint(x + size, y + size / 2);
        heart.addPoint(x + size / 2, y + size);
        heart.addPoint(x, y + size / 2);
        g2d.fill(heart);
    }
}
