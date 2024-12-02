package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import builder.OrderBuilder;
import factory.MenuItemFactory;
import model.Driver;
import model.MenuItem;
import model.Order;
import model.Size;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(final String[] args) {
        try {
            DeliverySystemCLI cli = new DeliverySystemCLI();
            cli.start();
        } catch (Exception e) {
            logger.severe("Application failed to start: " + e.getMessage());
            System.err.println("Failed to start application: " + e.getMessage());
        }
    }
}
