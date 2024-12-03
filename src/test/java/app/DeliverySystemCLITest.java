package app;

import managers.DriverManager;
import managers.MenuManager;
import managers.OrderManager;
import model.Driver;
import model.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.OrderService;
import validation.ConsoleInputHandler;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliverySystemCLITest {

    @Mock
    private Scanner scanner;
    @Mock
    private MenuManager menuManager;
    @Mock
    private OrderManager orderManager;
    @Mock
    private DriverManager driverManager;
    @Mock
    private ConsoleInputHandler<Integer> positiveIntegerHandler;
    @Mock
    private ConsoleInputHandler<Integer> menuChoiceHandler;
    @Mock
    private OrderService orderService;
    @Mock
    private ConsoleInputHandler<Long> orderIdHandler;

    @InjectMocks
    private DeliverySystemCLI deliverySystemCLI;

    @BeforeEach
    public void setUp() {
        when(this.menuManager.getMenuChoiceHandler()).thenReturn(this.menuChoiceHandler);
        when(this.orderManager.getOrderService()).thenReturn(this.orderService);
        when(this.orderManager.getOrderIdHandler()).thenReturn(this.orderIdHandler);
    }

    @Test
    public void testStartPlaceOrder() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(1)
                .thenReturn(null);
        when(this.positiveIntegerHandler.handleInput(this.scanner, "Enter your choice below: ")).thenReturn(1);

        this.deliverySystemCLI.start();

        verify(this.orderManager).processOrderPlacement(this.scanner, this.menuManager, this.positiveIntegerHandler);
    }

    @Test
    public void testStartCheckOrderStatus() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(2)
                .thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.orderManager).checkOrderStatus(this.scanner);
    }

    @Test
    public void testStartViewMenu() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(3)
                .thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.menuManager).displayMenu();
    }

    @Test
    public void testStartManageDrivers() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(4)
                .thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.driverManager).manageDriverMenu(this.scanner, this.orderManager);
    }

    @Test
    public void testStartRateDriver() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(5)
                .thenReturn(null);
        when(this.orderIdHandler.handleInput(this.scanner, "Enter Order ID to rate driver: "))
                .thenReturn(1L);
        when(this.orderService.getOrderById(1L)).thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.driverManager).rateDriver(this.scanner, null, this.menuChoiceHandler);
    }

    @Test
    public void testStartExit() {
        when(this.menuChoiceHandler.handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(6)
                .thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.scanner).close();
    }

    @Test
    public void testRateDriver() {
        Driver driver = new Driver(1L, "John Doe", "Car", "ABC123");
        when(scanner.nextLine()).thenReturn("1", "5");
        when(driverManager.getDriverById(1L)).thenReturn(driver);

        deliverySystemCLI.rateDriver();

        verify(driverManager).rateDriver(driver, 5);
    }

    @Test
    public void testGetDriverRatings() {
        Driver driver = new Driver(1L, "John Doe", "Car", "ABC123");
        Rating rating1 = new Rating(5);
        Rating rating2 = new Rating(4);
        driver.addRating(rating1);
        driver.addRating(rating2);

        List<Rating> ratings = deliverySystemCLI.getDriverRatings(driver);

        assertEquals(2, ratings.size());
        assertEquals(5, ratings.get(0).getValue());
        assertEquals(4, ratings.get(1).getValue());
    }
}
