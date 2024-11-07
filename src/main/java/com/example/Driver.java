package main.java.com.example;

import main.java.com.example.CircularBuffer; // Import the CircularBuffer class

public class Driver {
    private String name;
    private String vehicle;
    private CircularBuffer<Integer> ratings;

    public Driver(String name, String vehicle) {
        this.name = name;
        this.vehicle = vehicle;
    }

    public void acceptOrder(Order order) {
        // Logic to accept an order
    }

    public void updateRating(int rating) {
        ratings.add(rating); // Assuming add method adds a rating to the circular buffer
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public CircularBuffer<Integer> getRatings() {
        return ratings;
    }
}