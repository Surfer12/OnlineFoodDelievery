package matching;

import java.util.List;
import java.util.Optional;

import user.Driver;
import order.Order;

public interface DriverMatchingStrategy {
   Optional<Driver> findBestMatch(Order order, List<Driver> availableDrivers);
}