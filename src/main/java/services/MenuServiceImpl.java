package services;

import java.util.ArrayList;
import java.util.List;

import model.MenuItem;

public class MenuServiceImpl implements MenuService {
    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuServiceImpl() {
        // Initialize menu items will be handled by the implementing class
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== Menu ===");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.printf("%d. %s - $%.2f\n",
                    i + 1,
                    item.getName(),
                    item.getPrice());
        }
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(this.menuItems);
    }

    // ...methods to add, remove, update menu items...
}
    }    // ...methods to add, remove, update menu items...}
