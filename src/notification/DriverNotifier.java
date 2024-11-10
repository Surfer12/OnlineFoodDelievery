package notification;

import model.Order;
import observer.OrderEvent;
import observer.OrderObserver;

public class DriverNotifier implements OrderObserver {
   private final NotificationService notificationService;

   public DriverNotifier(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @Override
   public void update(Order order) {
      if (order.getDriverId() != null) {
         notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
      }
   }

   @Override
   public void onOrderEvent(Order order, OrderEvent event) {
      driverNotificationToCustomer(order, event);
   }

   @Override
   public void driverNotificationToCustomer(Order order, OrderEvent event) {
      notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
   }

   @Override
   public void customerNotificationOfOrder(Order order, OrderEvent event) {

   }
}