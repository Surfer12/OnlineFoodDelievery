package observer;

import model.Order;
import model.OrderStatus;
import notification.BasicNotificationService;
import tracker.OrderObserver;

public class CustomerNotifier implements OrderObserver {
    private final BasicNotificationService notificationService;

    public CustomerNotifier(BasicNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void update(Order order, OrderStatus newStatus) {
        // Notify customer about order status changes
        notificationService.sendOrderStatusUpdateToCustomer(order, newStatus);
    }
}