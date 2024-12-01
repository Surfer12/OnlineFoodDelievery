package ConsoleInputHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ConsoleInputValidator.InputValidator;

/**
 * The InputHandlerImpl class implements the InputHandler interface and provides methods for handling console input.
 *
 * @param <T> the type of input to handle
 */
public class InputHandlerImpl<T> implements InputHandler<T> {
   private final Scanner scanner;
   private final InputValidator<T> inputValidator;

   /**
    * Constructs an InputHandlerImpl with the specified Scanner and InputValidator.
    *
    * @param scanner the Scanner to use for reading input
    * @param inputValidator the InputValidator to use for validating input
    */
   public InputHandlerImpl(Scanner scanner, InputValidator<T> inputValidator) {
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
   public T getInput(String prompt) {
      while (true) {
         System.out.print(prompt);
         String input = scanner.nextLine();
         try {
            T value = inputValidator.parse(input);
            if (inputValidator.isValid(value)) {
               return value;
            } else {
               System.out.println("Invalid input. Please enter a valid " + inputValidator.getTypeName() + ".");
            }
         } catch (Exception e) {
            System.out.println("Error parsing input: " + e.getMessage());
         }
      }
   }

   /**
    * Prompts the user for multiple inputs until the stop command is entered, and validates each input.
    *
    * @param prompt the prompt to display to the user
    * @param stopCommand the command to stop input
    * @return a list of validated inputs
    */
   @Override
   public List<T> getMultipleInputs(String prompt, String stopCommand) {
      List<T> inputs = new ArrayList<>();
      while (true) {
         System.out.print(prompt);
         String input = scanner.nextLine();
         if (input.equalsIgnoreCase(stopCommand)) {
            break;
         }
         try {
            T value = inputValidator.parse(input);
            if (inputValidator.isValid(value)) {
               inputs.add(value);
            } else {
               System.out.println("Invalid input. Please try again.");
            }
         } catch (Exception e) {
            System.out.println("Error parsing input: " + e.getMessage());
         }
      }
      return inputs;
   }
}
