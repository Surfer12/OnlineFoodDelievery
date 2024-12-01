package utils;

import utilities.InputValidator;

public class InputValidatorImpl<T> implements InputValidator<T> {
    private final Validator<T> validator;

    public InputValidatorImpl(Validator<T> validator) {
        this.validator = validator;
    }

    @Override
    public String getTypeName() {
        return this.validator.getTypeName();
    }

    @Override
    public boolean isValid(T input) {
        return this.validator.validate(input);
    }

    @Override
    public T parse(String input) {
        return this.validator.parse(input);
    }
}