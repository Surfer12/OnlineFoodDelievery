package main;

import notification.CustomerNotifier;
import notification.DriverNotifier;
import model.Order;
import model.Driver;
import factory.MenuItemFactory;
import menu.MenuItem;
import orderUtilities.OrderBuilder;

public class Application {
    public static void main(String[] args) {
        DeliverySystem deliverySystem = new DeliverySystem();

        // Register observers
        deliverySystem.addObserver(new CustomerNotifier(null));
        deliverySystem.addObserver(new DriverNotifier(null));

        // Initialize MenuItemFactory
        MenuItemFactory factory = new MenuItemFactory();

        // Create menu items
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99);

        // Build the order using OrderBuilder
        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withDeliveryLocation("456 Elm Street", "12345")
                .build();

        // Submit the order
        deliverySystem.submitOrder(order);

        // Create a driver with required parameters
        Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        // Assign driver and complete delivery
        deliverySystem.assignOrderToDriver(order, driver);
        deliverySystem.completeDelivery(order.getOrderId(), driver.getId());
    }
}