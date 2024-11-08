package matching;

import java.util.List;
import java.util.Optional;

import location.Location;
import user.Driver;
import order.Order;

public class ProximityBasedMatchingStrategy implements DriverMatchingStrategy {
   private static final double MAX_ACCEPTABLE_DISTANCE = 10.0; // km
   private static final double DISTANCE_WEIGHT = 0.7;
   private static final double RATING_WEIGHT = 0.3;
   private static final double MINIMUM_ACCEPTABLE_RATING = 3.0;

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
      Location driverLocation = driver.getCurrentLocation();
      return isWithinAcceptableDistance(driverLocation, order.getDeliveryLocation())
            && hasAcceptableRating(driver);
   }

   private boolean isWithinAcceptableDistance(Location driverLocation, Location deliveryLocation) {
      return driverLocation.calculateDistanceInKilometers(deliveryLocation) <= MAX_ACCEPTABLE_DISTANCE;
   }

   private boolean hasAcceptableRating(Driver driver) {
      return driver.getAverageRating() >= MINIMUM_ACCEPTABLE_RATING;
   }

   private int compareDrivers(Driver d1, Driver d2, Location deliveryLocation) {
      // Compare based on distance and rating
      double d1Score = calculateDriverScore(d1, deliveryLocation);
      double d2Score = calculateDriverScore(d2, deliveryLocation);
      return Double.compare(d1Score, d2Score);
   }

   private double calculateDriverScore(Driver driver, Location deliveryLocation) {
      double distanceScore = calculateDistanceScore(driver, deliveryLocation);
      double ratingScore = calculateRatingScore(driver);

      return (distanceScore * DISTANCE_WEIGHT) + (ratingScore * RATING_WEIGHT);
   }

   private double calculateDistanceScore(Driver driver, Location deliveryLocation) {
      return driver.getCurrentLocation().calculateDistanceInKilometers(deliveryLocation);
   }

   private double calculateRatingScore(Driver driver) {
      // Convert rating to a score where lower is better (to match distance scoring)
      return 5 - driver.getAverageRating();
   }
}