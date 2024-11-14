package model;

/**
 * Abstract class representing a menu item.
 */
public abstract class AbstractMenuItem extends MenuItem {
    private String name;
    private double price;
    private boolean available;
    private String description;
    private String category;

    /**
     * Constructs an AbstractMenuItem with the specified details.
     *
     * @param id          the ID of the menu item
     * @param name        the name of the menu item
     * @param description the description of the menu item
     * @param price       the price of the menu item
     * @param category    the category of the menu item
     */
    public AbstractMenuItem(Long id, String name, String description, double price, String category) {
        super(id, name, description, price, category);
        this.name = name;
        this.price = price;
        this.available = true;
        this.description = description;
        this.category = category;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return the name of the menu item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return the price of the menu item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Checks if the menu item is available.
     *
     * @return true if the menu item is available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Gets the description of the menu item.
     *
     * @return an Optional containing the description of the menu item, or an empty Optional if no description is provided
     */
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    /**
     * Gets the category of the menu item.
     *
     * @return an Optional containing the category of the menu item, or an empty Optional if no category is provided
     */
    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }

    /**
     * Sets the availability status of the menu item.
     *
     * @param available the new availability status of the menu item
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Updates the price of the menu item.
     *
     * @param price the new price of the menu item
     */
    public void updatePrice(double price) {
        this.price = price;
    }

    /**
     * Gets the details of the menu item.
     *
     * @return a string containing the details of the menu item
     */
    public String getDetails() {
        return String.format("%s - %s ($%.2f)", name, description, price);
    }

    /**
     * Calculates the total price of the menu item.
     *
     * @return the total price of the menu item
     */
    public double calculateTotal() {
        return price;
    }

    /**
     * Gets the quantity of the menu item.
     *
     * @return the quantity of the menu item
     */
    public abstract int getQuantity();
}
