package tracker;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.Driver;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import observer.OrderSubject;
import orderUtilities.OrderStatus;
import observer.OrderObserver;

public class OrderTracker implements OrderSubject {
   private final Map<Long, OrderStatus> orderStatuses;
   private final Map<Long, LocalDateTime> estimatedDeliveryTimes;
   private final List<OrderObserver> observers;
   private final Map<Long, Order> orders = new ConcurrentHashMap<>();

   public OrderTracker() {
      this.orderStatuses = new ConcurrentHashMap<>();
      this.estimatedDeliveryTimes = new ConcurrentHashMap<>();
      this.observers = new ArrayList<>();
   }

   @Override
   public void attach(OrderObserver observer) {
      observers.add(observer);
   }

   @Override
   public void detach(OrderObserver observer) {
      observers.remove(observer);
   }

   @Override
   public void notifyObservers(Order order) {
      for (OrderObserver observer : observers) {
         observer.update(order);
      }
   }

   public void updateOrderStatus(Long orderId, OrderStatus newStatus, Driver assignedDriver) {
      validateOrderUpdateRequest(orderId, newStatus);
      updateStatusInDatabase(orderId, newStatus);

      if (isDeliveryInProgress(newStatus)) {
         updateDeliveryEstimates(orderId, assignedDriver);
      }

      notifyObserversAboutOrderUpdate(orderId);
   }

   private void validateOrderUpdateRequest(Long orderId, OrderStatus newStatus) {
      if (orderId == null || newStatus == null) {
         throw new IllegalArgumentException("Order ID and status cannot be null");
      }
   }

   private boolean isDeliveryInProgress(OrderStatus status) {
      return status == OrderStatus.IN_DELIVERY;
   }

   private void updateStatusInDatabase(Long orderId, OrderStatus newStatus) {
      orderStatuses.put(orderId, newStatus);
   }

   private void notifyObserversAboutOrderUpdate(Long orderId) {
      findOrderById(orderId).ifPresent(this::notifyObserversOfUpdate);
   }

   private void updateDeliveryEstimates(Long orderId, Driver driver) {
      LocalDateTime estimatedTime = calculateEstimatedDeliveryTime(driver);
      estimatedDeliveryTimes.put(orderId, estimatedTime);
   }

   public Optional<OrderStatus> getOrderStatus(Long orderId) {
      return Optional.ofNullable(orderStatuses.get(orderId));
   }

   public Optional<LocalDateTime> getEstimatedDeliveryTime(Long orderId) {
      return Optional.ofNullable(estimatedDeliveryTimes.get(orderId));
   }

   private Optional<Order> findOrderById(Long orderId) {
      return Optional.ofNullable(orders.get(orderId));
   }

   private void notifyObserversOfUpdate(Order order) {
      notifyObservers(order);
   }

   private LocalDateTime calculateEstimatedDeliveryTime(Driver driver) {
      // Basic calculation: current time + 30 minutes
      return LocalDateTime.now().plusMinutes(30);
   }
}