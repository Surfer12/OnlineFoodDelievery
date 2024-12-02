package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ConcreteMenuItem;
import model.MenuItem;
import validation.ConsoleInputHandler;
import validation.validators.EmailValidator;
import validation.validators.LocationValidator;
import validation.validators.PositiveIntegerValidator;
import order.Order; // Updated import
import managers.MenuManager;
import managers.OrderManager;
import managers.DriverManager;

public class DeliverySystemCLI {
    private final Scanner scanner;
    private final OrderQueue orderQueue;

    private final ConsoleInputHandler<Integer> positiveIntHandler;
    private final ConsoleInputHandler<String> emailHandler;
    private final ConsoleInputHandler<String> locationHandler;

    private MenuManager menuManager;
    private OrderManager orderManager;
    private DriverManager driverManager;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.orderQueue = new OrderQueue();
        this.initializeMenu();

        // Explicitly specify type arguments
        this.positiveIntHandler = new ConsoleInputHandler<Integer>(new PositiveIntegerValidator());
        this.emailHandler = new ConsoleInputHandler<String>(new EmailValidator());
        this.locationHandler = new ConsoleInputHandler<String>(new LocationValidator());

        // Initialize managers
        this.menuManager = new MenuManager();
        this.orderManager = new OrderManager();
        this.driverManager = new DriverManager();
    }

    // Constructor with required parameters
    public DeliverySystemCLI(Scanner scanner, MenuManager menuManager, OrderManager orderManager, DriverManager driverManager) {
        this.scanner = scanner;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.driverManager = driverManager;
    }

    private void initializeMenu() {
        // Initialize menu items using ConcreteMenuItem
        final MenuItem hamburger = new ConcreteMenuItem("Hamburger", 5.99);
        final MenuItem fries = new ConcreteMenuItem("Fries", 2.99);
        final MenuItem drink = new ConcreteMenuItem("Drink", 1.99);
        menuManager.addMenuItem(hamburger);
        menuManager.addMenuItem(fries);
        menuManager.addMenuItem(drink);
    }

    public void start() {
        System.out.println("=== Online Food Delivery System ===");

        while (true) {
            displayMainMenu();
            Integer choice = positiveIntHandler.handleInput(scanner, "Enter your choice: ");

            if (choice == null)
                continue;

            switch (choice) {
                case 1 -> orderManager.processOrderPlacement(scanner, menuManager, positiveIntHandler, emailHandler, locationHandler);
                case 2 -> orderManager.checkOrderStatus(scanner);
                case 3 -> menuManager.displayMenu();
                case 4 -> driverManager.manageDriverMenu(scanner, orderManager);
                case 5 -> driverManager.rateDriverInteractive(scanner, orderManager);
                case 6 -> {
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Place Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. View Menu");
        System.out.println("4. Manage Drivers");
        System.out.println("5. Rate Driver");
        System.out.println("6. Exit");
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
                            selectedItems.add(new ConcreteMenuItem("Hamburger", 5.99));
                            break;
                        case "2":
                            selectedItems.add(new ConcreteMenuItem("Fries", 2.99));
                            break;
                        case "3":
                            selectedItems.add(new ConcreteMenuItem("Drink", 1.99));
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