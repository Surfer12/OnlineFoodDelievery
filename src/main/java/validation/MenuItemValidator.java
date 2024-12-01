package validation;

import model.Drink;
import model.Fries;
import model.Hamburger;
import model.MenuItem;
import model.Size;
import utilities.Validator;

public class MenuItemValidator implements Validator<MenuItem> {
    @Override
    public MenuItem parse(final String input) {
        // Simplified parsing logic
        final String[] parts = input.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid menu item format");
        }

        final String type = parts[0].trim();
        final String name = parts[1].trim();
        final String description = parts[2].trim();
        final double price = Double.parseDouble(parts[3].trim());
        final Size size = Size.valueOf(parts[4].trim().toUpperCase());
        final int quantity = Integer.parseInt(parts[5].trim());

        return switch (type.toLowerCase()) {
            case "hamburger" -> new Hamburger(0L, name, description, price, size, quantity);
            case "drink" -> new Drink(0L, name, description, price, size, quantity);
            case "fries" -> new Fries(0L, name, description, price, size, quantity);
            default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
        };
    }

    @Override
    public boolean isValid(final MenuItem item) {
        return item != null
                && item.getName() != null && !item.getName().isEmpty()
                && item.getPrice() > 0;
    }

    public String getTypeName() {
        return "menu item";
    }

    public String getErrorMessage() {
        return "Invalid menu item";
    }

}
