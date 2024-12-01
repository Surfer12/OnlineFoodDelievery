package validation;

public class PositiveIntegerValidator implements Validator<Integer> {

    public PositiveIntegerValidator() {
    }
    @Override
    public Integer parse(final String input) {
        return Integer.parseInt(input);
    }

    @Override
    public boolean isValid(final Integer value) {
        return value > 0;
    }

    public String getTypeName() {
        return "positive integer";
    }
}