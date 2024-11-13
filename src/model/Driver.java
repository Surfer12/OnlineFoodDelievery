package model;

import location.Location;
import orderUtilities.OrderStatus;
import rating.Rating;
import rating.RatingsHandler;
import java.util.Optional;

/**
 * Represents a driver in the system.
 */
public class Driver {
   private Long id;
   private String name;
   private String vehicle;
   private String licenseNumber;
   private Location currentLocation;
   private RatingsHandler<Rating> ratings;
   private Order currentOrder;
   private boolean isAvailable;

   /**
    * Constructs a Driver with the specified details.
    *
    * @param id            the ID of the driver
    * @param name          the name of the driver
    * @param vehicle       the vehicle of the driver
    * @param licenseNumber the license number of the driver
    */
   public Driver(Long id, String name, String vehicle, String licenseNumber) {
      this.id = id;
      this.name = name;
      this.vehicle = vehicle;
      this.licenseNumber = licenseNumber;
      this.ratings = new RatingsHandler<Rating>(10);
      this.isAvailable = true;
   }

   /**
    * Accepts an order and updates its status to accepted.
    *
    * @param order the order to accept
    */
   public void acceptOrder(Order order) {
      order.setDriverId(this.id);
      order.updateStatus(OrderStatus.ACCEPTED);
   }

   /**
    * Completes the delivery of an order and updates its status to delivered.
    *
    * @param order the order to complete
    */
   public void completeDelivery(Order order) {
      order.updateStatus(OrderStatus.DELIVERED);
   }

   /**
    * Sets the current order for the driver and marks the driver as unavailable.
    *
    * @param order the current order
    */
   public void setCurrentOrder(Order order) {
      this.currentOrder = order;
      this.isAvailable = false;
   }

   /**
    * Returns the current order of the driver.
    *
    * @return an Optional containing the current order, or an empty Optional if no order is set
    */
   public Optional<Order> getCurrentOrder() {
      return Optional.ofNullable(currentOrder);
   }

   /**
    * Checks if the driver is available.
    *
    * @return true if the driver is available, false otherwise
    */
   public boolean isAvailable() {
      return isAvailable;
   }

   /**
    * Returns the average rating of the driver.
    *
    * @return the average rating
    */
   public double getAverageRating() {
      return ratings.calculateAverageRating();
   }

   /**
    * Adds a rating for the driver.
    *
    * @param rating the rating to add
    */
   public void addRating(Rating rating) {
      ratings.addRating(rating);
   }

   /**
    * Completes the current delivery and marks the driver as available.
    */
   public void completeCurrentDelivery() {
      this.currentOrder = null;
      this.isAvailable = true;
   }

   /**
    * Returns the ID of the driver.
    *
    * @return the ID of the driver
    */
   public Long getId() {
      return id;
   }

   /**
    * Returns the name of the driver.
    *
    * @return the name of the driver
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the vehicle of the driver.
    *
    * @return the vehicle of the driver
    */
   public String getVehicle() {
      return vehicle;
   }

   /**
    * Returns the license number of the driver.
    *
    * @return the license number of the driver
    */
   public String getLicenseNumber() {
      return licenseNumber;
   }

   /**
    * Returns the current location of the driver.
    *
    * @return the current location of the driver
    * @throws IllegalStateException if the driver's location is not set
    */
   public Location getCurrentLocation() {
      if (currentLocation == null) {
         throw new IllegalStateException("Driver location not set");
      }
      return currentLocation;
   }

   /**
    * Sets the current location of the driver.
    *
    * @param location the location to set
    * @throws IllegalArgumentException if the location is null
    */
   public void setCurrentLocation(Location location) {
      if (location == null) {
         throw new IllegalArgumentException("Location cannot be null");
      }
      this.currentLocation = location;
   }

   /**
    * Retrieves a driver by their ID.
    *
    * @param driverId the ID of the driver to retrieve
    * @return the driver with the specified ID
    */
   public static Driver getDriverById(Long driverId) {
      return Driver.getDriverById(driverId);
   }
}
