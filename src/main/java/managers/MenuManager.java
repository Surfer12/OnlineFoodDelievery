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
}