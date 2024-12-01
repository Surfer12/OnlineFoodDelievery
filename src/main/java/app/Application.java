package app;

import java.util.Scanner;

import apple.laf.JRSUIConstants.Size;
import core.builder.OrderBuilder;
import factory.MenuItemFactory;
import models.model.MenuItem;
import models.model.Order;
import utilities.src.main.java.ConsoleInputHandler.ConsoleInputHandler;
import utilities.src.main.java.ConsoleInputValidator.InputValidator;
import utilities.src.main.java.ConsoleInputValidator.PositiveIntegerValidator;

class DeliverySystem {
    public void submitOrder(final Order order) {
        // Placeholder implementation
        System.out.println("Order submitted: " + order);
    }

    public void assignOrderToDriver(final Order order, final Driver driver) {
        // Placeholder implementation
        System.out.println("Order assigned to driver: " + driver);
    }

    public void completeDelivery(final Long orderId, final Long driverId) {
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
    public static void main(final String[] args) {
        try {
            // Initialize delivery system
            final DeliverySystem deliverySystem = new DeliverySystem();

            // Create a placeholder NotificationService if needed
            final NotificationService notificationService = new NotificationService() {
                @Override
                public void sendNotification(final String message) {
                    System.out.println("Notification: " + message);
                }
            };
            
            final Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

            // Initialize MenuItemFactory
            final MenuItemFactory factory = new MenuItemFactory();

            // Create menu items
            final MenuItem pizza = factory.createMenuItem(
                "hamburger", 
                "Pepperoni Pizza", 
                "Spicy pepperoni with cheese", 
                12.99,
                Size.MEDIUM, 
                1
            );

            // Build the order using OrderBuilder
            final Order order = new OrderBuilder()
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
                final PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
                final InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");

                final ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(scanner, inputValidator);

                final Integer userInput = inputHandler.getInput("Enter a positive integer: ");
                System.out.println("You entered: " + userInput);
            }

        } catch (final Exception e) {
            Application.logger.error("An error occurred while processing the order", e);
            System.err.println("An error occurred while processing the order: " + e.getMessage());
        }
    }
}
