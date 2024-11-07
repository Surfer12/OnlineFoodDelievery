package main.java.com.example;


public class Customer {
    private String name;
    private String address;
    private String phone;

    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void placeOrder(Order order) {
        // Implementation for placing an order
    }

    public void rateDriver(Driver driver, int rating) {
        // Implementation for rating a driver
    }

    // Getters and Setters (if needed)
}