import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderTracker {
   private final Map<Long, OrderStatus> orderStatuses;
   private final Map<Long, LocalDateTime> estimatedDeliveryTimes;

   public OrderTracker() {
      this.orderStatuses = new ConcurrentHashMap<>();
      this.estimatedDeliveryTimes = new ConcurrentHashMap<>();
   }

   public void updateOrderStatus(Long orderId, OrderStatus newStatus, Driver driver) {
      orderStatuses.put(orderId, newStatus);

      if (newStatus == OrderStatus.IN_DELIVERY) {
         calculateAndUpdateEstimatedDeliveryTime(orderId, driver);
      }
   }

   private void calculateAndUpdateEstimatedDeliveryTime(Long orderId, Driver driver) {
      // Calculate based on driver location, traffic, etc.
      LocalDateTime estimatedTime = LocalDateTime.now().plusMinutes(30);
      estimatedDeliveryTimes.put(orderId, estimatedTime);
   }

   public OrderStatus getOrderStatus(Long orderId) {
      return orderStatuses.getOrDefault(orderId, OrderStatus.PLACED);
   }

   public LocalDateTime getEstimatedDeliveryTime(Long orderId) {
      return estimatedDeliveryTimes.get(orderId);
   }
}