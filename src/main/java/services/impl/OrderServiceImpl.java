package services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.MenuItem;
import model.Order;
import services.OrderService;

public class OrderServiceImpl implements OrderService {
    private final Map<Long, Order> orderTracker = new HashMap<>();

    @Override
    public Order createOrder(List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Order newOrder = new Order(items);
        this.orderTracker.put(newOrder.getOrderId(), newOrder);
        return newOrder;
    }

    @Override
    public void displayOrderDetails(Order order) {
        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Items:");

        Map<MenuItem, Integer> itemCounts = new HashMap<>();
        double totalPrice = 0;

        for (MenuItem item : order.getItems()) {
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
            totalPrice += item.getPrice();
        }

        itemCounts.forEach(
                (item, count) -> System.out.printf("%s x%d - $%.2f\n", item.getName(), count, item.getPrice() * count));

        System.out.printf("Total Price: $%.2f\n", totalPrice);
    }

    @Override
    public String getOrderStatus(Long orderId) {
        Order order = this.orderTracker.get(orderId);
        return order != null ? "Order in progress" : "Order not found";
    }
}