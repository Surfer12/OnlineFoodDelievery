package menu;

import java.util.List;

import menu.MenuItem;
import menu.Size;

public class Drink extends MenuItem {
   private Size size;

   public Drink(Long id, String name, String description, double basePrice, Size size) {
      super(id, name, description, basePrice, "DRINK", 2); // 2 minutes prep time
      this.size = size;
   }

   @Override
   public double calculateTotal() {
      return getPrice() * size.getPriceMultiplier();
   }
}