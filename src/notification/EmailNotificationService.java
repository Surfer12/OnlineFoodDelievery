package notification;

import order.Order;
import user.Driver;

public class EmailNotificationService implements NotificationService {
   @Override
   public void sendOrderConfirmation(Order order) {
      String message = String.format(
            "Order #%d confirmed. Total: $%.2f",
            order.getOrderId(),
            order.getTotalAmount());
      sendEmail(order.getCustomerEmail(), "Order Confirmation", message);
   }

   @Override
   public void sendDriverAssigned(Order order, Driver driver) {
      String message = String.format(
            "Driver %s has been assigned to your order. " +
                  "Estimated delivery time: %s",
            driver.getName(),
            order.getEstimatedDeliveryTime());
      sendEmail(order.getCustomerEmail(), "Driver Assigned", message);
   }

   private void sendEmail(String email, String subject, String message) {
      // Implementation for sending emails
      // This would typically integrate with an email service
   }
}