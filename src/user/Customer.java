package user;

import java.util.ArrayList;
import java.util.List;

import menu.MenuItem;
import order.Order;
import rating.Rating;

public class Customer {
   private Long id;
   private String name;
   private String address;
   private String phone;
   private String email;
   private List<Order> orderHistory;

   public Customer(Long id, String name, String address, String phone, String email) {
      this.id = id;
      this.name = name;
      this.address = address;
      this.phone = phone;
      this.email = email;
      this.orderHistory = new ArrayList<>();
   }

   public Order placeOrder(List<MenuItem> items) {
      Order order = new Order(this.id, items, new location.Location(0, 0), this.email);
      orderHistory.add(order);
      return order;
   }

   public void rateDriver(Driver driver, int score, String comment) {
      Rating rating = new Rating(this.id, driver.getId(), score, comment);
      driver.addRating(rating);
   }

   public List<Order> getOrderHistory() {
      return new ArrayList<>(orderHistory);
   }

   // Getters
   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getAddress() {
      return address;
   }

   public String getPhone() {
      return phone;
   }

   public String getEmail() {
      return email;
   }
}