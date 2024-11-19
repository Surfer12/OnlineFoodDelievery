package validation;

import ConsoleInputValidator.InputValidator;
import model.MenuItem;
import model.Hamburger;
import model.Drink;
import model.Fries;

public class MenuItemValidator implements InputValidator.Validator<MenuItem> {
   @Override
   public MenuItem parse(String input) {
      // Assuming input is in the format "name,price,available,description,category"
      String[] parts = input.split(",");
      String name = parts[0].trim();
      double price;
      boolean available;
      String description = parts.length > 3 ? parts[3].trim() : null; // Optional description
      String category = parts.length > 4 ? parts[4].trim() : null; // Optional category
      try {
         price = Double.parseDouble(parts[1].trim());
         available = Boolean.parseBoolean(parts[2].trim());
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
         System.err.println("Error in parse: Invalid input format. Please provide input in the format 'name,price,available,description,category'.");
         throw new IllegalArgumentException(
               "Invalid input format. Please provide input in the format 'name,price,available,description,category'.",
               e);
      }
      return createMenuItem(name, price, available, description, category);
   }

   private MenuItem createMenuItem(String name, double price, boolean available, String description, String category) {
      // Example of creating different MenuItem subclasses based on category
      switch (category.toLowerCase()) {
         case "hamburger":
            return new Hamburger(name, price, available, description);
         case "drink":
            return new Drink(name, price, available, description);
         case "fries":
            return new Fries(name, price, available, description);
         default:
            return new MenuItem(name, price, available, description, category) {};
      }
   }

   @Override
   public boolean isValid(MenuItem value) {
      // Check if the MenuItem has a valid price and name
      return value != null && value.getPrice() > 0 && !value.getName().isEmpty();
   }
}
