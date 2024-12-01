/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services.impl;

import java.util.ArrayList;
import java.util.List;

import model.Driver;
import model.Order;
import services.DriverService;

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
