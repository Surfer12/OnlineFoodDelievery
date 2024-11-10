package observer;

public enum OrderEvent {

    ORDER_SUBMITTED("submitted"),

    DRIVER_ASSIGNED("assigned"),

    DELIVERY_COMPLETED("completed");

    private final String status;

    OrderEvent(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}