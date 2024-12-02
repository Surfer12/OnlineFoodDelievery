package app;

import java.util.LinkedList;
import java.util.Queue;

import model.Order;

public class OrderQueue {
    private final Queue<Order> orders;

    public OrderQueue() {
        this.orders = new LinkedList<>();
    }

    public void addOrder(final Order order) {
        this.orders.offer(order);
    }

    public Order processNextOrder() {
        return this.orders.poll();
    }

    public Order peekNextOrder() {
        return this.orders.peek();
    }

    public int getOrderCount() {
        return this.orders.size();
    }

    public boolean isEmpty() {
        return this.orders.isEmpty();
    }
}