package order;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import user.Driver;
import observer.OrderSubject;
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

   public void updateOrderStatus(Long orderId, OrderStatus newStatus, Driver driver) {
      orderStatuses.put(orderId, newStatus);

      if (newStatus == OrderStatus.IN_DELIVERY) {
         calculateAndUpdateEstimatedDeliveryTime(orderId, driver);
      }

      findOrderById(orderId).ifPresent(this::notifyObservers);
   }

   private void calculateAndUpdateEstimatedDeliveryTime(Long orderId, Driver driver) {
      // Calculate based on driver location, traffic, etc.
      LocalDateTime estimatedTime = LocalDateTime.now().plusMinutes(30);
      estimatedDeliveryTimes.put(orderId, estimatedTime);
   }

   public OrderStatus getOrderStatus(Long orderId) {
      return orderStatuses.getOrDefault(orderId, OrderStatus.PLACED);
   }

   public Optional<LocalDateTime> getEstimatedDeliveryTime(Long orderId) {
      return Optional.ofNullable(estimatedDeliveryTimes.get(orderId));
   }

   private Optional<Order> findOrderById(Long orderId) {
      return Optional.ofNullable(orders.get(orderId));
   }
}