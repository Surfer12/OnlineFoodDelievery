package services;

import java.util.List;

import model.MenuItem;
import model.Order;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Order createOrder(List<MenuItem> items);

    void displayOrderDetails(Order order);

    String getOrderStatus(Long orderId);
}