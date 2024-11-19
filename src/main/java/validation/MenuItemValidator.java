package validation;

import ConsoleInputValidator.InputValidator;
import model.MenuItem;

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
      // Logic to determine which subclass to instantiate
      return new MenuItem(name, price, available, description, category);
   }

   @Override
   public boolean isValid(MenuItem value) {
      // Check if the MenuItem has a valid price and name
      return value != null && value.getPrice() > 0 && !value.getName().isEmpty();
   }
}
