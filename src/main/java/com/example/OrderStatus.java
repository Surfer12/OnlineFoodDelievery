package main.java.com.example;

public class OrderStatus {
    public static final OrderStatus PLACED = new OrderStatus("Placed");
    public static final OrderStatus PREPARING = new OrderStatus("Preparing");
    public static final OrderStatus READY = new OrderStatus("Ready");
    public static final OrderStatus DELIVERED = new OrderStatus("Delivered");
    public static final OrderStatus CANCELLED = new OrderStatus("Cancelled");

    private String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
