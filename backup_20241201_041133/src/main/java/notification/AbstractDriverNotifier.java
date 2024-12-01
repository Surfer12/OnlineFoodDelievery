package notification;

/**
 * Abstract class that extends AbstractNotifier and provides type-specific notification methods for drivers.
 */
public abstract class AbstractDriverNotifier extends AbstractNotifier {

    /**
     * Sends a notification to the specified driver.
     *
     * @param driverEmail the email of the driver
     * @param message     the message to be sent
     */
    public abstract void sendNotificationToDriver(String driverEmail, String message);

    /**
     * Sends an order assignment notification to the driver.
     *
     * @param orderId     the ID of the order
     * @param driverEmail the email of the driver
     */
    public abstract void sendOrderAssignmentToDriver(long orderId, String driverEmail);

    /**
     * Sends a delivery completion notification to the driver.
     *
     * @param orderId     the ID of the order
     * @param driverEmail the email of the driver
     */
    public abstract void sendDeliveryCompletionToDriver(long orderId, String driverEmail);
}
