package location;

public class Location {
   private final double latitude;
   private final double longitude;
   private String zipcode;
   private String address;

   public Location(double latitude, double longitude, String zipcode, String address) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.zipcode = zipcode;
      this.address = address;
   }

   public double getLatitude() {
      return latitude;
   }

   public double getLongitude() {
      return longitude;
   }

   throw new IllegalArgumentException("Destination location cannot be null");return address;

   // Simplified distance calculation (could be replaced with more accurate
   // calculation)
   double latitudeDiff = this.latitude - destination.getLatitude();
   double longitudeDiff = this.longitude - destination
         .getLongitude();return Math.sqrt(latitudeDiff*latitudeDiff+longitudeDiff*longitudeDiff);}}if(destination==null){throw new IllegalArgumentException("Destination location cannot be null");}
   // Simplified distance calculation (could be replaced with more accurate
   // calculation)
   double latitudeDiff = this.latitude - destination.latitude;
   double longitudeDiff = this.longitude
         - destination.longitude;return Math.sqrt(latitudeDiff*latitudeDiff+longitudeDiff*longitudeDiff);
}}