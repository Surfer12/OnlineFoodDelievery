package utilities;

public class InputValidator<T> {
    private final utilities.Validator<T> validator;
    private final String validationType;

    public InputValidator(final utilities.Validator<T> validator, final String validationType) {
        this.validator = validator;
        this.validationType = validationType;
    }

    public boolean validate(final T value) {
        return this.validator.isValid(value);
    }

    public String getValidationType() {
        return this.validationType;
    }
}