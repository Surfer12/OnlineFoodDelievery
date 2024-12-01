package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import model.Driver;
import model.MenuItem;
import model.Order;
import services.MenuService;
import services.OrderService;
import services.impl.MenuServiceImpl;
import services.impl.OrderServiceImpl;
import utilities.ConsoleInputHandler;
import utilities.InputValidator;
import utilities.InputValidatorImpl;
import utilities.PositiveIntegerValidator;
import validation.MenuItemValidator;

public class DeliverySystemCLI {
    private static final Logger logger = Logger.getLogger(DeliverySystemCLI.class.getName());

    private final Scanner scanner;
    private final MenuService menuService;
    private final OrderService orderService;

    // Validators and input handlers
    private final InputValidator<Integer> menuChoiceValidator;
    private final ConsoleInputHandler<Integer> menuChoiceHandler;
    private final InputValidator<Integer> positiveIntegerValidator;
    private final ConsoleInputHandler<Integer> positiveIntegerHandler;

    private final List<Driver> drivers;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.menuService = new MenuServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.drivers = new ArrayList<>();

        // Validator for menu choices (1-6)
        this.menuChoiceValidator = new MenuItemValidator();
        this.menuChoiceHandler = new ConsoleInputHandler<>(this.menuChoiceValidator);

        // Validator for positive integers
        final PositiveIntegerValidator positiveValidator = new PositiveIntegerValidator();
        this.positiveIntegerValidator = new InputValidatorImpl<>(positiveValidator, "Positive Integer");
        this.positiveIntegerHandler = new ConsoleInputHandler<>(this.positiveIntegerValidator);
    }

    public void start() {
        boolean running = true;
        while (running) {
            this.displayMainMenu();

            // Use validated input for menu choice
            Integer choice = this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice: ");

            if (choice == null)
                continue;

            switch (choice) {
                case 1 -> this.placeOrder();
                case 2 -> this.checkOrderStatus();
                case 3 -> this.viewMenu();
                case 4 -> this.manageDrivers();
                case 5 -> this.rateDriver();
                case 6 -> running = false;
            }
        }
        this.scanner.close();
    }

    private void placeOrder() {
        System.out.println("\n=== Place Order ===");

        // Display menu items
        this.menuService.displayMenu();

        List<MenuItem> orderItems = new ArrayList<>();
        boolean addingItems = true;

        while (addingItems) {
            System.out.print("Enter menu item number to add (0 to finish): ");
            Integer itemChoice = this.positiveIntegerHandler.handleInput(
                    this.scanner,
                    "Select a menu item: ",
                    input -> input >= 0 && input <= menuService.getMenuSize());

            if (itemChoice == null || itemChoice == 0) {
                addingItems = false;
                continue;
            }

            MenuItem selectedItem = this.menuService.getMenuItemByIndex(itemChoice);
            System.out.print("Enter quantity: ");
            Integer quantity = this.positiveIntegerHandler.handleInput(
                    this.scanner,
                    "Enter quantity: ",
                    input -> input > 0);

            if (quantity != null) {
                for (int i = 0; i < quantity; i++) {
                    orderItems.add(selectedItem);
                }
            }
        }

        if (orderItems.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        // Create and submit order
        Order newOrder = this.orderService.createOrder(orderItems);
        this.orderService.displayOrderDetails(newOrder);
        System.out.println("Order placed successfully! Order ID: " + newOrder.getOrderId());
    }

    private void viewMenu() {
        this.menuService.displayMenu();
    }

    private void checkOrderStatus() {
        try {
            // Validate order ID input
            Long orderId = this.positiveIntegerHandler.handleLongInput(
                    this.scanner,
                    "Enter Order ID to check status: ");

            if (orderId == null)
                return;

            final String status = this.orderService.getOrderStatus(orderId);
            System.out.println("Order Status for Order ID " + orderId + ": " + status);

        } catch (final Exception e) {
            logger.log(Level.SEVERE, "Error checking order status", e);
            System.out.println("Error checking order status: " + e.getMessage());
        }
    }

    // Other methods (manageDrivers, rateDriver, displayMainMenu) remain the same

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}