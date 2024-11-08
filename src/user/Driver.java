package main.java.com.example.user;

import main.java.com.example.OrderStatus;
import main.java.com.example.location.Location;
import main.java.com.example.order.Order;
import main.java.com.example.rating.Rating;
import main.java.com.example.rating.RatingsHandler;

public class Driver {
   private Long id;
   private String name;
   private String vehicle;
   private String licenseNumber;
   private Location currentLocation;
   private RatingsHandler ratings;

   public Driver(Long id, String name, String vehicle, String licenseNumber) {
      this.id = id;
      this.name = name;
      this.vehicle = vehicle;
      this.licenseNumber = licenseNumber;
      this.ratings = new RatingsHandler();
   }

   public void acceptOrder(Order order) {
      order.setDriverId(this.id);
      order.updateStatus(OrderStatus.ACCEPTED);
   }

   public void completeDelivery(Order order) {
      order.updateStatus(OrderStatus.DELIVERED);
   }

   public double getAverageRating() {
      return ratings.calculateAverageRating();
   }

   public void addRating(Rating rating) {
      ratings.add(rating);
   }

   // Getters
   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getVehicle() {
      return vehicle;
   }

   public String getLicenseNumber() {
      return licenseNumber;
   }

   public Location getCurrentLocation() {
      return currentLocation;
   }
}