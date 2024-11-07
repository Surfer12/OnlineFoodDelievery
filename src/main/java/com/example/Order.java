package main.java.com.example;
import java.util.List;

public class Order {
    private Customer customer;
    private List<MenuItem> items;
    private OrderStatus status;

    public Order(Customer customer, List<MenuItem> items) {
        this.customer = customer;
        this.items = items;
        this.status = OrderStatus.PLACED;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }
}