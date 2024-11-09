package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Optional;
import queue.OrderQueue; // Import the queue package
import model.Driver;
import matching.DriverMatchingStrategy;
import notification.NotificationService;
import notification.EmailNotificationService;
import exception.ValidationException;
import exception.PaymentException;
import exception.OrderProcessingException;
import tracker.OrderTracker;
import model.Order;
import orderUtilities.OrderStatus;
import java.util.Arrays;
import location.Location;
import menu.MenuItem;
import factory.MenuItemFactory; // Added import for MenuItemFactory
import orderUtilities.OrderBuilder; // Add import for OrderBuilder

public class DeliverySystem {
   private final queue.OrderQueue orderQueue;
   private final Map<Long, Driver> availableDrivers;
   private final Map<Long, Driver> busyDrivers;
   private final OrderTracker orderTracker;
   private final matching.DriverMatchingStrategy driverMatcher;
   private final NotificationService notificationService;

   public DeliverySystem() {
      this.orderQueue = new queue.OrderQueue(10);
      this.availableDrivers = new ConcurrentHashMap<>();
      this.busyDrivers = new ConcurrentHashMap<>();
      this.orderTracker = new OrderTracker();
      this.driverMatcher = new matching.ProximityBasedMatchingStrategy();
      this.notificationService = new EmailNotificationService();
   }

   public void submitOrder(Order order) {
      validateAndProcessOrder(order);
      notifyOrderSubmission(order);
      assignDriverIfAvailable(order);
   }

   private void validateAndProcessOrder(Order order) {
      try {
         order.processPayment("CREDIT_CARD");
         orderQueue.enqueue(order);
      } catch (ValidationException | PaymentException e) {
         throw new OrderProcessingException("Failed to submit order: " + e.getMessage());
      }
   }

   private void notifyOrderSubmission(Order order) {
      notificationService.sendOrderConfirmationToCustomer(order);
   }

   private void assignDriverIfAvailable(Order order) {
      Optional<Driver> matchedDriver = findMatchingDriver(order);
      matchedDriver.ifPresent(driver -> {
         assignOrderToDriver(order, driver);
         notificationService.sendDriverAssignmentNotification(order, driver);
      });
   }

   private Optional<Driver> findMatchingDriver(Order order) {
      return driverMatcher.findBestMatch(
            order,
            new ArrayList<>(availableDrivers.values()));
   }

   private void assignOrderToDriver(Order order, Driver driver) {
      driver.acceptOrder(order);
      updateDriverStatus(driver);
      updateOrderStatus(order, driver);
   }

   private void updateDriverStatus(Driver driver) {
      availableDrivers.remove(driver.getId());
      busyDrivers.put(driver.getId(), driver);
   }

   private void updateOrderStatus(Order order, Driver driver) {
      orderTracker.updateOrderStatus(order.getOrderId(), OrderStatus.ACCEPTED, driver);
   }

   public void registerDriver(Driver driver) {
      availableDrivers.put(driver.getId(), driver);
   }

   public void completeDelivery(Long orderId, Long driverId) {
      Optional<Driver> driver = Optional.ofNullable(busyDrivers.get(driverId));
      driver.ifPresent(d -> {
         processDeliveryCompletion(orderId, d);
         d.getCurrentOrder().ifPresent(order -> notificationService.sendDeliveryCompletionNotification(order));
      });
   }

   private void processDeliveryCompletion(Long orderId, Driver driver) {
      driver.completeCurrentDelivery();
      moveDriverToAvailable(driver);
      orderTracker.updateOrderStatus(orderId, OrderStatus.DELIVERED, driver);
   }

   private void moveDriverToAvailable(Driver driver) {
      busyDrivers.remove(driver.getId());
      availableDrivers.put(driver.getId(), driver);
   }

   public static void main(String[] args) {
      // ...existing code...
      DeliverySystem system = new DeliverySystem();

      // Register drivers
      Driver driver1 = new Driver(1L, "Alice", "Car", "ABC123");
      Driver driver2 = new Driver(2L, "Bob", "Bike", "XYZ789");
      system.registerDriver(driver1);
      system.registerDriver(driver2);

      // Initialize MenuItemFactory
      MenuItemFactory factory = new MenuItemFactory();

      // Create menu items using the factory
      MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99);
      MenuItem burger = factory.createMenuItem("hamburger", "Beef Burger", "Juicy beef patty with lettuce", 8.99);

      // Define delivery locations with zipcode and address
      Location location1 = new Location(40.7128, -74.0060, "10001", "123 Oak St");
      Location location2 = new Location(34.0522, -118.2437, "90001", "456 Elm St");

      // Create orders using OrderBuilder with zipcode and address
      Order order1 = new OrderBuilder()
                      .withValidatedCustomerId(101L)
                      .withCustomerEmail("customer1@example.com")
                      .addItem(pizza)
                      .withDeliveryLocation("123 Oak St", 40.7128, -74.0060, "10001")
                      .build();

      Order order2 = new OrderBuilder()
                      .withValidatedCustomerId(102L)
                      .withCustomerEmail("customer2@example.com")
                      .addItem(burger)
                      .withDeliveryLocation("456 Elm St", 34.0522, -118.2437, "90001")
                      .build();

      // Add orders to the list
      List<Order> orders = new ArrayList<>();
      orders.add(order1);
      orders.add(order2);

      // Submit orders
      system.submitOrder(order1);
      system.submitOrder(order2);

      // Complete deliveries
      system.completeDelivery(101L, 1L);
      system.completeDelivery(102L, 2L);
      // ...existing code...
   }
}