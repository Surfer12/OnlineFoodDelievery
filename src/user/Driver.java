package user;

import order.Order;
import order.OrderStatus;
import location.Location;
import rating.Rating;
import rating.RatingsHandler;

public class Driver {
   private Long id;
   private String name;
   private String vehicle;
   private String licenseNumber;
   private Location currentLocation;
   private RatingsHandler ratings;
   private Order currentOrder;
   private boolean isAvailable;

   public Driver(Long id, String name, String vehicle, String licenseNumber) {
      this.id = id;
      this.name = name;
      this.vehicle = vehicle;
      this.licenseNumber = licenseNumber;
      this.ratings = new RatingsHandler();
      this.isAvailable = true;
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

   public void completeCurrentDelivery() {
      this.currentOrder = null;
      this.isAvailable = true;
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