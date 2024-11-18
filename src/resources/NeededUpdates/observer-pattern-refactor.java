// Core Observer Interface
public interface OrderObserver {
    void onOrderEvent(Order order, OrderEvent event);
}

// Event Enumeration
public enum OrderEvent {
    ORDER_PLACED("Order has been placed"),
    ORDER_ACCEPTED("Order has been accepted"),
    DRIVER_ASSIGNED("Driver has been assigned"),
    IN_DELIVERY("Order is being delivered"),
    DELIVERED("Order has been delivered");

    private final String description;

    OrderEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

// Improved CustomerNotifier
public class CustomerNotifier implements OrderObserver {
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(CustomerNotifier.class);

    public CustomerNotifier(NotificationService notificationService) {
        this.notificationService = Objects.requireNonNull(notificationService, 
            "NotificationService cannot be null");
    }

    @Override
    public void onOrderEvent(Order order, OrderEvent event) {
        try {
            switch (event) {
                case ORDER_PLACED -> notifyOrderPlaced(order);
                case DRIVER_ASSIGNED -> notifyDriverAssigned(order);
                case IN_DELIVERY -> notifyOrderInDelivery(order);
                case DELIVERED -> notifyOrderDelivered(order);
                default -> logger.warn("Unhandled order event: {}", event);
            }
        } catch (Exception e) {
            logger.error("Failed to send notification for order {}: {}", 
                order.getOrderId(), e.getMessage());
            throw new NotificationException("Failed to send customer notification", e);
        }
    }

    private void notifyOrderPlaced(Order order) {
        String message = String.format("Order #%d has been placed. Total: $%.2f", 
            order.getOrderId(), order.getTotalAmount());
        notificationService.sendNotification(order.getCustomerEmail(), 
            "Order Confirmation", message);
    }

    private void notifyDriverAssigned(Order order) {
        String message = String.format("Driver %s has been assigned to your order #%d", 
            order.getAssignedDriver().getName(), order.getOrderId());
        notificationService.sendNotification(order.getCustomerEmail(), 
            "Driver Assigned", message);
    }

    private void notifyOrderInDelivery(Order order) {
        String message = String.format("Your order #%d is on its way!", 
            order.getOrderId());
        notificationService.sendNotification(order.getCustomerEmail(), 
            "Order In Delivery", message);
    }

    private void notifyOrderDelivered(Order order) {
        String message = String.format("Your order #%d has been delivered. " +
            "Please rate your experience!", order.getOrderId());
        notificationService.sendNotification(order.getCustomerEmail(), 
            "Order Delivered", message);
    }
}

// Improved NotificationService
public class NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final EmailService emailService;
    private final SMSService smsService;

    public NotificationService(EmailService emailService, SMSService smsService) {
        this.emailService = Objects.requireNonNull(emailService);
        this.smsService = Objects.requireNonNull(smsService);
    }

    public void sendNotification(String recipient, String subject, String message) {
        try {
            // Try email first
            emailService.sendEmail(recipient, subject, message);
            logger.info("Email notification sent to {}: {}", recipient, subject);
        } catch (EmailException e) {
            logger.warn("Failed to send email notification, falling back to SMS: {}", 
                e.getMessage());
            try {
                // Fallback to SMS
                smsService.sendSMS(recipient, message);
                logger.info("SMS notification sent to {}", recipient);
            } catch (SMSException se) {
                logger.error("Failed to send both email and SMS notifications: {}", 
                    se.getMessage());
                throw new NotificationException("Failed to send notification", se);
            }
        }
    }
}
