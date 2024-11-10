package main;

import java.util.Scanner;

import ConsoleInputHandler.ConsoleInputHandler;
import ConsoleInputValidator.InputValidator;
import ConsoleInputValidator.NameValidator;
import ConsoleInputValidator.PositiveIntegerValidator;

public class CommandLineApplication {
   public static void main(String[] args) {
      try (Scanner scanner = new Scanner(System.in)) {
         // Create a NameValidator instance
         NameValidator nameValidator = new NameValidator();
         InputValidator<String> nameInputValidator = new InputValidator<>(nameValidator, "Name");

         ConsoleInputHandler<String> nameInputHandler = new ConsoleInputHandler<>(scanner, nameInputValidator);
         String userName = nameInputHandler.getInput("Enter your name: ");
         System.out.println("Hello, " + userName + "!");

         // Create a PositiveIntegerValidator instance
         PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
         InputValidator<Integer> positiveIntegerInputValidator = new InputValidator<>(positiveIntegerValidator,
               "Positive Integer");

         ConsoleInputHandler<Integer> positiveIntegerInputHandler = new ConsoleInputHandler<>(scanner,
               positiveIntegerInputValidator);
         Integer userAge = positiveIntegerInputHandler.getInput("Enter your age (positive integer): ");
         System.out.println("You entered age: " + userAge + "!");
      } catch (Exception e) {
         System.err.println("An error occurred: " + e.getMessage());
      }
   }
}
