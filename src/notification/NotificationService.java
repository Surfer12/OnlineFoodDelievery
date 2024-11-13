package notification;

import model.Driver;
import model.Order;
import orderUtilities.OrderStatus;

/**
 * The NotificationService class handles sending notifications to customers and drivers.
 */
public class NotificationService {
   private static final String ORDER_CONFIRMATION_SUBJECT = "Order Confirmation";
   private static final String DRIVER_ASSIGNMENT_SUBJECT = "Driver Assigned";
   private static final String ORDER_STATUS_UPDATE_SUBJECT = "Order Status Update";
   private static final String DELIVERY_COMPLETION_SUBJECT = "Delivery Complete";

   /**
    * Sends an order confirmation email to the customer.
    *
    * @param order the order to confirm
    */
   public void sendOrderConfirmationToCustomer(Order order) {
      String message = formatOrderConfirmationMessage(order);
      sendEmail(order.getCustomerEmail(), ORDER_CONFIRMATION_SUBJECT, message);
   }

   /**
    * Sends a driver assignment notification email to the customer.
    *
    * @param order  the order for which the driver is assigned
    * @param driver the assigned driver
    */
   public void sendDriverAssignmentNotification(Order order, Driver driver) {
      String message = formatDriverAssignmentMessage(order, driver);
      sendEmail(order.getCustomerEmail(), DRIVER_ASSIGNMENT_SUBJECT, message);
   }

   /**
    * Sends an order status update email to the customer.
    *
    * @param order     the order to update
    * @param newStatus the new status of the order
    */
   public void sendOrderStatusUpdateToCustomer(Order order, OrderStatus newStatus) {
      String message = formatStatusUpdateMessage(order, newStatus);
      sendEmail(order.getCustomerEmail(), ORDER_STATUS_UPDATE_SUBJECT, message);
   }

   /**
    * Sends a delivery completion notification email to the customer.
    *
    * @param order the order that has been delivered
    */
   public void sendDeliveryCompletionNotification(Order order) {
      String message = formatDeliveryCompletionMessage(order);
      sendEmail(order.getCustomerEmail(), DELIVERY_COMPLETION_SUBJECT, message);
   }

   private String formatOrderConfirmationMessage(Order order) {
      return String.format(
            "Order #%d confirmed. Total: $%.2f",
            order.getOrderId(),
            order.getTotalAmount());
   }

   private String formatDriverAssignmentMessage(Order order, Driver driver) {
      return String.format(
            "Driver %s has been assigned to your order. %s",
            driver.getName(),
            formatEstimatedDeliveryTime(order));
   }

   private String formatEstimatedDeliveryTime(Order order) {
      return order.getEstimatedDeliveryTime() != null
            ? "Estimated delivery time: " + order.getEstimatedDeliveryTime()
            : "Delivery time to be determined";
   }

   private String formatStatusUpdateMessage(Order order, OrderStatus newStatus) {
      return String.format("Order #%d status updated: %s",
            order.getOrderId(),
            newStatus);
   }

   private String formatDeliveryCompletionMessage(Order order) {
      return String.format("Order #%d has been delivered successfully.",
            order.getOrderId());
   }

   /**
    * Sends an email to the specified recipient.
    *
    * @param recipientEmail the recipient's email address
    * @param subject        the subject of the email
    * @param message        the message body of the email
    */
   private void sendEmail(String recipientEmail, String subject, String message) {
      try {
         // In a real implementation, this would use an email service
         System.out.printf("Sending email to %s%nSubject: %s%nMessage: %s%n",
               recipientEmail, subject, message);
      } catch (Exception e) {
         System.err.printf("Failed to send email to %s%nSubject: %s%nMessage: %s%nError: %s%n",
               recipientEmail, subject, message, e.getMessage());
      }
   }

   /**
    * Sends a notification to the specified recipient.
    *
    * @param recipientEmail the recipient's email address
    * @param message        the message body of the notification
    */
   public void sendNotification(String recipientEmail, String message) {
      sendEmail(recipientEmail, "", message);
   }
}
