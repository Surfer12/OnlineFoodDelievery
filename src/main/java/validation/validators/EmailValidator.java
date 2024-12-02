package validation.validators;

import validation.InputValidator;

import java.util.regex.Pattern;

public class EmailValidator implements InputValidator<String> {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EmailValidator.EMAIL_REGEX);

    @Override
    public String parse(final String input) {
        return input.trim();
    }

    @Override
    public boolean isValid(final String email) {
        return email != null
                && !email.isEmpty()
                && EmailValidator.EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String getErrorMessage() {
        return "Invalid email address. Please enter a valid email format (e.g., username@example.com).";
    }

    @Override
    public String getTypeName() {
        return "email address";
    }
}