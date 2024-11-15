package ConsoleInputValidator;

import exception.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

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
      } catch (ValidationException e) {
         System.err.println("Error in parse: " + e.getMessage());
         throw e;
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

   /**
    * A Validator implementation for date validation.
    */
   public static class DateValidator implements Validator<LocalDate> {
      private final DateTimeFormatter formatter;
      private final LocalDate minDate;
      private final LocalDate maxDate;

      public DateValidator(String dateFormat, LocalDate minDate, LocalDate maxDate) {
         this.formatter = DateTimeFormatter.ofPattern(dateFormat);
         this.minDate = minDate;
         this.maxDate = maxDate;
      }

      @Override
      public LocalDate parse(String input) {
         try {
            return LocalDate.parse(input, formatter);
         } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Expected format: " + formatter);
         }
      }

      @Override
      public boolean isValid(LocalDate value) {
         return value != null && !value.isBefore(minDate) && !value.isAfter(maxDate);
      }
   }

   /**
    * A Validator implementation for enum validation.
    */
   public static class EnumValidator<E extends Enum<E>> implements Validator<E> {
      private final Class<E> enumClass;

      public EnumValidator(Class<E> enumClass) {
         this.enumClass = enumClass;
      }

      @Override
      public E parse(String input) {
         try {
            return Enum.valueOf(enumClass, input.toUpperCase());
         } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value. Expected one of: " + enumClass.getEnumConstants());
         }
      }

      @Override
      public boolean isValid(E value) {
         return value != null;
      }
   }

   /**
    * A Validator implementation for range validation.
    */
   public static class RangeValidator implements Validator<Integer> {
      private final int min;
      private final int max;

      public RangeValidator(int min, int max) {
         this.min = min;
         this.max = max;
      }

      @Override
      public Integer parse(String input) {
         try {
            return Integer.parseInt(input);
         } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format. Expected an integer.");
         }
      }

      @Override
      public boolean isValid(Integer value) {
         return value != null && value >= min && value <= max;
      }
   }

   /**
    * A Validator implementation for pattern validation.
    */
   public static class PatternValidator implements Validator<String> {
      private final Pattern pattern;

      public PatternValidator(String regex) {
         this.pattern = Pattern.compile(regex);
      }

      @Override
      public String parse(String input) {
         return input;
      }

      @Override
      public boolean isValid(String value) {
         return value != null && pattern.matcher(value).matches();
      }
   }

   /**
    * A Validator implementation for custom object validation.
    */
   public static class CustomObjectValidator implements Validator<Object> {
      private final Validator<Object>[] fieldValidators;

      @SafeVarargs
      public CustomObjectValidator(Validator<Object>... fieldValidators) {
         this.fieldValidators = fieldValidators;
      }

      @Override
      public Object parse(String input) {
         return input;
      }

      @Override
      public boolean isValid(Object value) {
         for (Validator<Object> validator : fieldValidators) {
            if (!validator.isValid(value)) {
               return false;
            }
         }
         return true;
      }
   }
}
