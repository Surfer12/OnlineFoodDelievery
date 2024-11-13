package utils;

import java.util.List;
import exception.ValidationException;
import payment.Payment;

/**
 * Utility class for validation methods.
 */
public class ValidationUtils {

   /**
    * Validates the customer ID.
    *
    * @param customerId the customer ID to validate
    * @throws ValidationException if the customer ID is invalid
    */
   public static void validateCustomerId(Long customerId) {
      if (customerId == null || customerId <= 0) {
         throw new ValidationException("Invalid customer ID");
      }
   }

   /**
    * Validates the list of items.
    *
    * @param items the list of items to validate
    * @param maxItems the maximum number of items allowed
    * @throws ValidationException if the list is null, empty, or exceeds the maximum limit
    */
   public static void validateItems(List<?> items, int maxItems) {
      if (items == null || items.isEmpty()) {
         throw new ValidationException("List cannot be null or empty");
      }
      if (items.size() > maxItems) {
         throw new ValidationException("Exceeds maximum item limit of " + maxItems);
      }
   }

   /**
    * Validates the amount.
    *
    * @param amount the amount to validate
    * @param maxAmount the maximum amount allowed
    * @throws ValidationException if the amount is less than or equal to zero or exceeds the maximum limit
    */
   public static void validateAmount(double amount, double maxAmount) {
      if (amount <= 0) {
         throw new ValidationException("Amount must be greater than zero");
      }
      if (amount > maxAmount) {
         throw new ValidationException("Amount exceeds maximum limit of " + maxAmount);
      }
   }

   /**
    * Validates the payment.
    *
    * @param payment the payment to validate
    * @throws ValidationException if the payment is not processed
    */
   public static void validatePayment(Payment payment) {
      if (payment != null && !payment.isProcessed()) {
         throw new ValidationException("Payment must be processed");
      }
   }
}
