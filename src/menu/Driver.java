package menu;

import java.util.Optional;
import location.Location;

public class Driver {
   private Optional<Location> currentLocation;

   public Optional<Location> getCurrentLocation() {
      return currentLocation;
   }
}