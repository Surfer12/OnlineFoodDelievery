package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import order.Order;
import user.Driver;
import order.OrderStatus;

import java.util.ArrayList;
import java.util.Optional;

import exceptions.ValidationException;

public class DeliverySystem {
   private final queue.OrderQueue orderQueue;
   private final Map<Long, Driver> availableDrivers;
   private final Map<Long, Driver> busyDrivers;
   private final order.OrderTracker orderTracker;
   private final matching.DriverMatchingStrategy driverMatcher;

   public DeliverySystem() {
      this.orderQueue = new queue.OrderQueue(10); // Max 10 orders in queue
      this.availableDrivers = new ConcurrentHashMap<>();
      this.busyDrivers = new ConcurrentHashMap<>();
      this.orderTracker = new order.OrderTracker();
      this.driverMatcher = new matching.ProximityBasedMatchingStrategy();
   }

   public void submitOrder(Order order) {
      try {
         order.processPayment("CREDIT_CARD"); // Process payment first
         orderQueue.enqueue(order); // Then add to queue
         attemptToAssignDriver(order);
      } catch (ValidationException | exceptions.PaymentException e) {
         // Handle exceptions appropriately
         throw new exceptions.OrderProcessingException("Failed to submit order: " + e.getMessage());
      }
   }

   private void attemptToAssignDriver(Order order) {
      Optional<Driver> matchedDriver = driverMatcher.findBestMatch(
            order,
            new ArrayList<>(availableDrivers.values()));

      matchedDriver.ifPresent(driver -> assignOrderToDriver(order, driver));
   }

   private void assignOrderToDriver(Order order, Driver driver) {
      driver.acceptOrder(order);
      availableDrivers.remove(driver.getId());
      busyDrivers.put(driver.getId(), driver);
      orderTracker.updateOrderStatus(order.getOrderId(), order.OrderStatus.ACCEPTED, driver);
   }

   public void registerDriver(Driver driver) {
      availableDrivers.put(driver.getId(), driver);
   }

   public void completeDelivery(Long orderId, Long driverId) {
      Driver driver = busyDrivers.remove(driverId);
      if (driver != null) {
         driver.completeCurrentDelivery();
         availableDrivers.put(driverId, driver);
         orderTracker.updateOrderStatus(orderId, order.OrderStatus.DELIVERED, driver);
      }
   }
}