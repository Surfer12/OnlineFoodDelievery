/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Driver;
import model.Order;
import services.DriverService;

public class DriverServiceImpl implements DriverService {
    private static final Logger logger = Logger.getLogger(DriverServiceImpl.class.getName());
    private final List<Driver> drivers = new ArrayList<>();

    @Override
    public List<Driver> getAvailableDrivers() {
        return this.drivers.stream()
                .filter(Driver::isAvailable)
                .toList();
    }

    @Override
    public Optional<Driver> findAvailableDriver() {
        return this.drivers.stream()
                .filter(Driver::isAvailable)
                .findFirst();
    }

    @Override
    public void assignDriverToOrder(final Driver driver, final Order order) {
        if (driver == null || order == null) {
            DriverServiceImpl.logger.warning("Cannot assign null driver or order");
            return;
        }

        try {
            driver.acceptOrder(order);
            DriverServiceImpl.logger.info(() -> String.format("Driver %s assigned to order %d",
                    driver.getName(), order.getId()));
        } catch (final Exception e) {
            DriverServiceImpl.logger.log(Level.SEVERE, "Error assigning driver to order", e);
        }
    }

    @Override
    public Driver getDriverForOrder(final Order order) {
        if (order == null) {
            DriverServiceImpl.logger.warning("Cannot find driver for null order");
            return null;
        }

        return this.drivers.stream()
                .filter(driver -> driver.getCurrentOrder()
                        .map(currentOrder -> currentOrder.getId().equals(order.getId()))
                        .orElse(false))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateDriverAvailability(final Driver driver, final boolean isAvailable) {
        if (driver == null) {
            DriverServiceImpl.logger.warning("Cannot update availability for null driver");
            return;
        }

        driver.setAvailable(isAvailable);
        DriverServiceImpl.logger.info(() -> String.format("Driver %s availability updated to %b",
                driver.getName(), isAvailable));
    }

    @Override
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(this.drivers);
    }

    // Additional utility methods
    public void addDriver(final Driver driver) {
        if (driver == null) {
            DriverServiceImpl.logger.warning("Cannot add null driver");
            return;
        }

        if (!this.drivers.contains(driver)) {
            this.drivers.add(driver);
            DriverServiceImpl.logger.info(() -> String.format("Driver %s added to system", driver.getName()));
        }
    }

    public void removeDriver(final Driver driver) {
        if (driver == null) {
            DriverServiceImpl.logger.warning("Cannot remove null driver");
            return;
        }

        if (this.drivers.remove(driver)) {
            DriverServiceImpl.logger.info(() -> String.format("Driver %s removed from system", driver.getName()));
        }
    }
}
