package builder;

import java.util.List;

import model.MenuItem;
import model.Order;

public class OrderBuilder {
   private Long customerId;
   private String customerEmail;
   private List<MenuItem> items;
   private String deliveryAddress;
   private String postalCode;

   public OrderBuilder withValidatedCustomerId(final Long customerId) {
      this.customerId = customerId;
      return this;
   }

   public OrderBuilder withCustomerEmail(final String email) {
      this.customerEmail = email;
      return this;
   }

   public OrderBuilder withItems(final List<MenuItem> items) {
      this.items = items;
      return this;
   }

   public OrderBuilder withDeliveryLocation(final String address, final String postalCode) {
      this.deliveryAddress = address;
      this.postalCode = postalCode;
      return this;
   }

   public Order build() {
      // Create a concrete implementation of Order
      return new Order(this.customerId, this.customerEmail, this.items, this.deliveryAddress, this.postalCode);
   }

   public Order createOrder(final List<MenuItem> orderItems) throws CustomException.QueueFullException {
      // ...existing code...
      final Long validatedCustomerId = // ...validation logic...
      final String validatedEmail = // ...validation logic...
      final String validatedAddress = // ...validation logic...
      final String validatedPostalCode = // ...validation logic...

      final Order newOrder = this.withCustomerId(validatedCustomerId)
            .withCustomerEmail(validatedEmail)
            .withItems(orderItems)
            .withDeliveryLocation(validatedAddress, validatedPostalCode)
            .build();

      // ...existing code...

      return newOrder;
   }
}

      // Validate order items
      if (orderItems == null || orderItems.isEmpty()) {
         throw new OrderProcessingException("Order must contain at least one item");
      }

      // Validate delivery address
      if (this.deliveryAddress == null || this.deliveryAddress.trim().isEmpty()) {
         throw new OrderProcessingException("Delivery address cannot be empty");
      }
      final String validatedAddress = this.deliveryAddress;

      // Validate postal code
      if (this.postalCode == null || !this.postalCode.matches("^\\d{5}(-\\d{4})?$")) {
         throw new OrderProcessingException("Invalid postal code");
      }
      final String validatedPostalCode = this.postalCode;

      // Create and return the new order
      final Order newOrder = this.withValidatedCustomerId(validatedCustomerId)
            .withCustomerEmail(validatedEmail)
            .withItems(orderItems)
            .withDeliveryLocation(validatedAddress, validatedPostalCode)
            .build();

      return newOrder;
   }
}
