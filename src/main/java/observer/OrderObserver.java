package observer;

import order.Order; // Updated import

public interface OrderObserver {
    void update(Order order);

    void onOrderEvent(Order order, OrderEvent event);

    void customerNotificationOfOrder(Order order, OrderEvent event);

    void driverNotificationToCustomer(Order order, OrderEvent event);
}
