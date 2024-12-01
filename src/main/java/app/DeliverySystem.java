package src.main.java.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;

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

   public void addObserver(final OrderObserver observer) {
      this.observers.add(observer);
   }

   private void notifyObservers(final Order order, final OrderEvent event) {
      for (final OrderObserver observer : this.observers) {
         observer.onOrderEvent(order, event);
      }
   }

   /**
    * Submits an order for processing.
    *
    * @param order the order to be submitted
    */
   public void submitOrder(final Order order) {
      try {
         this.validateAndProcessOrder(order);
         this.notifyOrderSubmission(order);
         this.assignDriverIfAvailable(order);
         this.notifyObservers(order, OrderEvent.ORDER_SUBMITTED);
      } catch (final OrderProcessingException e) {
         System.err.println("Error submitting order: " + e.getMessage());
      }
   }

   private void validateAndProcessOrder(final Order order) {
      try {
         order.processPayment("CREDIT_CARD");
         this.orderQueue.enqueue(order);
      } catch (ValidationException | PaymentException e) {
         throw new OrderProcessingException("Failed to submit order: " + e.getMessage());
      }
   }

   private void notifyOrderSubmission(final Order order) {
      this.notificationService.sendOrderConfirmationToCustomer(order);
   }

   private void assignDriverIfAvailable(final Order order) {
      final Optional<Driver> matchedDriver = this.findMatchingDriver(order);
      matchedDriver.ifPresent(driver -> {
         assignOrderToDriver(order, driver);
         notificationService.sendDriverAssignmentNotification(order, driver);
      });
   }

   private Optional<Driver> findMatchingDriver(final Order order) {
      return Optional.empty();
   }

   /**
    * Assigns an order to a driver.
    *
    * @param order  the order to be assigned
    * @param driver the driver to be assigned to the order
    */
   void assignOrderToDriver(final Order order, final Driver driver) {
      try {
         driver.acceptOrder(order);
         this.updateDriverStatus(driver);
         this.updateOrderStatus(order, driver);
         this.notifyObservers(order, OrderEvent.DRIVER_ASSIGNED);
      } catch (final Exception e) {
         System.err.println("Error assigning order to driver: " + e.getMessage());
      }
   }

   private void updateDriverStatus(final Driver driver) {
      this.availableDrivers.remove(driver.getId());
      this.busyDrivers.put(driver.getId(), driver);
   }

   private void updateOrderStatus(final Order order, final Driver driver) {
      this.orderTracker.updateOrderStatus(order.getOrderId(), OrderStatus.ACCEPTED, driver);
   }

   /**
    * Registers a driver to the system.
    *
    * @param driver the driver to be registered
    */
   public void registerDriver(final Driver driver) {
      this.availableDrivers.put(driver.getId(), driver);
   }

   /**
    * Completes the delivery of an order.
    *
    * @param orderId  the ID of the order to be completed
    * @param driverId the ID of the driver who completed the delivery
    */
   public void completeDelivery(final Long orderId, final Long driverId) {
      try {
         final Optional<Driver> driver = Optional.ofNullable(this.busyDrivers.get(driverId));
         driver.ifPresent(d -> {
            processDeliveryCompletion(orderId, d);
            d.getCurrentOrder().ifPresent(order -> {
               notificationService.sendDeliveryCompletionNotification(order);
               notifyObservers(order, OrderEvent.DELIVERY_COMPLETED);
            });
         });
      } catch (final Exception e) {
         System.err.println("Error completing delivery: " + e.getMessage());
      }
   }

   private void processDeliveryCompletion(final Long orderId, final Driver driver) {
      driver.completeCurrentDelivery();
      this.moveDriverToAvailable(driver);
      this.orderTracker.updateOrderStatus(orderId, OrderStatus.DELIVERED, driver);
   }

   private void moveDriverToAvailable(final Driver driver) {
      this.busyDrivers.remove(driver.getId());
      this.availableDrivers.put(driver.getId(), driver);
   }

   /**
    * The main method to run the application.
    *
    * @param args the command line arguments
    */
   public static void main(final String[] args) {
      // ...existing code...
      final DeliverySystem system = new DeliverySystem();

      // Register drivers
      final Driver driver1 = new Driver(1L, "Alice", "Car", "ABC123");
      final Driver driver2 = new Driver(2L, "Bob", "Bike", "XYZ789");
      system.registerDriver(driver1);
      system.registerDriver(driver2);

      // Initialize MenuItemFactory
      final MenuItemFactory factory = new MenuItemFactory();

      // Create menu items using the factory
      final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99,
            Size.MEDIUM, 1);
      final MenuItem burger = factory.createMenuItem("hamburger", "Beef Burger", "Juicy beef patty with lettuce", 8.99,
            Size.MEDIUM, 1);

      // Create orders using OrderBuilder with zipcode and address
      final Order order1 = new OrderBuilder()
            .withValidatedCustomerId(101L)
            .withCustomerEmail("customer1@example.com")
            .addItem(pizza)
            .withValidatedDeliveryLocation("10001", "123 Oak St")
            .build();

      final Order order2 = new OrderBuilder()
            .withValidatedCustomerId(102L)
            .withCustomerEmail("customer2@example.com")
            .addItem(burger)
            .withValidatedDeliveryLocation("90001", "456 Elm St")
            .build();

      // Add orders to the list
      final List<Order> orders = new ArrayList<>();
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
