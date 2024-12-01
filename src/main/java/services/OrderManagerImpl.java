package services;
package managers;

import java.util.Scanner;

import validation.ConsoleInputHandler;

public class OrderManager {
    // ...existing code...

    @Override
    public void processOrderPlacement(
        Scanner scanner,
        MenuManager menuManager,
        ConsoleInputHandler<Integer> positiveIntegerHandler,
        ConsoleInputHandler<String> emailHandler,
        ConsoleInputHandler<String> locationHandler
    ) {
        // ...existing code...

        // Use emailHandler to get and validate email
        String email = emailHandler.getInput("Enter your email: ");
        
        // Use locationHandler to get and validate delivery location
        String location = locationHandler.getInput("Enter delivery location: ");
        
        // ...existing code to process the order with email and location...
    }

    // ...existing code...
}

public class OrderManagerImpl {
    
}
