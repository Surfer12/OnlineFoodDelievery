package validation;

import ConsoleInputValidator.InputValidator;
import menu.MenuItem;

public class MenuItemValidator implements InputValidator.Validator<MenuItem> {
   @Override
   public MenuItem parse(String input) {
      // Assuming input is in the format "name,price,available"
      String[] parts = input.split(",");
      String name = parts[0].trim();
      double price;
      boolean available;
      try {
         price = Double.parseDouble(parts[1].trim());
         available = Boolean.parseBoolean(parts[2].trim());
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
         throw new IllegalArgumentException(
               "Invalid input format. Please provide input in the format 'name,price,available'.", e);
      }
      return new MenuItem(name, price, available);
   }

   @Override
   public boolean isValid(MenuItem value) {
      // Check if the MenuItem has a valid price and name
      return value != null && value.getPrice() > 0 && !value.getName().isEmpty();
   }
}
