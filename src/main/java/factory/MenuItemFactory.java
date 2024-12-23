package factory;

import model.*;

public class MenuItemFactory {
   public static MenuItem createMenuItem(String type, String name, String description,
         double price, Size size, int quantity) {
      return switch (type.toLowerCase()) {
         case "pizza" -> new Pizza(null, name, description, price, size, quantity);
         case "hamburger" -> new Hamburger(null, name, description, price, size, quantity);
         case "burger" -> new Hamburger(null, name, description, price, size, quantity);
         case "fries" -> new Fries(null, name, description, price, size, quantity);
         case "salad" -> new Salad(null, name, description, price, size, quantity);
         case "drink" -> new Drink(null, name, description, price, size, quantity);
         default -> throw new IllegalArgumentException("Unknown menu item type: " + type);
      };   }
}
