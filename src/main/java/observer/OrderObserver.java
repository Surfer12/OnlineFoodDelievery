package observer;

import order.Order; // Updated import
import model.OrderStatus; // Added import

public interface OrderObserver {
    void update(Order order, OrderStatus status); // Updated method signature

    void onOrderEvent(Order order, OrderEvent event);

    void customerNotificationOfOrder(Order order, OrderEvent event);

    void driverNotificationToCustomer(Order order, OrderEvent event);
}
