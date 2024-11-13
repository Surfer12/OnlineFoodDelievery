package main;

import notification.CustomerNotifier;
import notification.DriverNotifier;
import model.Order;
import model.Driver;
import model.Size;
import model.MenuItem;
import factory.MenuItemFactory;
import orderUtilities.OrderBuilder;
import notification.NotificationService;

import java.util.Scanner;

import ConsoleInputHandler.ConsoleInputHandler;
import ConsoleInputValidator.InputValidator;
import ConsoleInputValidator.PositiveIntegerValidator;

public class Application {

    /**
     * The main method to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DeliverySystem deliverySystem = new DeliverySystem();

        // Register observers
        // With a valid NotificationService
        NotificationService notificationService = new NotificationService();
        deliverySystem.addObserver(new CustomerNotifier(notificationService));
        deliverySystem.addObserver(new DriverNotifier(notificationService));

        // Initialize MenuItemFactory
        MenuItemFactory factory = new MenuItemFactory();

        // Create menu items
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99,
                Size.MEDIUM, 1);

        // Build the order using OrderBuilder
        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        try {
            // Submit the order
            deliverySystem.submitOrder(order);

            // Create a driver with required parameters
            Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

            // Assign driver and complete delivery
            deliverySystem.assignOrderToDriver(order, driver);
            deliverySystem.completeDelivery(order.getOrderId(), driver.getId());
        } catch (Exception e) {
            System.err.println("An error occurred while processing the order: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        // Create a PositiveIntegerValidator instance
        PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");

        ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(scanner, inputValidator);

        Integer userInput = inputHandler.getInput("Enter a positive integer: ");
        System.out.println("You entered: " + userInput);

        scanner.close();
    }
}
