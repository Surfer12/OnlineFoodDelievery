package builder;

import model.MenuItem;
import model.Order;
import java.util.List;
import exception.CustomException; // Ensure CustomException is correctly imported

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

   public Order createOrder(List<MenuItem> orderItems) throws CustomException.QueueFullException {
      // ...existing code...

      Order newOrder = this.withCustomerId(validatedCustomerId)
              .withCustomerEmail(validatedEmail)
              .withItems(orderItems)
              .withDeliveryLocation(validatedAddress, validatedPostalCode)
              .build();

      // ...existing code...
      return newOrder;
   }
}
