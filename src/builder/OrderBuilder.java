package builder;

import model.MenuItem;
import model.Order;
import location.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrderBuilder class is responsible for constructing and validating Order objects.
 * It follows the Builder design pattern to provide a flexible and readable way to create orders.
 */
public class OrderBuilder {
   private Long customerId;
   private List<MenuItem> items = new ArrayList<>();
   private Location deliveryLocation;
   private String customerEmail;

   /**
    * Sets the customer ID for the order and validates it.
    *
    * @param customerId the ID of the customer
    * @return the current instance of OrderBuilder
    * @throws IllegalArgumentException if the customer ID is null or invalid
    */
   public OrderBuilder withValidatedCustomerId(Long customerId) {
      try {
         if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
         }
         this.customerId = customerId;
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withValidatedCustomerId: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Sets the customer email for the order.
    *
    * @param email the email of the customer
    * @return the current instance of OrderBuilder
    */
   public OrderBuilder withCustomerEmail(String email) {
      this.customerEmail = email;
      return this;
   }

   /**
    * Adds a menu item to the order.
    *
    * @param item the menu item to add
    * @return the current instance of OrderBuilder
    */
   public OrderBuilder addItem(MenuItem item) {
      this.items.add(item);
      return this;
   }

   /**
    * Sets the delivery location for the order and validates it.
    *
    * @param location the delivery location
    * @return the current instance of OrderBuilder
    * @throws IllegalArgumentException if the delivery location is null
    */
   public OrderBuilder withValidatedDeliveryLocation(Location location) {
      try {
         if (location == null) {
            throw new IllegalArgumentException("Delivery location cannot be null");
         }
         this.deliveryLocation = location;
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withValidatedDeliveryLocation: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Builds and returns the Order object after validating the required fields.
    *
    * @return the constructed Order object
    * @throws IllegalStateException if any required field is missing or invalid
    */
   public Order build() {
      try {
         validateOrderRequirements();
      } catch (IllegalStateException e) {
         System.err.println("Error in build: " + e.getMessage());
         throw e;
      }
      return new Order(customerId, items, deliveryLocation, customerEmail);
   }

   /**
    * Validates the required fields for the order.
    *
    * @throws IllegalStateException if any required field is missing or invalid
    */
   private void validateOrderRequirements() {
      List<String> validationErrors = new ArrayList<>();

      if (customerId == null) {
         validationErrors.add("Customer ID is required");
      }
      if (items.isEmpty()) {
         validationErrors.add("Order must contain at least one item");
      }
      if (deliveryLocation == null) {
         validationErrors.add("Delivery location is required");
      }
      if (customerEmail == null || customerEmail.isEmpty()) {
         validationErrors.add("Customer email is required");
      }

      if (!validationErrors.isEmpty()) {
         throw new IllegalStateException("Order validation failed: " +
               String.join(", ", validationErrors));
      }
   }
}
