package services;

import java.util.List;
import java.util.Optional;

import model.Driver;
import model.Order;

public interface DriverService {
    /**
     * Retrieves a list of available drivers.
     *
     * @return List of available drivers
     */
    List<Driver> getAvailableDrivers();

    /**
     * Finds an available driver.
     *
     * @return Optional containing an available driver, or empty if no drivers are
     *         available
     */
    Optional<Driver> findAvailableDriver();

    /**
     * Assigns a driver to a specific order.
     *
     * @param driver The driver to assign
     * @param order  The order to be assigned
     */
    void assignDriverToOrder(Driver driver, Order order);

    /**
     * Retrieves the driver assigned to a specific order.
     *
     * @param order The order to find the driver for
     * @return The assigned driver, or null if no driver is assigned
     */
    Driver getDriverForOrder(Order order);

    /**
     * Updates a driver's availability status.
     *
     * @param driver      The driver to update
     * @param isAvailable The new availability status
     */
    void updateDriverAvailability(Driver driver, boolean isAvailable);

    /**
     * Retrieves all drivers in the system.
     *
     * @return List of all drivers
     */
    List<Driver> getAllDrivers();
}