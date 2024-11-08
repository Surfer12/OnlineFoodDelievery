package observer;

import order.Order;
import notification.NotificationService;

public class CustomerNotifier implements OrderObserver {
   private final NotificationService notificationService;

   public CustomerNotifier(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @Override
   public void update(Order order) {
      notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
   }
}