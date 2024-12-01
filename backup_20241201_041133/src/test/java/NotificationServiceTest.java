import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private Order mockOrder;
    private Driver mockDriver;

    @BeforeEach
    public void setUp() {
        this.notificationService = new NotificationService();
        this.mockOrder = Mockito.mock(Order.class);
        this.mockDriver = Mockito.mock(Driver.class);
    }

    @Test
    public void testSendOrderConfirmationToCustomer() {
        when(this.mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(this.mockOrder.getOrderId()).thenReturn(1L);
        when(this.mockOrder.getTotalAmount()).thenReturn(100.0);

        this.notificationService.sendOrderConfirmationToCustomer(this.mockOrder);

        verify(this.mockOrder).getCustomerEmail();
        verify(this.mockOrder).getOrderId();
        verify(this.mockOrder).getTotalAmount();
    }

    @Test
    public void testSendDriverAssignmentNotification() {
        when(this.mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(this.mockOrder.getOrderId()).thenReturn(1L);
        when(this.mockDriver.getName()).thenReturn("John Doe");

        this.notificationService.sendDriverAssignmentNotification(this.mockOrder, this.mockDriver);

        verify(this.mockOrder).getCustomerEmail();
        verify(this.mockOrder).getOrderId();
        verify(this.mockDriver).getName();
    }

    @Test
    public void testSendOrderStatusUpdateToCustomer() {
        when(this.mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(this.mockOrder.getOrderId()).thenReturn(1L);

        this.notificationService.sendOrderStatusUpdateToCustomer(this.mockOrder, OrderStatus.ACCEPTED);

        verify(this.mockOrder).getCustomerEmail();
        verify(this.mockOrder).getOrderId();
    }

    @Test
    public void testSendDeliveryCompletionNotification() {
        when(this.mockOrder.getCustomerEmail()).thenReturn("customer@example.com");
        when(this.mockOrder.getOrderId()).thenReturn(1L);

        this.notificationService.sendDeliveryCompletionNotification(this.mockOrder);

        verify(this.mockOrder).getCustomerEmail();
        verify(this.mockOrder).getOrderId();
    }
}
