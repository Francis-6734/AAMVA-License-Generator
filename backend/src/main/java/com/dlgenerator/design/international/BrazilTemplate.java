package com.dlgenerator.design.international;

import com.dlgenerator.graphics.CardRenderer;
import com.dlgenerator.graphics.ImageProcessor;
import com.dlgenerator.graphics.SecurityFeatureGenerator;
import com.dlgenerator.model.LicenseData;
import com.dlgenerator.model.SubJurisdiction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

/**
 * Brazilian CNH (Carteira Nacional de Habilitação) template.
 * Features QR code instead of PDF417 barcode.
 */
public class BrazilTemplate extends InternationalCardTemplate {

    private static final Color BRAZIL_GREEN = new Color(0, 155, 58);
    private static final Color BRAZIL_YELLOW = new Color(255, 223, 0);
    private static final Color BRAZIL_BLUE = new Color(0, 39, 118);

    public BrazilTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                          SecurityFeatureGenerator securityGenerator) {
        super(renderer, imageProcessor, securityGenerator);
    }

    public BrazilTemplate(CardRenderer renderer, ImageProcessor imageProcessor,
                          SecurityFeatureGenerator securityGenerator,
                          SubJurisdiction subJurisdiction) {
        super(renderer, imageProcessor, securityGenerator, subJurisdiction);
    }

    @Override
    public String getTemplateName() {
        if (subJurisdiction != null) {
            return subJurisdiction.getName().toUpperCase();
        }
        return "BRAZIL";
    }

    @Override
    public Color getPrimaryColor() {
        return BRAZIL_GREEN;
    }

    @Override
    public Color getSecondaryColor() {
        return BRAZIL_YELLOW;
    }

    @Override
    public Locale getLocale() {
        return new Locale("pt", "BR");
    }

    @Override
    public String getLocalizedDocumentName() {
        return "CARTEIRA NACIONAL DE HABILITAÇÃO";
    }

    @Override
    public BarcodeType getBarcodeType() {
        return BarcodeType.QRCODE; // Brazil uses QR codes
    }

    @Override
    public void drawBackground(Graphics2D g2d, int width, int height) {
        // White/light yellow gradient
        renderer.drawGradientBackground(g2d,
            new Color(255, 255, 255),
            new Color(255, 255, 245),
            width, height);

        // Green header bar
        g2d.setColor(BRAZIL_GREEN);
        g2d.fillRect(0, 0, width, 90);

        // Yellow accent line
        g2d.setColor(BRAZIL_YELLOW);
        g2d.fillRect(0, 90, width, 5);
    }

    @Override
    public void drawHeader(Graphics2D g2d, LicenseData data) {
        // Brazilian coat of arms (simplified)
        drawBrazilianEmblem(g2d, 60, 45, 35);

        // Government text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        g2d.drawString("REPÚBLICA FEDERATIVA DO BRASIL", 120, 30);

        // Ministry text
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.drawString("MINISTÉRIO DA INFRAESTRUTURA", 120, 45);

        // Document title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("CNH", 120, 70);

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("CARTEIRA NACIONAL DE HABILITAÇÃO", 170, 70);

        // State badge
        if (subJurisdiction != null) {
            g2d.setColor(BRAZIL_YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString(subJurisdiction.getCode(), 850, 55);
        }
    }

    private void drawBrazilianEmblem(Graphics2D g2d, int x, int y, int radius) {
        // Green circle with yellow border
        g2d.setColor(BRAZIL_YELLOW);
        g2d.fillOval(x - radius - 3, y - radius - 3, (radius + 3) * 2, (radius + 3) * 2);

        g2d.setColor(BRAZIL_GREEN);
        g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Stars representation
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("BR", x - 10, y + 5);
    }

    @Override
    public BufferedImage generateFront(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Background
        drawBackground(g2d, CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // Header
        drawHeader(g2d, data);

        // Photo
        if (data.getPhotoBase64() != null) {
            BufferedImage photo = imageProcessor.decodeBase64Image(data.getPhotoBase64());
            if (photo != null) {
                drawBrazilianPhoto(g2d, photo);
            }
        }

        // License fields
        drawBrazilianFields(g2d, data);

        g2d.dispose();
        return card;
    }

    private void drawBrazilianPhoto(Graphics2D g2d, BufferedImage photo) throws IOException {
        int photoX = 50;
        int photoY = 120;
        int photoWidth = 200;
        int photoHeight = 250;

        BufferedImage resized = imageProcessor.resizeImage(photo, photoWidth, photoHeight);

        // Photo border
        g2d.setColor(BRAZIL_GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(photoX - 2, photoY - 2, photoWidth + 4, photoHeight + 4);

        g2d.drawImage(resized, photoX, photoY, null);
    }

    private void drawBrazilianFields(Graphics2D g2d, LicenseData data) {
        int x = 280;
        int y = 130;

        // Registration number (Registro)
        drawPortugueseField(g2d, "REGISTRO", data.getLicenseNumber(), x, y, true);
        y += 45;

        // CPF
        String cpf = data.getCpfNumber() != null ? data.getCpfNumber() : "000.000.000-00";
        drawPortugueseField(g2d, "CPF", cpf, x, y, false);
        y += 35;

        // Name
        String fullName = (data.getFirstName() != null ? data.getFirstName() : "") + " " +
                         (data.getLastName() != null ? data.getLastName() : "");
        drawPortugueseField(g2d, "NOME", fullName.toUpperCase(), x, y, false);
        y += 35;

        // Filiation (Parents)
        drawPortugueseField(g2d, "FILIAÇÃO", "DADOS PROTEGIDOS", x, y, false);
        y += 35;

        // Date of Birth
        drawPortugueseField(g2d, "DATA NASCIMENTO", formatDate(data.getDob()), x, y, false);
        y += 35;

        // Physical characteristics on same row
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("SEXO", x, y);
        g2d.drawString("NACIONALIDADE", x + 60, y);
        g2d.drawString("TIPO SANG.", x + 180, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        String sex = data.getGender() != null ? (data.getGender().equals("M") ? "M" : "F") : "M";
        g2d.drawString(sex, x, y + 14);
        g2d.drawString("BRASILEIRA", x + 60, y + 14);
        g2d.drawString("O+", x + 180, y + 14);
        y += 35;

        // Identity document
        drawPortugueseField(g2d, "DOC. IDENTIDADE", "RG: 00.000.000-0", x, y, false);
        y += 35;

        // Validity dates
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("1ª HABILITAÇÃO", x, y);
        g2d.drawString("VALIDADE", x + 150, y);

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString(formatDate(data.getIssueDate()), x, y + 14);
        g2d.drawString(formatDate(data.getExpirationDate()), x + 150, y + 14);
        y += 35;

        // Category
        String category = data.getLicenseClass() != null ? data.getLicenseClass() : "B";
        drawPortugueseField(g2d, "CATEGORIA", category, x, y, false);

        // Observations
        y += 35;
        drawPortugueseField(g2d, "OBSERVAÇÕES", data.getRestrictions() != null ? data.getRestrictions() : "---", x, y, false);
    }

    private void drawPortugueseField(Graphics2D g2d, String label, String value, int x, int y, boolean large) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString(label, x, y);

        if (large) {
            g2d.setFont(new Font("Courier New", Font.BOLD, 18));
        } else {
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString(value != null ? value : "", x, y + 14);
    }

    @Override
    public BufferedImage generateBack(LicenseData data) throws IOException {
        BufferedImage card = renderer.createCanvas();
        Graphics2D g2d = renderer.getHighQualityGraphics(card);

        // Background
        renderer.drawGradientBackground(g2d,
            new Color(255, 255, 255),
            new Color(250, 250, 245),
            CardRenderer.CARD_WIDTH, CardRenderer.CARD_HEIGHT);

        // Header
        g2d.setColor(BRAZIL_GREEN);
        g2d.fillRect(0, 0, CardRenderer.CARD_WIDTH, 60);
        g2d.setColor(BRAZIL_YELLOW);
        g2d.fillRect(0, 60, CardRenderer.CARD_WIDTH, 5);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("CNH - CARTEIRA NACIONAL DE HABILITAÇÃO", 50, 40);

        // QR Code area (placeholder - will be drawn as box)
        int qrX = 700;
        int qrY = 100;
        int qrSize = 180;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(qrX - 5, qrY - 5, qrSize + 10, qrSize + 10);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(qrX, qrY, qrSize, qrSize);

        // QR Code label
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString("QR CODE", qrX + 55, qrY + qrSize + 20);

        // Simulated QR pattern
        drawSimulatedQRCode(g2d, qrX + 10, qrY + 10, qrSize - 20);

        // Category table
        int y = 100;
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("CATEGORIAS / CATEGORIES", 50, y);

        y += 25;
        String[] categories = {"ACC", "A", "B", "C", "D", "E"};
        String[] descriptions = {
            "Ciclomotor / Moped",
            "Motocicleta / Motorcycle",
            "Automóvel / Car",
            "Carga / Truck",
            "Passageiros / Bus",
            "Combinação / Combination"
        };

        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = 0; i < categories.length; i++) {
            g2d.setColor(new Color(100, 100, 100));
            g2d.drawRect(50, y, 30, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString(categories[i], 55, y + 14);
            g2d.drawString(descriptions[i], 90, y + 14);
            y += 25;
        }

        // Address
        y += 20;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawString("LOCAL / PLACE:", 50, y);
        g2d.setColor(Color.BLACK);
        String address = data.getAddress() != null ? data.getAddress() : "";
        if (data.getCity() != null) address += ", " + data.getCity();
        if (subJurisdiction != null) address += " - " + subJurisdiction.getCode();
        g2d.drawString(address.toUpperCase(), 120, y);

        // Compliance text
        y = 500;
        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
        g2d.setColor(new Color(100, 100, 100));
        g2d.drawString("Documento expedido nos termos da Lei nº 9.503/97 - Código de Trânsito Brasileiro", 50, y);
        g2d.drawString("Document issued under Law 9.503/97 - Brazilian Traffic Code", 50, y + 12);
        g2d.drawString("DETRAN - Departamento Estadual de Trânsito", 50, y + 24);

        g2d.dispose();
        return card;
    }

    private void drawSimulatedQRCode(Graphics2D g2d, int x, int y, int size) {
        // Draw a simplified QR code pattern
        int moduleSize = size / 25;

        g2d.setColor(Color.BLACK);

        // Corner squares (finder patterns)
        // Top-left
        g2d.fillRect(x, y, moduleSize * 7, moduleSize * 7);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + moduleSize, y + moduleSize, moduleSize * 5, moduleSize * 5);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + moduleSize * 2, y + moduleSize * 2, moduleSize * 3, moduleSize * 3);

        // Top-right
        int rx = x + size - moduleSize * 7;
        g2d.fillRect(rx, y, moduleSize * 7, moduleSize * 7);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(rx + moduleSize, y + moduleSize, moduleSize * 5, moduleSize * 5);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(rx + moduleSize * 2, y + moduleSize * 2, moduleSize * 3, moduleSize * 3);

        // Bottom-left
        int by = y + size - moduleSize * 7;
        g2d.fillRect(x, by, moduleSize * 7, moduleSize * 7);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + moduleSize, by + moduleSize, moduleSize * 5, moduleSize * 5);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x + moduleSize * 2, by + moduleSize * 2, moduleSize * 3, moduleSize * 3);

        // Random data modules
        for (int i = 8; i < 17; i++) {
            for (int j = 8; j < 17; j++) {
                if ((i + j) % 3 == 0 || (i * j) % 5 == 0) {
                    g2d.fillRect(x + i * moduleSize, y + j * moduleSize, moduleSize, moduleSize);
                }
            }
        }
    }

    @Override
    protected boolean hasMagneticStripe() {
        return false; // Brazilian CNH doesn't have magnetic stripe
    }

    @Override
    protected String getLocalizedLabel(String key) {
        return switch (key) {
            case "firstName" -> "Nome";
            case "lastName" -> "Sobrenome";
            case "dob" -> "Data de Nascimento";
            case "address" -> "Endereço";
            case "city" -> "Cidade";
            case "licenseNumber" -> "Registro";
            case "issueDate" -> "Data de Emissão";
            case "expiryDate" -> "Validade";
            case "class" -> "Categoria";
            default -> super.getLocalizedLabel(key);
        };
    }
}
