package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import backup_20241201_041133.src.main.java.ConsoleInputHandler.InputHandler;

/**
 * The ConsoleInputHandler class handles console input and validates it using
 * the provided InputValidator.
 *
 * @param <T> the type of input to handle
 *
 *            Usage example:
 * 
 *            Scanner scanner = new Scanner(System.in);
 *            InputValidator<Integer> validator = new
 *            PositiveIntegerValidator();
 *            ConsoleInputHandler<Integer> inputHandler = new
 *            ConsoleInputHandler<>(scanner, validator);
 *            Integer userInput = inputHandler.getInput("Enter a positive
 *            integer: ");
 *            System.out.println("You entered: " + userInput);
 */
public class ConsoleInputHandler<T> implements InputHandler<T> {
   private final Scanner scanner;
   private final InputValidator<T> inputValidator;

   /**
    * Constructs a ConsoleInputHandler with the specified Scanner and
    * InputValidator.
    *
    * @param scanner        the Scanner to use for reading input
    * @param inputValidator the InputValidator to use for validating input
    */
   public ConsoleInputHandler(final Scanner scanner, final InputValidator<T> inputValidator) {
      this.scanner = scanner;
      this.inputValidator = inputValidator;
   }

   /**
    * Prompts the user for input and validates it.
    *
    * @param prompt the prompt to display to the user
    * @return the validated input
    */
   @Override
   public T getInput(final String prompt) {
      while (true) {
         System.out.print(prompt);
         final String input = this.scanner.nextLine();
         try {
            final T value = this.inputValidator.parse(input);
            if (this.inputValidator.isValid(value)) {
               return value;
            } else {
               System.out.println("Invalid input. Please enter a valid " + this.inputValidator.getTypeName() + ".");
            }
         } catch (final Exception e) {
            System.out.println("Error parsing input: " + e.getMessage());
         }
      }
   }

   /**
    * Prompts the user for multiple inputs until the stop command is entered, and
    * validates each input.
    *
    * @param prompt      the prompt to display to the user
    * @param stopCommand the command to stop input
    * @return a list of validated inputs
    */
   @Override
   public List<T> getMultipleInputs(final String prompt, final String stopCommand) {
      final List<T> inputs = new ArrayList<>();
      while (true) {
         System.out.print(prompt);
         final String input = this.scanner.nextLine();
         if (input.equalsIgnoreCase(stopCommand)) {
            break;
         }
         try {
            final T value = this.inputValidator.parse(input);
            if (this.inputValidator.isValid(value)) {
               inputs.add(value);
            } else {
               System.out.println("Invalid input. Please try again.");
            }
         } catch (final Exception e) {
            System.out.println("Error parsing input: " + e.getMessage());
         }
      }
      return inputs;
   }
}
