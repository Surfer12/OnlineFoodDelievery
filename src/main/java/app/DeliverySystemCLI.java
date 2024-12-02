package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import managers.DriverManager;
import managers.MenuManager;
import managers.OrderManager;
import model.ConcreteMenuItem;
import model.MenuItem;
import model.Size;
import queue.OrderQueue;
import validation.ConsoleInputHandler;
import validation.validators.EmailValidator;
import validation.validators.LocationValidator;
import validation.validators.PositiveIntegerValidator;

public class DeliverySystemCLI {
    private final Scanner scanner;
    private final OrderQueue orderQueue;

    private final ConsoleInputHandler<Integer> positiveIntHandler;
    private final ConsoleInputHandler<String> emailHandler;
    private final ConsoleInputHandler<String> locationHandler;

    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private final DriverManager driverManager;

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
    public DeliverySystemCLI(final Scanner scanner, final MenuManager menuManager, final OrderManager orderManager,
            final DriverManager driverManager) {
        this.scanner = scanner;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.driverManager = driverManager;
    }

    private void initializeMenu() {
        // Initialize menu items using ConcreteMenuItem
        final MenuItem hamburger = new ConcreteMenuItem(1L, "Hamburger", "Beef patty with lettuce and tomato", 5.99,
                Size.MEDIUM, 1);
        final MenuItem fries = new ConcreteMenuItem(2L, "Fries", "Crispy golden fries", 2.99, Size.LARGE, 1);
        final MenuItem drink = new ConcreteMenuItem(3L, "Drink", "Refreshing beverage", 1.99, Size.SMALL, 1);
        this.menuManager.addMenuItem(hamburger);
        this.menuManager.addMenuItem(fries);
        this.menuManager.addMenuItem(drink);
    }

    public void start() {
        System.out.println("=== Online Food Delivery System ===");

        while (true) {
            this.displayMainMenu();
            final Integer choice = this.positiveIntHandler.handleInput(this.scanner, "Enter your choice: ");

            if (choice == null)
                continue;

            switch (choice) {
                case 1 -> this.orderManager.processOrderPlacement(this.scanner, this.menuManager,
                        this.positiveIntHandler, this.emailHandler,
                        this.locationHandler);
                case 2 -> this.orderManager.checkOrderStatus(this.scanner);
                case 3 -> this.menuManager.displayMenu();
                case 4 -> this.driverManager.manageDriverMenu(this.scanner, this.orderManager);
                case 5 -> this.driverManager.rateDriverInteractive(this.scanner, this.orderManager);
                case 6 -> {
                    System.out.println("Exiting system. Goodbye!");
                    this.scanner.close();
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
                            selectedItems.add(new ConcreteMenuItem(1L, "Hamburger",
                                    "Beef patty with lettuce and tomato", 5.99, Size.MEDIUM, 1));
                            break;
                        case "2":
                            selectedItems
                                    .add(new ConcreteMenuItem(2L, "Fries", "Crispy golden fries", 2.99, Size.LARGE, 1));
                            break;
                        case "3":
                            selectedItems
                                    .add(new ConcreteMenuItem(3L, "Drink", "Refreshing beverage", 1.99, Size.SMALL, 1));
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