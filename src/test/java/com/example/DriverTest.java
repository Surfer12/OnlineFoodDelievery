package test.java.com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DriverTest {
    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver("John Doe", "Toyota Camry");
    }

    @Test
    void testAcceptOrder() {
        Order order = new Order(new Customer("Jane Smith", "123 Main St", "555-1234"));
        driver.acceptOrder(order);
        assertEquals(order, driver.getCurrentOrder());
    }

    @Test
    void testUpdateRating() {
        driver.updateRating(5);
        assertEquals(5, driver.getAverageRating());
    }

    @Test
    void testMultipleRatings() {
        driver.updateRating(4);
        driver.updateRating(3);
        assertEquals(3.5, driver.getAverageRating(), 0.01);
    }
}