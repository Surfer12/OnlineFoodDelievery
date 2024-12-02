package validation.validators;

import validation.InputValidator;

public class LocationValidator implements InputValidator<String> {
    @Override
    public String parse(final String input) {
        return input.trim();
    }

    @Override
    public boolean isValid(final String location) {
        return location != null
                && !location.trim().isEmpty()
                && location.length() >= 5 // Minimum meaningful address length
                && location.matches("^[a-zA-Z0-9\\s.,'-]+$"); // Basic address characters
    }

    @Override
    public String getErrorMessage() {
        return "Invalid delivery address. Please enter a complete and valid address.";
    }

    @Override
    public String getTypeName() {
        return "delivery address";
    }
}