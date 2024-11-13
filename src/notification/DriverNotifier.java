package notification;

import model.Order;
import observer.OrderEvent;
import observer.OrderObserver;

/**
 * The DriverNotifier class is responsible for sending notifications to drivers
 * about order status updates and events.
 */
public class DriverNotifier implements OrderObserver {
   private final NotificationService notificationService;

   /**
    * Constructs a DriverNotifier with the specified NotificationService.
    *
    * @param notificationService the NotificationService to use for sending notifications
    */
   public DriverNotifier(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   /**
    * Updates the driver with the current order status.
    *
    * @param order the order to update
    */
   @Override
   public void update(Order order) {
      if (order.getDriverId() != null) {
         notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
      }
   }

   /**
    * Handles order events and sends notifications to the driver.
    *
    * @param order the order associated with the event
    * @param event the event to handle
    */
   @Override
   public void onOrderEvent(Order order, OrderEvent event) {
      driverNotificationToCustomer(order, event);
   }

   /**
    * Sends a notification to the driver about the order status update.
    *
    * @param order the order to notify about
    * @param event the event associated with the notification
    */
   @Override
   public void driverNotificationToCustomer(Order order, OrderEvent event) {
      notificationService.sendOrderStatusUpdateToCustomer(order, order.getStatus());
   }

   /**
    * Sends a notification to the customer about the order status update.
    *
    * @param order the order to notify about
    * @param event the event associated with the notification
    */
   @Override
   public void customerNotificationOfOrder(Order order, OrderEvent event) {
      // Implementation for customer notification
   }
}
