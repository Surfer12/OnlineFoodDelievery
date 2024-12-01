package model;

public class Driver {
   private final Long id;
   private final String name;
   private final String vehicleType;
   private final String licensePlate;

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

   public void addRating(final double rating) {
      // Placeholder for adding rating logic
   }
}
