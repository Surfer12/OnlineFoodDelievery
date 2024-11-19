package model;

import java.util.ArrayList;
import java.util.List;

import rating.Rating;

/**
 * Represents a customer in the system.
 */
public class Customer extends Person {
   private List<Order> orderHistory;

   /**
    * Constructs a Customer with the specified details.
    *
    * @param id      the ID of the customer
    * @param name    the name of the customer
    * @param address the address of the customer
    * @param phone   the phone number of the customer
    * @param email   the email address of the customer
    */
   public Customer(Long id, String name, String address, String phone, String email) {
      super(id, name, address, phone, email);
      this.orderHistory = new ArrayList<>();
   }

   /**
    * Places an order for the customer.
    *
    * @param items the list of menu items to order
    * @return the created Order object
    */
   public Order placeOrder(List<MenuItem> items) {
      Order order = new Order(this.getId(), items, new location.Location(this.getAddress(), "zipcode"), this.getEmail());
      orderHistory.add(order);
      return order;
   }

   /**
    * Rates a driver for a completed delivery.
    *
    * @param driver  the driver to rate
    * @param score   the rating score
    * @param comment the rating comment
    */
   public void rateDriver(Driver driver, int score, String comment) {
      Rating rating = new Rating.Builder()
            .customerId(this.getId())
            .driverId(driver.getId())
            .score(score)
            .comment(comment)
            .build();
      driver.addRating(rating);
   }

   /**
    * Returns the order history of the customer.
    *
    * @return a list of past orders
    */
   public List<Order> getOrderHistory() {
      return new ArrayList<>(orderHistory);
   }
}
