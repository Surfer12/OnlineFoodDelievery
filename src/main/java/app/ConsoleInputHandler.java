package app;

import java.util.Scanner;
import java.util.function.Predicate;

public class ConsoleInputHandler<T> {
    private final Predicate<T> validator;
    private final String errorMessage;

    public ConsoleInputHandler(final Predicate<T> validator, final String errorMessage) {
        this.validator = validator;
        this.errorMessage = errorMessage;
    }

    public T handleInput(final Scanner scanner, final String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                final T input = (T) scanner.nextLine();
                if (this.validator.test(input)) {
                    return input;
                }
                System.out.println(this.errorMessage);
            } catch (final Exception e) {
                System.out.println("Invalid input. " + this.errorMessage);
            }
        }
    }
} 