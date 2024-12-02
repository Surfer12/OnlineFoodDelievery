package services;

import managers.MenuManager;
import validation.ConsoleInputHandler;

import java.util.Scanner;

public class OrderManagerImpl implements OrderManager {
    // ...existing code...

    @Override
    public void processOrderPlacement(
            final Scanner scanner,
            final MenuManager menuManager,
            final ConsoleInputHandler<Integer> positiveIntegerHandler,
            final ConsoleInputHandler<String> emailHandler,
            final ConsoleInputHandler<String> locationHandler) {
        // ...existing code...
        // Use emailHandler to get and validate email
        final String email = emailHandler.getInput("Enter your email: ");

        // Use locationHandler to get and validate delivery location
        final String location = locationHandler.getInput("Enter delivery location: ");

        // TODO OrderServiceImpl ID Generator
        OrderServiceImpl orderService = new OrderServiceImpl();
        IdGenerator idGenerator = new IdGenerator();
        // Create a new order with the provided email and location
        final Order order = new Order(idGenerator.generateId(), email, location);

        // Add the order to the queue
        orderService.addOrder(order);
    }

    // ...existing code...
}
