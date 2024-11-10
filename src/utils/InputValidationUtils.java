package utils;

import exception.ValidationException;

public class InputValidationUtils {

   public static void validateTextInput(String input, String fieldName) {
      if (input == null || input.trim().isEmpty()) {
         throw new ValidationException(fieldName + " cannot be null or empty");
      }
   }

   public static void validateNumericInput(String input, String fieldName) {
      try {
         Double.parseDouble(input);
      } catch (NumberFormatException e) {
         throw new ValidationException(fieldName + " must be a valid number");
      }
   }

   public static void validatePositiveNumber(double number, String fieldName) {
      if (number <= 0) {
         throw new ValidationException(fieldName + " must be greater than zero");
      }
   }

   public static void validateEmailFormat(String email) {
      String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
      if (!email.matches(emailRegex)) {
         throw new ValidationException("Invalid email format");
      }
   }

   public static void validatePhoneNumber(String phoneNumber) {
      String phoneRegex = "^\\+?[0-9]{10,15}$";
      if (!phoneNumber.matches(phoneRegex)) {
         throw new ValidationException("Invalid phone number format");
      }
   }
}