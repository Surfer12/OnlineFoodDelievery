package model;

import java.util.Optional;

/**
 * The ConcreteMenuItem class represents a concrete implementation of a menu item.
 * It extends the abstract MenuItem class and provides specific details for a menu item.
 */
public class ConcreteMenuItem extends MenuItem {
    private final String name;
    private final double price;
    private final boolean available;
    private final String description;
    private final String category;

    /**
     * Constructs a ConcreteMenuItem with the specified details.
     *
     * @param name        the name of the menu item
     * @param price       the price of the menu item
     * @param available   the availability status of the menu item
     * @param description the description of the menu item
     * @param category    the category of the menu item
     */
    public ConcreteMenuItem(String name, double price, boolean available, String description, String category) {
        super(null, name, description, price, category);
        this.name = name;
        this.price = price;
        this.available = available;
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
     * Gets the quantity of the menu item.
     *
     * @return the quantity of the menu item
     */
    @Override
    public int getQuantity() {
        return 1;
    }

    /**
     * Returns a string representation of the ConcreteMenuItem.
     *
     * @return a string representation of the ConcreteMenuItem
     */
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
