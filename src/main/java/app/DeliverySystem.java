package app;

import model.Driver;
import model.Order;
import model.Rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliverySystem {
   private final Map<Long, String> orderStatuses = new HashMap<>();

   public void submitOrder(final Order order) {
      System.out.println("Order submitted: " + order.getOrderId());
      this.orderStatuses.put(order.getOrderId(), "Pending");
   }

   public void assignOrderToDriver(final Order order, final Driver driver) {
      System.out.println("Order " + order.getOrderId() + " assigned to driver " + driver.getName());
      this.orderStatuses.put(order.getOrderId(), "In Progress");
   }

   public void completeDelivery(final Long orderId, final Long driverId) {
      System.out.println("Delivery completed for order " + orderId + " by driver " + driverId);
      this.orderStatuses.put(orderId, "Delivered");
   }

   public String getOrderStatus(final Long orderId) {
      return this.orderStatuses.getOrDefault(orderId, "Order Not Found");
   }

   public void rateDriver(Driver driver, int ratingValue) {
      Rating rating = new Rating(ratingValue);
      driver.addRating(rating);
      System.out.println("Driver " + driver.getName() + " rated with " + ratingValue + " stars.");
   }

   public List<Rating> getDriverRatings(Driver driver) {
      return driver.getRatings();
   }
}
