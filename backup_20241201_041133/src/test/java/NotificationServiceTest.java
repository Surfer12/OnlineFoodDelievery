import model.Driver;
import model.Order;
import orderUtilities.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private Order mockOrder;
    private Driver mockDriver;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService();
        mockOrder = Mockito.mock(Order.class);
        mockDriver = Mockito.mock(Driver.class);
    }

    @Test
    public void testSendOrderConfirmationToCustomer() {
        when(mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(mockOrder.getOrderId()).thenReturn(1L);
        when(mockOrder.getTotalAmount()).thenReturn(100.0);

        notificationService.sendOrderConfirmationToCustomer(mockOrder);

        verify(mockOrder).getCustomerEmail();
        verify(mockOrder).getOrderId();
        verify(mockOrder).getTotalAmount();
    }

    @Test
    public void testSendDriverAssignmentNotification() {
        when(mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(mockOrder.getOrderId()).thenReturn(1L);
        when(mockDriver.getName()).thenReturn("John Doe");

        notificationService.sendDriverAssignmentNotification(mockOrder, mockDriver);

        verify(mockOrder).getCustomerEmail();
        verify(mockOrder).getOrderId();
        verify(mockDriver).getName();
    }

    @Test
    public void testSendOrderStatusUpdateToCustomer() {
        when(mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(mockOrder.getOrderId()).thenReturn(1L);

        notificationService.sendOrderStatusUpdateToCustomer(mockOrder, OrderStatus.ACCEPTED);

        verify(mockOrder).getCustomerEmail();
        verify(mockOrder).getOrderId();
    }

    @Test
    public void testSendDeliveryCompletionNotification() {
        when(mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(mockOrder.getOrderId()).thenReturn(1L);

        notificationService.sendDeliveryCompletionNotification(mockOrder);

        verify(mockOrder).getCustomerEmail();
        verify(mockOrder).getOrderId();
    }
}
