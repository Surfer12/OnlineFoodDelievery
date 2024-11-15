package orderUtilities;

import java.util.ArrayList;
import java.util.List;

import model.MenuItem;
import model.Order;
import location.Location;
import utils.InputValidationUtils;

/**
 * The OrderBuilder class is used to construct Order objects with validated inputs.
 */
public class OrderBuilder {
   private Long customerId;
   private List<MenuItem> items = new ArrayList<>();
   private Location deliveryLocation;
   private String customerEmail;

   /**
    * Sets and validates the customer ID.
    *
    * @param customerId the customer ID
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the customer ID is invalid
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
    * Sets and validates the customer email.
    *
    * @param email the customer email
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the email is invalid
    */
   public OrderBuilder withCustomerEmail(String email) {
      try {
         InputValidationUtils.validateTextInput(email, "Customer email");
         InputValidationUtils.validateEmailFormat(email);
         this.customerEmail = email;
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withCustomerEmail: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Adds a menu item to the order.
    *
    * @param item the menu item
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the item is null
    */
   public OrderBuilder addItem(MenuItem item) {
      try {
         if (item == null) {
            throw new IllegalArgumentException("Menu item cannot be null");
         }
         this.items.add(item);
      } catch (IllegalArgumentException e) {
         System.err.println("Error in addItem: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Sets and validates the list of menu items.
    *
    * @param items the list of menu items
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the items list is null or empty
    */
   public OrderBuilder withItems(List<MenuItem> items) {
      try {
         if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
         }
         this.items = new ArrayList<>(items); // Create defensive copy
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withItems: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Sets and validates the delivery location.
    *
    * @param address the delivery address
    * @param zipcode the delivery zipcode
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the address or zipcode is invalid
    */
   public OrderBuilder withValidatedDeliveryLocation(String address, String zipcode) {
      try {
         InputValidationUtils.validateTextInput(address, "Address");
         InputValidationUtils.validateTextInput(zipcode, "Zipcode");
         this.deliveryLocation = new Location(zipcode, address);
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withValidatedDeliveryLocation: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Sets and validates the phone number.
    *
    * @param phoneNumber the phone number
    * @return the OrderBuilder instance
    * @throws IllegalArgumentException if the phone number is invalid
    */
   public OrderBuilder withValidatedPhoneNumber(String phoneNumber) {
      try {
         InputValidationUtils.validateTextInput(phoneNumber, "Phone number");
         InputValidationUtils.validatePhoneNumber(phoneNumber);
         // Store phone number if needed
      } catch (IllegalArgumentException e) {
         System.err.println("Error in withValidatedPhoneNumber: " + e.getMessage());
         throw e;
      }
      return this;
   }

   /**
    * Validates the order requirements.
    *
    * @throws IllegalStateException if any order requirement is not met
    */
   private void validateOrderRequirements() {
      try {
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
      } catch (IllegalStateException e) {
         System.err.println("Error in validateOrderRequirements: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Builds and returns the validated Order object.
    *
    * @return the constructed Order object
    * @throws IllegalStateException if the order validation fails
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
}
