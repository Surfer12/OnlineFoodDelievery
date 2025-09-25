package services;

import model.Driver;
import model.Order;
import model.OrderStatus;
import rating.Rating;

import java.util.ArrayList;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    private List<Driver> drivers = new ArrayList<>();

    @Override
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(this.drivers);
    }

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
        if (driver != null && order != null) {
            driver.setAvailable(false);
            order.setDriver(driver);
            order.setStatus(OrderStatus.IN_PROGRESS);
        }
    }

    @Override
    public java.util.Optional<Driver> findAvailableDriver() {
        return this.drivers.stream()
                .filter(Driver::isAvailable)
                .findFirst();
    }

    @Override
    public void updateDriverAvailability(Driver driver, boolean isAvailable) {
        if (driver != null) {
            driver.setAvailable(isAvailable);
        }
    }

    @Override
    public void rateDriver(Driver driver, rating.Rating rating) {
        if (driver != null && rating != null) {
            // Convert rating.Rating to model.Rating
            model.Rating driverRating = new model.Rating(rating.getScore());
            driver.addRating(driverRating);
        } else {
            System.out.println("Driver or rating not found.");
        }
    }
}
