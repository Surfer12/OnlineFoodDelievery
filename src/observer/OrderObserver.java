package observer;

import model.Order;

public interface OrderObserver {
    void update(Order order);

    void onOrderEvent(Order order, OrderEvent event);

    void customerNotification(Order order, OrderEvent event);

    void driverNotification(Order order, OrderEvent event);
}
