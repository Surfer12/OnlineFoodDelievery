package model;

public class Hamburger extends MenuItem {
   private int quantity;

   public Hamburger(Long id, String name, String description, double basePrice, Size size, int quantity) {
      super(id, name, description, basePrice, "HAMBURGER");
      this.quantity = quantity;
   }

   @Override
   public double calculateTotal() {
      return getPrice();
   }

   @Override
   public int getQuantity() {
      return quantity;
   }
}