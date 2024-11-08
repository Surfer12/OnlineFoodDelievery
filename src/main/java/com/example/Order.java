package main.java.com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private Long customerId;
    private Long driverId;
    private List<MenuItem> items;
    private OrderStatus status;
    private double totalAmount;
    private LocalDateTime orderTime;
    private Payment payment;

    public Order(Long customerId, List<MenuItem> items) {
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PLACED;
        this.orderTime = LocalDateTime.now();
        this.totalAmount = calculateTotal();
    }

    public void addItem(MenuItem item) {
        if (status == OrderStatus.PLACED) {
            items.add(item);
            this.totalAmount = calculateTotal();
        } else {
            throw new IllegalStateException("Cannot modify order after it has been accepted");
        }
    }

    public void removeItem(MenuItem item) {
        if (status == OrderStatus.PLACED) {
            items.remove(item);
            this.totalAmount = calculateTotal();
        } else {
            throw new IllegalStateException("Cannot modify order after it has been accepted");
        }
    }

    public double calculateTotal() {
        return items.stream()
                   .mapToDouble(MenuItem::getPrice)
                   .sum();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // Getters
    public Long getOrderId() { return orderId; }
    public Long getCustomerId() { return customerId; }
    public Long getDriverId() { return driverId; }
    public List<MenuItem> getItems() { return new ArrayList<>(items); }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public Payment getPayment() { return payment; }
}