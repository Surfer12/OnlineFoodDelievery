package validation;

import utilities.Validator;

public class EmailValidator implements Validator<String> {
   @Override
   public String parse(final String input) {
      return input.trim();
   }

   @Override
   public boolean isValid(final String value) {
      final String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
      return value != null && value.matches(emailRegex);
   }
}
