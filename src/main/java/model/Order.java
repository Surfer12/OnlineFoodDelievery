package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
   private final Long customerId;
   private final String customerEmail;
   private final List<MenuItem> items;
   private final Location deliveryLocation;
   private final Long orderId;
   private double totalAmount;
   private LocalDateTime estimatedDeliveryTime;

   public Order(final Long customerId, final String customerEmail, final List<MenuItem> items,
         final Location deliveryLocation) {
      this.customerId = customerId;
      this.customerEmail = customerEmail;
      this.items = items;
      this.deliveryLocation = deliveryLocation;
      this.orderId = System.currentTimeMillis(); // Simple ID generation
      this.calculateTotalAmount();
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

   private void calculateTotalAmount() {
      this.totalAmount = this.items.stream()
            .mapToDouble(MenuItem::getPrice)
            .sum();
   }

   public void setEstimatedDeliveryTime(final LocalDateTime estimatedDeliveryTime) {
      this.estimatedDeliveryTime = estimatedDeliveryTime;
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

   public double getTotalAmount() {
      return this.totalAmount;
   }

   public LocalDateTime getEstimatedDeliveryTime() {
      return this.estimatedDeliveryTime;
   }
}
