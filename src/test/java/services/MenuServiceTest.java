package services;

import model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.impl.MenuServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        menuService = new MenuServiceImpl();
    }

    @Test
    void getAllMenuItems_ReturnsNonEmptyList() {
        List<MenuItem> items = menuService.getAllMenuItems();
        assertFalse(items.isEmpty());
    }

    @Test
    void getMenuItemByIndex_ValidIndex_ReturnsItem() {
        MenuItem item = menuService.getMenuItemByIndex(1);
        assertNotNull(item);
    }

    @Test
    void getMenuItemByIndex_InvalidIndex_ReturnsNull() {
        MenuItem item = menuService.getMenuItemByIndex(-1);
        assertNull(item);
    }
}

