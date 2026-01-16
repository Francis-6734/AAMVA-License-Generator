package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Japanese Driving License (運転免許証) template.
 * Features color-coded backgrounds based on driver experience.
 */
public class JapanTemplate extends InternationalCardTemplate {

    // License type colors
    private static final Color GREEN_LICENSE = new Color(0, 150, 0);      // Beginner
    private static final Color BLUE_LICENSE = new Color(0, 100, 180);     // Regular
    private static final Color GOLD_LICENSE = new Color(200, 150, 0);     // Excellent driver

    private static final Color JAPAN_RED = new Color(188, 0, 45);
    private static final Color JAPAN_WHITE = Color.WHITE;

    public JapanTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                         SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    @Override
    public String getTemplateName() {
        return "JAPAN";
    }

    @Override
    public Color getPrimaryColor() {
        return BLUE_LICENSE;
    }

    @Override
    public Color getSecondaryColor() {
        return JAPAN_RED;
    }

    @Override
    public Locale getLocale() {
        return Locale.JAPAN;
    }

    @Override
    public String getLocalizedDocumentName() {
        return "運転免許証";
    }

    @Override
    public BarcodeType getBarcodeType() {
        return BarcodeType.NONE; // Japanese licenses don't have barcodes
    }

    private Color getLicenseTypeColor(String colorCode) {
        if (colorCode == null) return BLUE_LICENSE;
        return switch (colorCode.toUpperCase()) {
            case "GREEN" -> GREEN_LICENSE;
            case "GOLD" -> GOLD_LICENSE;
            default -> BLUE_LICENSE;
        };
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // Light gray/white base
        renderer.drawGradientBackground(g2d,
            new Color(250, 250, 250),
            new Color(235, 235, 240),
            width, height);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        Color licenseColor = getLicenseTypeColor(data.getColorCode());

        // Top color bar based on license type
        g2d.setColor(licenseColor);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 10);

        // Japanese title
        g2d.setFont(new Font("MS Gothic", Font.BOLD, 32));
        g2d.setColor(Color.BLACK);
        g2d.drawString(getLocalizedDocumentName(), 50, 55);

        // License type indicator
        g2d.setFont(new Font("MS Gothic", Font.BOLD, 14));
        g2d.setColor(licenseColor);
        String typeText = switch (data.getColorCode() != null ? data.getColorCode().toUpperCase() : "BLUE") {
            case "GREEN" -> "初心者 (Beginner)";
            case "GOLD" -> "優良 (Excellent)";
            default -> "一般 (Regular)";
        };
        g2d.drawString(typeText, 50, 80);

        // Prefectural police authority (placeholder)
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 12));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("公安委員会", 750, 50);
    }

    @Override
    public BufferedImage generateFront(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Background
        drawBackground(g2d, CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // Header
        drawHeader(g2d, data);

        // Photo (Japanese licenses have specific photo requirements)
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                // Japanese license photo: 24mm x 30mm, positioned on left
                drawJapanesePhoto(g2d, photo);
            }
        }

        // License fields in Japanese format
        drawJapaneseFields(g2d, data);

        g2d.dispose();
        return card;
    }

    private void drawJapanesePhoto(Graphics2D g2d, BufferedImage photo) throws IOException {
        int photoX = 60;
        int photoY = 130;
        int photoWidth = 200;
        int photoHeight = 250;

        BufferedImage resized = imageProcessor.resizeImage(photo, photoWidth, photoHeight);

        // White border
        g2d.setColor(Color.WHITE);
        g2d.fillRect(photoX - 3, photoY - 3, photoWidth + 6, photoHeight + 6);

        // Photo
        g2d.drawImage(resized, photoX, photoY, null);

        // Border
        g2d.setColor(new Color(100, 100, 100));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(photoX, photoY, photoWidth, photoHeight);
    }

    private void drawJapaneseFields(Graphics2D g2d, LicenseData data) {
        int x = 290;
        int y = 140;
        int lineHeight = 40;

        // License number
        drawJapaneseField(g2d, "番号", data.getLicenseNumber(), x, y, true);
        y += lineHeight;

        // Name in Kanji/Katakana (using romanized name as placeholder)
        String fullName = (data.getLastName() != null ? data.getLastName() : "") + " " +
                         (data.getFirstName() != null ? data.getFirstName() : "");
        drawJapaneseField(g2d, "氏名", fullName.toUpperCase(), x, y, false);
        y += lineHeight;

        // Date of birth (Japanese era)
        String dobJapanese = formatJapaneseEraDate(data.getDob());
        drawJapaneseField(g2d, "生年月日", dobJapanese, x, y, false);
        y += lineHeight;

        // Address
        String address = (data.getAddress() != null ? data.getAddress() : "") +
                        (data.getCity() != null ? " " + data.getCity() : "");
        drawJapaneseField(g2d, "住所", address, x, y, false);
        y += lineHeight;

        // Issue date
        String issueJapanese = formatJapaneseEraDate(data.getIssueDate());
        drawJapaneseField(g2d, "交付", issueJapanese, x, y, false);

        // Expiry date
        String expiryJapanese = formatJapaneseEraDate(data.getExpirationDate());
        drawJapaneseField(g2d, "有効期限", expiryJapanese, x + 250, y, false);
        y += lineHeight;

        // License categories
        drawJapaneseField(g2d, "種類", data.getLicenseClass() != null ? data.getLicenseClass() : "普通", x, y, false);

        // Physical characteristics
        y += lineHeight + 20;
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 11));
        g2d.setColor(new Color(80, 80, 80));

        String height = data.getHeight() != null ? formatHeight(data.getHeight()) : "";
        String eyeColor = data.getEyeColor() != null ? data.getEyeColor() : "";

        g2d.drawString("身長: " + height + "  眼の色: " + eyeColor, x, y);
    }

    private void drawJapaneseField(Graphics2D g2d, String label, String value, int x, int y, boolean large) {
        // Label
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 11));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y);

        // Value
        if (large) {
            g2d.setFont(new Font("Courier New", Font.BOLD, 20));
        } else {
            g2d.setFont(new Font("MS Gothic", Font.BOLD, 14));
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString(value != null ? value : "", x + 70, y);
    }

    private String formatJapaneseEraDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return "";
        try {
            LocalDate date = LocalDate.parse(dateStr);
            JapaneseDate japaneseDate = JapaneseDate.from(date);

            // Format as "令和X年X月X日" style
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("GGGGy年M月d日", Locale.JAPAN)
                .withChronology(JapaneseChronology.INSTANCE);

            return japaneseDate.format(formatter);
        } catch (Exception e) {
            // Fallback to standard format
            return formatDate(dateStr);
        }
    }

    @Override
    public BufferedImage generateBack(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Light background
        renderer.drawGradientBackground(g2d,
            new Color(250, 250, 250),
            new Color(240, 240, 245),
            CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // Header
        g2d.setColor(getLicenseTypeColor(data.getColorCode()));
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 60);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("MS Gothic", Font.BOLD, 24));
        g2d.drawString("運転免許証", 50, 40);

        // Conditions and endorsements
        int y = 100;
        g2d.setFont(new Font("MS Gothic", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("条件等 (Conditions)", 50, y);

        y += 30;
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 12));
        String restrictions = data.getRestrictions();
        if (restrictions != null && !restrictions.isEmpty()) {
            g2d.drawString(restrictions, 50, y);
        } else {
            g2d.drawString("なし (None)", 50, y);
        }

        // License categories table (simplified)
        y = 200;
        g2d.setFont(new Font("MS Gothic", Font.BOLD, 12));
        g2d.drawString("免許の種類 (License Categories)", 50, y);

        y += 25;
        String[] categories = {"大型", "中型", "準中型", "普通", "大特", "大自二", "普自二", "小特", "原付", "け引"};
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 10));

        int colWidth = 85;
        for (int i = 0; i < categories.length; i++) {
            int col = i % 5;
            int row = i / 5;
            int cx = 50 + col * colWidth;
            int cy = y + row * 30;

            g2d.setColor(new Color(200, 200, 200));
            g2d.drawRect(cx, cy, colWidth - 5, 25);

            g2d.setColor(Color.BLACK);
            g2d.drawString(categories[i], cx + 5, cy + 17);
        }

        // Organ donor info (Japanese licenses include this)
        y = 350;
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 10));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("臓器提供意思表示欄", 50, y);

        // Compliance text
        y = 500;
        g2d.setFont(new Font("MS Gothic", Font.PLAIN, 9));
        g2d.drawString("この免許証は、道路交通法に基づき交付されたものです。", 50, y);
        g2d.drawString("This license is issued under the Road Traffic Law of Japan.", 50, y + 15);

        g2d.dispose();
        return card;
    }

    @Override
    protected boolean hasMagneticStripe() {
        return false; // Japanese licenses don't have magnetic stripe
    }
}
