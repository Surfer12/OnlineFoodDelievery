package model;

import java.util.ArrayList;
import java.util.List;

import rating.Rating;

public class Driver {
   private final Long id;
   private final String name;
   private final String vehicleType;
   private final String licensePlate;
   private String vehicle;
   private boolean available;
   private List<Integer> ratings = new ArrayList<>();

   public Driver(final Long id, final String name, final String vehicleType, final String licensePlate) {
      this.id = id;
      this.name = name;
      this.vehicleType = vehicleType;
      this.licensePlate = licensePlate;
   }

   public Driver(final String name, final String email, final String phoneNumber) {
      this(null, name, null, null);
   }

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

   public void setVehicle(String vehicle) {
      this.vehicle = vehicle;
   }

   public boolean isAvailable() {
      return this.available;
   }

   public void setAvailable(boolean available) {
      this.available = available;
   }

   public void addRating(final Rating rating) {
      // Placeholder for adding rating logic
      System.out.println("Rating added: " + rating);
   }

   public void addRating(Integer rating) {
      if (this.ratings.size() >= 10) {
         this.ratings.remove(0);
      }
      this.ratings.add(rating);
   }
}
