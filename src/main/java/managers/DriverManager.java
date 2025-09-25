package managers;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Driver;
import model.Order;
import model.OrderStatus;
import services.DriverService;
import services.impl.DriverServiceImpl;
import validation.ConsoleInputHandler;
import validation.InputValidatorImpl;
import validation.MenuItemValidator;

public class DriverManager {
    private static final Logger logger = Logger.getLogger(DriverManager.class.getName());

    private final DriverService driverService;
    private final ConsoleInputHandler<Integer> menuChoiceHandler;

    public DriverManager() {
        this.driverService = new DriverServiceImpl();
        this.menuChoiceHandler = new ConsoleInputHandler<>(
                new InputValidatorImpl<>(
                        new MenuItemValidator(3),
                        "Driver Management Choice",
                        "Invalid choice"));
    }

    public void listAvailableDrivers() {
        final List<Driver> availableDrivers = this.driverService.getAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers currently available.");
            return;
        }

        System.out.println("\n--- Available Drivers ---");
        availableDrivers.forEach(driver -> System.out.println(driver.getName() + " - " + driver.getVehicle()));
    }

    public Optional<Driver> assignDriverToOrder(final Scanner scanner, final Order order) {
        if (order == null) {
            DriverManager.logger.warning("Attempted to assign driver to null order.");
            return Optional.empty();
        }

        final List<Driver> availableDrivers = this.driverService.getAvailableDrivers();
        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers at the moment.");
            return Optional.empty();
        }

        System.out.println("\n--- Available Drivers ---");
        for (int i = 0; i < availableDrivers.size(); i++) {
            final Driver driver = availableDrivers.get(i);
            System.out.printf("%d. %s - %s\n", i + 1, driver.getName(), driver.getVehicle());
        }

        final Integer driverChoice = this.menuChoiceHandler.handleInput(
                scanner,
                "Select a driver (enter number): ");

        if (driverChoice == null || driverChoice < 1 || driverChoice > availableDrivers.size()) {
            System.out.println("Invalid driver selection.");
            return Optional.empty();
        }

        final Driver selectedDriver = availableDrivers.get(driverChoice - 1);
        this.driverService.assignDriverToOrder(selectedDriver, order);
        order.setStatus(OrderStatus.CONFIRMED);

        DriverManager.logger.info(() -> String.format("Driver %s assigned to order %d",
                selectedDriver.getName(), order.getId()));

        return Optional.of(selectedDriver);
    }

    public void rateDriver(final Scanner scanner, final Order order) {
        if (order == null) {
            System.out.println("No order provided for driver rating.");
            return;
        }

        final Driver driver = this.driverService.getDriverForOrder(order);
        if (driver == null) {
            System.out.println("No driver assigned to this order.");
            return;
        }

        System.out.println("Rate your driver (1-5 stars):");
        try {
            final int rating = Integer.parseInt(scanner.nextLine());
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                return;
            }

            driver.updateRating(rating);
            System.out.println("Thank you for your feedback!");
            DriverManager.logger.info(() -> String.format("Driver %s rated %d stars", driver.getName(), rating));
        } catch (final NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            DriverManager.logger.log(Level.WARNING, "Invalid rating input", e);
        }
    }

    public void acceptPendingOrder(final Scanner scanner, final List<Order> pendingOrders) {
        if (pendingOrders == null || pendingOrders.isEmpty()) {
            System.out.println("No pending orders available.");
            return;
        }

        System.out.println("\n--- Pending Orders ---");
        for (int i = 0; i < pendingOrders.size(); i++) {
            final Order order = pendingOrders.get(i);
            System.out.printf("%d. Order ID: %d - Status: %s\n",
                    i + 1, order.getId(), order.getStatus());
        }

        final Integer orderChoice = this.menuChoiceHandler.handleInput(
                scanner,
                "Select an order to accept: ");

        if (orderChoice == null || orderChoice < 1 || orderChoice > pendingOrders.size()) {
            System.out.println("Invalid order selection.");
            return;
        }

        final Order selectedOrder = pendingOrders.get(orderChoice - 1);
        final Optional<Driver> driver = this.driverService.findAvailableDriver();

        driver.ifPresentOrElse(
                selectedDriver -> {
                    this.driverService.assignDriverToOrder(selectedDriver, selectedOrder);
                    selectedOrder.setStatus(OrderStatus.IN_PROGRESS);
                    System.out.println("Order assigned to driver " + selectedDriver.getName() +
                            " and is now in progress.");
                    DriverManager.logger.info(() -> String.format("Order %d assigned to driver %s",
                            selectedOrder.getId(), selectedDriver.getName()));
                },
                () -> {
                    System.out.println("No available drivers at the moment.");
                    DriverManager.logger.warning("No available drivers to accept order");
                });
    }

    public Driver findDriverById(Long driverId) {
        return driverService.getAllDrivers().stream()
                .filter(driver -> driver.getId().equals(driverId))
                .findFirst()
                .orElse(null);
    }

    public void manageDriverMenu(Scanner scanner, OrderManager orderManager) {
        System.out.println("\n--- Driver Management Menu ---");
        System.out.println("1. List Available Drivers");
        System.out.println("2. View Driver Details");
        System.out.println("3. Update Driver Availability");
        System.out.println("4. Return to Main Menu");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> listAvailableDrivers();
                case 2 -> {
                    System.out.println("Enter driver ID: ");
                    Long driverId = Long.parseLong(scanner.nextLine());
                    Driver driver = findDriverById(driverId);
                    if (driver != null) {
                        System.out.println("Driver Details: " + driver);
                    } else {
                        System.out.println("Driver not found.");
                    }
                }
                case 3 -> {
                    System.out.println("Enter driver ID: ");
                    Long driverId = Long.parseLong(scanner.nextLine());
                    Driver driver = findDriverById(driverId);
                    if (driver != null) {
                        System.out.println("Enter availability (true/false): ");
                        boolean available = Boolean.parseBoolean(scanner.nextLine());
                        driverService.updateDriverAvailability(driver, available);
                        System.out.println("Driver availability updated.");
                    } else {
                        System.out.println("Driver not found.");
                    }
                }
                case 4 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}