package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import model.Driver;
import model.MenuItem;
import model.Order;
import queue.OrderQueue;
import services.DriverService;
import services.MenuService;
import services.OrderService;
import services.impl.DriverServiceImpl;
import services.impl.MenuServiceImpl;
import services.impl.OrderServiceImpl;
import utilities.PositiveIntegerValidator;
import validation.ConsoleInputHandler;
import validation.InputValidatorImpl;
import validation.MenuItemValidator;

public class DeliverySystemCLI {
    private static final Logger logger = Logger.getLogger(DeliverySystemCLI.class.getName());
    private static final int MAX_QUEUE_SIZE = 100; // Maximum number of orders in queue

    private final Scanner scanner;
    private final MenuService menuService;
    private final OrderService orderService;
    private final DriverService driverService;
    private final OrderQueue orderQueue;

    // Validators and input handlers
    private final InputValidatorImpl<Integer> menuChoiceValidator;
    private final ConsoleInputHandler<Integer> menuChoiceHandler;
    private final InputValidatorImpl<Integer> positiveIntegerValidator;
    private final ConsoleInputHandler<Integer> positiveIntegerHandler;

    private final List<Driver> drivers;
    private boolean running = true;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.driverService = new DriverServiceImpl();
        this.menuService = new MenuServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.orderQueue = new OrderQueue(MAX_QUEUE_SIZE);
        this.drivers = new ArrayList<>();

        // Initialize validators
        this.menuChoiceValidator = new InputValidatorImpl<>(new MenuItemValidator());
        this.menuChoiceHandler = new ConsoleInputHandler<>(this.menuChoiceValidator);

        this.positiveIntegerValidator = new InputValidatorImpl<Integer>(new PositiveIntegerValidator());
        this.positiveIntegerHandler = new ConsoleInputHandler<>(this.positiveIntegerValidator);
    }

    public void start() {
        while (this.running) {
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
                case 6 -> this.running = false;
            }
        }
        this.scanner.close();
    }

    private void placeOrder() {
        System.out.println("\n=== Place Order ===");
        this.menuService.displayMenu();

        List<MenuItem> orderItems = new ArrayList<>();
        boolean addingItems = true;

        while (addingItems) {
            System.out.print("Enter menu item number to add (0 to finish): ");
            Integer itemChoice = this.positiveIntegerHandler.handleInput(
                    this.scanner,
                    "Select a menu item: ",
                    input -> input >= 0 && input <= this.menuService.getMenuSize());

            if (itemChoice == null || itemChoice == 0) {
                addingItems = false;
                continue;
            }

            MenuItem selectedItem = this.menuService.getMenuItemByIndex(itemChoice);
            System.out.print("Enter quantity: ");
            Integer quantity = this.positiveIntegerHandler.handleInput(
                    this.scanner,
                    "Enter quantity: ",
                    input -> input > 0 && input <= 10); // Added maximum quantity limit

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

        try {
            // Create order and add to FIFO queue
            Order newOrder = this.orderService.createOrder(orderItems);
            this.orderQueue.enqueue(newOrder);
            this.orderService.displayOrderDetails(newOrder);
            System.out.println("Order placed successfully! Order ID: " + newOrder.getOrderId());
            logger.info("New order added to queue: " + newOrder.getOrderId());
        } catch (CustomException.QueueFullException e) {
            System.out.println("Sorry, we are currently at maximum order capacity. Please try again later.");
            logger.warning("Order queue full: " + e.getMessage());
        }
    }

    private void viewMenu() {
        List<MenuItem> menu = this.menuService.getAllMenuItems();
        System.out.println("\n--- Current Menu ---");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.printf("%d. %s - $%.2f\n", i + 1, item.getName(), item.getPrice());
        }
    }

    private void checkOrderStatus() {
        try {
            Long orderId = this.menuChoiceHandler.handleInput(this.scanner, "Enter Order ID to check status: ");

            if (orderId == null)
                return;

            Order order = this.orderService.getOrderById(orderId);
            if (order != null) {
                System.out.println("Order Status: " + order.getStatus());
                System.out.println("Queue Position: " + this.orderQueue.getPositionInQueue(order));
            } else {
                System.out.println("Order not found.");
            }
        } catch (Exception e) {
            System.out.println("Error checking order status: " + e.getMessage());
            logger.severe("Error in checkOrderStatus: " + e.getMessage());
        }
    }

    private void manageDrivers() {
        System.out.println("\n--- Driver Management ---");
        System.out.println("1. View Available Drivers");
        System.out.println("2. Assign Driver to Order");
        System.out.println("3. Return to Main Menu");

        Integer choice = this.menuChoiceHandler.handleInput(
                this.scanner,
                "Enter your choice: ");

        if (choice == null)
            return;

        switch (choice) {
            case 1 -> this.listAvailableDrivers();
            case 2 -> this.assignDriverToOrder();
            case 3 -> {
            } // Return to main menu
            default -> System.out.println("Invalid choice.");
        }
    }

    private void listAvailableDrivers() {
        List<Driver> availableDrivers = this.driverService.getAvailableDrivers();
        System.out.println("\n--- Available Drivers ---");
        for (Driver driver : availableDrivers) {
            System.out.println(driver.getName() + " - " + driver.getVehicle()); // Ensure getVehicle() is defined in
                                                                                // Driver
        }
    }

    private void assignDriverToOrder() {
        Long orderId = this.menuChoiceHandler.handleInput(
                this.scanner,
                "Enter Order ID to assign driver: ");

        if (orderId == null)
            return;

        List<Driver> availableDrivers = this.driverService.getAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers at the moment.");
            return;
        }

        System.out.println("\n--- Available Drivers ---");
        for (int i = 0; i < availableDrivers.size(); i++) {
            Driver driver = availableDrivers.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, driver.getName(), driver.getVehicle()); // Ensure getVehicle() is
                                                                                              // defined in Driver
        }

        Integer driverChoice = this.menuChoiceHandler.handleInput(
                this.scanner,
                "Select a driver (enter number): ");

        if (driverChoice == null || driverChoice < 1 || driverChoice > availableDrivers.size())
            return;

        Driver selectedDriver = availableDrivers.get(driverChoice - 1);
        Order order = this.orderService.getOrderById(orderId);

        if (order != null) {
            this.driverService.assignDriverToOrder(selectedDriver, order);
            System.out.println("Driver assigned successfully.");
        } else {
            System.out.println("Order not found.");
        }
    }

    private void rateDriver() {
        Long orderId = this.menuChoiceHandler.handleInput(
                this.scanner,
                "Enter Order ID to rate driver: ");

        if (orderId == null)
            return;

        Order order = this.orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        Driver driver = this.driverService.getDriverForOrder(order);
        if (driver == null) {
            System.out.println("No driver assigned to this order.");
            return;
        }

        Integer rating = this.menuChoiceHandler.handleInput(
                this.scanner,
                "Rate the driver (1-5 stars): ",
                input -> input >= 1 && input <= 5);

        if (rating != null) {
            this.driverService.rateDriver(driver, rating);
            System.out.println("Thank you for your feedback!");
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Online Food Delivery System ---");
        System.out.println("1. Place a New Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. View Menu");
        System.out.println("4. Manage Drivers");
        System.out.println("5. Rate Driver");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}