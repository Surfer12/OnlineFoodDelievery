package factory;

import model.MenuItem;
import model.Size;

public class MenuItemFactory {
   public MenuItem createMenuItem(
         final String type,
         final String name,
         final String description,
         final double price,
         final Size size,
         final int quantity) {
      // Create a concrete implementation of MenuItem
      return new MenuItem(type, name, description, price, size, quantity) {
         @Override
         public double calculateTotal() {
            return price * quantity;
         }
      };
   }

   // Overloaded method for compatibility
   public MenuItem createMenuItem(
         final String type,
         final String name,
         final String description,
         final double price,
         final int quantity) {
      return this.createMenuItem(type, name, description, price, Size.MEDIUM, quantity);
   }
}
