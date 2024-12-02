package app;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum OrderStatus {
        PLACED, ACCEPTED, IN_TRANSIT, DELIVERED
    }

    private final String customerName;
    private final String customerEmail;
    private final List<MenuItem> items;
    private OrderStatus status;
    private Driver assignedDriver;

    public Order(final String customerName, final String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED;
    }

    public void addItem(final MenuItem item) {
        this.items.add(item);
    }

    public double calculateTotal() {
        return this.items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    public void assignDriver(final Driver driver) {
        this.assignedDriver = driver;
        this.status = OrderStatus.ACCEPTED;
    }

    public void updateStatus(final OrderStatus newStatus) {
        this.status = newStatus;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(this.items);
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Driver getAssignedDriver() {
        return this.assignedDriver;
    }
} 