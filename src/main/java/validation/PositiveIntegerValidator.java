package validation;

import utils.Validator;

public class PositiveIntegerValidator implements Validator<Integer> {
    @Override
    public boolean validate(Integer input) {
        return input != null && input > 0;
    }

    @Override
    public Integer parse(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getTypeName() {
        return "Positive Integer";
    }
}