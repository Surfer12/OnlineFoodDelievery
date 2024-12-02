package managers;

import java.util.ArrayList;
import java.util.List;

import model.MenuItem;

public class MenuManager {
    private final List<MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem(final MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return new ArrayList<>(this.menuItems);
    }

    public MenuItem getMenuItemById(final Long id) {
        return this.menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void displayMenu() {
        System.out.println("--- Menu Items ---");
        for (final MenuItem item : this.menuItems) {
            System.out.printf("ID: %d, Name: %s, Price: $%.2f%n",
                    item.getId(), item.getName(), item.getPrice());
        }
    }
}