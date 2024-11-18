package model;

import java.util.Optional;

/**
 * Represents a menu item in the system.
 */
public abstract class MenuItem {
   private Long id;
   private String name;
   private String description;
   private double price;
   private String category;
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
   public MenuItem(Long id, String name, String description, double price, String category) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
      this.available = true;
      validatePrice(price);
   }

   /**
    * Updates the price of the menu item.
    *
    * @param price the new price of the menu item
    */
   public void updatePrice(double price) {
      validatePrice(price);
      this.price = price;
   }

   /**
    * Checks if the menu item is available.
    *
    * @return true if the menu item is available, false otherwise
    */
   public boolean isAvailable() {
      return available;
   }

   /**
    * Gets the details of the menu item.
    *
    * @return a string containing the details of the menu item
    */
   public String getDetails() {
      return String.format("%s - %s ($%.2f)", name, description, price);
   }

   /**
    * Gets the ID of the menu item.
    *
    * @return the ID of the menu item
    */
   public Long getId() {
      return id;
   }

   /**
    * Gets the name of the menu item.
    *
    * @return the name of the menu item
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the availability status of the menu item.
    *
    * @param available the new availability status of the menu item
    */
   public void setAvailable(boolean available) {
      this.available = available;
   }

   /**
    * Gets the description of the menu item.
    *
    * @return an Optional containing the description of the menu item, or an empty Optional if no description is provided
    */
   public Optional<String> getDescription() {
      return Optional.ofNullable(description);
   }

   /**
    * Gets the price of the menu item.
    *
    * @return the price of the menu item
    */
   public double getPrice() {
      return price;
   }

   /**
    * Gets the category of the menu item.
    *
    * @return an Optional containing the category of the menu item, or an empty Optional if no category is provided
    */
   public Optional<String> getCategory() {
      return Optional.ofNullable(category);
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
   protected void validatePrice(double price) {
      if (price <= 0) {
         throw new IllegalArgumentException("Price must be greater than zero");
      }
   }
}
