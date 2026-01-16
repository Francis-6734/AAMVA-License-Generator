package com.dlgenerator.design.international;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;

public class CanadaTemplate extends CardTemplate {
    
    private static final Color CANADA_RED = new Color(255, 0, 0);
    
    public CanadaTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }
    
    @Override
    public String getTemplateName() {
        return "CANADA";
    }
    
    @Override
    public Color getPrimaryColor() {
        return CANADA_RED;
    }
    
    @Override
    public Color getSecondaryColor() {
        return Color.WHITE;
    }
    
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(CANADA_RED);
        g2d.fillRect(0, 0, 80, height);
        g2d.fillRect(width - 80, 0, 80, height);
        
        drawMapleLeafWatermark(g2d, width / 2, height / 2);
    }
    
    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        g2d.setColor(CANADA_RED);
        g2d.drawString("CANADA", 120, 50);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("DRIVER'S LICENCE / PERMIS DE CONDUIRE", 120, 75);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String province = data.getState() != null ? data.getState() : "ONTARIO";
        g2d.drawString(province, 120, 100);
        
        drawMapleLeaf(g2d, 900, 40, 40, CANADA_RED, 1.0f);
    }
    
    private void drawMapleLeaf(Graphics2D g2d, int x, int y, int size, Color color, float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(color);
        
        int[] xPoints = {x, x-8, x-15, x-10, x-18, x-8, x, x+8, x+18, x+10, x+15, x+8};
        int[] yPoints = {y-size, y-size/2, y-size/3, y, y+size/3, y+size/2, y+size/3, y+size/2, y+size/3, y, y-size/3, y-size/2};
        
        g2d.fillPolygon(xPoints, yPoints, 12);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private void drawMapleLeafWatermark(Graphics2D g2d, int x, int y) {
        drawMapleLeaf(g2d, x, y, 150, CANADA_RED, 0.05f);
    }
}