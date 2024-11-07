package test.java.com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer customer;
    private Order order;
    private Driver driver;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", "123 Main St", "555-1234");
        driver = new Driver("Jane Smith", "Toyota Camry");
        order = new Order(customer);
    }

    @Test
    void testPlaceOrder() {
        customer.placeOrder(order);
        assertEquals(order, customer.getOrders().get(0));
    }

    @Test
    void testRateDriver() {
        customer.rateDriver(driver, 5);
        assertEquals(5, driver.getRatings().get(0));
    }
}