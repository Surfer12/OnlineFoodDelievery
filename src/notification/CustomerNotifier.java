package notification;

import model.Order;
import observer.OrderEvent;
import observer.OrderObserver;

public class CustomerNotifier implements OrderObserver {
   private final NotificationService notificationService;

   public CustomerNotifier() {
      throw new IllegalStateException("NotificationService is required");
   }

   public CustomerNotifier(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @Override
   public void update(Order order) {
      notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
   }

   @Override
   public void customerNotification(Order order, OrderEvent event) {

   }

   @Override
   public void driverNotification(Order order, OrderEvent event) {
      // TODO Auto-generated method stub
   }

   @Override
   public void onOrderEvent(Order order, OrderEvent event) {
      switch (event) {
         case ORDER_SUBMITTED:
            notificationService.sendOrderConfirmationToCustomer(order);
            break;
         case DRIVER_ASSIGNED:
            notificationService.sendDriverAssignmentNotification(order, order.getAssignedDriver());
            break;
         case DELIVERY_COMPLETED:
            notificationService.sendDeliveryCompletionNotification(order);
            break;
      }
   }
}