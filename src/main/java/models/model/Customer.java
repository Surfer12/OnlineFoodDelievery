package src.main.java.models.model;

import java.util.ArrayList;
import java.util.List;

import backup_20241201_041133.src.main.java.model.Person;

/**
 * Represents a customer in the system.
 */
public class Customer extends Person {
   private final List<Order> orderHistory;

   /**
    * Constructs a Customer with the specified details.
    *
    * @param id      the ID of the customer
    * @param name    the name of the customer
    * @param address the address of the customer
    * @param phone   the phone number of the customer
    * @param email   the email address of the customer
    */
   public Customer(final Long id, final String name, final String address, final String phone, final String email) {
      super(id, name, address, phone, email);
      this.orderHistory = new ArrayList<>();
   }

   /**
    * Places an order for the customer.
    *
    * @param items the list of menu items to order
    * @return the created Order object
    */
   public Order placeOrder(final List<MenuItem> items) {
      final Order order = new Order(this.getId(), items, new location.Location(this.getAddress(), "zipcode"), this.getEmail());
      this.orderHistory.add(order);
      return order;
   }

   /**
    * Rates a driver for a completed delivery.
    *
    * @param driver  the driver to rate
    * @param score   the rating score
    * @param comment the rating comment
    */
   public void rateDriver(final Driver driver, final int score, final String comment) {
      final Rating rating = new Rating.Builder()
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
      return new ArrayList<>(this.orderHistory);
   }
}
