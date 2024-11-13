package model;

import java.util.ArrayList;
import java.util.List;

import rating.Rating;

/**
 * Represents a customer in the system.
 */
public class Customer {
   private Long id;
   private String name;
   private String address;
   private String phone;
   private String email;
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
      this.id = id;
      this.name = name;
      this.address = address;
      this.phone = phone;
      this.email = email;
      this.orderHistory = new ArrayList<>();
   }

   /**
    * Places an order for the customer.
    *
    * @param items the list of menu items to order
    * @return the created Order object
    */
   public Order placeOrder(List<MenuItem> items) {
      Order order = new Order(this.id, items, new location.Location(this.address, "zipcode"), this.email);
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
      Rating rating = new Rating(this.id, driver.getId(), score, comment);
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

   /**
    * Returns the ID of the customer.
    *
    * @return the ID of the customer
    */
   public Long getId() {
      return id;
   }

   /**
    * Returns the name of the customer.
    *
    * @return the name of the customer
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the address of the customer.
    *
    * @return the address of the customer
    */
   public String getAddress() {
      return address;
   }

   /**
    * Returns the phone number of the customer.
    *
    * @return the phone number of the customer
    */
   public String getPhone() {
      return phone;
   }

   /**
    * Returns the email address of the customer.
    *
    * @return the email address of the customer
    */
   public String getEmail() {
      return email;
   }
}
