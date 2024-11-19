

import model.Driver;
import model.MenuItem;
import model.Order;
import model.Size;
import factory.MenuItemFactory;
import orderUtilities.OrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import notification.NotificationService;
import CustomException.OrderProcessingException;
import CustomException.PaymentException;
import CustomException.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliverySystemTest {

    private DeliverySystem deliverySystem;
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        deliverySystem = new DeliverySystem();
        notificationService = mock(NotificationService.class);
    }

    @Test
    public void testSubmitOrder_Success() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        deliverySystem.submitOrder(order);

        assertTrue(deliverySystem.getOrderQueue().contains(order));
    }

    @Test
    public void testSubmitOrder_ValidationException() {
        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(OrderProcessingException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testSubmitOrder_PaymentException() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
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
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        deliverySystem.registerDriver(driver);
        deliverySystem.submitOrder(order);
        deliverySystem.assignOrderToDriver(order, driver);

        assertTrue(driver.getCurrentOrder().isPresent());
        assertEquals(order, driver.getCurrentOrder().get());
    }

    @Test
    public void testCompleteDelivery_Success() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        deliverySystem.registerDriver(driver);
        deliverySystem.submitOrder(order);
        deliverySystem.assignOrderToDriver(order, driver);
        deliverySystem.completeDelivery(order.getOrderId(), driver.getId());

        assertFalse(driver.getCurrentOrder().isPresent());
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    public void testSubmitOrder_UsingMockito() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        deliverySystem.submitOrder(order);

        verify(order).processPayment(anyString());
        verify(notificationService).sendOrderConfirmationToCustomer(order);
    }

    @Test
    public void testAssignOrderToDriver_UsingMockito() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        Driver driver = Mockito.mock(Driver.class);
        when(driver.getId()).thenReturn(101L);
        when(driver.getName()).thenReturn("Bob Smith");
        when(driver.getVehicle()).thenReturn("Car");
        when(driver.getLicenseNumber()).thenReturn("ABC123");

        deliverySystem.registerDriver(driver);
        deliverySystem.submitOrder(order);
        deliverySystem.assignOrderToDriver(order, driver);

        verify(driver).acceptOrder(order);
        verify(notificationService).sendDriverAssignmentNotification(order, driver);
    }

    @Test
    public void testCompleteDelivery_UsingMockito() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = Mockito.mock(Order.class);
        when(order.getCustomerId()).thenReturn(1L);
        when(order.getCustomerEmail()).thenReturn("jane.doe@example.com");
        when(order.getItems()).thenReturn(List.of(pizza));
        when(order.getDeliveryLocation()).thenReturn("456 Elm Street");
        when(order.getZipcode()).thenReturn("12345");

        Driver driver = Mockito.mock(Driver.class);
        when(driver.getId()).thenReturn(101L);
        when(driver.getName()).thenReturn("Bob Smith");
        when(driver.getVehicle()).thenReturn("Car");
        when(driver.getLicenseNumber()).thenReturn("ABC123");

        deliverySystem.registerDriver(driver);
        deliverySystem.submitOrder(order);
        deliverySystem.assignOrderToDriver(order, driver);
        deliverySystem.completeDelivery(order.getOrderId(), driver.getId());

        verify(driver).completeCurrentDelivery();
        verify(notificationService).sendDeliveryCompletionNotification(order);
    }

    @Test
    public void testSubmitOrder_InvalidEmail() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("invalid-email")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(ValidationException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testAssignOrderToDriver_NoAvailableDrivers() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        deliverySystem.submitOrder(order);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.assignOrderToDriver(order, null));
    }

    @Test
    public void testCompleteDelivery_InvalidOrderId() {
        Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        deliverySystem.registerDriver(driver);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.completeDelivery(999L, driver.getId()));
    }

    @Test
    public void testSubmitOrder_EmptyOrderItems() {
        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        assertThrows(ValidationException.class, () -> deliverySystem.submitOrder(order));
    }

    @Test
    public void testAssignOrderToDriver_DriverAlreadyAssigned() {
        MenuItemFactory factory = new MenuItemFactory();
        MenuItem pizza = factory.createMenuItem("hamburger", "Pepperoni Pizza", "Spicy pepperoni with cheese", 12.99, Size.MEDIUM, 1);

        Order order = new OrderBuilder()
                .withValidatedCustomerId(1L)
                .withCustomerEmail("jane.doe@example.com")
                .addItem(pizza)
                .withValidatedDeliveryLocation("456 Elm Street", "12345")
                .build();

        Driver driver = new Driver(101L, "Bob Smith", "Car", "ABC123");

        deliverySystem.registerDriver(driver);
        deliverySystem.submitOrder(order);
        deliverySystem.assignOrderToDriver(order, driver);

        assertThrows(OrderProcessingException.class, () -> deliverySystem.assignOrderToDriver(order, driver));
    }
}
