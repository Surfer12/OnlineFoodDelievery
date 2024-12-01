package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import CustomException.PaymentException;
import CustomException.ValidationException;
import location.Location;
import orderUtilities.OrderStatus;
import payment.Payment;

/**
 * Represents an order in the system.
 * 
 * <p>Usage example:
 * <pre>
 * {@code
 * List<MenuItem> items = new ArrayList<>();
 * items.add(new Hamburger(1L, "Cheeseburger", "Delicious cheeseburger", 5.99, Size.MEDIUM, 1));
 * Location location = new Location("12345", "123 Main St");
 * Order order = new Order(1L, items, location, "customer@example.com");
 * order.processPayment("CREDIT_CARD");
 * }
 * </pre>
 */
public class Order {
   private Long orderId;
   private Long customerId;
   private Long driverId;
   private List<MenuItem> items;
   private OrderStatus status;
   private double totalAmount;
   private LocalDateTime orderTime;
   private Payment payment;
   private Location deliveryLocation;
   private LocalDateTime estimatedDeliveryTime;
   private String customerEmail;

   /**
    * Constructs an Order with the specified details.
    *
    * @param customerId the ID of the customer
    * @param items the list of menu items in the order
    * @param deliveryLocation the delivery location of the order
    * @param customerEmail the email of the customer
    */
   public Order(Long customerId, List<MenuItem> items, Location deliveryLocation, String customerEmail) {
      validateOrderInputs(customerId, items, deliveryLocation, customerEmail);
      this.customerId = customerId;
      this.items = new ArrayList<>(items);
      this.status = OrderStatus.PLACED;
      this.orderTime = LocalDateTime.now();
      this.totalAmount = calculateTotal();
      this.deliveryLocation = new Location(deliveryLocation.getZipcode(), deliveryLocation.getAddress());
      this.customerEmail = customerEmail;
   }

   private void validateOrderInputs(Long customerId, List<MenuItem> items, Location deliveryLocation, String customerEmail) {
      List<String> errors = new ArrayList<>();
      
      if (customerId == null || customerId <= 0) {
         errors.add("Invalid customer ID");
      }
      if (items == null || items.isEmpty()) {
         errors.add("Order must contain at least one item");
      }
      if (deliveryLocation == null) {
         errors.add("Delivery location is required");
      }
      if (customerEmail == null || !isValidEmail(customerEmail)) {
         errors.add("Valid customer email is required");
      }
      
      if (!errors.isEmpty()) {
         throw new ValidationException("Order validation failed: " + String.join(", ", errors));
      }
   }

   /**
    * Adds a menu item to the order.
    *
    * @param item the menu item to add
    * @throws IllegalStateException if the order has already been accepted
    * @throws ValidationException if the item is null or not available
    */
   public void addItem(MenuItem item) {
      try {
         if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Cannot modify order after it has been accepted");
         }

         if (item == null) {
            throw new ValidationException("Cannot add null item to order");
         }

         if (!item.isAvailable()) {
            throw new ValidationException("Item " + item.getName() + " is not available");
         }

         items.add(item);
         this.totalAmount = calculateTotal();
      } catch (IllegalStateException | ValidationException e) {
         System.err.println("Error in addItem: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Removes a menu item from the order.
    *
    * @param item the menu item to remove
    * @throws IllegalStateException if the order has already been accepted
    */
   public void removeItem(MenuItem item) {
      try {
         if (status == OrderStatus.PLACED) {
            items.remove(item);
            this.totalAmount = calculateTotal();
         } else {
            throw new IllegalStateException("Cannot modify order after it has been accepted");
         }
      } catch (IllegalStateException e) {
         System.err.println("Error in removeItem: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Calculates the total amount of the order.
    *
    * @return the total amount of the order
    */
   public double calculateTotal() {
      return items.stream()
            .mapToDouble(MenuItem::calculateTotal)
            .sum();
   }

   /**
    * Updates the status of the order.
    *
    * @param status the new status of the order
    */
   public void updateStatus(OrderStatus status) {
      validateStatusTransition(this.status, status);
      this.status = status;
   }

   private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
      if (!isValidStatusTransition(currentStatus, newStatus)) {
         throw new IllegalStateException(
            String.format("Invalid status transition from %s to %s", currentStatus, newStatus)
         );
      }
   }

   private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {
      return switch (current) {
         case PLACED -> next == OrderStatus.ACCEPTED;
         case ACCEPTED -> next == OrderStatus.IN_DELIVERY;
         case IN_DELIVERY -> next == OrderStatus.DELIVERED;
         case DELIVERED -> false;
      };
   }

   /**
    * Sets the driver ID for the order.
    *
    * @param driverId the ID of the driver
    */
   public void setDriverId(Long driverId) {
      this.driverId = driverId;
   }

   /**
    * Sets the payment for the order.
    *
    * @param payment the payment to set
    */
   public void setPayment(Payment payment) {
      this.payment = payment;
   }

   /**
    * Processes the payment for the order.
    *
    * @param paymentMethod the payment method to use
    * @throws IllegalStateException if the payment has already been processed
    * @throws PaymentException if the payment processing fails
    */
   public void processPayment(String paymentMethod) {
      try {
         if (payment != null) {
            throw new IllegalStateException("Payment has already been processed");
         }

         payment = new Payment(this.orderId, paymentMethod, this.totalAmount);
         if (!payment.processPayment()) {
            throw new PaymentException("Payment processing failed");
         }
      } catch (IllegalStateException | PaymentException e) {
         System.err.println("Error in processPayment: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Returns the delivery location of the order.
    *
    * @return the delivery location of the order
    */
   public Location getDeliveryLocation() {
      return deliveryLocation;
   }

   /**
    * Returns the customer email of the order.
    *
    * @return the customer email of the order
    */
   public String getCustomerEmail() {
      return customerEmail;
   }

   /**
    * Sets the estimated delivery time for the order.
    *
    * @param time the estimated delivery time
    */
   public void setEstimatedDeliveryTime(LocalDateTime time) {
      this.estimatedDeliveryTime = time;
   }

   /**
    * Returns the estimated delivery time of the order.
    *
    * @return the estimated delivery time of the order
    */
   public LocalDateTime getEstimatedDeliveryTime() {
      return estimatedDeliveryTime;
   }

   /**
    * Returns the order ID.
    *
    * @return the order ID
    */
   public Long getOrderId() {
      return orderId;
   }

   /**
    * Returns the customer ID.
    *
    * @return the customer ID
    */
   public Long getCustomerId() {
      return customerId;
   }

   /**
    * Returns the driver ID.
    *
    * @return the driver ID
    */
   public Long getDriverId() {
      return driverId;
   }

   /**
    * Returns the list of menu items in the order.
    *
    * @return the list of menu items in the order
    */
   public List<MenuItem> getItems() {
      return new ArrayList<>(items);
   }

   /**
    * Returns the status of the order.
    *
    * @return the status of the order
    */
   public OrderStatus getStatus() {
      return status;
   }

   /**
    * Returns the total amount of the order.
    *
    * @return the total amount of the order
    */
   public double getTotalAmount() {
      return totalAmount;
   }

   /**
    * Returns the order time.
    *
    * @return the order time
    */
   public LocalDateTime getOrderTime() {
      return orderTime;
   }

   /**
    * Returns the payment of the order.
    *
    * @return the payment of the order
    */
   public Payment getPayment() {
      return payment;
   }

   /**
    * Returns the assigned driver for the order.
    *
    * @return the assigned driver for the order
    */
   public Driver getAssignedDriver() {
      return Driver.getDriverById(driverId);
   }

   private boolean isValidEmail(String email) {
      return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
   }
}
