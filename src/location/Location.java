package location;

public class Location {
   private String zipcode;
   private String address;

   public Location(String zipcode, String address) {
      this.zipcode = zipcode;
      this.address = address;
   }

   public String getZipcode() {
      return zipcode;
   }

   public String getAddress() {
      return address;
   }

   public double distanceTo(Location destination) {
      return 0;
   }
}
