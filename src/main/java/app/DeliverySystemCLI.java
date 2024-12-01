package app;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import builder.OrderBuilder;
import factory.MenuItemFactory;
import model.MenuItem;
import model.Order;
import model.Size;
import utilities.ConsoleInputHandler;
import utilities.InputValidator;
import utilities.InputValidatorImpl;
import utilities.PositiveIntegerValidator;

public class DeliverySystemCLI {
    private static final Logger logger = Logger.getLogger(DeliverySystemCLI.class.getName());

    private final Scanner scanner;
    private final DeliverySystem deliverySystem;
    private final InputValidator<Integer> inputValidator;
    private final ConsoleInputHandler<Integer> inputHandler;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.deliverySystem = new DeliverySystem();

        // Setup input validation
        final PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        this.inputValidator = new InputValidatorImpl<>(positiveIntegerValidator, "Positive Integer");
        this.inputHandler = new ConsoleInputHandler<>(this.inputValidator);
    }

    public void start() {
        boolean running = true;
        while (running) {
            this.displayMainMenu();
            final String choice = this.scanner.nextLine();
            switch (choice) {
                case "1" -> this.placeOrder();
                case "2" -> this.checkOrderStatus();
                case "3" -> this.viewMenu();
                case "4" -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        this.scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n=== Food Delivery System ===");
        System.out.println("1. Place Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. View Menu");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private void placeOrder() {
        try {
            // Get customer details
            System.out.print("Enter Customer ID: ");
            final Long customerId = Long.parseLong(this.scanner.nextLine());

            System.out.print("Enter Customer Email: ");
            final String customerEmail = this.scanner.nextLine();

            // View menu and select items
            final List<MenuItem> menuItems = this.viewMenuItems();

            if (menuItems.isEmpty()) {
                System.out.println("No menu items available.");
                return;
            }

            // Select menu item
            System.out.print("Enter the number of the item you want to order: ");
            final int itemChoice = Integer.parseInt(this.scanner.nextLine());

            if (itemChoice < 1 || itemChoice > menuItems.size()) {
                System.out.println("Invalid item selection.");
                return;
            }

            final MenuItem selectedItem = menuItems.get(itemChoice - 1);

            // Get delivery location
            System.out.print("Enter Delivery Address: ");
            final String address = this.scanner.nextLine();

            System.out.print("Enter Postal Code: ");
            final String postalCode = this.scanner.nextLine();

            // Build and submit order
            final Order order = new OrderBuilder()
                    .withValidatedCustomerId(customerId)
                    .withCustomerEmail(customerEmail)
                    .addItem(selectedItem)
                    .withDeliveryLocation(address, postalCode)
                    .build();

            this.deliverySystem.submitOrder(order);
            System.out.println("Order placed successfully! Order ID: " + order.getOrderId());

        } catch (final Exception e) {
            DeliverySystemCLI.logger.log(Level.SEVERE, "Error placing order", e);
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    private void checkOrderStatus() {
        try {
            System.out.print("Enter Order ID to check status: ");
            final Long orderId = Long.parseLong(this.scanner.nextLine());

            // Assuming DeliverySystem has a method to get order status
            final String status = this.deliverySystem.getOrderStatus(orderId);
            System.out.println("Order Status for Order ID " + orderId + ": " + status);

        } catch (final Exception e) {
            DeliverySystemCLI.logger.log(Level.SEVERE, "Error checking order status", e);
            System.out.println("Error checking order status: " + e.getMessage());
        }
    }

    private List<MenuItem> viewMenuItems() {
        // Create some sample menu items
        final List<MenuItem> menuItems = List.of(
                MenuItemFactory.createMenuItem("pizza", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99,
                        Size.MEDIUM, 1),
                MenuItemFactory.createMenuItem("burger", "Classic Hamburger", "Juicy beef patty with cheese", 8.99,
                        Size.MEDIUM, 1),
                MenuItemFactory.createMenuItem("salad", "Caesar Salad", "Fresh romaine with Caesar dressing", 6.99,
                        Size.MEDIUM, 1));

        System.out.println("\n=== Menu Items ===");
        for (int i = 0; i < menuItems.size(); i++) {
            final MenuItem item = menuItems.get(i);
            System.out.printf("%d. %s - $%.2f\n", i + 1, item.getName(), item.getPrice());
        }

        return menuItems;
    }

    private void viewMenu() {
        this.viewMenuItems();
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}