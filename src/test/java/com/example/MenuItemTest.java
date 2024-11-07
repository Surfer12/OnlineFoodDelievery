package test.java.com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem("Pizza", 9.99, "Delicious cheese pizza");
    }

    @Test
    void testMenuItemCreation() {
        assertNotNull(menuItem);
        assertEquals("Pizza", menuItem.getName());
        assertEquals(9.99, menuItem.getPrice());
        assertEquals("Delicious cheese pizza", menuItem.getDescription());
    }

    @Test
    void testMenuItemAttributes() {
        menuItem.setName("Burger");
        menuItem.setPrice(5.99);
        menuItem.setDescription("Juicy beef burger");

        assertEquals("Burger", menuItem.getName());
        assertEquals(5.99, menuItem.getPrice());
        assertEquals("Juicy beef burger", menuItem.getDescription());
    }
}