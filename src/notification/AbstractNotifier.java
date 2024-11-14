package notification;

/**
 * Abstract class that defines common methods for sending notifications.
 */
public abstract class AbstractNotifier {

    /**
     * Sends a notification to the specified recipient.
     *
     * @param recipient the recipient of the notification
     * @param message   the message to be sent
     */
    public abstract void sendNotification(String recipient, String message);

    /**
     * Sends an order confirmation notification.
     *
     * @param orderId   the ID of the order
     * @param recipient the recipient of the notification
     */
    public abstract void sendOrderConfirmation(long orderId, String recipient);

    /**
     * Sends a driver assignment notification.
     *
     * @param orderId   the ID of the order
     * @param driverId  the ID of the driver
     * @param recipient the recipient of the notification
     */
    public abstract void sendDriverAssignment(long orderId, long driverId, String recipient);

    /**
     * Sends a delivery completion notification.
     *
     * @param orderId   the ID of the order
     * @param recipient the recipient of the notification
     */
    public abstract void sendDeliveryCompletion(long orderId, String recipient);
}
