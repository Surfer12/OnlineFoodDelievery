package tracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;

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
   public void attach(final OrderObserver observer) {
      this.observers.add(observer);
   }

   @Override
   public void detach(final OrderObserver observer) {
      this.observers.remove(observer);
   }

   @Override
   public void notifyObservers(final Order order) {
      for (final OrderObserver observer : this.observers) {
         observer.update(order);
      }
   }

   public void updateOrderStatus(final Long orderId, final OrderStatus newStatus, final Driver assignedDriver) {
      this.validateOrderUpdateRequest(orderId, newStatus);
      this.updateStatusInDatabase(orderId, newStatus);

      if (this.isDeliveryInProgress(newStatus)) {
         this.updateDeliveryEstimates(orderId, assignedDriver);
      }

      this.notifyObserversAboutOrderUpdate(orderId);
   }

   private void validateOrderUpdateRequest(final Long orderId, final OrderStatus newStatus) {
      if (orderId == null || newStatus == null) {
         throw new IllegalArgumentException("Order ID and status cannot be null");
      }
   }

   private boolean isDeliveryInProgress(final OrderStatus status) {
      return status == OrderStatus.IN_DELIVERY;
   }

   private void updateStatusInDatabase(final Long orderId, final OrderStatus newStatus) {
      this.orderStatuses.put(orderId, newStatus);
   }

   private void notifyObserversAboutOrderUpdate(final Long orderId) {
      this.findOrderById(orderId).ifPresent(this::notifyObserversOfUpdate);
   }

   private void updateDeliveryEstimates(final Long orderId, final Driver driver) {
      final LocalDateTime estimatedTime = this.calculateEstimatedDeliveryTime(driver);
      this.estimatedDeliveryTimes.put(orderId, estimatedTime);
   }

   public Optional<OrderStatus> getOrderStatus(final Long orderId) {
      return Optional.ofNullable(this.orderStatuses.get(orderId));
   }

   public Optional<LocalDateTime> getEstimatedDeliveryTime(final Long orderId) {
      return Optional.ofNullable(this.estimatedDeliveryTimes.get(orderId));
   }

   private Optional<Order> findOrderById(final Long orderId) {
      return Optional.ofNullable(this.orders.get(orderId));
   }

   private void notifyObserversOfUpdate(final Order order) {
      this.notifyObservers(order);
   }

   private LocalDateTime calculateEstimatedDeliveryTime(final Driver driver) {
      // Basic calculation: current time + 30 minutes
      return LocalDateTime.now().plusMinutes(30);
   }
}