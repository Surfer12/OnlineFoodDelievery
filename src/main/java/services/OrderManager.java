package services;
package managers;

import java.util.Scanner;

import validation.ConsoleInputHandler;

public interface OrderManager {
    // ...existing code...
    void processOrderPlacement(
        Scanner scanner,
        MenuManager menuManager,
        ConsoleInputHandler<Integer> positiveIntegerHandler,
        ConsoleInputHandler<String> emailHandler,
        ConsoleInputHandler<String> locationHandler
    );
    // ...existing code...
}

public class OrderManager {
    
}
