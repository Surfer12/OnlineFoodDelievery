package factory;

import menu.MenuItem;
import menu.Hamburger;
import menu.Drink;
import menu.Fries;
import menu.Size;

public class MenuItemFactory {
   private static long nextId = 1;

   public MenuItem createMenuItem(String type, String name, String description, double basePrice) {
      return switch (type.toLowerCase()) {
         case "hamburger" -> new Hamburger(nextId++, name, description, basePrice);
         case "drink" -> new Drink(nextId++, name, description, basePrice, Size.MEDIUM);
         case "fries" -> new Fries(nextId++, name, description, basePrice, Size.MEDIUM);
         default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
      };
   }

   public MenuItem createCustomMenuItem(String type, String name, String description,
         double basePrice, Size size) {
      return switch (type.toLowerCase()) {
         case "drink" -> new Drink(nextId++, name, description, basePrice, size);
         case "fries" -> new Fries(nextId++, name, description, basePrice, size);
         default -> throw new IllegalArgumentException("Size not applicable for " + type);
      };
   }
}