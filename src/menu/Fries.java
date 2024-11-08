package menu;

public class Fries extends MenuItem {
   private Size size;
   private boolean seasoned;

   public Fries(Long id, String name, String description, double basePrice, Size size) {
      super(id, name, description, basePrice, 5); // 5 minutes prep time
      this.category = "FRIES";
      this.size = size;
      this.seasoned = false;
   }

   public void addSeasoning() {
      this.seasoned = true;
   }

   @Override
   public double calculateTotal() {
      double total = price * size.getPriceMultiplier();
      return seasoned ? total + 0.50 : total;
   }
}