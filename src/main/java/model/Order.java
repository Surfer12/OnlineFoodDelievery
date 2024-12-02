package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Long customerId;
    private final String customerEmail;
    private final List<MenuItem> items;
    private final String deliveryAddress;
    private final String postalCode;
    private final Long orderId;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime estimatedDeliveryTime;
    private Driver driver;

    public Order(final Long customerId, final String customerEmail, final List<MenuItem> items,
            final String deliveryAddress, final String postalCode) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.deliveryAddress = deliveryAddress;
        this.postalCode = postalCode;
        this.orderId = System.currentTimeMillis(); // Simple ID generation
        this.totalAmount = calculateTotalAmount();
        this.status = OrderStatus.PENDING;
    }

    private double calculateTotalAmount() {
        return this.items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    public Long getId() {
        return this.orderId;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(this.items);
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return this.estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}