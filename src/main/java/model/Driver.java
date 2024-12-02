package model;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.logging.Logger;

public class Driver {
   private static final Logger logger = Logger.getLogger(Driver.class.getName());
   private static final int MAX_RATINGS = 10;
   private static final int MIN_RATING = 1;
   private static final int MAX_RATING = 5;

   private final Long id;
   private final String name;
   private final String vehicleType;
   private final String licensePlate;
   private String vehicle;
   private boolean available;
   private final Queue<Integer> ratings = new ArrayDeque<>(Driver.MAX_RATINGS);
   private Order currentOrder;
   private Location currentLocation;

   public Driver(final Long id, final String name, final String vehicleType, final String licensePlate) {
      this.id = Objects.requireNonNull(id, "Driver ID cannot be null");
      this.name = Objects.requireNonNull(name, "Driver name cannot be null");
      this.vehicleType = Objects.requireNonNull(vehicleType, "Vehicle type cannot be null");
      this.licensePlate = Objects.requireNonNull(licensePlate, "License plate cannot be null");
      this.available = true;
   }

   // Getters
   public Long getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getVehicleType() {
      return this.vehicleType;
   }

   public String getLicensePlate() {
      return this.licensePlate;
   }

   public String getVehicle() {
      return this.vehicle;
   }

   public boolean isAvailable() {
      return this.available;
   }

   public void setAvailable(final boolean available) {
      this.available = available;
   }

   public Optional<Order> getCurrentOrder() {
      return Optional.ofNullable(this.currentOrder);
   }

   public Location getCurrentLocation() {
      return this.currentLocation;
   }

   public void setCurrentLocation(final Location location) {
      this.currentLocation = location;
   }

   // Order Management
   public void acceptOrder(final Order order) {
      if (order == null) {
         Driver.logger.warning("Cannot accept null order");
         return;
      }

      if (!this.available) {
         Driver.logger.warning("Driver is not available to accept order");
         return;
      }

      this.currentOrder = order;
      this.available = false;
      order.setDriver(this);
      order.setStatus(OrderStatus.IN_PROGRESS);
      Driver.logger.info(() -> String.format("Driver %s accepted order %d", this.name, order.getId()));
   }

   public void completeDelivery(final Order order) {
      if (order == null || this.currentOrder == null || !this.currentOrder.getId().equals(order.getId())) {
         Driver.logger.warning("Cannot complete delivery for unassigned or different order");
         return;
      }

      this.currentOrder = null;
      this.available = true;
      order.setStatus(OrderStatus.DELIVERED);
      Driver.logger.info(() -> String.format("Driver %s completed delivery of order %d", this.name, order.getId()));
   }

   // Rating Management
   public void updateRating(final int rating) {
      if (rating < Driver.MIN_RATING || rating > Driver.MAX_RATING) {
         Driver.logger.warning(() -> String.format("Invalid rating: %d. Must be between %d and %d",
               rating, Driver.MIN_RATING, Driver.MAX_RATING));
         return;
      }

      if (this.ratings.size() >= Driver.MAX_RATINGS) {
         this.ratings.poll(); // Remove oldest rating
      }
      this.ratings.offer(rating);
      Driver.logger.info(() -> String.format("Added rating %d to driver %s", rating, this.name));
   }

   public double getAverageRating() {
      return this.ratings.stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o)
         return true;
      if (o == null || this.getClass() != o.getClass())
         return false;
      final Driver driver = (Driver) o;
      return Objects.equals(this.id, driver.id) &&
            Objects.equals(this.name, driver.name) &&
            Objects.equals(this.licensePlate, driver.licensePlate);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id, this.name, this.licensePlate);
   }

   @Override
   public String toString() {
      return String.format("Driver{id=%d, name='%s', vehicle='%s', available=%b}",
            this.id, this.name, this.vehicle, this.available);
   }
}
