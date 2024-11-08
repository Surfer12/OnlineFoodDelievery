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
    private Location deliveryLocation;
    private LocalDateTime estimatedDeliveryTime;
    private String customerEmail;

    public Order(Long customerId, List<MenuItem> items, Location deliveryLocation, String customerEmail) {
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PLACED;
        this.orderTime = LocalDateTime.now();
        this.totalAmount = calculateTotal();
        this.deliveryLocation = deliveryLocation;
        this.customerEmail = customerEmail;
    }

    public void addItem(MenuItem item) {
        if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Cannot modify order after it has been accepted");
        }

        if (item == null) {
            throw new ValidationException("Cannot add null item to order");
        }

        if (!item.isAvailable()) {
            throw new ValidationException("Item " + item.getName() + " is not available");
        }

        items.add(item);
        this.totalAmount = calculateTotal();
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
                .mapToDouble(MenuItem::calculateTotal)
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

    public void processPayment(String paymentMethod) {
        if (payment != null) {
            throw new IllegalStateException("Payment has already been processed");
        }

        payment = new Payment(this.orderId, paymentMethod, this.totalAmount);
        if (!payment.processPayment()) {
            throw new PaymentException("Payment processing failed");
        }
    }

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setEstimatedDeliveryTime(LocalDateTime time) {
        this.estimatedDeliveryTime = time;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    // Getters
    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public Payment getPayment() {
        return payment;
    }
}