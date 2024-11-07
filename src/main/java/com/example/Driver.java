package main.java.com.example;

public class Driver {
    private String name;
    private String vehicle;
    private RatingsHandler<Integer> ratings;

    public Driver(String name, String vehicle) {
        this.name = name;
        this.vehicle = vehicle;
        this.ratings = new RatingsHandler<>();
    }

    public void acceptOrder(Order order) {
        // Logic to accept an order
    }

    public void updateRating(int rating) {
        ratings.add(rating); 
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getVehicle() {
        return vehicle;
    }
}