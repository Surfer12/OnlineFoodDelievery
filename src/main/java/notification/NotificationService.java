package notification;

import model.Driver;
import model.OrderStatus;
import order.Order;

public interface NotificationService {
    void sendNotification(String message);

    void sendOrderConfirmationToCustomer(Order order);

    void sendDriverAssignmentNotification(Order order, Driver driver);

    void sendOrderStatusUpdateToCustomer(Order order, OrderStatus newStatus); // Updated method

    void sendDeliveryCompletionNotification(Order order);
}
