package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Optional;

import order.Order;
import order.OrderStatus;
import user.Driver;
import exceptions.ValidationException;
import exceptions.PaymentException;
import exceptions.OrderProcessingException;
import notification.NotificationService;
import notification.EmailNotificationService;

public class DeliverySystem {
   private final queue.OrderQueue orderQueue;
   private final Map<Long, Driver> availableDrivers;
   private final Map<Long, Driver> busyDrivers;
   private final order.OrderTracker orderTracker;
   private final matching.DriverMatchingStrategy driverMatcher;
   private final NotificationService notificationService;

   public DeliverySystem() {
      this.orderQueue = new queue.OrderQueue(10);
      this.availableDrivers = new ConcurrentHashMap<>();
      this.busyDrivers = new ConcurrentHashMap<>();
      this.orderTracker = new order.OrderTracker();
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
      notificationService.sendOrderConfirmation(order);
   }

   private void assignDriverIfAvailable(Order order) {
      Optional<Driver> matchedDriver = findMatchingDriver(order);
      matchedDriver.ifPresent(driver -> {
         assignOrderToDriver(order, driver);
         notificationService.sendDriverAssigned(order, driver);
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
      Driver driver = busyDrivers.get(driverId);
      if (driver != null) {
         processDeliveryCompletion(orderId, driver);
         notificationService.sendDeliveryComplete(driver.getCurrentOrder());
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
}