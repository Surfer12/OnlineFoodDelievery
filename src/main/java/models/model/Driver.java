package src.main.java.models.model;

import java.util.Optional;

import backup_20241201_041133.src.main.java.model.Person;
import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;

/**
 * Represents a driver in the system.
 */
public class Driver extends Person {
   private final String vehicle;
   private final String licenseNumber;
   private Location currentLocation;
   private final RatingsHandler<Rating> ratings;
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
   public Driver(final Long id, final String name, final String vehicle, final String licenseNumber) {
      super(id, name, null, null, null);
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
   public void acceptOrder(final Order order) {
      order.setDriverId(this.getId());
      order.updateStatus(OrderStatus.ACCEPTED);
   }

   /**
    * Completes the delivery of an order and updates its status to delivered.
    *
    * @param order the order to complete
    */
   public void completeDelivery(final Order order) {
      order.updateStatus(OrderStatus.DELIVERED);
   }

   /**
    * Sets the current order for the driver and marks the driver as unavailable.
    *
    * @param order the current order
    */
   public void setCurrentOrder(final Order order) {
      this.currentOrder = order;
      this.isAvailable = false;
   }

   /**
    * Returns the current order of the driver.
    *
    * @return an Optional containing the current order, or an empty Optional if no order is set
    */
   public Optional<Order> getCurrentOrder() {
      return Optional.ofNullable(this.currentOrder);
   }

   /**
    * Checks if the driver is available.
    *
    * @return true if the driver is available, false otherwise
    */
   public boolean isAvailable() {
      return this.isAvailable;
   }

   /**
    * Returns the average rating of the driver.
    *
    * @return the average rating
    */
   public double getAverageRating() {
      return this.ratings.calculateAverageRating();
   }

   /**
    * Adds a rating for the driver.
    *
    * @param rating the rating to add
    */
   public void addRating(final Rating rating) {
      this.ratings.addRating(rating);
   }

   /**
    * Completes the current delivery and marks the driver as available.
    */
   public void completeCurrentDelivery() {
      this.currentOrder = null;
      this.isAvailable = true;
   }

   /**
    * Returns the vehicle of the driver.
    *
    * @return the vehicle of the driver
    */
   public String getVehicle() {
      return this.vehicle;
   }

   /**
    * Returns the license number of the driver.
    *
    * @return the license number of the driver
    */
   public String getLicenseNumber() {
      return this.licenseNumber;
   }

   /**
    * Returns the current location of the driver.
    *
    * @return the current location of the driver
    * @throws IllegalStateException if the driver's location is not set
    */
   public Location getCurrentLocation() {
      try {
         if (this.currentLocation == null) {
            throw new IllegalStateException("Driver location not set");
         }
         return this.currentLocation;
      } catch (final IllegalStateException e) {
         System.err.println("Error in getCurrentLocation: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Sets the current location of the driver.
    *
    * @param location the location to set
    * @throws IllegalArgumentException if the location is null
    */
   public void setCurrentLocation(final Location location) {
      try {
         if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
         }
         this.currentLocation = location;
      } catch (final IllegalArgumentException e) {
         System.err.println("Error in setCurrentLocation: " + e.getMessage());
         throw e;
      }
   }

   /**
    * Retrieves a driver by their ID.
    *
    * @param driverId the ID of the driver to retrieve
    * @return the driver with the specified ID
    */
   public static Driver getDriverById(final Long driverId) {
      return Driver.getDriverById(driverId);
   }
}
