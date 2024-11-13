package ConsoleInputValidator;

import exception.ValidationException;

/**
 * A class that validates and parses input of a generic type.
 *
 * @param <T> the type of input to validate and parse
 */
public class InputValidator<T> {
   private final Validator<T> validator;
   private final String typeName;

   /**
    * Constructs an InputValidator with the specified Validator and type name.
    *
    * @param validator the Validator to use for validating and parsing input
    * @param typeName  the name of the type to be validated and parsed
    */
   public InputValidator(Validator<T> validator, String typeName) {
      this.validator = validator;
      this.typeName = typeName;
   }

   /**
    * Checks if the given value is valid.
    *
    * @param value the value to check
    * @return true if the value is valid, false otherwise
    */
   public boolean isValid(T value) {
      return validator.isValid(value);
   }

   /**
    * Parses the given input string into a value of type T.
    *
    * @param input the input string to parse
    * @return the parsed value of type T
    * @throws ValidationException if the input is invalid
    */
   public T parse(String input) {
      try {
         return validator.parse(input);
      } catch (Exception e) {
         throw new ValidationException("Invalid input: " + e.getMessage());
      }
   }

   /**
    * Returns the name of the type being validated and parsed.
    *
    * @return the type name
    */
   public String getTypeName() {
      return typeName;
   }

   /**
    * An interface for validating and parsing input of a generic type.
    *
    * @param <T> the type of input to validate and parse
    */
   public interface Validator<T> {
      /**
       * Parses the given input string into a value of type T.
       *
       * @param input the input string to parse
       * @return the parsed value of type T
       */
      T parse(String input);

      /**
       * Checks if the given value is valid.
       *
       * @param value the value to check
       * @return true if the value is valid, false otherwise
       */
      boolean isValid(T value);
   }
}
