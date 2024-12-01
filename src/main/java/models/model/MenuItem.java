package src.main.java.models.model;

import java.util.Optional;

/**
 * Represents a menu item in the system.
 */
public abstract class MenuItem {
   private final Long id;
   private final String name;
   private final String description;
   private double price;
   private final String category;
   private boolean available;

   /**
    * Constructs a MenuItem with the specified details.
    *
    * @param id          the ID of the menu item
    * @param name        the name of the menu item
    * @param description the description of the menu item
    * @param price       the price of the menu item
    * @param category    the category of the menu item
    */
   public MenuItem(final Long id, final String name, final String description, final double price, final String category) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
      this.available = true;
   }

   /**
    * Updates the price of the menu item.
    *
    * @param price the new price of the menu item
    */
   public void updatePrice(final double price) {
      this.validatePrice(price);
      this.price = price;
   }


   /**
    * Checks if the menu item is available.
    *
    * @return true if the menu item is available, false otherwise
    */
   public boolean isAvailable() {
      return this.available;
   }

   /**
    * Gets the details of the menu item.
    *
    * @return a string containing the details of the menu item
    */
   public String getDetails() {
      return String.format("%s - %s ($%.2f)", this.name, this.description, this.price);
   }

   /**
    * Gets the ID of the menu item.
    *
    * @return the ID of the menu item
    */
   public Long getId() {
      return this.id;
   }

   /**
    * Gets the name of the menu item.
    *
    * @return the name of the menu item
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the availability status of the menu item.
    *
    * @param available the new availability status of the menu item
    */
   public void setAvailable(final boolean available) {
      this.available = available;
   }

   /**
    * Gets the description of the menu item.
    *
    * @return an Optional containing the description of the menu item, or an empty Optional if no description is provided
    */
   public Optional<String> getDescription() {
      return Optional.ofNullable(this.description);
   }

   /**
    * Gets the price of the menu item.
    *
    * @return the price of the menu item
    */
   public double getPrice() {
      return this.price;
   }

   /**
    * Gets the category of the menu item.
    *
    * @return an Optional containing the category of the menu item, or an empty Optional if no category is provided
    */
   public Optional<String> getCategory() {
      return Optional.ofNullable(this.category);
   }

   /**
    * Calculates the total price of the menu item.
    *
    * @return the total price of the menu item
    */
   public abstract double calculateTotal();

   /**
    * Gets the quantity of the menu item.
    *
    * @return the quantity of the menu item
    */
   public abstract int getQuantity();

   /**
    * Validates the price of the menu item.
    *
    * @param price the price to validate
    */
   protected void validatePrice(final double price) {
      if (price <= 0) {
         throw new IllegalArgumentException("Price must be greater than zero");
      }
   }
}
