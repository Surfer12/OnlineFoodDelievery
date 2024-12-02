package app;

import java.util.Scanner;
import java.util.logging.Logger;

import managers.DriverManager;
import managers.MenuManager;
import managers.OrderManager;
import validation.ConsoleInputHandler;
import validation.InputValidatorImpl;
import validation.PositiveIntegerValidator;

public class DeliverySystemCLI {
    private static final Logger logger = Logger.getLogger(DeliverySystemCLI.class.getName());

    private final Scanner scanner;
    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private final DriverManager driverManager;

    private final ConsoleInputHandler<Integer> positiveIntegerHandler;
    private final ConsoleInputHandler<String> emailHandler;
    private final ConsoleInputHandler<String> locationHandler;

    private boolean running = true;

    public DeliverySystemCLI(final Scanner scanner, final MenuManager menuManager, final OrderManager orderManager,
            final DriverManager driverManager, final ConsoleInputHandler<Integer> positiveIntegerHandler,
            final ConsoleInputHandler<String> emailHandler, final ConsoleInputHandler<String> locationHandler) {
        this.scanner = scanner;
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.driverManager = driverManager;
        this.positiveIntegerHandler = positiveIntegerHandler;
        this.emailHandler = emailHandler;
        this.locationHandler = locationHandler;
    }

    public DeliverySystemCLI() {
        this(new Scanner(System.in),
                new MenuManager(),
                new OrderManager(),
                new DriverManager(),
                new ConsoleInputHandler<>(
                        new InputValidatorImpl<>(
                                new PositiveIntegerValidator(),
                                "Positive Integer",
                                "Invalid positive integer")),
                new ConsoleInputHandler<>(
                        InputValidatorImpl.emailValidator()),
                new ConsoleInputHandler<>(
                        InputValidatorImpl.deliveryLocationValidator()));
    }

    public void start() {
        while (this.running) {
            this.displayMainMenu();

            Integer choice = this.menuManager.getMenuChoiceHandler().handleInput(
                    this.scanner,
                    "Enter your choice below: ");

            if (choice == null) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Allow customer to place a new order
                    this.orderManager.processOrderPlacement(
                            this.scanner,
                            this.menuManager,
                            this.positiveIntegerHandler);
                    break;
                case 2:
                    // Allow driver to accept and deliver an order
                    this.driverManager.acceptAndDeliverOrder(this.scanner, this.orderManager);
                    break;
                case 3:
                    // Check order status
                    this.orderManager.checkOrderStatus(this.scanner);
                    break;
                case 4:
                    // View menu
                    this.menuManager.displayMenu();
                    break;
                case 5:
                    // Manage drivers
                    this.driverManager.manageDriverMenu(this.scanner, this.orderManager);
                    break;
                case 6:
                    // Rate driver
                    this.driverManager.rateDriverInteractive(this.scanner, this.orderManager);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    this.running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
        this.scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n--- Online Food Delivery System ---");
        System.out.println("1. Place a New Order");
        System.out.println("2. Accept and Deliver an Order");
        System.out.println("3. Check Order Status");
        System.out.println("4. View Menu");
        System.out.println("5. Manage Drivers");
        System.out.println("6. Rate Driver");
        System.out.println("7. Exit");
        System.out.print("Please choose an option from the list above (1-7): ");
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}