package integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import app.DeliverySystemCLI;
import managers.MenuManager;
import managers.OrderManager;
import managers.DriverManager;
import model.ConcreteMenuItem;
import model.MenuItem;
import model.Size;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

/**
 * Basic integration test to verify system components can be instantiated and work together
 */
public class BasicSystemTest {
    
    private MenuManager menuManager;
    private OrderManager orderManager;
    private DriverManager driverManager;
    
    @BeforeEach
    void setUp() {
        menuManager = new MenuManager();
        orderManager = new OrderManager();
        driverManager = new DriverManager();
    }
    
    @Test
    void testSystemInstantiation() {
        // Test that we can create all major components without errors
        assertNotNull(menuManager);
        assertNotNull(orderManager);
        assertNotNull(driverManager);
    }
    
    @Test
    void testMenuManagerBasicFunctionality() {
        // Test adding and retrieving menu items
        MenuItem item = new ConcreteMenuItem(1L, "Test Burger", "Delicious test burger", 9.99, Size.MEDIUM, 1);
        menuManager.addMenuItem(item);
        
        assertEquals(1, menuManager.getMenuItems().size());
        assertEquals(item, menuManager.getMenuItemById(1L));
    }
    
    @Test 
    void testDeliverySystemCLICanBeInstantiated() {
        // Test that the main CLI can be created without throwing exceptions
        String input = "6\n"; // Exit option
        ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(testInput);
        
        assertDoesNotThrow(() -> {
            DeliverySystemCLI cli = new DeliverySystemCLI(scanner, menuManager, orderManager, driverManager);
            assertNotNull(cli);
        });
    }
}