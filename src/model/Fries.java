package model;

/**
 * Represents a Fries menu item.
 */
public class Fries extends MenuItem {
   private Size size;
   private boolean seasoned;
   private int quantity;

   /**
    * Constructs a Fries object with the specified details.
    *
    * @param id          the ID of the fries
    * @param name        the name of the fries
    * @param description the description of the fries
    * @param basePrice   the base price of the fries
    * @param size        the size of the fries
    * @param quantity    the quantity of the fries
    */
   public Fries(Long id, String name, String description, double basePrice, Size size, int quantity) {
      super(id, name, description, basePrice, "FRIES");
      this.size = size;
      this.quantity = quantity;
   }

   /**
    * Returns the quantity of the fries.
    *
    * @return the quantity of the fries
    */
   @Override
   public int getQuantity() {
      return quantity;
   }

   /**
    * Returns the size of the fries.
    *
    * @return the size of the fries
    */
   public Size getSize() {
      return size;
   }

   /**
    * Calculates the total price of the fries.
    *
    * @return the total price of the fries
    */
   @Override
   public double calculateTotal() {
      double total = getPrice() * size.getPriceMultiplier() * quantity;
      return seasoned ? total + 0.50 : total;
   }

   /**
    * Sets the size of the fries.
    *
    * @param size the new size of the fries
    */
   public void setSize(Size size) {
      this.size = size;
   }

   /**
    * Checks if the fries are seasoned.
    *
    * @return true if the fries are seasoned, false otherwise
    */
   public boolean isSeasoned() {
      return seasoned;
   }

   /**
    * Sets the seasoned status of the fries.
    *
    * @param seasoned the new seasoned status of the fries
    */
   public void setSeasoned(boolean seasoned) {
      this.seasoned = seasoned;
   }
}
