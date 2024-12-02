package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.MenuItem;
import validation.ConsoleInputHandler;
import validation.EmailValidator;
import validation.PositiveIntegerValidator;
import validation.validators.LocationValidator;

public class DeliverySystemCLI {
    private final Scanner scanner;
    private final OrderQueue orderQueue;

    private final ConsoleInputHandler<Integer> positiveIntHandler;
    private final ConsoleInputHandler<String> emailHandler;
    private final ConsoleInputHandler<String> locationHandler;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.orderQueue = new OrderQueue();
        this.initializeMenu();

        // Initialize input handlers with specific validators
        this.positiveIntHandler = new ConsoleInputHandler<>(new PositiveIntegerValidator());
        this.emailHandler = new ConsoleInputHandler<>(new EmailValidator());
        this.locationHandler = new ConsoleInputHandler<>(new LocationValidator());
    }

    private void initializeMenu() {
        // Initialize menu items
        final MenuItem hamburger = new MenuItem("Hamburger", 5.99);
        final MenuItem fries = new MenuItem("Fries", 2.99);
        final MenuItem drink = new MenuItem("Drink", 1.99);
    }

    public void start() {
        System.out.println("=== Online Food Delivery System ===");

        // Customer ID Input
        System.out.println("\nCustomer ID Requirements:");
        System.out.println("- Must be a positive number");
        final Integer customerId = this.positiveIntHandler.getInput("Enter Customer ID: ");

        // Email Input
        System.out.println("\nEmail Address Requirements:");
        System.out.println("- Must be a valid email format");
        System.out.println("- Example: username@example.com");
        final String customerEmail = this.emailHandler.getInput("Enter Email Address: ");

        // Delivery Address Input
        System.out.println("\nDelivery Address Requirements:");
        System.out.println("- Cannot be empty");
        System.out.println("- Must be a valid location");
        final String deliveryAddress = this.locationHandler.getInput("Enter Delivery Address: ");

        // Postal Code Input
        System.out.println("\nPostal Code Requirements:");
        System.out.println("- Must be a valid US postal code");
        System.out.println("- 5 digits (e.g., 12345)");
        System.out.println("- Optional 4-digit extension (e.g., 12345-6789)");
        final String postalCode = this.getValidPostalCode();

        // Order Items Input
        System.out.println("\nOrder Items Requirements:");
        System.out.println("- Must select at least one item");
        System.out.println("Available Menu Items:");
        this.displayMenuItems();
        final List<MenuItem> orderItems = this.selectOrderItems();

        // TODO: Complete order processing logic
        System.out.println("\nOrder Summary:");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Email: " + customerEmail);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Postal Code: " + postalCode);
        System.out.println("Selected Items: " + orderItems);
    }

    private String getValidPostalCode() {
        while (true) {
            System.out.print("Enter Postal Code: ");
            final String input = this.scanner.nextLine().trim();
            if (input.matches("^\\d{5}(-\\d{4})?$")) {
                return input;
            }
            System.out.println("Invalid postal code. Please enter a valid US postal code (e.g., 12345 or 12345-6789):");
        }
    }

    private void displayMenuItems() {
        // Assuming menu items were initialized in initializeMenu()
        System.out.println("1. Hamburger - $5.99");
        System.out.println("2. Fries - $2.99");
        System.out.println("3. Drink - $1.99");
    }

    private List<MenuItem> selectOrderItems() {
        final List<MenuItem> selectedItems = new ArrayList<>();
        System.out.println("Enter item numbers (separated by space), or press Enter to finish:");

        while (true) {
            final String input = this.scanner.nextLine().trim();
            if (input.isEmpty() && !selectedItems.isEmpty()) {
                break;
            }

            try {
                final String[] itemNumbers = input.split("\\s+");
                for (final String itemNum : itemNumbers) {
                    switch (itemNum) {
                        case "1":
                            selectedItems.add(new MenuItem("Hamburger", 5.99));
                            break;
                        case "2":
                            selectedItems.add(new MenuItem("Fries", 2.99));
                            break;
                        case "3":
                            selectedItems.add(new MenuItem("Drink", 1.99));
                            break;
                        default:
                            System.out.println("Invalid item number: " + itemNum);
                    }
                }
            } catch (final Exception e) {
                System.out.println("Error selecting items. Please try again.");
            }
        }

        return selectedItems;
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}