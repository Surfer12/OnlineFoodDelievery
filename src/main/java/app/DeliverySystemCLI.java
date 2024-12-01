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

    private boolean running = true;

    public DeliverySystemCLI() {
        this.scanner = new Scanner(System.in);
        this.menuManager = new MenuManager();
        this.orderManager = new OrderManager();
        this.driverManager = new DriverManager();

        this.positiveIntegerHandler = new ConsoleInputHandler<>(
                new InputValidatorImpl<>(
                        new PositiveIntegerValidator(),
                        "Positive Integer",
                        "Invalid positive integer"));
    }

    public void start() {
        while (this.running) {
            this.displayMainMenu();

            Integer choice = this.menuManager.getMenuChoiceHandler().handleInput(
                    this.scanner,
                    "Enter your choice below: ");

            if (choice == null)
                continue;

            switch (choice) {
                case 1->this.orderManager.processOrderPlacement(
                        this.scanner,
                        this.menuManager,
                        this.positiveIntegerHandler);
                case 2->this.orderManager.checkOrderStatus(this.scanner);
                case 3->this.menuManager.displayMenu();
                case 4->this.driverManager.manageDriverMenu(this.scanner, this.orderManager);
                case 5 -> this.driverManager.rateDriver(
                        this.scanner,
                        this.orderManager.getOrderService().getOrderById(
                                this.orderManager.getOrderIdHandler().handleInput(
                                        this.scanner,
                                        "Enter Order ID to rate driver: ")),
                        this.menuManager.getMenuChoiceHandler());
                case 6 -> this.running = false;
            }
        }
        this.scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n--- Online Food Delivery System ---");
        System.out.println("1. Place a New Order");
        System.out.println("2. Check Order Status");
        System.out.println("3. View Menu");
        System.out.println("4. Manage Drivers");
        System.out.println("5. Rate Driver");
        System.out.println("6. Exit");
        System.out.print("Please choose an option from the list above; Options One through Six: ");
    }

    public static void main(final String[] args) {
        final DeliverySystemCLI cli = new DeliverySystemCLI();
        cli.start();
    }
}