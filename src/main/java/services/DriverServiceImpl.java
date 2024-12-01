import java.util.ArrayList;
import java.util.List;

import model.Order;

public class DriverServiceImpl implements DriverService {
    private List<Driver> drivers = new ArrayList<>();

    @Override
    public List<Driver> getAvailableDrivers() {
        // Implementation to retrieve available drivers
        return this.drivers.stream().filter(Driver::isAvailable).toList();
    }

    @Override
    public Driver getDriverForOrder(Order order) {
        // Implementation to assign a driver to the order
        return this.drivers.stream().filter(Driver::isAvailable).findFirst().orElse(null);
    }

    @Override
    public void assignDriverToOrder(Driver driver, Order order) {
        // Implementation to assign the driver to the order
        if (driver != null) {
            driver.setAvailable(false);
            order.setDriver(driver);
        }
    }

    @Override
    public void rateDriver(Driver driver, Integer rating) {
        // Implementation to rate the driver
        if (driver != null) {
            driver.addRating(rating);
        }
    }
}