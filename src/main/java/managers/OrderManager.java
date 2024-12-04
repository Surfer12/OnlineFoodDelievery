package managers;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import model.MenuItem;
import model.Order;
import model.OrderStatus;
import notification.BasicNotificationService;
import observer.CustomerNotifier;
import observer.DriverNotifier;
import queue.OrderQueue;
import services.OrderService;
import services.impl.OrderServiceImpl;
import tracker.OrderTracker;
import validation.ConsoleInputHandler;
import validation.InputValidatorImpl;
import validation.PositiveLongValidator;

public class OrderManager {
    private static final Logger logger = Logger.getLogger(OrderManager.class.getName());
    private static final int MAX_QUEUE_SIZE = 10;

    private final OrderService orderService;
    private final OrderQueue orderQueue;
    private final ConsoleInputHandler<Long> orderIdHandler;
    private final OrderTracker orderTracker; // Added OrderTracker

    public OrderManager() {
        this.orderService = new OrderServiceImpl();
        this.orderQueue = new OrderQueue(OrderManager.MAX_QUEUE_SIZE);
        this.orderIdHandler = new ConsoleInputHandler<>(
                new InputValidatorImpl<>(
                        new PositiveLongValidator(),
                        "Order ID",
                        "Invalid Order ID"));
        this.orderTracker = new OrderTracker(); // Initialize OrderTracker
    }

    public Order createOrder(final List<MenuItem> orderItems) throws CustomException.QueueFullException {
        if (orderItems.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return null;
        }

        try {
            final Order newOrder = this.orderService.createOrder(orderItems);
            this.orderQueue.enqueue(newOrder);
            this.orderService.displayOrderDetails(newOrder);
            System.out.println("Order placed successfully!");
            System.out.println("Order ID: " + newOrder.getOrderId());
            System.out.println("Total Amount: $" + newOrder.getTotalAmount());
            this.orderTracker.attach(new CustomerNotifier(new BasicNotificationService())); // Attach observer
            this.orderTracker.attach(new DriverNotifier(new BasicNotificationService())); // Attach another observer
            this.orderTracker.notifyObservers(newOrder); // Notify observers
            OrderManager.logger.info("New order added to queue: " + newOrder.getOrderId());
            return newOrder;
        } catch (final CustomException.QueueFullException e) {
            System.out.println("Sorry, we are currently at maximum order capacity. Please try again later.");
            OrderManager.logger.warning("Order queue full: " + e.getMessage());
            throw e;
        }
    }

    public void checkOrderStatus(final Scanner scanner) {
        try {
            final Long orderId = this.orderIdHandler.handleInput(scanner, "Enter Order ID to check status: ");

            if (orderId == null)
                return;

            final Order order = this.orderService.getOrderById(orderId);
            if (order != null) {
                System.out.println("Order Status: " + order.getStatus());
            } else {
                System.out.println("Order not found.");
            }
        } catch (final Exception e) {
            System.out.println("Error checking order status: " + e.getMessage());
            OrderManager.logger.severe("Error in checkOrderStatus: " + e.getMessage());
        }
    }

    public ConsoleInputHandler<Long> getOrderIdHandler() {
        return this.orderIdHandler;
    }

    public OrderService getOrderService() {
        return this.orderService;
    }

    public void processOrderPlacement(final Scanner scanner, final MenuManager menuManager,
            final ConsoleInputHandler<Integer> positiveIntegerHandler,
            final ConsoleInputHandler<String> emailHandler,
            final ConsoleInputHandler<String> locationHandler) {
        try {
            final List<MenuItem> orderItems = menuManager.selectMenuItems(scanner, positiveIntegerHandler);

            if (!orderItems.isEmpty()) {
                final Order newOrder = this.createOrder(orderItems);
                if (newOrder != null) {
                    System.out.println("Would you like to assign a driver now? (Y/N)");
                    final String response = scanner.nextLine().trim().toUpperCase();
                    if ("Y".equals(response)) {
                        this.assignDriverToOrder(newOrder);
                    }
                }
            }
        } catch (final CustomException.QueueFullException e) {
            logger.warning("Order queue full: " + e.getMessage());
            System.out.println("Sorry, we cannot accept more orders at the moment.");
        }
    }

    private void assignDriverToOrder(final Order order) {
        Driver driver = driverManager.findAvailableDriver();
        if (driver != null) {
            driverManager.assignDriver(driver, order);
            orderTracker.updateOrderStatus(order.getOrderId(), OrderStatus.CONFIRMED, driver);
        }
    }

    public List<Order> getPendingOrders() {
        return this.orderService.getAllOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.SUBMITTED)
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(final Order order, final OrderStatus status) {
        order.setStatus(status);
        orderService.save(order);
        orderTracker.updateOrderStatus(order.getOrderId(), status, order.getDriver());
        System.out.println("Order status updated to: " + status);
    }
}
