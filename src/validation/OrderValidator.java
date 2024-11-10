package validation;

import model.Order;

import exception.ValidationException;
import utils.ValidationUtils;

public class OrderValidator {
   private static final int MAX_ITEMS_PER_ORDER = 20;
   private static final double MAX_ORDER_AMOUNT = 500.0;

   public void validateOrder(Order order) {
      if (order == null) {
         throw new ValidationException("Order cannot be null");
      }

      ValidationUtils.validateCustomerId(order.getCustomerId());
      ValidationUtils.validateItems(order.getItems(), MAX_ITEMS_PER_ORDER);
      ValidationUtils.validateAmount(order.getTotalAmount(), MAX_ORDER_AMOUNT);
   }
}