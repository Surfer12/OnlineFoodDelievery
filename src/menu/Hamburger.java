package menu;

import java.util.ArrayList;
import java.util.List;

public class Hamburger extends MenuItem {
   private List<Topping> toppings;

   public Hamburger(Long id, String name, String description, double basePrice) {
      super(id, name, description, basePrice, 10); // 10 minutes prep time
      this.category = "HAMBURGER";
      this.toppings = new ArrayList<>();
   }

   public void addTopping(Topping topping) {
      toppings.add(topping);
   }

   @Override
   public double calculateTotal() {
      return price + toppings.stream()
            .mapToDouble(Topping::getPrice)
            .sum();
   }
}