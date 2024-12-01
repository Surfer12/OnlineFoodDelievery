package services;

import java.util.ArrayList;
import java.util.List;

import model.Driver;
import model.Order;

public class DriverServiceImpl implements DriverService {
    private List<Driver> drivers = new ArrayList<>();

    @Override
    public List<Driver> getAvailableDrivers() {
        return this.drivers.stream()
                .filter(Driver::isAvailable)
                .toList();
    }

    @Override
    public Driver getDriverForOrder(Order order) {
        return this.drivers.stream()
                .filter(Driver::isAvailable)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void assignDriverToOrder(Driver driver, Order order) {
        if (driver != null) {
            driver.setAvailable(false);
            order.setDriver(driver);
        }
    }

    @Override
    public void rateDriver(Driver driver, Integer rating) {
        if (driver != null) {
            driver.addRating(rating);
        }
    }
}