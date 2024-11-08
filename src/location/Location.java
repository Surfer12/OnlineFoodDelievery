package location;

public class Location {
   private double latitude;
   private double longitude;

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

   public double distanceTo(Location other) {
      // Simplified distance calculation (could be replaced with more accurate
      // calculation)
      double dx = this.latitude - other.latitude;
      double dy = this.longitude - other.longitude;
      return Math.sqrt(dx * dx + dy * dy);
   }
}