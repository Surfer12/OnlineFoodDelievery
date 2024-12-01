package validation;

public class MenuItemValidator implements Validator<Integer> {
    @Override
    public boolean validate(Integer input) {
        return input != null && input >= 1 && input <= 6;
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
        return "Menu Choice";
    }
}
