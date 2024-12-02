package services;

import model.Driver;
import model.OrderModel;

import java.util.List;

public interface DriverService {
    List<Driver> getAvailableDrivers();
    Driver getDriverForOrder(OrderModel order);
    void assignDriverToOrder(Driver driver, OrderModel order);
    void rateDriver(Driver driver, Integer rating);
}