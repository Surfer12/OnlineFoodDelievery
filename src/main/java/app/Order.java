package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order in the Online Food Delivery System.
 * 
 * This class manages the complete lifecycle of an order:
 * - Customer information
 * - Selected menu items
 * - Order status
 * - Driver assignment
 * 
 * Implements key project requirements for order management.
 */
public class Order {
    /**
     * Enum representing the possible states of an order.
     * 
     * Demonstrates type safety and clear order progression.
     */
    public enum OrderStatus {
        PLACED, // Initial order state
        ACCEPTED, // Order accepted by a driver
        IN_TRANSIT, // Order is being delivered
        DELIVERED // Order successfully completed
    }

    // Customer details - immutable after order creation
    private final String customerName; // Name of the customer
    private final String customerEmail; // Email of the customer

    // Order items and tracking
    private final List<MenuItem> items; // List of items in the order
    private OrderStatus status; // Current status of the order
    private Driver assignedDriver; // Driver responsible for delivery

    /**
     * Constructor to create a new order.
     * 
     * @param customerName  Name of the customer placing the order
     * @param customerEmail Email of the customer
     */
    public Order(final String customerName, final String customerEmail) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED; // Default initial status
    }

    /**
     * Adds a menu item to the current order.
     * 
     * @param item MenuItem to be added to the order
     */
    public void addItem(final MenuItem item) {
        this.items.add(item);
    }

    /**
     * Calculates the total cost of all items in the order.
     * 
     * Uses Java 8 Stream API for efficient calculation.
     * 
     * @return Total order cost
     */
    public double calculateTotal() {
        return this.items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    /**
     * Assigns a driver to the order and updates its status.
     * 
     * @param driver Driver assigned to deliver the order
     */
    public void assignDriver(final Driver driver) {
        this.assignedDriver = driver;
        this.status = OrderStatus.ACCEPTED;
    }

    /**
     * Updates the current status of the order.
     * 
     * @param newStatus New status to be set for the order
     */
    public void updateStatus(final OrderStatus newStatus) {
        this.status = newStatus;
    }

    // Getter methods provide controlled access to order information

    public String getCustomerName() {
        return this.customerName;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    /**
     * Returns a copy of the order items to prevent direct modification.
     * 
     * @return List of menu items in the order
     */
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