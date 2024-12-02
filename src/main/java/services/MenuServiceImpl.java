package services;

import java.util.ArrayList;
import java.util.List;

import model.MenuItem;
import model.Hamburger;
import model.Fries;
import model.Drink;
import model.Size;

public class MenuServiceImpl implements MenuService {
    private final List<MenuItem> menuItems;

    public MenuServiceImpl() {
        this.menuItems = List.of(
            new Hamburger(1L, "Hamburger", "A simple hamburger", 5.99, Size.MEDIUM, 1),
            new Fries(2L, "Fries", "A side of fries", 2.99, Size.MEDIUM, 1),
            new Drink(3L, "Soft Drink", "A refreshing beverage", 1.99, Size.MEDIUM, 1)
            // Add more items if needed
        );
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== Menu ===");
        for (int i = 0; i < this.menuItems.size(); i++) {
            MenuItem item = this.menuItems.get(i);
            System.out.printf("%d. %s - $%.2f\n",
                    i + 1,
                    item.getName(),
                    item.getPrice());
        }
    }

    @Override
    public List<MenuItem> getMenu() {
        return new ArrayList<>(this.menuItems);
    }

    @Override
    public MenuItem getMenuItemByIndex(int index) {
        if (index > 0 && index <= this.menuItems.size()) {
            return this.menuItems.get(index - 1);
        }
        throw new IllegalArgumentException("Invalid menu item index");
    }

    @Override
    public int getMenuSize() {
        return this.menuItems.size();
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(this.menuItems);
    }
}
