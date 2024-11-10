package model;

public class Fries extends MenuItem {
   private Size size;
   private boolean seasoned;
   private int quantity;

   public Fries(Long id, String name, String description, double basePrice, Size size, int quantity) {
      super(id, name, description, basePrice, "FRIES");
      this.size = size;
      this.quantity = quantity;
   }

   @Override
   public int getQuantity() {
      return quantity;
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