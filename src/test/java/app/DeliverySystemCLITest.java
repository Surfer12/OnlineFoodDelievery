package app;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import managers.DriverManager;
import managers.MenuManager;
import managers.OrderManager;
import validation.ConsoleInputHandler;

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

    @InjectMocks
    private DeliverySystemCLI deliverySystemCLI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartPlaceOrder() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(1);
        when(this.positiveIntegerHandler.handleInput(this.scanner, "Enter your choice below: ")).thenReturn(1);

        this.deliverySystemCLI.start();

        verify(this.orderManager).processOrderPlacement(this.scanner, this.menuManager, this.positiveIntegerHandler);
    }

    @Test
    public void testStartCheckOrderStatus() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(2);

        this.deliverySystemCLI.start();

        verify(this.orderManager).checkOrderStatus(this.scanner);
    }

    @Test
    public void testStartViewMenu() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(3);

        this.deliverySystemCLI.start();

        verify(this.menuManager).displayMenu();
    }

    @Test
    public void testStartManageDrivers() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(4);

        this.deliverySystemCLI.start();

        verify(this.driverManager).manageDriverMenu(this.scanner, this.orderManager);
    }

    @Test
    public void testStartRateDriver() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(5);
        when(this.orderManager.getOrderIdHandler().handleInput(this.scanner, "Enter Order ID to rate driver: "))
                .thenReturn(1L);
        when(this.orderManager.getOrderService().getOrderById(1L)).thenReturn(null);

        this.deliverySystemCLI.start();

        verify(this.driverManager).rateDriver(this.scanner, null, this.menuManager.getMenuChoiceHandler());
    }

    @Test
    public void testStartExit() {
        when(this.menuManager.getMenuChoiceHandler().handleInput(this.scanner, "Enter your choice below: "))
                .thenReturn(6);

        this.deliverySystemCLI.start();

        verify(this.scanner).close();
    }
}