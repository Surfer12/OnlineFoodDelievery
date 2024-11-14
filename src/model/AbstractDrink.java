package model;

/**
 * Abstract class representing a drink menu item.
 */
public abstract class AbstractDrink extends AbstractMenuItem {
    private Size size;
    private int quantity;

    /**
     * Constructs an AbstractDrink with the specified details.
     *
     * @param id          the ID of the drink
     * @param name        the name of the drink
     * @param description the description of the drink
     * @param price       the price of the drink
     * @param category    the category of the drink
     * @param size        the size of the drink
     * @param quantity    the quantity of the drink
     */
    public AbstractDrink(Long id, String name, String description, double price, String category, Size size, int quantity) {
        super(id, name, description, price, category);
        this.size = size;
        this.quantity = quantity;
    }

    /**
     * Gets the size of the drink.
     *
     * @return the size of the drink
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the drink.
     *
     * @param size the new size of the drink
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the quantity of the drink.
     *
     * @return the quantity of the drink
     */
    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the total price of the drink.
     *
     * @return the total price of the drink
     */
    @Override
    public double calculateTotal() {
        return getPrice() * quantity;
    }

    /**
     * Gets the details of the drink.
     *
     * @return a string containing the details of the drink
     */
    @Override
    public String getDetails() {
        return String.format("%s - %s ($%.2f)", getName(), getDescription().orElse(""), getPrice());
    }
}
