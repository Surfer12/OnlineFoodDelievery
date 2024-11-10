package utils;

import java.util.List;
import exception.ValidationException;
import payment.Payment;

public class ValidationUtils {

   public static void validateCustomerId(Long customerId) {
      if (customerId == null || customerId <= 0) {
         throw new ValidationException("Invalid customer ID");
      }
   }

   public static void validateItems(List<?> items, int maxItems) {
      if (items == null || items.isEmpty()) {
         throw new ValidationException("List cannot be null or empty");
      }
      if (items.size() > maxItems) {
         throw new ValidationException("Exceeds maximum item limit of " + maxItems);
      }
   }

   public static void validateAmount(double amount, double maxAmount) {
      if (amount <= 0) {
         throw new ValidationException("Amount must be greater than zero");
      }
      if (amount > maxAmount) {
         throw new ValidationException("Amount exceeds maximum limit of " + maxAmount);
      }
   }

   public static void validatePayment(Payment payment) {
      if (payment != null && !payment.isProcessed()) {
         throw new ValidationException("Payment must be processed");
      }
   }
}