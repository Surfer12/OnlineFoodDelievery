package test.java.com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RatingTest {
    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating("Driver1", 5, "Excellent service!");
    }

    @Test
    void testCreateRating() {
        assertNotNull(rating);
        assertEquals("Driver1", rating.getDriver());
        assertEquals(5, rating.getScore());
        assertEquals("Excellent service!", rating.getComments());
    }

    @Test
    void testRetrieveRating() {
        assertEquals("Driver1", rating.getDriver());
        assertEquals(5, rating.getScore());
        assertEquals("Excellent service!", rating.getComments());
    }
}