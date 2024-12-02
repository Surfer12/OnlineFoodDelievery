package app;

import java.util.logging.Logger;

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
