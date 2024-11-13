package location;

/**
 * Represents a location with a zipcode and address.
 */
public class Location {
   private String zipcode;
   private String address;

   /**
    * Constructs a Location with the specified zipcode and address.
    *
    * @param zipcode the zipcode of the location
    * @param address the address of the location
    */
   public Location(String zipcode, String address) {
      this.zipcode = zipcode;
      this.address = address;
   }

   /**
    * Returns the zipcode of the location.
    *
    * @return the zipcode of the location
    */
   public String getZipcode() {
      return zipcode;
   }

   /**
    * Returns the address of the location.
    *
    * @return the address of the location
    */
   public String getAddress() {
      return address;
   }

   /**
    * Calculates the distance to another location.
    *
    * @param destination the destination location
    * @return the distance to the destination location
    */
   public double distanceTo(Location destination) {
      return 0;
   }
}
