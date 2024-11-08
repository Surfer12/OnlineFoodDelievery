package menu;

import java.util.ArrayList;
import java.util.List;

import menu.MenuItem;
import menu.Topping;

public class Hamburger extends MenuItem {
   private List<Topping> toppings;

   public Hamburger(Long id, String name, String description, double basePrice) {
      super(id, name, description, basePrice, "HAMBURGER", 10); // 10 minutes prep time
      this.toppings = new ArrayList<>();
   }

   public void addTopping(Topping topping) {
      toppings.add(topping);
   }

   @Override
   public double calculateTotal() {
      return getPrice() + toppings.stream()
            .mapToDouble(Topping::getPrice)
            .sum();
   }
}