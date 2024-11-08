package main.java.com.example.exceptions;

public class PaymentException extends RuntimeException {
   public PaymentException(String message) {
      super(message);
   }
}