package app;

/**
 * Represents a menu item in the Online Food Delivery System.
 * 
 * This class encapsulates the essential properties of a food item:
 * - Name of the item
 * - Price of the item
 * 
 * Implements immutability to ensure data integrity after creation.
 * 
 * Aligns with project requirements for menu item management.
 */
public class MenuItem {
    // Immutable fields to ensure data consistency
    private final String name; // Name of the menu item
    private final double price; // Price of the menu item

    /**
     * Constructor to create a new menu item.
     * 
     * @param name  The name of the menu item (e.g., "Hamburger", "Fries")
     * @param price The price of the menu item
     */
    public MenuItem(final String name, final double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Retrieves the name of the menu item.
     * 
     * @return The name of the menu item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the price of the menu item.
     * 
     * @return The price of the menu item
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Provides a string representation of the menu item.
     * 
     * Demonstrates polymorphism by overriding toString() method.
     * Formats the item with its name and price.
     * 
     * @return A formatted string representing the menu item
     */
    @Override
    public String toString() {
        return this.name + " - $" + String.format("%.2f", this.price);
    }
}