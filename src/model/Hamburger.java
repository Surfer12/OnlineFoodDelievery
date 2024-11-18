package model;

public class Hamburger extends MenuItem {
   private int quantity;
   private boolean hasCheese;
   private boolean hasBacon;

   public Hamburger(Long id, String name, String description, double price, int quantity) {
      super(id, name, description, price, "HAMBURGER");
      this.quantity = quantity;
   }

   @Override
   public double calculateTotal() {
      double basePrice = getPrice() * quantity;
      if (hasCheese) basePrice += 0.50;
      if (hasBacon) basePrice += 1.00;
      return basePrice;
   }

   @Override
   public int getQuantity() {
      return quantity;
   }

   public void addCheese() {
      this.hasCheese = true;
   }

   public void addBacon() {
      this.hasBacon = true;
   }
}
