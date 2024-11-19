package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import observer.OrderObserver;
import model.Driver;
import model.MenuItem;
import model.Size;
import notification.NotificationService;
import observer.OrderEvent;
import exception.ValidationException;
import exception.PaymentException;
import exception.OrderProcessingException;
import tracker.OrderTracker;
import model.Order;
import orderUtilities.OrderStatus;
import factory.MenuItemFactory; // Added import for MenuItemFactory
import orderUtilities.OrderBuilder; // Add import for OrderBuilder

/**
 * The DeliverySystem class manages the order processing, driver assignment, and delivery tracking.
 */
public class DeliverySystem {
   private final queue.OrderQueue orderQueue;
   private final Map<Long, Driver> availableDrivers;
   private final Map<Long, Driver> busyDrivers;
   private final OrderTracker orderTracker;
   private final NotificationService notificationService;
   private final List<OrderObserver> observers = new ArrayList<>();

   public DeliverySystem() {
      this.orderQueue = new queue.OrderQueue(10);
      this.availableDrivers = new ConcurrentHashMap<>();
      this.busyDrivers = new ConcurrentHashMap<>();
      this.orderTracker = new OrderTracker();
      this.notificationService = new NotificationService();
   }

   public void addObserver(OrderObserver observer) {
      observers.add(observer);
   }

   private void notifyObservers(Order order, OrderEvent event) {
      for (OrderObserver observer : observers) {
         observer.onOrderEvent(order, event);
      }
   }

   /**
    * Submits an order for processing.
    *
    * @param order the order to be submitted
    */
   public void submitOrder(Order order) {
      try {
         validateAndProcessOrder(order);
         notifyOrderSubmission(order);
         assignDriverIfAvailable(order);
         notifyObservers(order, OrderEvent.ORDER_SUBMITTED);
      } catch (OrderProcessingException e) {
         System.err.println("Error submitting order: " + e.getMessage());
      }
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
      return Optional.empty();
   }

   /**
    * Assigns an order to a driver.
    *
    * @param order  the order to be assigned
    * @param driver the driver to be assigned to the order
    */
   void assignOrderToDriver(Order order, Driver driver) {
      try {
         driver.acceptOrder(order);
         updateDriverStatus(driver);
         updateOrderStatus(order, driver);
         notifyObservers(order, OrderEvent.DRIVER_ASSIGNED);
      } catch (Exception e) {
         System.err.println("Error assigning order to driver: " + e.getMessage());
      }
   }

   private void updateDriverStatus(Driver driver) {
      availableDrivers.remove(driver.getId());
      busyDrivers.put(driver.getId(), driver);
   }

   private void updateOrderStatus(Order order, Driver driver) {
      orderTracker.updateOrderStatus(order.getOrderId(), OrderStatus.ACCEPTED, driver);
   }

   /**
    * Registers a driver to the system.
    *
    * @param driver the driver to be registered
    */
   public void registerDriver(Driver driver) {
      availableDrivers.put(driver.getId(), driver);
   }

   /**
    * Completes the delivery of an order.
    *
    * @param orderId  the ID of the order to be completed
    * @param driverId the ID of the driver who completed the delivery
    */
   public void completeDelivery(Long orderId, Long driverId) {
      try {
         Optional<Driver> driver = Optional.ofNullable(busyDrivers.get(driverId));
         driver.ifPresent(d -> {
            processDeliveryCompletion(orderId, d);
            d.getCurrentOrder().ifPresent(order -> {
               notificationService.sendDeliveryCompletionNotification(order);
               notifyObservers(order, OrderEvent.DELIVERY_COMPLETED);
            });
         });
      } catch (Exception e) {
         System.err.println("Error completing delivery: " + e.getMessage());
      }
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

   /**
    * The main method to run the application.
    *
    * @param args the command line arguments
    */
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
      MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99,
            Size.MEDIUM, 1);
      MenuItem burger = factory.createMenuItem("hamburger", "Beef Burger", "Juicy beef patty with lettuce", 8.99,
            Size.MEDIUM, 1);

      // Create orders using OrderBuilder with zipcode and address
      Order order1 = new OrderBuilder()
            .withValidatedCustomerId(101L)
            .withCustomerEmail("customer1@example.com")
            .addItem(pizza)
            .withValidatedDeliveryLocation("10001", "123 Oak St")
            .build();

      Order order2 = new OrderBuilder()
            .withValidatedCustomerId(102L)
            .withCustomerEmail("customer2@example.com")
            .addItem(burger)
            .withValidatedDeliveryLocation("90001", "456 Elm St")
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
