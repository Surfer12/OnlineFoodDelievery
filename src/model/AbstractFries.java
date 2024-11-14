package model;

/**
 * Abstract class representing a fries menu item.
 */
public abstract class AbstractFries extends AbstractMenuItem {
    private Size size;
    private boolean seasoned;

    /**
     * Constructs an AbstractFries object with the specified details.
     *
     * @param id          the ID of the fries
     * @param name        the name of the fries
     * @param description the description of the fries
     * @param basePrice   the base price of the fries
     * @param size        the size of the fries
     * @param seasoned    whether the fries are seasoned
     */
    public AbstractFries(Long id, String name, String description, double basePrice, Size size, boolean seasoned) {
        super(id, name, description, basePrice, "FRIES");
        this.size = size;
        this.seasoned = seasoned;
    }

    /**
     * Returns the size of the fries.
     *
     * @return the size of the fries
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the fries.
     *
     * @param size the new size of the fries
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Checks if the fries are seasoned.
     *
     * @return true if the fries are seasoned, false otherwise
     */
    public boolean isSeasoned() {
        return seasoned;
    }

    /**
     * Sets the seasoning status of the fries.
     *
     * @param seasoned the new seasoning status of the fries
     */
    public void setSeasoned(boolean seasoned) {
        this.seasoned = seasoned;
    }

    /**
     * Calculates the total price of the fries.
     *
     * @return the total price of the fries
     */
    @Override
    public double calculateTotal() {
        double total = getPrice() * size.getPriceMultiplier();
        return seasoned ? total + 0.50 : total;
    }

    /**
     * Gets the quantity of the fries.
     *
     * @return the quantity of the fries
     */
    @Override
    public abstract int getQuantity();
}
