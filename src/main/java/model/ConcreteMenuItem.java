package model;

public class ConcreteMenuItem implements MenuItem {
    private String name;
    private double price;
    private Size size;
    private int quantity;

    public ConcreteMenuItem(final Long id, final String name, final String description,
                            final double price, final Size size, final int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price * this.quantity;
    }

    public Size getSize() {
        return this.size;
    }

    public int getQuantity() {
        return this.quantity;
    }
}