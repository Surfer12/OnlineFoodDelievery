package validation;

import ConsoleInputValidator.InputValidator.Validator;

public class EmailValidator implements Validator<String> {
   @Override
   public String parse(String input) {
      return input.trim();
   }

   @Override
   public boolean isValid(String value) {
      String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
      return value != null && value.matches(emailRegex);
   }
}
