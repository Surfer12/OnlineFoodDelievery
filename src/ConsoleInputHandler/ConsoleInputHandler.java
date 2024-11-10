package ConsoleInputHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ConsoleInputValidator.InputValidator;

public class ConsoleInputHandler<T> implements InputHandler<T> {
   private final Scanner scanner;
   private final InputValidator<T> inputValidator;

   public ConsoleInputHandler(Scanner scanner, InputValidator<T> inputValidator) {
      this.scanner = scanner;
      this.inputValidator = inputValidator;
   }

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