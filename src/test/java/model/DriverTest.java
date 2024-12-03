package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverTest {

    private Driver driver;

    @BeforeEach
    public void setUp() {
        driver = new Driver(1L, "John Doe", "Car", "ABC123");
    }

    @Test
    public void testGetRatings() {
        Rating rating1 = new Rating(5);
        Rating rating2 = new Rating(4);
        driver.addRating(rating1);
        driver.addRating(rating2);

        List<Rating> ratings = driver.getRatings();

        assertEquals(2, ratings.size());
        assertEquals(5, ratings.get(0).getValue());
        assertEquals(4, ratings.get(1).getValue());
    }

    @Test
    public void testAddRating() {
        Rating rating1 = new Rating(5);
        Rating rating2 = new Rating(4);
        driver.addRating(rating1);
        driver.addRating(rating2);

        List<Rating> ratings = driver.getRatings();

        assertEquals(2, ratings.size());
        assertEquals(5, ratings.get(0).getValue());
        assertEquals(4, ratings.get(1).getValue());
    }
}
