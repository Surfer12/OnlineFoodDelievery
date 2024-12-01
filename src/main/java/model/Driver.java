package model;

public class Driver {
   private final String name;
   private final String email;
   private final String phoneNumber;

   public Driver(final String name, final String email, final String phoneNumber) {
      this.name = name;
      this.email = email;
      this.phoneNumber = phoneNumber;
   }

   public String getName() {
      return this.name;
   }

   public String getEmail() {
      return this.email;
   }

   public String getPhoneNumber() {
      return this.phoneNumber;
   }
}
