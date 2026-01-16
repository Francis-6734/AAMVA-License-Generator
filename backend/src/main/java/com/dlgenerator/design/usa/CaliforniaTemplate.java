package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.awt.geom.Path2D;

public class CaliforniaTemplate extends CardTemplate {
    
    private static final Color CA_GOLD = new Color(253, 181, 21);
    private static final Color CA_BLUE = new Color(0, 84, 164);
    
    public CaliforniaTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }
    
    @Override
    public String getTemplateName() {
        return "CALIFORNIA";
    }
    
    @Override
    public Color getPrimaryColor() {
        return CA_BLUE;
    }
    
    @Override
    public Color getSecondaryColor() {
        return CA_GOLD;
    }
    
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Professional gradient background
        GradientPaint mainGradient = new GradientPaint(
            0, 0, new Color(255, 252, 245),
            width, height, new Color(240, 245, 255)
        );
        g2d.setPaint(mainGradient);
        g2d.fillRect(0, 0, width, height);

        // Accent overlay - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
        // GradientPaint accentGradient = new GradientPaint(
        //     0, 0, CA_BLUE,
        //     width, height, new Color(255, 255, 255, 0)
        // );
        // g2d.setPaint(accentGradient);
        // g2d.fillRect(0, 0, width, height);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Gold accent stripe at bottom
        g2d.setColor(CA_GOLD);
        g2d.fillRect(0, height - 40, width, 40);

        // California Bear watermark - REMOVED for better visibility
        // drawCaliforniaBear(g2d, width - 230, height - 220);

        // Guilloche pattern - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
        // securityGenerator.drawGuillochePattern(g2d, 0, 0, width, height, CA_GOLD);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Blue header bar with gradient
        GradientPaint headerGradient = new GradientPaint(
            0, 0, CA_BLUE,
            0, 100, new Color(0, 60, 120)
        );
        g2d.setPaint(headerGradient);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 100);

        // Gold accent line at top
        g2d.setColor(CA_GOLD);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 4);

        // "CALIFORNIA" text
        g2d.setColor(Color.WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("CALIFORNIA", 160, 58);

        // "DRIVER LICENSE" text
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER LICENSE", 160, 85);

        // "USA" in top right corner
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("USA", 960, 30);

        // California Bear icon in header - REMOVED for better visibility
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        // g2d.setColor(CA_GOLD);
        // g2d.fillOval(50, 40, 80, 50);
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // California Poppy decoration
        drawCaliforniaPoppy(g2d, 850, 35);
    }
    
    private void drawCaliforniaBear(Graphics2D g2d, int x, int y) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
        g2d.setColor(CA_GOLD);
        g2d.fillOval(x, y, 120, 80);
        g2d.fillOval(x + 100, y - 20, 60, 60);
        g2d.fillOval(x + 130, y - 40, 15, 25);
        g2d.fillOval(x + 155, y - 40, 15, 25);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private void drawCaliforniaPoppy(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(255, 140, 0));
        for (int i = 0; i < 4; i++) {
            g2d.fillOval(x, y + 15, 30, 20);
            g2d.rotate(Math.toRadians(90), x + 25, y + 25);
        }
        g2d.setColor(CA_GOLD);
        g2d.fillOval(x + 15, y + 15, 20, 20);
    }
}