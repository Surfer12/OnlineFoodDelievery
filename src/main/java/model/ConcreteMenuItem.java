package model;

public class ConcreteMenuItem implements MenuItem {
    private final String name;
    private final double price;

    public ConcreteMenuItem(final String name, final double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return this.name + " - $" + String.format("%.2f", this.price);
    }
}