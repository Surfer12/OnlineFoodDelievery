package main;

import ConsoleInputHandler.ConsoleInputHandler;
import ConsoleInputValidator.InputValidator;
import ConsoleInputValidator.NameValidator;
import ConsoleInputValidator.PositiveIntegerValidator;
import validation.EmailValidator;
import validation.MenuItemValidator;
import model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandLineApplicationTest {

    @Mock
    private Scanner mockScanner;

    @Mock
    private ConsoleInputHandler<String> mockNameInputHandler;

    @Mock
    private ConsoleInputHandler<Integer> mockAgeInputHandler;

    @Mock
    private ConsoleInputHandler<String> mockEmailInputHandler;

    @Mock
    private ConsoleInputHandler<MenuItem> mockMenuItemInputHandler;

    @Mock
    private ConsoleInputHandler<MenuItem> mockOrderItemInputHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNameInput() {
        when(mockScanner.nextLine()).thenReturn("John Doe");
        NameValidator nameValidator = new NameValidator();
        InputValidator<String> nameInputValidator = new InputValidator<>(nameValidator, "Name");
        ConsoleInputHandler<String> nameInputHandler = new ConsoleInputHandler<>(mockScanner, nameInputValidator);

        String result = nameInputHandler.getInput("Enter your name: ");
        assertEquals("John Doe", result);
    }

    @Test
    public void testAgeInput() {
        when(mockScanner.nextLine()).thenReturn("25");
        PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        InputValidator<Integer> positiveIntegerInputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");
        ConsoleInputHandler<Integer> positiveIntegerInputHandler = new ConsoleInputHandler<>(mockScanner, positiveIntegerInputValidator);

        Integer result = positiveIntegerInputHandler.getInput("Enter your age (positive integer): ");
        assertEquals(25, result);
    }

    @Test
    public void testEmailInput() {
        when(mockScanner.nextLine()).thenReturn("john.doe@example.com");
        EmailValidator emailValidator = new EmailValidator();
        InputValidator<String> emailInputValidator = new InputValidator<>(emailValidator, "Email");
        ConsoleInputHandler<String> emailInputHandler = new ConsoleInputHandler<>(mockScanner, emailInputValidator);

        String result = emailInputHandler.getInput("Enter your email: ");
        assertEquals("john.doe@example.com", result);
    }

    @Test
    public void testMenuItemInput() {
        when(mockScanner.nextLine()).thenReturn("Pizza,12.99,true");
        MenuItemValidator menuItemValidator = new MenuItemValidator();
        InputValidator<MenuItem> menuItemInputValidator = new InputValidator<>(menuItemValidator, "Menu Item");
        ConsoleInputHandler<MenuItem> menuItemInputHandler = new ConsoleInputHandler<>(mockScanner, menuItemInputValidator);

        MenuItem result = menuItemInputHandler.getInput("Enter menu item (name,price,available): ");
        assertEquals("Pizza", result.getName());
        assertEquals(12.99, result.getPrice());
        assertTrue(result.isAvailable());
    }

    @Test
    public void testOrderItemInput() {
        when(mockScanner.nextLine()).thenReturn("1,2");
        MenuItemValidator orderItemValidator = new MenuItemValidator();
        InputValidator<MenuItem> orderItemInputValidator = new InputValidator<>(orderItemValidator, "Order Item");
        ConsoleInputHandler<MenuItem> orderItemInputHandler = new ConsoleInputHandler<>(mockScanner, orderItemInputValidator);

        MenuItem result = orderItemInputHandler.getInput("Enter order item (menuItemId,quantity): ");
        assertEquals(1, result.getId());
        assertEquals(2, result.getQuantity());
    }
}
