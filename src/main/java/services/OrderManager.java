package services;

import managers.MenuManager;
import validation.ConsoleInputHandler;

import java.util.Scanner;

public interface OrderManager {
    // ...existing code...
    void processOrderPlacement(
            Scanner scanner,
            MenuManager menuManager,
            ConsoleInputHandler<Integer> positiveIntegerHandler,
            ConsoleInputHandler<String> emailHandler,
            ConsoleInputHandler<String> locationHandler);
    // ...existing code...
}
