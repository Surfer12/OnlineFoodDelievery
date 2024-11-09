package location;

public class GenericLocation {
   private final double latitude;
   private final double longitude;
   private String zipcode;
   private String address;

   public GenericLocation(double latitude, double longitude, String zipcode, String address) {
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

   public String getZipcode() {
      return zipcode;
   }

   public String getAddress() {
      return address;
   }

   public double calculateDistance(GenericLocation destination) {
      if (destination == null) {
         throw new IllegalArgumentException("Destination location cannot be null");
      }
      double latitudeDiff = this.latitude - destination.getLatitude();
      double longitudeDiff = this.longitude - destination.getLongitude();
      return Math.sqrt(latitudeDiff * latitudeDiff + longitudeDiff * longitudeDiff);
   }
}