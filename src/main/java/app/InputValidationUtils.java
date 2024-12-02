package app;

import java.util.regex.Pattern;

public class InputValidationUtils {
    // Email validation regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    /**
     * Validates email format
     * @param email Email to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean validateEmailFormat(final String email) {
        if (email == null) {
            return false;
        }
        return InputValidationUtils.EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates location input
     * @param location Location to validate
     * @return true if location is valid, false otherwise
     */
    public static boolean validateLocation(final String location) {
        // Basic validation - non-empty and reasonable length
        return location != null && 
               !location.trim().isEmpty() && 
               location.trim().length() <= 100;
    }
} 