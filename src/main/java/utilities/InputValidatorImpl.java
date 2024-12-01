package utilities;

public class InputValidatorImpl<T> implements InputValidator<T> {
    private final Validator<T> validator;
    private final String typeName;

    private final String errorMessage;

    public InputValidatorImpl(final Validator<T> validator, final String typeName, final String errorMessage) {
        this.validator = validator;
        this.typeName = typeName;
        this.errorMessage = errorMessage;
    }

    @Override
    public T parse(final String input) {
        return this.validator.parse(input);
    }

    @Override
    public boolean isValid(final T value) {
        return this.validator.isValid(value);
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override

    public String getErrorMessage() {
        return this.errorMessage;

    }

}
