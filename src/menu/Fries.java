package menu;

public class Fries extends MenuItem {
   private Size size;
   private boolean seasoned;

   public Fries(Long id, String name, String description, double basePrice, Size size) {
      super(id, name, description, basePrice, "FRIES", 5); // 5 minutes prep time
      this.size = size;
      this.seasoned = false;
   }

   public void addSeasoning() {
      this.seasoned = true;
   }

   public Size getSize() {
      return size;
   }

   @Override
   public double calculateTotal() {
      double total = getPrice() * size.getPriceMultiplier();
      return seasoned ? total + 0.50 : total;
   }
}