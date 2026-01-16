package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

public class NewYorkTemplate extends CardTemplate {
    
    private static final Color NY_BLUE = new Color(0, 71, 186);
    private static final Color NY_ORANGE = new Color(255, 102, 0);
    
    public NewYorkTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }
    
    @Override
    public String getTemplateName() {
        return "NEW YORK";
    }
    
    @Override
    public Color getPrimaryColor() {
        return NY_BLUE;
    }
    
    @Override
    public Color getSecondaryColor() {
        return NY_ORANGE;
    }
    
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        renderer.drawGradientBackground(g2d, new Color(245, 248, 255), new Color(230, 235, 245), width, height);
        
        // Draw Statue of Liberty silhouette
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setColor(NY_BLUE);
        g2d.fillRect(width - 100, height - 300, 30, 200);
        g2d.fillOval(width - 110, height - 310, 50, 50);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        g2d.setFont(HEADER_FONT);
        g2d.setColor(NY_BLUE);
        g2d.drawString("NEW YORK", 50, 60);
        
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER LICENSE", 50, 90);
        
        g2d.setFont(new Font("Arial", Font.ITALIC, 11));
        g2d.setColor(Color.GRAY);
        g2d.drawString("THE EMPIRE STATE", 50, 110);
    }
}