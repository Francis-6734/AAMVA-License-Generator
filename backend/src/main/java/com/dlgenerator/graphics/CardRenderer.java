package com.dlgenerator.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

@Component
public class CardRenderer {
    
    public static final int CARD_WIDTH = 1050;
    public static final int CARD_HEIGHT = 660;
    
    /**
     * Create blank card canvas
     */
    public BufferedImage createCanvas() {
        return new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    
    /**
     * Get Graphics2D with anti-aliasing enabled
     */
    public Graphics2D getHighQualityGraphics(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        
        // Enable anti-aliasing and high quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        
        return g2d;
    }
    
    /**
     * Draw gradient background
     */
    public void drawGradientBackground(Graphics2D g2d, Color startColor, Color endColor, int width, int height) {
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
    
    /**
     * Draw text with shadow
     */
    public void drawTextWithShadow(Graphics2D g2d, String text, int x, int y, Font font, Color textColor, Color shadowColor) {
        g2d.setFont(font);
        
        // Draw shadow
        g2d.setColor(shadowColor);
        g2d.drawString(text, x + 2, y + 2);
        
        // Draw text
        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }
    
    /**
     * Draw centered text
     */
    public void drawCenteredText(Graphics2D g2d, String text, int centerX, int y, Font font, Color color) {
        g2d.setFont(font);
        g2d.setColor(color);
        
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int x = centerX - (textWidth / 2);
        
        g2d.drawString(text, x, y);
    }
    
    /**
     * Draw star shape
     */
    public void drawStar(Graphics2D g2d, int centerX, int centerY, int outerRadius, int innerRadius, int points, Color color) {
        int[] xPoints = new int[points * 2];
        int[] yPoints = new int[points * 2];
        
        for (int i = 0; i < points * 2; i++) {
            double angle = Math.PI / 2 + (i * Math.PI / points);
            int radius = (i % 2 == 0) ? outerRadius : innerRadius;
            xPoints[i] = centerX + (int)(radius * Math.cos(angle));
            yPoints[i] = centerY + (int)(radius * Math.sin(angle));
        }
        
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, points * 2);
    }
    
    /**
     * Draw watermark text
     */
    public void drawWatermark(Graphics2D g2d, String text, int centerX, int centerY, Font font, float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setFont(font);
        g2d.setColor(Color.GRAY);
        
        // Rotate text
        g2d.rotate(Math.toRadians(-45), centerX, centerY);
        
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        g2d.drawString(text, centerX - textWidth / 2, centerY);
        
        // Reset rotation and alpha
        g2d.rotate(Math.toRadians(45), centerX, centerY);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}