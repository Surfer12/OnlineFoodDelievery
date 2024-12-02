package app;

public class MenuItem {
    private final String name;
    private final double price;

    public MenuItem(final String name, final double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return this.name + " - $" + String.format("%.2f", this.price);
    }
} 