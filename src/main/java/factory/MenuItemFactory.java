package factory;

import model.Drink;
import model.Fries;
import model.Hamburger;
import model.MenuItem;
import model.Size;

public class MenuItemFactory {
   public static MenuItem createMenuItem(final String type, final String name, final String description,
         final double price, final Size size, final int quantity) {
      return switch (type.toLowerCase()) {
         case "hamburger" -> new Hamburger(0L, name, description, price, size, quantity);
         case "drink" -> new Drink(0L, name, description, price, size, quantity);
         case "fries" -> new Fries(0L, name, description, price, size, quantity);
         default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
      };
   }

   public static MenuItem createMenuItem(final String type, final String name, final String description,
         final double price, final int quantity) {
      return MenuItemFactory.createMenuItem(type, name, description, price, Size.MEDIUM, quantity);
   }
}
