package src.main.java.utilities.src.main.java.ConsoleInputValidator;

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
   public InputValidator(final Validator<T> validator, final String typeName) {
      this.validator = validator;
      this.typeName = typeName;
   }

   /**
    * Checks if the given value is valid.
    *
    * @param value the value to check
    * @return true if the value is valid, false otherwise
    */
   public boolean isValid(final T value) {
      return this.validator.isValid(value);
   }

   /**
    * Parses the given input string into a value of type T.
    *
    * @param input the input string to parse
    * @return the parsed value of type T
    * @throws ValidationException if the input is invalid
    */
   public T parse(final String input) {
      try {
         return this.validator.parse(input);
      } catch (final ValidationException e) {
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
      return this.typeName;
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

      public DateValidator(final String dateFormat, final LocalDate minDate, final LocalDate maxDate) {
         this.formatter = DateTimeFormatter.ofPattern(dateFormat);
         this.minDate = minDate;
         this.maxDate = maxDate;
      }

      @Override
      public LocalDate parse(final String input) {
         try {
            return LocalDate.parse(input, this.formatter);
         } catch (final DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Expected format: " + this.formatter);
         }
      }

      @Override
      public boolean isValid(final LocalDate value) {
         return value != null && !value.isBefore(this.minDate) && !value.isAfter(this.maxDate);
      }
   }

   /**
    * A Validator implementation for enum validation.
    */
   public static class EnumValidator<E extends Enum<E>> implements Validator<E> {
      private final Class<E> enumClass;

      public EnumValidator(final Class<E> enumClass) {
         this.enumClass = enumClass;
      }

      @Override
      public E parse(final String input) {
         try {
            return Enum.valueOf(this.enumClass, input.toUpperCase());
         } catch (final IllegalArgumentException e) {
            throw new ValidationException("Invalid value. Expected one of: " + this.enumClass.getEnumConstants());
         }
      }

      @Override
      public boolean isValid(final E value) {
         return value != null;
      }
   }

   /**
    * A Validator implementation for range validation.
    */
   public static class RangeValidator implements Validator<Integer> {
      private final int min;
      private final int max;

      public RangeValidator(final int min, final int max) {
         this.min = min;
         this.max = max;
      }

      @Override
      public Integer parse(final String input) {
         try {
            return Integer.parseInt(input);
         } catch (final NumberFormatException e) {
            throw new ValidationException("Invalid number format. Expected an integer.");
         }
      }

      @Override
      public boolean isValid(final Integer value) {
         return value != null && value >= this.min && value <= this.max;
      }
   }

   /**
    * A Validator implementation for pattern validation.
    */
   public static class PatternValidator implements Validator<String> {
      private final Pattern pattern;

      public PatternValidator(final String regex) {
         this.pattern = Pattern.compile(regex);
      }

      @Override
      public String parse(final String input) {
         return input;
      }

      @Override
      public boolean isValid(final String value) {
         return value != null && this.pattern.matcher(value).matches();
      }
   }

   /**
    * A Validator implementation for custom object validation.
    */
   public static class CustomObjectValidator implements Validator<Object> {
      private final Validator<Object>[] fieldValidators;

      @SafeVarargs
      public CustomObjectValidator(final Validator<Object>... fieldValidators) {
         this.fieldValidators = fieldValidators;
      }

      @Override
      public Object parse(final String input) {
         return input;
      }

      @Override
      public boolean isValid(final Object value) {
         for (final Validator<Object> validator : this.fieldValidators) {
            if (!validator.isValid(value)) {
               return false;
            }
         }
         return true;
      }
   }
}
