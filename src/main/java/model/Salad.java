package model;

public class Salad extends ConcreteMenuItem {
    public Salad(final Long id, final String name, final String description,
            final double price, final Size size, final int quantity) {
        super(id, name, description, price, size, quantity);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public Size getSize() {
        return super.getSize();
    }

    @Override
    public int getQuantity() {
        return super.getQuantity();
    }

    @Override
    public Long getId() {
        return super.getId();
    }
}