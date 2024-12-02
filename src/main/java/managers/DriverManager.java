package managers;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import model.Driver;
import order.OrderService;
import model.OrderModel;
import services.DriverService;
import services.impl.DriverServiceImpl;
import validation.ConsoleInputHandler;
import validation.InputValidatorImpl;
import validation.MenuItemValidator;
import model.OrderStatus;

public class DriverManager {
    private static final Logger logger = Logger.getLogger(DriverManager.class.getName());

    private final DriverService driverService;
    private final ConsoleInputHandler<Integer> menuChoiceHandler;

    public DriverManager() {
        this.driverService = new DriverServiceImpl(); // Added DriverServiceImpl integration
        this.menuChoiceHandler = new ConsoleInputHandler<>(
                new InputValidatorImpl<>(
                        new MenuItemValidator(3),
                        "Driver Management Choice",
                        "Invalid choice"));
    }

    public void listAvailableDrivers() {
        List<Driver> availableDrivers = this.driverService.getAvailableDrivers(); // Updated to use driverService
        System.out.println("\n--- Available Drivers ---");
        for (Driver driver : availableDrivers) {
            System.out.println(driver.getName() + " - " + driver.getVehicle());
        }
    }

    public void assignDriverToOrder(Scanner scanner, Order order, ConsoleInputHandler<Long> orderIdHandler) {
        List<Driver> availableDrivers = this.driverService.getAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers at the moment.");
            return;
        }

        System.out.println("\n--- Available Drivers ---");
        for (int i = 0; i < availableDrivers.size(); i++) {
            Driver driver = availableDrivers.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, driver.getName(), driver.getVehicle());
        }

        Integer driverChoice = this.menuChoiceHandler.handleInput(
                scanner,
                "Select a driver (enter number): ");

        if (driverChoice == null || driverChoice < 1 || driverChoice > availableDrivers.size())
            return;

        Driver selectedDriver = availableDrivers.get(driverChoice - 1);

        if (order != null) {
            this.driverService.assignDriverToOrder(selectedDriver, order);
            order.setStatus(OrderStatus.CONFIRMED); // Update order status
            System.out.println("Driver assigned successfully.");
            logger.info("Driver " + selectedDriver.getName() + " assigned to order " + order.getId()); // Use getId() method
        } else {
            System.out.println("Order not found.");
        }
    }

    public void rateDriver(Scanner scanner, Order order, ConsoleInputHandler<Integer> menuChoiceHandler) {
        Driver driver = this.driverService.getDriverForOrder(order);
        if (driver == null) {
            System.out.println("No driver assigned to this order.");
            return;
        }

        Integer rating = menuChoiceHandler.handleInput(
                scanner,
                "Rate the driver (1-5 stars): ",
                input -> input >= 1 && input <= 5);

        if (rating != null) {
            driver.addRating(rating); // Use addRating method
            System.out.println("Thank you for your feedback!");
            logger.info("Driver " + driver.getName() + " rated: " + rating + " stars");
        }
    }

    public void rateDriverInteractive(Scanner scanner, OrderManager orderManager) {
        Long orderId = orderManager.getOrderIdHandler().handleInput(
                scanner,
                "Enter Order ID to rate driver: ");

        if (orderId == null) return;

        Order order = orderManager.getOrderService().getOrderById(orderId);
        if (order != null && order.getStatus() == OrderStatus.DELIVERED) {
            this.rateDriver(scanner, order, this.menuChoiceHandler);
        } else {
            System.out.println("Order not found or not delivered yet.");
        }
    }

    public ConsoleInputHandler<Integer> getMenuChoiceHandler() {
        return this.menuChoiceHandler;
    }

    public void manageDriverMenu(Scanner scanner, OrderManager orderManager) {
        while (true) {
            System.out.println("\n--- Driver Management ---");
            System.out.println("1. View Available Drivers");
            System.out.println("2. Assign Driver to Order");
            System.out.println("3. Return to Main Menu");

            Integer choice = this.menuChoiceHandler.handleInput(
                    scanner,
                    "Enter your choice: ");

            if (choice == null)
                return;

            switch (choice) {
                case 1 -> this.listAvailableDrivers();
                case 2 -> this.assignDriverToOrderInteractive(scanner, orderManager);
                case 3 -> {
                    return; // Return to main menu
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void assignDriverToOrderInteractive(Scanner scanner, OrderManager orderManager) {
        Long orderId = orderManager.getOrderIdHandler().handleInput(
                scanner,
                "Enter Order ID to assign driver: ");

        if (orderId == null)
            return;

        Order order = orderManager.getOrderService().getOrderById(orderId);
        if (order != null) {
            this.assignDriverToOrder(scanner, order, orderManager.getOrderIdHandler());
        } else { 
            System.out.println("Order not found.");
        }
    }

    public void acceptAndDeliverOrder(Scanner scanner, OrderManager orderManager) {
        List<Order> pendingOrders = orderManager.getPendingOrders();
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders to accept.");
            return;
        }

        System.out.println("\n--- Pending Orders ---");
        for (int i = 0; i < pendingOrders.size(); i++) {
            Order order = pendingOrders.get(i);
            System.out.printf("%d. Order ID: %d, Customer: %s\n", i + 1, order.getId(), order.getCustomerEmail());
        }

        Integer orderChoice = this.menuChoiceHandler.handleInput(
                scanner,
                "Select an order to accept: ");

        if (orderChoice == null || orderChoice < 1 || orderChoice > pendingOrders.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Order selectedOrder = pendingOrders.get(orderChoice - 1);

        Driver driver = driverService.getAvailableDrivers().stream().findFirst().orElse(null);
        if (driver == null) {
            System.out.println("No available drivers at the moment.");
            return;
        }

        driverService.assignDriverToOrder(driver, selectedOrder);
        selectedOrder.setStatus(OrderStatus.IN_PROGRESS);
        System.out.println("Order assigned to driver " + driver.getName() + " and is now in progress.");
        logger.info("Order " + selectedOrder.getId() + " delivered.");
    }
}