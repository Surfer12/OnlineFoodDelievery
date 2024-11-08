package location;

public class Location {
   private final double latitude;
   private final double longitude;

   public Location(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public double getLongitude() {
      return longitude;
   }

   public double calculateDistanceInKilometers(Location destination) {
      if (destination == null) {
         throw new IllegalArgumentException("Destination location cannot be null");
      }
      // Simplified distance calculation (could be replaced with more accurate
      // calculation)
      double latitudeDiff = this.latitude - destination.latitude;
      double longitudeDiff = this.longitude - destination.longitude;
      return Math.sqrt(latitudeDiff * latitudeDiff + longitudeDiff * longitudeDiff);
   }
}