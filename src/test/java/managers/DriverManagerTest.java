package managers;

import model.Driver;
import model.Order;
import model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.DriverService;
import validation.ConsoleInputHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class DriverManagerTest {

    @Mock private DriverService driverService;
    @Mock private ConsoleInputHandler<Integer> menuChoiceHandler;
    @Mock private Scanner scanner;
    @Mock private OrderManager orderManager;

    private DriverManager driverManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverManager = new DriverManager();
        setField(driverManager, "driverService", driverService);
        setField(driverManager, "menuChoiceHandler", menuChoiceHandler);
    }

    @Test
    void listAvailableDrivers_ShouldDisplayDrivers() {
        // Arrange
        Driver driver1 = new Driver(1L, "John", "Car", "ABC123");
        Driver driver2 = new Driver(2L, "Jane", "Bike", "XYZ789");
        when(driverService.getAvailableDrivers()).thenReturn(Arrays.asList(driver1, driver2));

        // Act
        driverManager.listAvailableDrivers();

        // Assert
        verify(driverService).getAvailableDrivers();
    }

    @Test
    void assignDriverToOrder_ValidOrder_Success() {
        // Arrange
        Order order = new Order(new ArrayList<>());
        Driver driver = new Driver(1L, "John", "Car", "ABC123");
        when(driverService.getAvailableDrivers()).thenReturn(Arrays.asList(driver));
        when(menuChoiceHandler.handleInput(any(), any())).thenReturn(1);

        // Act
        driverManager.assignDriverToOrder(scanner, order, mock(ConsoleInputHandler.class));

        // Assert
        verify(driverService).assignDriverToOrder(driver, order);
    }

    @Test
    void rateDriver_ValidRating_Success() {
        // Arrange
        Order order = new Order(new ArrayList<>());
        Driver driver = new Driver(1L, "John", "Car", "ABC123");
        when(driverService.getDriverForOrder(order)).thenReturn(driver);
        when(menuChoiceHandler.handleInput(any(), any(), any())).thenReturn(5);

        // Act
        driverManager.rateDriver(scanner, order, menuChoiceHandler);

        // Assert
        verify(driverService).rateDriver(driver, 5);
    }

    @Test
    void acceptAndDeliverOrder_ValidOrder_Success() {
        // Arrange
        // Create an empty order to avoid null pointer exceptions
        Order order = new Order(new ArrayList<>());
        order.setStatus(OrderStatus.SUBMITTED);
        Driver driver = new Driver(1L, "John", "Car", "ABC123");
        when(orderManager.getPendingOrders()).thenReturn(Arrays.asList(order));
        when(driverService.getAvailableDrivers()).thenReturn(Arrays.asList(driver));
        when(menuChoiceHandler.handleInput(any(), any())).thenReturn(1);

        // Act
        driverManager.acceptAndDeliverOrder(scanner, orderManager);

        // Assert
        verify(driverService).assignDriverToOrder(driver, order);
        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
