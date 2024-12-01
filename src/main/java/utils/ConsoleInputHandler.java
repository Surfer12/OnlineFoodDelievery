package utils;

import java.util.Scanner;

import utilities.InputValidator;

public class ConsoleInputHandler<T> {
    private final InputValidator<T> validator;

    public ConsoleInputHandler(InputValidator<T> validator) {
        this.validator = validator;
    }

    public T handleInput(Scanner scanner, String prompt) {
        T input;
        while (true) {
            System.out.print(prompt);
            try {
                input = (T) scanner.nextLine();
                if (this.validator.validate(input)) {
                    break;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error processing input. Please try again.");
            }
        }
        return input;
    }
}
