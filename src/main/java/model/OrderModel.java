package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
   private final Long customerId;
   private final String customerEmail;
   private final List<MenuItem> items;
   private final Location deliveryLocation;
   private final Long orderId;
   private String deliveryAddress;
   private String postalCode;
   private double totalAmount;
   private LocalDateTime estimatedDeliveryTime;
   private OrderStatus status;
   private Driver driver;

// Convenience constructor for single item
public OrderModel(final Long customerId, final String customerEmail, final MenuItem item, final String address,
final String postalCode) {
   this(customerId, customerEmail, List.of(item), new Location(address, postalCode));
}

   public OrderModel(final Long customerId, final String customerEmail, final List<MenuItem> items,
         final Location deliveryLocation) {
      this.customerId = customerId;
      this.customerEmail = customerEmail;
      this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
      this.deliveryLocation = deliveryLocation;
      this.orderId = System.currentTimeMillis(); // Simple ID generation
      this.calculateTotalAmount();
      this.status = OrderStatus.SUBMITTED;
   }


   public Order(Long customerId, String customerEmail, List<MenuItem> items, String deliveryAddress, String postalCode) {
      this.customerId = customerId;
      this.customerEmail = customerEmail;
      this.items = items;
      this.deliveryAddress = deliveryAddress;
      this.postalCode = postalCode;
      this.deliveryLocation = new Location(deliveryAddress, postalCode); // Added initialization
      this.orderId = System.currentTimeMillis(); // Simple ID generation
      this.status = OrderStatus.SUBMITTED;
      this.totalAmount = calculateTotalAmount();
   }

   private double calculateTotalAmount() {
      return this.items.stream()
            .mapToDouble(MenuItem::getPrice)
            .sum();
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
      return new ArrayList<>(this.items);
   }

   public Location getDeliveryLocation() {
      return this.deliveryLocation;
   }

   public double getTotalAmount() {
      return this.totalAmount;
   }

   public OrderStatus getStatus() {
      return this.status;
   }

   public void setStatus(OrderStatus status) {
      this.status = status;
   }

   public LocalDateTime getEstimatedDeliveryTime() {
      return this.estimatedDeliveryTime;
   }

   public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
      this.estimatedDeliveryTime = estimatedDeliveryTime;
   }

   public Long getId() {
      return this.orderId;
   }

   public Driver getDriver() {
      return this.driver;
   }

   public void setDriver(Driver driver) {
      this.driver = driver;
   }
}
