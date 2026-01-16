package com.dlgenerator.util;

import com.dlgenerator.model.StateFormat;
import com.dlgenerator.model.CountryFormat;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Comprehensive license number generator for ALL 50 US states + DC + territories
 * Implements accurate format-compliant license numbers based on actual DMV standards
 */
@Component
public class ComprehensiveLicenseNumberGenerator {

    private final Random random = new Random();

    /**
     * Generate license number for any US state or territory
     */
    public String generateForState(StateFormat state, String firstName, String lastName, String dob) {
        return switch (state) {
            case AL -> generateNumeric(7);
            case AK -> generateNumeric(7);
            case AZ -> generateLetter() + generateNumeric(8);
            case AR -> generateNumeric(8);
            case CA -> generateLetter() + generateNumeric(7);
            case CO -> generateNumeric(9);
            case CT -> generateNumeric(9);
            case DE -> generateNumeric(7);
            case FL -> generateFL();
            case GA -> generateNumeric(9);
            case HI -> generateLetter() + generateNumeric(8);
            case ID -> generateLetters(2) + generateNumeric(6) + generateLetter();
            case IL -> generateLetter() + generateNumeric(11);
            case IN -> generateNumeric(10);
            case IA -> generateNumeric(9);
            case KS -> generateKansas();
            case KY -> generateLetter() + generateNumeric(8);
            case LA -> generateNumeric(9);
            case ME -> generateNumeric(7);
            case MD -> generateMaryland();
            case MA -> generateMassachusetts();
            case MI -> generateMichigan();
            case MN -> generateLetter() + generateNumeric(12);
            case MS -> generateNumeric(9);
            case MO -> generateMissouri();
            case MT -> generateMontana();
            case NE -> generateLetter() + generateNumeric(8);
            case NV -> generateNumeric(10);
            case NH -> generateNewHampshire();
            case NJ -> generateLetter() + generateNumeric(14);
            case NM -> generateNumeric(9);
            case NY -> generateNumeric(9);
            case NC -> generateNumeric(12);
            case ND -> generateLetters(3) + generateNumeric(6);
            case OH -> generateLetters(2) + generateNumeric(6);
            case OK -> generateLetter() + generateNumeric(9);
            case OR -> generateNumeric(9);
            case PA -> generateNumeric(8);
            case RI -> generateNumeric(7);
            case SC -> generateNumeric(9);
            case SD -> generateNumeric(8);
            case TN -> generateNumeric(9);
            case TX -> generateNumeric(8);
            case UT -> generateNumeric(9);
            case VT -> generateNumeric(8);
            case VA -> generateLetter() + generateNumeric(9);
            case WA -> generateWashington(firstName, lastName);
            case WV -> generateLetter() + generateNumeric(6);
            case WI -> generateLetter() + generateNumeric(13);
            case WY -> generateNumeric(9);
            case DC -> generateNumeric(9);
            // Territories
            case AS, GU, MP, PR, VI -> generateNumeric(9);
        };
    }

    /**
     * Generate license number for international countries
     */
    public String generateForCountry(CountryFormat country, String firstName, String lastName, String dob) {
        return switch (country) {
            case UK -> generateUK(firstName, lastName, dob);
            case CANADA -> generateCanada();
            case GERMANY -> generateGermany();
            case FRANCE -> generateNumeric(12);
            case AUSTRALIA -> generateNumeric(9);
            case JAPAN -> generateNumeric(12);
            case INDIA -> generateIndia();
            case MEXICO -> generateLetter() + generateNumeric(8);
            case BRAZIL -> generateNumeric(11);
            case SPAIN -> generateNumeric(8) + generateLetter();
            case ITALY -> generateLetters(2) + generateNumeric(7) + generateLetter();
            case NETHERLANDS -> generateNumeric(10);
            case SWEDEN -> generateNumeric(8);
            case NORWAY -> generateNumeric(8);
            default -> generateLetter() + generateNumeric(7);
        };
    }

    // ========== US STATE-SPECIFIC GENERATORS ==========

    private String generateFlorida() {
        return generateLetter() + generateNumeric(12);
    }

    private String generateFL() {
        char letter = generateLetter().charAt(0);
        String num = generateNumeric(12);
        return String.format("%c-%s-%s-%s-%s",
            letter,
            num.substring(0, 3),
            num.substring(3, 6),
            num.substring(6, 9),
            num.substring(9, 12)
        );
    }

    private String generateMaryland() {
        return String.format("%c-%s-%s-%s-%s",
            generateLetter().charAt(0),
            generateNumeric(3),
            generateNumeric(3),
            generateNumeric(3),
            generateNumeric(3)
        );
    }

    private String generateMassachusetts() {
        // MA can start with S for social security based or letter for name-based
        char prefix = random.nextBoolean() ? 'S' : generateLetter().charAt(0);
        return prefix + generateNumeric(8);
    }

    private String generateMichigan() {
        return generateLetter() + " " + generateNumeric(3) + " " +
               generateNumeric(3) + " " + generateNumeric(3) + " " + generateNumeric(3);
    }

    private String generateMissouri() {
        // Missouri has multiple formats
        return generateLetter() + generateNumeric(5,9);
    }

    private String generateMontana() {
        // Montana uses multiple formats, most common is 13 digits
        if (random.nextBoolean()) {
            return generateLetter() + generateNumeric(12);
        } else {
            return generateNumeric(13);
        }
    }

    private String generateKansas() {
        // Kansas: K + 8 digits or letter + 8 digits
        return "K" + generateNumeric(8);
    }

    private String generateNewHampshire() {
        // Format: 2 digits + 3 letters + 5 digits
        return generateNumeric(2) + generateLetters(3) + generateNumeric(5);
    }

    private String generateWashington(String firstName, String lastName) {
        // Washington uses complex name-based format
        StringBuilder sb = new StringBuilder();

        // Extract from last name (up to 5 chars)
        String lastNamePart = lastName.toUpperCase().replaceAll("[^A-Z]", "");
        sb.append(lastNamePart.substring(0, Math.min(5, lastNamePart.length())));
        while (sb.length() < 5) sb.append(generateLetter());

        // Two asterisks
        sb.append("**");

        // First initial + middle initial (or random)
        sb.append(firstName.toUpperCase().charAt(0));
        sb.append(generateLetters(2));

        // 2 digit year indicator
        sb.append(generateNumeric(2));

        return sb.toString();
    }

    // ========== INTERNATIONAL GENERATORS ==========

    private String generateUK(String firstName, String lastName, String dob) {
        StringBuilder sb = new StringBuilder();

        // 5 letters from surname
        String surname = lastName.toUpperCase().replaceAll("[^A-Z]", "");
        sb.append(surname.substring(0, Math.min(5, surname.length())));
        while (sb.length() < 5) sb.append('9');

        // Date encoding: YYMDD (year, decade+month, day)
        String[] dateParts = dob.split("-");
        String year = dateParts[0].substring(2);  // YY
        int month = Integer.parseInt(dateParts[1]);
        String day = dateParts[2];

        sb.append(year);
        sb.append(String.format("%02d", month)); // month
        sb.append(day);

        // Initials
        sb.append(firstName.toUpperCase().charAt(0));
        sb.append(lastName.toUpperCase().charAt(0));

        // Check digit
        sb.append(random.nextInt(10));

        // Computer check characters
        sb.append(generateLetter());
        sb.append(generateLetter());

        return sb.toString();
    }

    private String generateCanada() {
        return generateLetter() + generateNumeric(4) + "-" +
               generateNumeric(5) + "-" + generateNumeric(5);
    }

    private String generateGermany() {
        return generateLetter() + generateNumeric(2) +
               generateLetter() + generateNumeric(6);
    }

    private String generateIndia() {
        String[] stateCodes = {"AP", "AR", "AS", "BR", "CG", "GA", "GJ", "HR", "HP", "JK",
                               "JH", "KA", "KL", "MP", "MH", "MN", "ML", "MZ", "NL", "OR",
                               "PB", "RJ", "SK", "TN", "TG", "TR", "UP", "UK", "WB", "DL"};
        String stateCode = stateCodes[random.nextInt(stateCodes.length)];
        return stateCode + generateNumeric(13);
    }

    // ========== HELPER METHODS ==========

    /**
     * Generate a single random uppercase letter
     */
    private String generateLetter() {
        return String.valueOf((char) ('A' + random.nextInt(26)));
    }

    /**
     * Generate N random uppercase letters
     */
    private String generateLetters(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append((char) ('A' + random.nextInt(26)));
        }
        return sb.toString();
    }

    /**
     * Generate N random digits as a string
     */
    private String generateNumeric(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Generate random number of digits between min and max length
     */
    private String generateNumeric(int minLength, int maxLength) {
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        return generateNumeric(length);
    }
}
