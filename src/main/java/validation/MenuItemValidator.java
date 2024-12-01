package validation;

public class MenuItemValidator implements InputValidator<Integer> {
    private final int maxMenuSize;

    public MenuItemValidator(int maxMenuSize) {
        this.maxMenuSize = maxMenuSize;
    }

    @Override
    public boolean isValid(Integer input) {
        return input != null && input > 0 && input <= this.maxMenuSize;
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
