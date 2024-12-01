package src.main.java.notification.src.main.java.notification;

public class BasicNotificationService implements NotificationService {
    @Override
    public void sendNotification(final String message) {
        System.out.println("Notification: " + message);
    }
    import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;
    
    /**
     * The NotificationService class handles sending notifications to customers and drivers.
     * This class provides methods to send various types of notifications, including order confirmation,
     * driver assignment, order status updates, and delivery completion notifications.
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
       public void sendOrderConfirmationToCustomer(final Order order) {
          final String message = this.formatOrderConfirmationMessage(order);
          this.sendEmail(order.getCustomerEmail(), NotificationService.ORDER_CONFIRMATION_SUBJECT, message);
       }
    
       /**
        * Sends a driver assignment notification email to the customer.
        *
        * @param order  the order for which the driver is assigned
        * @param driver the assigned driver
        */
       public void sendDriverAssignmentNotification(final Order order, final Driver driver) {
          final String message = this.formatDriverAssignmentMessage(order, driver);
          this.sendEmail(order.getCustomerEmail(), NotificationService.DRIVER_ASSIGNMENT_SUBJECT, message);
       }
    
       /**
        * Sends an order status update email to the customer.
        *
        * @param order     the order to update
        * @param newStatus the new status of the order
        */
       public void sendOrderStatusUpdateToCustomer(final Order order, final OrderStatus newStatus) {
          final String message = this.formatStatusUpdateMessage(order, newStatus);
          this.sendEmail(order.getCustomerEmail(), NotificationService.ORDER_STATUS_UPDATE_SUBJECT, message);
       }
    
       /**
        * Sends a delivery completion notification email to the customer.
        *
        * @param order the order that has been delivered
        */
       public void sendDeliveryCompletionNotification(final Order order) {
          final String message = this.formatDeliveryCompletionMessage(order);
          this.sendEmail(order.getCustomerEmail(), NotificationService.DELIVERY_COMPLETION_SUBJECT, message);
       }
    
       /**
        * Formats the order confirmation message.
        *
        * @param order the order to confirm
        * @return the formatted order confirmation message
        */
       private String formatOrderConfirmationMessage(final Order order) {
          return String.format(
                "Order #%d confirmed. Total: $%.2f",
                order.getOrderId(),
                order.getTotalAmount());
       }
    
       /**
        * Formats the driver assignment message.
        *
        * @param order  the order for which the driver is assigned
        * @param driver the assigned driver
        * @return the formatted driver assignment message
        */
       private String formatDriverAssignmentMessage(final Order order, final Driver driver) {
          return String.format(
                "Driver %s has been assigned to your order. %s",
                driver.getName(),
                this.formatEstimatedDeliveryTime(order));
       }
    
       /**
        * Formats the estimated delivery time message.
        *
        * @param order the order for which the delivery time is estimated
        * @return the formatted estimated delivery time message
        */
       private String formatEstimatedDeliveryTime(final Order order) {
          return order.getEstimatedDeliveryTime() != null
                ? "Estimated delivery time: " + order.getEstimatedDeliveryTime()
                : "Delivery time to be determined";
       }
    
       /**
        * Formats the status update message.
        *
        * @param order     the order to update
        * @param newStatus the new status of the order
        * @return the formatted status update message
        */
       private String formatStatusUpdateMessage(final Order order, final OrderStatus newStatus) {
          return String.format("Order #%d status updated: %s",
                order.getOrderId(),
                newStatus);
       }
    
       /**
        * Formats the delivery completion message.
        *
        * @param order the order that has been delivered
        * @return the formatted delivery completion message
        */
       private String formatDeliveryCompletionMessage(final Order order) {
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
       private void sendEmail(final String recipientEmail, final String subject, final String message) {
          try {
             // In a real implementation, this would use an email service
             System.out.printf("Sending email to %s%nSubject: %s%nMessage: %s%n",
                   recipientEmail, subject, message);
          } catch (final Exception e) {
             System.err.printf("Failed to send email to %s%nSubject: %s%nMessage: %s%nError: %s%n",
                   recipientEmail, subject, message, e.getMessage());
             this.sendSMS(recipientEmail, message); // Fallback to SMS if email fails
          }
       }
    
       /**
        * Sends a notification to the specified recipient.
        *
        * @param recipientEmail the recipient's email address
        * @param message        the message body of the notification
        */
       public void sendNotification(final String recipientEmail, final String message) {
          this.sendEmail(recipientEmail, "", message);
       }
    
       /**
        * Sends an SMS to the specified recipient.
        *
        * @param recipientPhone the recipient's phone number
        * @param message        the message body of the SMS
        */
       private void sendSMS(final String recipientPhone, final String message) {
          try {
             // In a real implementation, this would use an SMS service
             System.out.printf("Sending SMS to %s%nMessage: %s%n",
                   recipientPhone, message);
          } catch (final Exception e) {
             System.err.printf("Failed to send SMS to %s%nMessage: %s%nError: %s%n",
                   recipientPhone, message, e.getMessage());
          }
       }
    
    
} 