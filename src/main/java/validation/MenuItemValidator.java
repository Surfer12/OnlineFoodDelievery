package validation;

import ConsoleInputValidator.InputValidator;
import model.MenuItem;
import model.Hamburger;
import model.Drink;
import model.Fries;
import model.Size;

public class MenuItemValidator implements InputValidator.Validator<MenuItem> {
    @Override
    public MenuItem parse(String input) {
        // Assuming input is in the format "name,price,available,description,category,size,quantity"
        String[] parts = input.split(",");
        String name = parts[0].trim();
        double price;
        boolean available;
        String description = parts.length > 3 ? parts[3].trim() : null; // Optional description
        String category = parts.length > 4 ? parts[4].trim() : null; // Optional category
        Size size = parts.length > 5 ? Size.valueOf(parts[5].trim().toUpperCase()) : Size.MEDIUM; // Default size
        int quantity = parts.length > 6 ? Integer.parseInt(parts[6].trim()) : 1; // Default quantity
        try {
            price = Double.parseDouble(parts[1].trim());
            available = Boolean.parseBoolean(parts[2].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error in parse: Invalid input format. Please provide input in the format 'name,price,available,description,category,size,quantity'.");
            throw new IllegalArgumentException(
                    "Invalid input format. Please provide input in the format 'name,price,available,description,category,size,quantity'.",
                    e);
        }
        return createMenuItem(name, price, available, description, category, size, quantity);
    }

    private MenuItem createMenuItem(String name, double price, boolean available, String description, String category, Size size, int quantity) {
        // Example of creating different MenuItem subclasses based on category
        switch (category.toLowerCase()) {
            case "hamburger":
                return new Hamburger(0L, name, description, price, size, quantity);
            case "drink":
                return new Drink(0L, name, description, price, size, quantity);
            case "fries":
                return new Fries(0L, name, description, price, size, quantity);
            default:
                return new MenuItem(0L, name, description, price, category) {
                    @Override
                    public double calculateTotal() {
                        return price * quantity;
                    }

                    @Override
                    public int getQuantity() {
                        return quantity;
                    }
                };
        }
    }

    @Override
    public boolean isValid(MenuItem value) {
        // Check if the MenuItem has a valid price and name
        return value != null && value.getPrice() > 0 && !value.getName().isEmpty();
    }
}
