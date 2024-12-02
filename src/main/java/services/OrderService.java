package services;

import java.util.List;

import model.MenuItem;
import order.Order; // Updated import

public interface OrderService {
    Order getOrderById(Long orderId);
    Order createOrder(List<MenuItem> items);

    void displayOrderDetails(Order order);

    String getOrderStatus(Long orderId);

    List<Order> getAllOrders();
}