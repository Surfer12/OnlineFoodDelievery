

import java.util.List;

import backup_20241201_041133.src.main.java.orderUtilities.OrderStatus;

public class DeliverySystemTest {

    private DeliverySystem deliverySystem;
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        this.deliverySystem = new DeliverySystem();
        this.notificationService = mock(NotificationService.class);
    }

    @Test
    public void testSubmitOrder_Success() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        this.deliverySystem.submitOrder(order);

        assertTrue(this.deliverySystem.getOrderQueue().contains(order));
    }

    @Test
    public void testSubmitOrder_ValidationException() {
        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(OrderProcessingException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testSubmitOrder_PaymentException() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        doThrow(new PaymentException("Payment failed")).when(order).processPayment(anyString());

        assertThrows(OrderProcessingException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testAssignOrderToDriver_Success() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        final Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        this.deliverySystem.registerDriver(driver);
        this.deliverySystem.submitOrder(order);
        this.deliverySystem.assignOrderToDriver(order, driver);

        assertTrue(driver.getCurrentOrder().isPresent());
        assertEquals(order, driver.getCurrentOrder().get());
    }

    @Test
    public void testCompleteDelivery_Success() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        final Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        this.deliverySystem.registerDriver(driver);
        this.deliverySystem.submitOrder(order);
        this.deliverySystem.assignOrderToDriver(order, driver);
        this.deliverySystem.completeDelivery(order.getOrderId(), driver.getId());

        assertFalse(driver.getCurrentOrder().isPresent());
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    public void testSubmitOrder_UsingMockito() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        this.deliverySystem.submitOrder(order);

        verify(order).processPayment(anyString());
        verify(this.notificationService).sendOrderConfirmationToCustomer(order);
    }

    @Test
    public void testAssignOrderToDriver_UsingMockito() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        final Driver driver = Mockito.mock(Driver.class);
        when(driver.getId()).thenReturn(101L);
        when(driver.getName()).thenReturn("Bob Smith");
        when(driver.getVehicle()).thenReturn("Car");
        when(driver.getLicenseNumber()).thenReturn("ABC123");

        this.deliverySystem.registerDriver(driver);
        this.deliverySystem.submitOrder(order);
        this.deliverySystem.assignOrderToDriver(order, driver);

        verify(driver).acceptOrder(order);
        verify(this.notificationService).sendDriverAssignmentNotification(order, driver);
    }

    @Test
    public void testCompleteDelivery_UsingMockito() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        final Driver driver = Mockito.mock(Driver.class);
        when(driver.getId()).thenReturn(101L);
        when(driver.getName()).thenReturn("Bob Smith");
        when(driver.getVehicle()).thenReturn("Car");
        when(driver.getLicenseNumber()).thenReturn("ABC123");

        this.deliverySystem.registerDriver(driver);
        this.deliverySystem.submitOrder(order);
        this.deliverySystem.assignOrderToDriver(order, driver);
        this.deliverySystem.completeDelivery(order.getOrderId(), driver.getId());

        verify(driver).completeCurrentDelivery();
        verify(this.notificationService).sendDeliveryCompletionNotification(order);
    }

    @Test
    public void testSubmitOrder_InvalidEmail() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("invalid-email")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(ValidationException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testAssignOrderToDriver_NoAvailableDrivers() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        this.deliverySystem.submitOrder(order);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.assignOrderToDriver(order, null));
    }

    @Test
    public void testCompleteDelivery_InvalidOrderId() {
        final Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        this.deliverySystem.registerDriver(driver);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.completeDelivery(999L, driver.getId()));
    }

    @Test
    public void testSubmitOrder_EmptyOrderItems() {
        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(ValidationException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testAssignOrderToDriver_DriverAlreadyAssigned() {
        final MenuItemFactory factory = new MenuItemFactory();
        final MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        final Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        final Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        this.deliverySystem.registerDriver(driver);
        this.deliverySystem.submitOrder(order);
        this.deliverySystem.assignOrderToDriver(order, driver);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.assignOrderToDriver(order, driver));
    }
}
