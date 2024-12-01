package services;
import java.util.ArrayList;
import java.util.List;

import model.MenuItem;

public class MenuServiceImpl implements MenuService {
    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuServiceImpl() {
        // Initialize menu items 
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(this.menuItems);
    }

    // ...methods to add, remove, update menu items...
}
    }    // ...methods to add, remove, update menu items...}
