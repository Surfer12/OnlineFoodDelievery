package notification;

import order.Order;
import user.Driver;
import order.OrderStatus;

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

   @Override
   public void sendOrderStatusUpdate(Order order, OrderStatus newStatus) {
      String message = String.format("Order #%d status updated: %s", order.getOrderId(), newStatus);
      sendEmail(order.getCustomerEmail(), "Order Status Update", message);
   }

   @Override
   public void sendDeliveryComplete(Order order) {
      String message = String.format("Order #%d has been delivered.", order.getOrderId());
      sendEmail(order.getCustomerEmail(), "Delivery Complete", message);
   }

   private void sendEmail(String email, String subject, String message) {
      System.out.printf("Sending email to %s%nSubject: %s%nMessage: %s%n",
            email, subject, message);
   }
}