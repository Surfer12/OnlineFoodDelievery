public interface DriverMatchingStrategy {
   Optional<Driver> findBestMatch(Order order, List<Driver> availableDrivers);
}