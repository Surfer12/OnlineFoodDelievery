package model;

public class Hamburger extends MenuItem {
   private int quantity;
   private String type;
   private long id;
   private boolean hasCheese;
   private boolean hasBacon;

   public Hamburger(long id, String name, String description, double basePrice, Size size, int quantity) {
      super(id, name, description, basePrice, "HAMBURGER");
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

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public void setType(String type) {
      this.type = type;
   }

   public void addCheese() {
      this.hasCheese = true;
   }

   public void addBacon() {
      this.hasBacon = true;
   }

   public String getType() {
      return type;
   }
}


