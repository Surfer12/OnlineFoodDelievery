package builder;

import order.Order;
import menu.MenuItem;
import location.Location;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
   private Long customerId;
   private List<MenuItem> items = new ArrayList<>();
   private Location deliveryLocation;
   private String customerEmail;

   public OrderBuilder withCustomerId(Long customerId) {
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

   public OrderBuilder withDeliveryLocation(Location location) {
      this.deliveryLocation = location;
      return this;
   }

   public Order build() {
      validateOrder();
      return new Order(customerId, items, deliveryLocation, customerEmail);
   }

   private void validateOrder() {
      if (customerId == null) {
         throw new IllegalStateException("Order must have a customer ID");
      }
      if (items.isEmpty()) {
         throw new IllegalStateException("Order must contain at least one item");
      }
      if (deliveryLocation == null) {
         throw new IllegalStateException("Order must have a delivery location");
      }
      if (customerEmail == null || customerEmail.isEmpty()) {
         throw new IllegalStateException("Order must have a customer email");
      }
   }
}