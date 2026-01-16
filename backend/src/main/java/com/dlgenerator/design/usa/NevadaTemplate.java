package com.dlgenerator.design.usa;

import com.dlgenerator.design.CardTemplate;
import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.awt.geom.Path2D;

public class NevadaTemplate extends CardTemplate {
    
    private static final Color NEVADA_BLUE = new Color(0, 51, 102);
    private static final Color NEVADA_GOLD = new Color(255, 215, 0);
    
    public NevadaTemplate(CardRenderer renderer, ImageProcessor imageProcessor, SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }
    
    @Override
    public String getTemplateName() {
        return "NEVADA";
    }
    
    @Override
    public Color getPrimaryColor() {
        return NEVADA_BLUE;
    }
    
    @Override
    public Color getSecondaryColor() {
        return NEVADA_GOLD;
    }
    
    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        renderer.drawGradientBackground(g2d, Color.WHITE, new Color(230, 240, 255), width, height);
        drawNevadaMountains(g2d, width, height);
        drawDesertPattern(g2d, width, height);
    }
    
    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        g2d.setFont(HEADER_FONT);
        g2d.setColor(NEVADA_BLUE);
        g2d.drawString("NEVADA", 50, 60);
        
        g2d.setFont(SUBHEADER_FONT);
        g2d.drawString("DRIVER LICENSE", 50, 90);
        
        g2d.setFont(new Font("Arial", Font.ITALIC, 12));
        g2d.setColor(Color.GRAY);
        g2d.drawString("THE SILVER STATE", 50, 110);
        
        renderer.drawStar(g2d, 900, 50, 40, 18, 5, NEVADA_GOLD);
    }
    
    private void drawNevadaMountains(Graphics2D g2d, int width, int height) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2d.setColor(NEVADA_BLUE);
        
        Path2D mountain = new Path2D.Double();
        mountain.moveTo(width - 400, height);
        mountain.lineTo(width - 300, height - 200);
        mountain.lineTo(width - 200, height - 100);
        mountain.lineTo(width - 100, height - 250);
        mountain.lineTo(width, height);
        mountain.closePath();
        
        g2d.fill(mountain);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    private void drawDesertPattern(Graphics2D g2d, int width, int height) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g2d.setColor(NEVADA_GOLD);
        
        for (int x = 0; x < width; x += 20) {
            for (int y = 0; y < height; y += 20) {
                g2d.fillOval(x, y, 3, 3);
            }
        }
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}