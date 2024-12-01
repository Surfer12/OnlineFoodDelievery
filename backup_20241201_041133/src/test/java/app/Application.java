package app;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DeliverySystem {
    public void submitOrder(Order order) {
        // Placeholder implementation
        System.out.println("Order submitted: " + order);
    }

    public void assignOrderToDriver(Order order, Driver driver) {
        // Placeholder implementation
        System.out.println("Order assigned to driver: " + driver);
    }

    public void completeDelivery(Long orderId, Long driverId) {
        // Placeholder implementation
        System.out.println("Delivery completed for order: " + orderId);
    }
}

/**
 * The Application class is the entry point for the delivery system application.
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * The main method to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Initialize delivery system
            DeliverySystem deliverySystem = new DeliverySystem();

            // Create a placeholder NotificationService if needed
            NotificationService notificationService = new NotificationService() {
                @Override
                public void sendNotification(String message) {
                    System.out.println("Notification: " + message);
                }
            };
            
            Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

            // Initialize MenuItemFactory
            MenuItemFactory factory = new MenuItemFactory();

            // Create menu items
            MenuItem pizza = factory.createMenuItem(
                "hamburger", 
                "Pepperoni Pizza", 
                "Spicy pepperoni with cheese", 
                12.99,
                Size.MEDIUM, 
                1
            );

            // Build the order using OrderBuilder
            Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

            // Submit the order
            deliverySystem.submitOrder(order);

            // Assign driver and complete delivery
            deliverySystem.assignOrderToDriver(order, driver);
            deliverySystem.completeDelivery(order.getOrderId(), driver.getId());

            // Use try-with-resources for Scanner
            try (Scanner scanner = new Scanner(System.in)) {
                // Create a PositiveIntegerValidator instance
                PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
                InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");

                ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(scanner, inputValidator);

                Integer userInput = inputHandler.getInput("Enter a positive integer: ");
                System.out.println("You entered: " + userInput);
            }

        } catch (Exception e) {
            logger.error("An error occurred while processing the order", e);
            System.err.println("An error occurred while processing the order: " + e.getMessage());
        }
    }
}
