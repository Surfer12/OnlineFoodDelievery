package services;

import model.MenuItem;
import model.OrderStatus;
import model.Order;
import queue.OrderQueue;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class OrderServiceImpl implements OrderService {
    private final Queue<Order> orderQueue = new LinkedList<>(); // Ensure FIFO processing
    private IdGenerator idGenerator = new IdGenerator();

    // Static inner class for generating unique order IDs
    private static class IdGenerator {
        private final AtomicLong counter = new AtomicLong(0);

        public Long generateId() {
            return counter.incrementAndGet();
        }
    }

    @Override
    public Order getOrderById(Long orderId) {
        return this.orderQueue.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order createOrder(List<MenuItem> items) {
        return createOrder(null, null, null, items);
    }

    public Order createOrder(String customerEmail, String deliveryAddress, String postalCode, List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Order order = new Order(
                idGenerator.generateId(),
                items,
                customerEmail,
                deliveryAddress,
                postalCode);
        order.setStatus(OrderStatus.PENDING); // Initialize status
        orderQueue.add(order);
        return order;
    }

    @Override
    public void displayOrderDetails(Order order) {
        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Customer Email: " + order.getCustomerEmail());
        System.out.println("Delivery Address: " + order.getDeliveryAddress());
        System.out.println("Postal Code: " + order.getPostalCode());
        System.out.println("Status: " + order.getStatus()); // Display status
        System.out.println("Items:");

        Map<MenuItem, Integer> itemCounts = new HashMap<>();
        double totalPrice = 0;

        for (MenuItem item : order.getItems()) {
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
            totalPrice += item.getPrice();
        }

        itemCounts.forEach((item, count) -> System.out.printf("%s x%d - $%.2f\n",
                item.getName(),
                count,
                item.getPrice() * count));

        System.out.printf("Total Price: $%.2f\n", totalPrice);
    }

    @Override
    public String getOrderStatus(Long orderId) {
        Order order = this.getOrderById(orderId);
        return order != null ? order.getStatus().toString() : "Order not found";
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderQueue); // Return a copy to preserve encapsulation
    }
}