package main.java.com.example.exceptions;

public class QueueFullException extends RuntimeException {
   public QueueFullException(String message) {
      super(message);
   }
}