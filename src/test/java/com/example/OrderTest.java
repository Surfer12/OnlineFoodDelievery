package test.java.com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class OrderTest {
    private Order order;
    private Customer customer;
    private List<MenuItem> items;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", "123 Main St", "555-1234");
        items = new ArrayList<>();
        items.add(new MenuItem("Pizza", 10.99, "Cheese Pizza"));
        items.add(new MenuItem("Burger", 8.99, "Beef Burger"));
        order = new Order(customer, items);
    }

    @Test
    void testOrderCreation() {
        assertNotNull(order);
        assertEquals(customer, order.getCustomer());
        assertEquals(items, order.getItems());
        assertEquals(OrderStatus.PLACED, order.getStatus());
    }

    @Test
    void testOrderStatusManagement() {
        order.setStatus(OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED, order.getStatus());

        order.setStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }
}