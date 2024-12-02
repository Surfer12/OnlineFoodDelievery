package app;

import java.util.Scanner;

public class DeliverySystemCLI {
    private final Scanner scanner;
    private final OrderQueue orderQueue;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.orderQueue = new OrderQueue();
        this.initializeMenu();
    }

    private void initializeMenu() {
        // Initialize menu items
        final MenuItem hamburger = new MenuItem("Hamburger", 5.99);
        final MenuItem fries = new MenuItem("Fries", 2.99);
        final MenuItem drink = new MenuItem("Drink", 1.99);
    }

    private final ConsoleInputHandler<String> emailHandler = 
        new ConsoleInputHandler<>(
            InputValidationUtils::validateEmailFormat, 
            "Invalid email format"
        );

    private final ConsoleInputHandler<Integer> positiveIntHandler = 
        new ConsoleInputHandler<>(
            input -> input > 0, 
            "Invalid positive integer"
        );

    private final ConsoleInputHandler<String> locationHandler = 
        new ConsoleInputHandler<>(
            InputValidationUtils::validateLocation, 
            "Invalid location"
        );

    public void start() {
        // Main application logic will be implemented here
        System.out.println("Online Food Delivery System");
        // TODO: Implement main menu and core functionality
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}