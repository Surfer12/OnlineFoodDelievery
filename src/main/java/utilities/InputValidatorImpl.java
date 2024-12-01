package utilities;

public class InputValidatorImpl<T> implements InputValidator<T> {
    private final Validator<T> validator;
    private final String typeName;

    public InputValidatorImpl(final Validator<T> validator, final String typeName) {
        this.validator = validator;
        this.typeName = typeName;
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
}