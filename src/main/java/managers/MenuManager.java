package managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.MenuItem;
import validation.ConsoleInputHandler;

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

    public List<MenuItem> selectMenuItems(Scanner scanner, ConsoleInputHandler<Integer> positiveIntegerHandler) {
        List<MenuItem> selectedItems = new ArrayList<>();
        displayMenu();
        
        System.out.println("\nSelect menu items (enter 0 to finish):");
        while (true) {
            System.out.print("Enter menu item ID: ");
            try {
                Integer itemId = positiveIntegerHandler.handleInput(scanner, "Menu item ID: ");
                if (itemId == null || itemId == 0) {
                    break;
                }
                
                MenuItem item = getMenuItemById(itemId.longValue());
                if (item != null) {
                    selectedItems.add(item);
                    System.out.println("Added: " + item.getName());
                } else {
                    System.out.println("Item not found.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        
        return selectedItems;
    }
}