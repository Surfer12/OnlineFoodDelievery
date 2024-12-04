package services;

import java.util.List;
import java.util.Scanner;

import managers.MenuManager;
import model.MenuItem;
import model.Order;
import validation.ConsoleInputHandler;

public class OrderManagerImpl implements OrderManager {
    // ...existing code...

    @Override
    public void processOrderPlacement(
            final Scanner scanner,
            final MenuManager menuManager,
            final ConsoleInputHandler<Integer> positiveIntegerHandler,
            final ConsoleInputHandler<String> emailHandler,
            final ConsoleInputHandler<String> locationHandler) {
        // Get menu items
        final List<MenuItem> orderItems = menuManager.selectMenuItems(scanner, positiveIntegerHandler);

        // Use emailHandler to get and validate email
        final String email = emailHandler.getInput("Enter your email: ");

        // Use locationHandler to get and validate delivery location
        final String location = locationHandler.getInput("Enter delivery location: ");
        final String postalCode = locationHandler.getInput("Enter postal code: ");

        // Create a new order with the provided details
        final OrderServiceImpl orderService = new OrderServiceImpl();
        final Order order = orderService.createNewOrder(
                IdGenerator.generateId(),
                email,
                orderItems,
                location,
                postalCode);

        orderService.saveOrder(order);
    }

    // ...existing code...
}
