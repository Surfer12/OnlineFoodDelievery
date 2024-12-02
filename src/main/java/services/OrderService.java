package services;

import model.MenuItem;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long orderId);
    Order createOrder(List<MenuItem> items);

    void displayOrderDetails(Order order);

    String getOrderStatus(Long orderId);

    List<Order> getAllOrders();
}