package app;

import java.util.Scanner;

import ConsoleInputHandler.ConsoleInputHandler;
import ConsoleInputValidator.InputValidator;
import ConsoleInputValidator.NameValidator;
import ConsoleInputValidator.PositiveIntegerValidator;
import validation.EmailValidator;
import validation.MenuItemValidator;

/**
 * CommandLineApplication is the entry point for the command-line interface of the application.
 * It handles user input and performs validation.
 */
public class CommandLineApplication {

    /**
     * The main method to run the command-line application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Name Input
            NameValidator nameValidator = new NameValidator();
            InputValidator<String> nameInputValidator = new InputValidator<>(nameValidator, "Name");
            ConsoleInputHandler<String> nameInputHandler = new ConsoleInputHandler<>(scanner, nameInputValidator);
            String userName = nameInputHandler.getInput("Enter your name: ");
            System.out.println("Hello, " + userName + "!");

            // Age Input
            PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
            InputValidator<Integer> positiveIntegerInputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");
            ConsoleInputHandler<Integer> positiveIntegerInputHandler = new ConsoleInputHandler<>(scanner, positiveIntegerInputValidator);
            Integer userAge = positiveIntegerInputHandler.getInput("Enter your age (positive integer): ");
            System.out.println("You entered age: " + userAge + "!");

            // Email Input
            EmailValidator emailValidator = new EmailValidator();
            InputValidator<String> emailInputValidator = new InputValidator<>(emailValidator, "Email");
            ConsoleInputHandler<String> emailInputHandler = new ConsoleInputHandler<>(scanner, emailInputValidator);
            String userEmail = emailInputHandler.getInput("Enter your email: ");
            System.out.println("You entered email: " + userEmail + "!");

            // Menu Item Input
            MenuItemValidator menuItemValidator = new MenuItemValidator();
            InputValidator<MenuItem> menuItemInputValidator = new InputValidator<>(menuItemValidator, "Menu Item");
            ConsoleInputHandler<MenuItem> menuItemInputHandler = new ConsoleInputHandler<>(scanner, menuItemInputValidator);
            MenuItem menuItem = menuItemInputHandler.getInput("Enter menu item (name,price,available): ");
            System.out.println("Menu item added: " + menuItem.getName() + "!");

            // Order Item Input
            MenuItemValidator orderItemValidator = new MenuItemValidator();
            InputValidator<MenuItem> orderItemInputValidator = new InputValidator<>(orderItemValidator, "Order Item");
            ConsoleInputHandler<MenuItem> orderItemInputHandler = new ConsoleInputHandler<>(scanner, orderItemInputValidator);
            MenuItem orderItem = orderItemInputHandler.getInput("Enter order item (menuItemId,quantity): ");
            System.out.println("Order item added with ID: " + orderItem.getId() + " and quantity: " + orderItem.getQuantity() + "!");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
