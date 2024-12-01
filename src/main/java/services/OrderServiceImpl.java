package services

import java.util.ArrayList;
import java.util.List;

import model.Order;

public class OrderServiceImpl implements OrderService {
    private List<Order> orders = new ArrayList<>();

    @Override
    public Order getOrderById(Long orderId) {
        return this.orders.stream()
                     .filter(order -> order.getId().equals(orderId))
                     .findFirst()
                     .orElse(null);
    }

    // ...methods to create, update, delete orders...
}   