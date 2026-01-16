package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

/**
 * Texas Driver License Card Template
 * Design based on Texas DPS standards
 */
public class TexasTemplate extends CardTemplate {

    // Texas Colors
    private static final Color TX_BLUE = new Color(0, 51, 160);
    private static final Color TX_RED = new Color(191, 10, 48);
    private static final Color TX_WHITE = new Color(255, 255, 255);
    private static final Color TX_CREAM = new Color(245, 242, 235);
    private static final Color TX_GOLD = new Color(255, 204, 0);

    public TexasTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "TEXAS";
    }

    @Override
    public Color getPrimaryColor() {
        return TX_BLUE;
    }

    @Override
    public Color getSecondaryColor() {
        return TX_RED;
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Professional gradient background
        GradientPaint mainGradient = new GradientPaint(
            0, 0, TX_CREAM,
            width, height, new Color(240, 245, 255)
        );
        g2d.setPaint(mainGradient);
        g2d.fillRect(0, 0, width, height);

        // Subtle diagonal accent gradient - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.12f));
        // GradientPaint accentGradient = new GradientPaint(
        //     0, 0, TX_BLUE,
        //     width, height, new Color(255, 255, 255, 0)
        // );
        // g2d.setPaint(accentGradient);
        // g2d.fillRect(0, 0, width, height);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Texas flag inspired stripe at bottom
        g2d.setColor(TX_RED);
        g2d.fillRect(0, height - 45, width, 45);

        // White stripe above red
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.fillRect(0, height - 50, width, 5);

        // Texas star watermark - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
        // drawTexasStar(g2d, width - 250, height - 250, 200);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Guilloche pattern - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        // securityGenerator.drawGuillochePattern(g2d, 0, 0, width, height, TX_GOLD);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Blue header bar with gradient
        GradientPaint headerGradient = new GradientPaint(
            0, 0, TX_BLUE,
            0, 100, new Color(0, 40, 130)
        );
        g2d.setPaint(headerGradient);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 100);

        // Subtle gold accent line at top
        g2d.setColor(TX_GOLD);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 3);

        // Draw Texas star with better positioning
        drawTexasStar(g2d, 70, 90, 55);

        // "TEXAS" text with improved styling
        g2d.setColor(TX_WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("TEXAS", 160, 58);

        // "DRIVER LICENSE" text
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER LICENSE", 160, 85);

        // "USA" in top right corner
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("USA", 960, 30);

        // Veteran indicator if applicable
        if (data.getVeteran() != null && data.getVeteran()) {
            drawVeteranIndicator(g2d, 820, 25);
        }
    }

    private void drawTexasStar(Graphics2D g2d, int centerX, int centerY, int radius) {
        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();

        g2d.setColor(TX_WHITE);
        Polygon star = createStar(centerX, centerY, radius, radius / 2, 5);
        g2d.fill(star);

        // Gold outline
        g2d.setColor(TX_GOLD);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(star);
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }

    private Polygon createStar(int centerX, int centerY, int outerRadius, int innerRadius, int points) {
        Polygon star = new Polygon();
        double angle = Math.PI / points;

        for (int i = 0; i < 2 * points; i++) {
            double r = (i % 2 == 0) ? outerRadius : innerRadius;
            double theta = i * angle - Math.PI / 2;
            int x = centerX + (int) (r * Math.cos(theta));
            int y = centerY + (int) (r * Math.sin(theta));
            star.addPoint(x, y);
        }

        return star;
    }

    private void drawVeteranIndicator(Graphics2D g2d, int x, int y) {
        g2d.setColor(TX_RED);
        g2d.fillRect(x, y, 100, 35);
        g2d.setColor(TX_WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("VETERAN", x + 12, y + 22);
    }
}
