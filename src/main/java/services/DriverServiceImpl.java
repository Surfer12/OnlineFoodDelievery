package services;

import java.util.ArrayList;
import java.util.List;

import model.Driver;
import model.Order;
import model.OrderStatus;

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
        if (driver != null && order != null) {
            driver.setAvailable(false);
            order.setDriver(driver);
            // Optionally update order status
            order.setStatus(OrderStatus.IN_PROGRESS);
        }
    }

    @Override
    public void rateDriver(Driver driver, Integer rating) {
        if (driver != null) {
            driver.addRating(rating);
        }
    }
}