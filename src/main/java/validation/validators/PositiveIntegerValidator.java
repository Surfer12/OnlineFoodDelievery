package validation.validators;

import validation.InputValidator;

public class PositiveIntegerValidator implements InputValidator<Integer> {
    @Override
    public Integer parse(final String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    @Override
    public boolean isValid(final Integer value) {
        return value != null && value > 0;
    }

    @Override
    public String getErrorMessage() {
        return "Input must be a positive integer greater than zero.";
    }

    @Override
    public String getTypeName() {
        return "positive integer";
    }
}