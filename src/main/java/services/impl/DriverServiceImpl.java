/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services.impl;

import model.Driver;
import model.Order;
import model.OrderStatus;
import services.DriverService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DriverServiceImpl implements DriverService {
    private static final Logger logger = Logger.getLogger(DriverServiceImpl.class.getName());
    private final List<Driver> drivers;
    private final Map<Long, Driver> orderDriverMap;

    public DriverServiceImpl() {
        this.drivers = new ArrayList<>();
        this.orderDriverMap = new ConcurrentHashMap<>();
        initializeDrivers();
    }

    private void initializeDrivers() {
        // Initialize with sample drivers
        drivers.add(new Driver(1L, "John Doe", "Car", "ABC123"));
        drivers.add(new Driver(2L, "Jane Smith", "Bike", "XYZ789"));
        drivers.add(new Driver(3L, "Mike Johnson", "Scooter", "DEF456"));
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        return drivers.stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Driver getDriverForOrder(Order order) {
        return orderDriverMap.get(order.getOrderId());
    }

    @Override
    public void assignDriverToOrder(Driver driver, Order order) {
        if (driver == null || order == null) {
            logger.warning("Cannot assign null driver or order");
            return;
        }

        driver.setAvailable(false);
        orderDriverMap.put(order.getOrderId(), driver);
        order.setDriver(driver);
        order.setStatus(OrderStatus.IN_PROGRESS);
        logger.info("Assigned driver " + driver.getId() + " to order " + order.getOrderId());
    }

    @Override
    public void rateDriver(Driver driver, Integer rating) {
        if (driver == null) {
            logger.warning("Cannot rate null driver");
            return;
        }

        if (rating == null || rating < 1 || rating > 5) {
            logger.warning("Invalid rating value: " + rating);
            return;
        }

        try {
            driver.addRating(rating);
            logger.info("Added rating " + rating + " to driver " + driver.getId());
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to add rating: " + e.getMessage());
        }
    }

    public void completeDelivery(Order order) {
        Driver driver = orderDriverMap.get(order.getOrderId());
        if (driver != null) {
            driver.setAvailable(true);
            order.setStatus(OrderStatus.DELIVERED);
            logger.info("Delivery completed for order " + order.getOrderId());
        }
    }
}
