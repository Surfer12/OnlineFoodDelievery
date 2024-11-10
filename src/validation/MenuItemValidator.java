package validation;

import ConsoleInputValidator.InputValidator.Validator;
import menu.MenuItem;

public class MenuItemValidator implements Validator<MenuItem> {
   @Override
   public MenuItem parse(String input) {
      // Assuming input is in the format "name,price,available"
      String[] parts = input.split(",");
      String name = parts[0].trim();
      double price = Double.parseDouble(parts[1].trim());
      boolean available = Boolean.parseBoolean(parts[2].trim());
      return new MenuItem(name, price, available);
   }

   @Override
   public boolean isValid(MenuItem value) {
      return value != null && value.getPrice() > 0 && !value.getName().isEmpty();
   }
}
