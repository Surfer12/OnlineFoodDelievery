package factory;

import model.Drink;
import model.Fries;
import model.Hamburger;
import model.MenuItem;
import model.Size;

public class MenuItemFactory {
   private static long nextId = 1;

   public MenuItem createMenuItem(String type, String name, String description, double basePrice, Size size,
         int quantity) {
      try {
         return switch (type.toLowerCase()) {
            case "hamburger" -> new Hamburger(nextId++, name, description, basePrice, size, quantity);
            case "drink" -> new Drink(nextId++, name, description, basePrice, size, quantity);
            case "fries" -> new Fries(nextId++, name, description, basePrice, size, quantity);
            default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
         };
      } catch (IllegalArgumentException e) {
         System.err.println("Error in createMenuItem: " + e.getMessage());
         throw e;
      }
   }

   public MenuItem createCustomMenuItem(String type, String name, String description,
         double basePrice, Size size, int quantity) {
      try {
         return switch (type.toLowerCase()) {
            case "drink" -> new Drink(nextId++, name, description, basePrice, size, 1);
            case "fries" -> new Fries(nextId++, name, description, basePrice, size, 1);
            default -> throw new IllegalArgumentException("Size not applicable for " + type);
         };
      } catch (IllegalArgumentException e) {
         System.err.println("Error in createCustomMenuItem: " + e.getMessage());
         throw e;
      }
   }
}
