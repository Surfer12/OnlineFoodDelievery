package com.example.matching;

import com.example.Order;
import com.example.Driver;
import com.example.Location;
import java.util.List;
import java.util.Optional;

public class ProximityBasedMatchingStrategy implements DriverMatchingStrategy {
   private static final double MAX_ACCEPTABLE_DISTANCE = 10.0; // km

   @Override
   public Optional<Driver> findBestMatch(Order order, List<Driver> availableDrivers) {
      if (availableDrivers.isEmpty()) {
         return Optional.empty();
      }

      Location deliveryLocation = order.getDeliveryLocation();

      return availableDrivers.stream()
            .filter(driver -> isDriverSuitable(driver, order))
            .min((d1, d2) -> compareDrivers(d1, d2, deliveryLocation));
   }

   private boolean isDriverSuitable(Driver driver, Order order) {
      double distance = driver.getCurrentLocation()
            .distanceTo(order.getDeliveryLocation());

      return distance <= MAX_ACCEPTABLE_DISTANCE
            && driver.getAverageRating() >= 3.0;
   }

   private int compareDrivers(Driver d1, Driver d2, Location deliveryLocation) {
      // Compare based on distance and rating
      double d1Score = calculateDriverScore(d1, deliveryLocation);
      double d2Score = calculateDriverScore(d2, deliveryLocation);
      return Double.compare(d1Score, d2Score);
   }

   private double calculateDriverScore(Driver driver, Location deliveryLocation) {
      double distance = driver.getCurrentLocation().distanceTo(deliveryLocation);
      double rating = driver.getAverageRating();

      // Weight distance more heavily than rating
      return (distance * 0.7) + ((5 - rating) * 0.3);
   }
}