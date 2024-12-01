package src.main.java.utilities.src.main.java.ConsoleInputValidator;

/**
 * PositiveIntegerValidator class that implements the
 * InputValidator.Validator interface for Integer type.
 * This class provides methods to parse and validate positive integer inputs.
 */
public class PositiveIntegerValidator implements InputValidator.Validator<Integer> {

   /**
    * Parses the input string to an Integer.
    *
    * @param input The input string to be parsed
    * @return The parsed Integer value
    * @throws NumberFormatException if the input is not a valid integer
    */
   @Override
   public Integer parse(final String input) throws NumberFormatException {
      if ("unknown".equalsIgnoreCase(input)) {
         return 10; // default size for ArrayList if total student count is unknown
      }
      try {
         return Integer.parseInt(input);
      } catch (final NumberFormatException e) {
         throw new NumberFormatException("Invalid input. Please enter a valid integer.");
      }
   }

   /**
    * Validates the parsed integer to ensure it is a positive number.
    *
    * @param value The parsed integer to be validated
    * @return true if the integer is positive, false otherwise
    */
   @Override
   public boolean isValid(final Integer value) {
      return value != null && value > 0;
   }
}
