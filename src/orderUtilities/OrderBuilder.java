package orderUtilities;

import java.util.ArrayList;
import java.util.List;
import menu.MenuItem;
import model.Order;
import location.Location;
import utils.InputValidationUtils;

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
      InputValidationUtils.validateTextInput(email, "Customer email");
      InputValidationUtils.validateEmailFormat(email);
      this.customerEmail = email;
      return this;
   }

   public OrderBuilder addItem(MenuItem item) {
      if (item == null) {
         throw new IllegalArgumentException("Menu item cannot be null");
      }
      this.items.add(item);
      return this;
   }

   public OrderBuilder withItems(List<MenuItem> items) {
      if (items == null || items.isEmpty()) {
         throw new IllegalArgumentException("Items list cannot be null or empty");
      }
      this.items = new ArrayList<>(items); // Create defensive copy
      return this;
   }

   public OrderBuilder withValidatedDeliveryLocation(String address, String zipcode) {
      InputValidationUtils.validateTextInput(address, "Address");
      InputValidationUtils.validateTextInput(zipcode, "Zipcode");
      this.deliveryLocation = new Location(zipcode, address);
      return this;
   }

   public OrderBuilder withValidatedPhoneNumber(String phoneNumber) {
      InputValidationUtils.validateTextInput(phoneNumber, "Phone number");
      InputValidationUtils.validatePhoneNumber(phoneNumber);
      // Store phone number if needed
      return this;
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
      if (customerEmail == null || customerEmail.trim().isEmpty()) {
         validationErrors.add("Customer email is required");
      }

      if (!validationErrors.isEmpty()) {
         throw new IllegalStateException("Order validation failed: " +
               String.join(", ", validationErrors));
      }
   }

   public Order build() {
      validateOrderRequirements();
      return new Order(customerId, items, deliveryLocation, customerEmail);
   }
}