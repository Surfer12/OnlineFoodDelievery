package observer;

import model.Order;
import model.OrderStatus;
import notification.BasicNotificationService;
import tracker.OrderObserver;

public class DriverNotifier implements OrderObserver {
    private final BasicNotificationService notificationService;

    public DriverNotifier(BasicNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void update(Order order, OrderStatus newStatus) {
        // Notify driver about order status changes
        // Note: There's no direct method for driver status update in
        // BasicNotificationService
        // You might want to add a method to BasicNotificationService for driver
        // notifications
        notificationService.sendNotification("Driver order status updated: " + newStatus);
    }
}