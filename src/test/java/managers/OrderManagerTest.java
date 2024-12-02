package managers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.MenuItem;
import model.Order;
import model.OrderStatus;
import services.OrderService;
import validation.ConsoleInputHandler;

class OrderManagerTest {

    @Mock
    private OrderService orderService;
    
    @Mock
    private ConsoleInputHandler<Long> orderIdHandler;
    
    @Mock
    private Scanner scanner;

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderManager = new OrderManager();
        // Use reflection to set mocked orderService
        setField(orderManager, "orderService", orderService);
        setField(orderManager, "orderIdHandler", orderIdHandler);
    }

    @Test
    void createOrder_ValidItems_Success() throws CustomException.QueueFullException {
        // Arrange
        MenuItem item = new ConcreteMenuItem("Test Item", "Description", 10.0);
        List<MenuItem> items = Arrays.asList(item);
        Order expectedOrder = new Order(items);
        when(orderService.createOrder(items)).thenReturn(expectedOrder);

        // Act
        Order result = orderManager.createOrder(items);

        // Assert
        assertNotNull(result);
        verify(orderService).createOrder(items);
        verify(orderService).displayOrderDetails(expectedOrder);
    }

    @Test
    void createOrder_EmptyItems_ReturnsNull() throws CustomException.QueueFullException {
        // Arrange
        List<MenuItem> items = Arrays.asList();

        // Act
        Order result = orderManager.createOrder(items);

        // Assert
        assertNull(result);
        verify(orderService, never()).createOrder(any());
    }

    @Test
    void checkOrderStatus_ValidOrder_DisplaysStatus() {
        // Arrange
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setStatus(OrderStatus.IN_PROGRESS);
        when(orderIdHandler.handleInput(any(), any())).thenReturn(orderId);
        when(orderService.getOrderById(orderId)).thenReturn(mockOrder);

        // Act
        orderManager.checkOrderStatus(scanner);

        // Assert
        verify(orderService).getOrderById(orderId);
    }

    @Test
    void processOrderPlacement_QueueFull_HandlesException() {
        // Arrange
        MenuItem item = new MenuItem("Test Item", "Description", 10.0);
        List<MenuItem> items = Arrays.asList(item);
        when(orderService.createOrder(any()))
            .thenThrow(new CustomException.QueueFullException("Queue is full"));

        // Act & Assert
        assertThrows(CustomException.QueueFullException.class, () ->
            orderManager.createOrder(items)
        );
    }

    @Test
    void getPendingOrders_ReturnsOnlySubmittedOrders() {
        // Arrange
        Order submittedOrder = new Order();
        submittedOrder.setStatus(OrderStatus.SUBMITTED);
        Order deliveredOrder = new Order();
        deliveredOrder.setStatus(OrderStatus.DELIVERED);
        
        when(orderService.getAllOrders())
            .thenReturn(Arrays.asList(submittedOrder, deliveredOrder));

        // Act
        List<Order> pendingOrders = orderManager.getPendingOrders();

        // Assert
        assertEquals(1, pendingOrders.size());
        assertEquals(OrderStatus.SUBMITTED, pendingOrders.get(0).getStatus());
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
