package com.dlgenerator.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ImageProcessor {
    
    /**
     * Decode Base64 string to BufferedImage
     */
    public BufferedImage decodeBase64Image(String base64Image) throws IOException {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }
        
        // Remove data URL prefix if present
        String imageData = base64Image;
        if (base64Image.contains(",")) {
            imageData = base64Image.split(",")[1];
        }
        
        byte[] imageBytes = Base64.getDecoder().decode(imageData);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }
    
    /**
     * Encode BufferedImage to Base64
     */
    public String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    
    /**
     * Resize image to target dimensions
     */
    public BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        
        // High quality rendering
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        
        return resized;
    }
    
    /**
     * Crop image to square from center
     */
    public BufferedImage cropToSquare(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = Math.min(width, height);
        
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        
        return image.getSubimage(x, y, size, size);
    }
    
    /**
     * Apply rounded corners to image
     */
    public BufferedImage addRoundedCorners(BufferedImage image, int cornerRadius) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage rounded = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rounded.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        return rounded;
    }
    
    /**
     * Convert image to grayscale
     */
    public BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscale = new BufferedImage(
            image.getWidth(), 
            image.getHeight(), 
            BufferedImage.TYPE_BYTE_GRAY
        );
        
        Graphics2D g2d = grayscale.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        return grayscale;
    }
    
    /**
     * Create circular cropped image
     */
    public BufferedImage createCircularImage(BufferedImage image) {
        int diameter = Math.min(image.getWidth(), image.getHeight());
        BufferedImage circular = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = circular.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2d.setClip(circle);
        
        int x = (image.getWidth() - diameter) / 2;
        int y = (image.getHeight() - diameter) / 2;
        g2d.drawImage(image, -x, -y, null);
        g2d.dispose();
        
        return circular;
    }
    
    /**
     * Add border to image
     */
    public BufferedImage addBorder(BufferedImage image, int borderWidth, Color borderColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage bordered = new BufferedImage(
            width + 2 * borderWidth,
            height + 2 * borderWidth,
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2d = bordered.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw border
        g2d.setColor(borderColor);
        g2d.fillRect(0, 0, bordered.getWidth(), bordered.getHeight());
        
        // Draw image
        g2d.drawImage(image, borderWidth, borderWidth, null);
        g2d.dispose();
        
        return bordered;
    }
}