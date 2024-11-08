package com.example.validation;

import com.example.exceptions.ValidationException;
import com.example.Order;
import com.example.MenuItem;
import com.example.Payment;

import java.util.ArrayList;
import java.util.List;

public class OrderValidator {
   private static final int MAX_ITEMS_PER_ORDER = 20;
   private static final double MAX_ORDER_AMOUNT = 500.0;

   public void validateOrder(Order order) {
      List<String> violations = new ArrayList<>();

      if (order == null) {
         throw new ValidationException("Order cannot be null");
      }

      validateCustomer(order.getCustomerId(), violations);
      validateItems(order.getItems(), violations);
      validateAmount(order.getTotalAmount(), violations);
      validatePayment(order.getPayment(), violations);

      if (!violations.isEmpty()) {
         throw new ValidationException("Order validation failed: " + String.join(", ", violations));
      }
   }

   private void validateCustomer(Long customerId, List<String> violations) {
      if (customerId == null || customerId <= 0) {
         violations.add("Invalid customer ID");
      }
   }

   private void validateItems(List<MenuItem> items, List<String> violations) {
      if (items == null || items.isEmpty()) {
         violations.add("Order must contain at least one item");
         return;
      }

      if (items.size() > MAX_ITEMS_PER_ORDER) {
         violations.add("Order exceeds maximum item limit");
      }

      for (MenuItem item : items) {
         if (!item.isAvailable()) {
            violations.add("Item " + item.getName() + " is not available");
         }
      }
   }

   private void validateAmount(double amount, List<String> violations) {
      if (amount <= 0) {
         violations.add("Order amount must be greater than zero");
      }
      if (amount > MAX_ORDER_AMOUNT) {
         violations.add("Order amount exceeds maximum limit");
      }
   }

   private void validatePayment(Payment payment, List<String> violations) {
      if (payment != null && !payment.isProcessed()) {
         violations.add("Payment must be processed before order can be queued");
      }
   }
}