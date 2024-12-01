package app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Driver;
import model.Order;
import rating.Rating;
import utilities.ConsoleInputHandler;
import utilities.InputValidator;
import utilities.InputValidatorImpl;
import utilities.PositiveIntegerValidator;

public class DeliverySystemCLI {
    private static final Logger logger = Logger.getLogger(DeliverySystemCLI.class.getName());

    private final Scanner scanner;
    private final DeliverySystem deliverySystem;

    // Main menu choice validator
    private final InputValidator<Integer> menuChoiceValidator;
    private final ConsoleInputHandler<Integer> menuChoiceHandler;

    // Positive integer validator for various inputs
    private final InputValidator<Integer> positiveIntegerValidator;
    private final ConsoleInputHandler<Integer> positiveIntegerHandler;

    private final Queue<Order> orderQueue;
    private final List<Driver> drivers;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.deliverySystem = new DeliverySystem();
        this.orderQueue = new LinkedList<>();
        this.drivers = new ArrayList<>();

        // Validator for menu choices (1-6)
        this.menuChoiceValidator = new InputValidatorImpl<>(
                (Predicate<Integer>) input -> input >= 1 && input <= 6,
                "Menu Choice must be between 1 and 6");
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

    // Placeholder methods to resolve undefined method errors
    private void placeOrder() {
        System.out.println("Place Order functionality not implemented yet.");
    }

    private void viewMenu() {
        System.out.println("View Menu functionality not implemented yet.");
    }

    private void manageDrivers() {
        System.out.println("\n=== Driver Management ===");
        System.out.println("1. Add Driver");
        System.out.println("2. View Drivers");

        // Use validated input for driver management choice
        Integer choice = this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice: ");

        if (choice == null)
            return;

        switch (choice) {
            case 1 -> this.addDriver();
            case 2 -> this.viewDrivers();
        }
    }

    private void addDriver() {
        System.out.print("Enter Driver Name: ");
        String name = this.scanner.nextLine();

        System.out.print("Enter Driver Location: ");
        String location = this.scanner.nextLine();

        Driver newDriver = new Driver(name, location);
        this.drivers.add(newDriver);
        System.out.println("Driver added successfully!");
    }

    private void viewDrivers() {
        if (this.drivers.isEmpty()) {
            System.out.println("No drivers registered.");
            return;
        }

        System.out.println("\n=== Registered Drivers ===");
        for (int i = 0; i < this.drivers.size(); i++) {
            Driver driver = this.drivers.get(i);
            System.out.printf("%d. %s (Location: %s)\n",
                    i + 1, driver.getName(), driver.getLocation());
        }
    }

    private void rateDriver() {
        this.viewDrivers();

        if (this.drivers.isEmpty())
            return;

        // Validate driver selection
        Integer driverIndex = this.positiveIntegerHandler.handleInput(
                this.scanner,
                "Select driver to rate (number): ",
                input -> input > 0 && input <= drivers.size());

        if (driverIndex == null)
            return;

        // Validate rating input
        Integer ratingValue = this.positiveIntegerHandler.handleInput(
                this.scanner,
                "Enter rating (1-5): ",
                input -> input >= 1 && input <= 5);

        if (ratingValue == null)
            return;

        Driver selectedDriver = this.drivers.get(driverIndex - 1);
        selectedDriver.addRating(new Rating(ratingValue));
        System.out.println("Rating added successfully!");
    }

    private void checkOrderStatus() {
        try {
            // Validate order ID input
            Long orderId = this.positiveIntegerHandler.handleLongInput(
                    this.scanner,
                    "Enter Order ID to check status: ");

            if (orderId == null)
                return;

            final String status = this.deliverySystem.getOrderStatus(orderId);
            System.out.println("Order Status for Order ID " + orderId + ": " + status);

        } catch (final Exception e) {
            DeliverySystemCLI.logger.log(Level.SEVERE, "Error checking order status", e);
            System.out.println("Error checking order status: " + e.getMessage());
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== Food Delivery System ===");
        System.out.println("1. Place Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. View Menu");
        System.out.println("4. Manage Drivers");
        System.out.println("5. Rate Driver");
        System.out.println("6. Exit");
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}