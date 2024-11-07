package main.java.com.example;

public enum OrderStatus {
    PLACED("Order has been placed"),
    ACCEPTED("Order has been accepted by driver"),
    IN_DELIVERY("Order is being delivered"),
    DELIVERED("Order has been delivered");

    private final String description;
    private long timestamp;

    OrderStatus(String description) {
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    public String getDescription() {
        return description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void updateTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        switch (this) {
            case PLACED:
                return nextStatus == ACCEPTED;
            case ACCEPTED:
                return nextStatus == IN_DELIVERY;
            case IN_DELIVERY:
                return nextStatus == DELIVERED;
            case DELIVERED:
                return false;
            default:
                return false;
        }
    }
}