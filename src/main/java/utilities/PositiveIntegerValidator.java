package utilities;

public class PositiveIntegerValidator implements Validator<Integer> {
    @Override
    public boolean isValid(final Integer value) {
        return value != null && value > 0;
    }
}