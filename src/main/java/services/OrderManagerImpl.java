package services;

import java.util.List;
import java.util.Scanner;

import managers.MenuManager;
import model.MenuItem;
import model.Order;
import validation.ConsoleInputHandler;

public class OrderManagerImpl implements OrderManager {
    private final OrderServiceImpl orderService;

    public OrderManagerImpl() {
        this.orderService = new OrderServiceImpl();
    }

    @Override
    public void processOrderPlacement(
            final Scanner scanner,
            final MenuManager menuManager,
            final ConsoleInputHandler<Integer> positiveIntegerHandler,
            final ConsoleInputHandler<String> emailHandler,
            final ConsoleInputHandler<String> locationHandler) {
        // Get menu items
        final List<MenuItem> orderItems = menuManager.selectMenuItems(scanner, positiveIntegerHandler);

        if (orderItems.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        // Use emailHandler to get and validate email
        final String email = emailHandler.handleInput(scanner, "Enter your email: ");

        // Use locationHandler to get and validate delivery location
        final String location = locationHandler.handleInput(scanner, "Enter delivery location: ");
        final String postalCode = locationHandler.handleInput(scanner, "Enter postal code: ");

        // Create a new order with the provided details
        final Order order = orderService.createOrder(email, location, postalCode, orderItems);

        orderService.save(order);
        orderService.displayOrderDetails(order);
        System.out.println("Order placed successfully!");
    }
}
