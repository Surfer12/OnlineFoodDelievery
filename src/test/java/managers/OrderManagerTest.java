package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import services.OrderService;
import services.impl.OrderServiceImpl;
import model.MenuItem;
import model.Order;
import java.util.Arrays;
import java.util.List;

public class OrderManagerTest {
    private OrderService orderServiceMock;
    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderServiceMock = mock(OrderServiceImpl.class);
        orderManager = new OrderManager();
        orderManager.orderService = orderServiceMock; // Inject mock
    }

    @Test
    void testCreateOrderSuccess() throws CustomException.QueueFullException {
        MenuItem item = new MenuItem("Pizza", "Delicious cheese pizza", 10.0, Size.MEDIUM, 1);
        List<MenuItem> items = Arrays.asList(item);
        Order mockOrder = new Order();
        when(orderServiceMock.createOrder(items)).thenReturn(mockOrder);

        Order createdOrder = orderManager.createOrder(items);
        assertNotNull(createdOrder);
        verify(orderServiceMock, times(1)).createOrder(items);
    }

    @Test
    void testCreateOrderEmptyItems() throws CustomException.QueueFullException {
        List<MenuItem> items = Arrays.asList();
        Order createdOrder = orderManager.createOrder(items);
        assertNull(createdOrder);
        verify(orderServiceMock, never()).createOrder(anyList());
    }

    // ...additional test methods...
}

public class OrderManagerTest {
    
}
