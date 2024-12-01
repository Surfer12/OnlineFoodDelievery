
package utils;

import utilities.InputValidator;

public class InputValidatorImpl<T> implements InputValidator<T> {
    private final Validator<T> validator;

    public InputValidatorImpl(Validator<T> validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(T input) {
        return this.validator.validate(input);
    }
}