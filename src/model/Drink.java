package model;

public class Drink extends MenuItem {
   private Size size;
   private int quantity;

   public Drink(Long id, String name, String description, double basePrice, Size size, int quantity) {
      super(id, name, description, basePrice, "DRINK");
      this.size = size;
      this.quantity = quantity;
   }

   @Override
   public int getQuantity() {
      return quantity;
   }

   @Override
   public double calculateTotal() {
      return getPrice() * size.getPriceMultiplier() * quantity;
   }

   public Size getSize() {
      return size;
   }

   public void setSize(Size size) {
      this.size = size;
   }
}
