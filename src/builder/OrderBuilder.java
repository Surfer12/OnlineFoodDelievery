package builder;

import model.MenuItem;
import model.Order;
import location.Location;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
   private Long customerId;
   private List<MenuItem> items = new ArrayList<>();
   private Location deliveryLocation;
   private String customerEmail;

   public OrderBuilder withValidatedCustomerId(Long customerId) {
      if (customerId == null || customerId <= 0) {
         throw new IllegalArgumentException("Invalid customer ID");
      }
      this.customerId = customerId;
      return this;
   }

   public OrderBuilder withCustomerEmail(String email) {
      this.customerEmail = email;
      return this;
   }

   public OrderBuilder addItem(MenuItem item) {
      this.items.add(item);
      return this;
   }

   public OrderBuilder withValidatedDeliveryLocation(Location location) {
      if (location == null) {
         throw new IllegalArgumentException("Delivery location cannot be null");
      }
      this.deliveryLocation = location;
      return this;
   }

   public Order build() {
      validateOrderRequirements();
      return new Order(customerId, items, deliveryLocation, customerEmail);
   }

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