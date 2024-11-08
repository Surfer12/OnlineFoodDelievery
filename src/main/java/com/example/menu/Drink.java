public class Drink extends MenuItem {
   private Size size;

   public Drink(Long id, String name, String description, double basePrice, Size size) {
      super(id, name, description, basePrice, 2); // 2 minutes prep time
      this.category = "DRINK";
      this.size = size;
   }

   @Override
   public double calculateTotal() {
      return price * size.getPriceMultiplier();
   }
}