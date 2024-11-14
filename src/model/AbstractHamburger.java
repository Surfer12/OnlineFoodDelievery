package model;

/**
 * Abstract class representing a hamburger menu item.
 */
public abstract class AbstractHamburger extends AbstractMenuItem {
    private boolean hasCheese;
    private boolean hasBacon;

    /**
     * Constructs an AbstractHamburger with the specified details.
     *
     * @param id          the ID of the hamburger
     * @param name        the name of the hamburger
     * @param description the description of the hamburger
     * @param price       the price of the hamburger
     * @param category    the category of the hamburger
     * @param hasCheese   whether the hamburger has cheese
     * @param hasBacon    whether the hamburger has bacon
     */
    public AbstractHamburger(Long id, String name, String description, double price, String category, boolean hasCheese, boolean hasBacon) {
        super(id, name, description, price, category);
        this.hasCheese = hasCheese;
        this.hasBacon = hasBacon;
    }

    /**
     * Checks if the hamburger has cheese.
     *
     * @return true if the hamburger has cheese, false otherwise
     */
    public boolean hasCheese() {
        return hasCheese;
    }

    /**
     * Checks if the hamburger has bacon.
     *
     * @return true if the hamburger has bacon, false otherwise
     */
    public boolean hasBacon() {
        return hasBacon;
    }

    /**
     * Sets whether the hamburger has cheese.
     *
     * @param hasCheese the new cheese status of the hamburger
     */
    public void setHasCheese(boolean hasCheese) {
        this.hasCheese = hasCheese;
    }

    /**
     * Sets whether the hamburger has bacon.
     *
     * @param hasBacon the new bacon status of the hamburger
     */
    public void setHasBacon(boolean hasBacon) {
        this.hasBacon = hasBacon;
    }

    /**
     * Calculates the total price of the hamburger.
     *
     * @return the total price of the hamburger
     */
    @Override
    public double calculateTotal() {
        double total = getPrice();
        if (hasCheese) {
            total += 0.50;
        }
        if (hasBacon) {
            total += 1.00;
        }
        return total;
    }
}
