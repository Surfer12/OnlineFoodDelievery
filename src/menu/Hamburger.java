package menu;

public class Hamburger extends MenuItem {
   public Hamburger(Long id, String name, String description, double basePrice) {
      super(id, name, description, basePrice, "HAMBURGER", 10); // 10 minutes prep time
   }

   @Override
   public double calculateTotal() {
      return getPrice();
   }
}