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
      // Implement logic to notify driver about the event
   }

   @Override
   public void driverNotification(Order order, OrderEvent event) {
      // Implement logic to notify driver about the event
   }

   @Override
   public void customerNotification(Order order, OrderEvent event) {
      // TODO Auto-generated method stub

   }
}