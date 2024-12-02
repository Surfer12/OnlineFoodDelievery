package integration;


import app.DeliverySystemCLI;
import managers.DriverManager;
import managers.MenuManager;
import managers.OrderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliverySystemIntegrationTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private DeliverySystemCLI cli;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testCompleteOrderFlow() {
        // Simulate user input for complete order flow
        String input = String.join("\n",
            "1",        // Place order
            "1",        // Select first menu item
            "2",        // Quantity
            "0",        // Finish order
            "Y",        // Assign driver
            "1",        // Select first driver
            "7"         // Exit
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        cli = new DeliverySystemCLI(scanner, new MenuManager(), new OrderManager(), new DriverManager());
        
        cli.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Order placed successfully"));
        assertTrue(output.contains("Driver assigned successfully"));
    }

    @Test
    void testDriverAssignmentAndRating() {
        // Simulate driver assignment and rating flow
        String input = String.join("\n",
            "5",        // Manage drivers
            "2",        // Assign driver
            "1",        // Order ID
            "1",        // Select driver
            "6",        // Rate driver
            "1",        // Order ID
            "5",        // 5-star rating
            "7"         // Exit
        );

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        cli = new DeliverySystemCLI(scanner, new MenuManager(), new OrderManager(), new DriverManager());
        
        cli.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Driver assigned successfully"));
        assertTrue(output.contains("Thank you for your feedback"));
    }
}
