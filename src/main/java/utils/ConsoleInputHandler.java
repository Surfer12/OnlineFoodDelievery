package utils;

import java.util.Scanner;

import utilities.InputValidator;

public class ConsoleInputHandler<T> {
    private final InputValidator<T> validator;

    public ConsoleInputHandler(InputValidator<T> validator) {
        this.validator = validator;
    }

    public T handleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                T parsedInput = this.validator.parse(input);
                if (this.validator.isValid(parsedInput)) {
                    return parsedInput;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error parsing input: " + e.getMessage());
            }
        }
    }

    public T handleInput(Scanner scanner, String prompt, ValidatorCondition<T> condition) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                T parsedInput = this.validator.parse(input);
                if (this.validator.isValid(parsedInput) && condition.test(parsedInput)) {
                    return parsedInput;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error parsing input: " + e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface ValidatorCondition<T> {
        boolean test(T input);
    }
}
