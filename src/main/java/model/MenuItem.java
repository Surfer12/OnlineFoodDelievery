package model;

public abstract class MenuItem {
   private final String type;
   private final String name;
   private final String description;
   private final double price;
   private final Size size;
   private final int quantity;

   public MenuItem(final String type, final String name, final String description, final double price, final Size size,
         final int quantity) {
      this.type = type;
      this.name = name;
      this.description = description;
      this.price = price;
      this.size = size;
      this.quantity = quantity;
   }

   public abstract double calculateTotal();

   public int getQuantity() {
      return this.quantity;
   }

   // Getters for other fields
   public String getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public double getPrice() {
      return this.price;
   }

   public Size getSize() {
      return this.size;
   }
}
