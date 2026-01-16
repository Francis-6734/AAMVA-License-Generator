package com.dlgenerator.design.international;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

public class UKTemplate extends CardTemplate {
    
    private static final Color UK_RED = new Color(200, 16, 46);
    private static final Color UK_BLUE = new Color(1, 33, 105);
    private static final Color EU_BLUE = new Color(0, 51, 153);
    
    public UKTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }
    
    @Override
    public String getTemplateName() {
        return "UNITED KINGDOM";
    }
    
    @Override
    public Color getPrimaryColor() {
        return UK_RED;
    }
    
    @Override
    public Color getSecondaryColor() {
        return UK_BLUE;
    }
    
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        renderer.drawGradientBackground(g2d, new Color(255, 192, 203), new Color(255, 160, 180), width, height);
        drawEUFlag(g2d);
    }
    
    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(UK_BLUE);
        g2d.drawString("UNITED KINGDOM", 150, 60);
        
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVING LICENCE", 150, 85);
    }
    
    @Override
    protected void drawLicenseNumber(Graphics2D g2d, LicenseData data) {
        g2d.setFont(new Font("Courier New", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);

        String licenseNumber = data.getLicenseNumber();

        // Format: ABCDE 123456 AB 1CD
        if (licenseNumber.length() >= 16) {
            String formatted = licenseNumber.substring(0, 5) + " " +
                              licenseNumber.substring(5, 11) + " " +
                              licenseNumber.substring(11, 13) + " " +
                              licenseNumber.substring(13);
            g2d.drawString(formatted, 150, 120);
        } else {
            g2d.drawString(licenseNumber, 150, 120);
        }

        g2d.setFont(LABEL_FONT);
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("5. ", 130, 120);
    }
    
    private void drawEUFlag(Graphics2D g2d) {
        g2d.setColor(EU_BLUE);
        g2d.fillRect(20, 20, 100, 100);
        
        g2d.setColor(Color.YELLOW);
        int centerX = 70;
        int centerY = 70;
        int radius = 35;
        
        for (int i = 0; i < 12; i++) {
            double angle = (i * 30 - 90) * Math.PI / 180;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            renderer.drawStar(g2d, x, y, 5, 2, 5, Color.YELLOW);
        }
        
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.WHITE);
        g2d.drawString("UK", 55, 135);
    }
}