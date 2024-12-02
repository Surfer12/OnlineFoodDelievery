package observer;

import model.OrderStatus;
import order.Order;

public interface OrderObserver {
    void update(Order order, OrderStatus status); // Updated method signature

    void onOrderEvent(Order order, OrderEvent event);

    void customerNotificationOfOrder(Order order, OrderEvent event);

    void driverNotificationToCustomer(Order order, OrderEvent event);
}
