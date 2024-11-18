public class Order {
    private final Long orderId;
    private final Long customerId;
    private Long driverId;
    private final List<MenuItem> items;
    private OrderStatus status;
    private double totalAmount;
    private final Location deliveryLocation;
    private Payment payment;
    private final LocalDateTime orderTime;
    private LocalDateTime estimatedDeliveryTime;
    private final String customerEmail;

    public Order(Long customerId, List<MenuItem> items, Location deliveryLocation, String customerEmail) {
        validateOrderInputs(customerId, items, deliveryLocation, customerEmail);
        this.orderId = generateOrderId();
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.deliveryLocation = deliveryLocation;
        this.customerEmail = customerEmail;
        this.status = OrderStatus.PLACED;
        this.orderTime = LocalDateTime.now();
        this.totalAmount = calculateTotal();
    }

    private void validateOrderInputs(Long customerId, List<MenuItem> items, 
                                   Location deliveryLocation, String customerEmail) {
        List<String> errors = new ArrayList<>();
        
        if (customerId == null || customerId <= 0) {
            errors.add("Invalid customer ID");
        }
        if (items == null || items.isEmpty()) {
            errors.add("Order must contain at least one item");
        }
        if (deliveryLocation == null) {
            errors.add("Delivery location is required");
        }
        if (customerEmail == null || !isValidEmail(customerEmail)) {
            errors.add("Valid customer email is required");
        }
        
        if (!errors.isEmpty()) {
            throw new OrderProcessingException("Order validation failed: " + String.join(", ", errors));
        }
    }

    public void processPayment(String paymentMethod) {
        try {
            if (payment != null) {
                throw new PaymentException("Payment already processed");
            }
            
            payment = new Payment(orderId, paymentMethod, totalAmount);
            if (!payment.processPayment()) {
                throw new PaymentException("Payment processing failed");
            }
        } catch (Exception e) {
            throw new PaymentException("Payment failed: " + e.getMessage());
        }
    }

    public void updateStatus(OrderStatus newStatus) {
        validateStatusTransition(this.status, newStatus);
        this.status = newStatus;
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new IllegalStateException(
                String.format("Invalid status transition from %s to %s", currentStatus, newStatus)
            );
        }
    }

    private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PLACED -> next == OrderStatus.ACCEPTED;
            case ACCEPTED -> next == OrderStatus.IN_DELIVERY;
            case IN_DELIVERY -> next == OrderStatus.DELIVERED;
            case DELIVERED -> false;
        };
    }

    public double calculateTotal() {
        return items.stream()
                   .mapToDouble(MenuItem::calculateTotal)
                   .sum();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Essential getters
    public Long getOrderId() { return orderId; }
    public Long getCustomerId() { return customerId; }
    public Long getDriverId() { return driverId; }
    public List<MenuItem> getItems() { return new ArrayList<>(items); }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public Location getDeliveryLocation() { return deliveryLocation; }
    public String getCustomerEmail() { return customerEmail; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public Optional<LocalDateTime> getEstimatedDeliveryTime() { 
        return Optional.ofNullable(estimatedDeliveryTime); 
    }
}
