package order;

import model.MenuItem;
import java.util.List;

public class Order {
    private final Long id;
    private final String customerEmail;
    private final String deliveryAddress;
    private final String postalCode;
    private final List<MenuItem> items;
    private final double totalPrice;

    public Order(Long id, String customerEmail, String deliveryAddress, String postalCode, List<MenuItem> items) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.deliveryAddress = deliveryAddress;
        this.postalCode = postalCode;
        this.items = items;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    public Long getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
