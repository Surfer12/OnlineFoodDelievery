package services;

import model.MenuItem;

import java.util.List;

public interface MenuService {
    void displayMenu();

    List<MenuItem> getMenu();

    MenuItem getMenuItemByIndex(int index);

    int getMenuSize();

    List<MenuItem> getAllMenuItems();
}