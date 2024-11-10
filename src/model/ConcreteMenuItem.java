package model;

import java.util.Optional;

public class ConcreteMenuItem extends MenuItem {
    private final String name;
    private final double price;
    private final boolean available;
    private final String description;
    private final String category;

    public ConcreteMenuItem(String name, double price, boolean available, String description, String category) {
        super(null, name, description, price, category);
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
        this.category = category;
    }

    // Getters for the fields
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }

    @Override
    public int getQuantity() {
        return 1;
    }

    @Override
    public String toString() {
        return "ConcreteMenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
