package model;

public class Salad extends ConcreteMenuItem {
    public Salad(final Long id, final String name, final String description,
                 final double price, final Size size, final int quantity) {
        super(id, name, description, price, size, quantity);
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