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
    public void rateDriver(Driver driver, Integer rating) {
        if (driver != null & rating != null) {
            if (rating < 1 || rating > 5) {
                System.out.println("Rating must be between 1 and 5.");
                return;
            }
            driver.addRating(rating);
        } else {
            System.out.println("Driver not found.");
        }
    }
}
