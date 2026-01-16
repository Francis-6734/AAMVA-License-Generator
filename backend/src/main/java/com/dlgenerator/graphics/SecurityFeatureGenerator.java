package com.dlgenerator.graphics;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Path2D;

@Component
public class SecurityFeatureGenerator {
    
    /**
     * Draw holographic overlay effect
     */
    public void drawHolographicOverlay(Graphics2D g2d, int x, int y, int width, int height, Color baseColor) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        
        GradientPaint gradient = new GradientPaint(
            x, y, baseColor,
            x + width, y + height, baseColor.brighter()
        );
        g2d.setPaint(gradient);
        g2d.fillRect(x, y, width, height);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    /**
     * Draw microprint text (very small security text)
     */
    public void drawMicroprint(Graphics2D g2d, String text, int x, int y, int repeat) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 4));
        g2d.setColor(new Color(100, 100, 100));
        
        StringBuilder repeated = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            repeated.append(text).append(" ");
        }
        
        g2d.drawString(repeated.toString(), x, y);
    }
    
    /**
     * Draw UV feature indicator
     */
    public void drawUVFeature(Graphics2D g2d, int centerX, int centerY, int radius) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.setColor(new Color(0, 255, 255)); // Cyan for UV simulation
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    /**
     * Draw guilloche pattern (wavy lines)
     */
    public void drawGuillochePattern(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(0.5f));
        
        for (int i = 0; i < 20; i++) {
            Path2D path = new Path2D.Double();
            path.moveTo(x, y + i * 5);
            
            for (int j = 0; j < width; j += 10) {
                path.quadTo(
                    x + j + 5, y + i * 5 + Math.sin(j * 0.1) * 3,
                    x + j + 10, y + i * 5
                );
            }
            
            g2d.draw(path);
        }
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    /**
     * Draw rainbow gradient security strip
     */
    public void drawSecurityStrip(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
        int stripWidth = width / colors.length;
        
        for (int i = 0; i < colors.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(x + i * stripWidth, y, stripWidth, height);
        }
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}