package model;

import java.util.List;

public abstract class Order {
   private final Long customerId;
   private final String customerEmail;
   private final List<MenuItem> items;
   private final Location deliveryLocation;
   private final Long orderId;

   public Order(final Long customerId, final String customerEmail, final List<MenuItem> items,
         final Location deliveryLocation) {
      this.customerId = customerId;
      this.customerEmail = customerEmail;
      this.items = items;
      this.deliveryLocation = deliveryLocation;
      this.orderId = System.currentTimeMillis(); // Simple ID generation
   }

   // Convenience constructor for single item
   public Order(final Long customerId, final String customerEmail, final MenuItem item, final String address,
         final String postalCode) {
      this(
            customerId,
            customerEmail,
            List.of(item),
            new Location(address, postalCode));
   }

   public Long getOrderId() {
      return this.orderId;
   }

   public Long getCustomerId() {
      return this.customerId;
   }

   public String getCustomerEmail() {
      return this.customerEmail;
   }

   public List<MenuItem> getItems() {
      return this.items;
   }

   public Location getDeliveryLocation() {
      return this.deliveryLocation;
   }
}
