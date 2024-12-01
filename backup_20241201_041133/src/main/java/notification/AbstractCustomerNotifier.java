package notification;

/**
 * Abstract class that extends AbstractNotifier and provides type-specific notification methods for customers.
 */
public abstract class AbstractCustomerNotifier extends AbstractNotifier {

    /**
     * Sends a notification to the specified customer.
     *
     * @param customerEmail the email of the customer
     * @param message       the message to be sent
     */
    public abstract void sendNotificationToCustomer(String customerEmail, String message);

    /**
     * Sends an order confirmation notification to the customer.
     *
     * @param orderId       the ID of the order
     * @param customerEmail the email of the customer
     */
    public abstract void sendOrderConfirmationToCustomer(long orderId, String customerEmail);

    /**
     * Sends a driver assignment notification to the customer.
     *
     * @param orderId       the ID of the order
     * @param driverId      the ID of the driver
     * @param customerEmail the email of the customer
     */
    public abstract void sendDriverAssignmentToCustomer(long orderId, long driverId, String customerEmail);

    /**
     * Sends a delivery completion notification to the customer.
     *
     * @param orderId       the ID of the order
     * @param customerEmail the email of the customer
     */
    public abstract void sendDeliveryCompletionToCustomer(long orderId, String customerEmail);
}
