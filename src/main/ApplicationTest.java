package main;

import ConsoleInputHandler.ConsoleInputHandler;
import ConsoleInputValidator.InputValidator;
import ConsoleInputValidator.PositiveIntegerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    @Mock
    private Scanner mockScanner;

    @Mock
    private ConsoleInputHandler<Integer> mockInputHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPositiveIntegerInput() {
        when(mockScanner.nextLine()).thenReturn("5");
        PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");
        ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(mockScanner, inputValidator);

        Integer result = inputHandler.getInput("Enter a positive integer: ");
        assertEquals(5, result);
    }

    @Test
    public void testInvalidPositiveIntegerInput() {
        when(mockScanner.nextLine()).thenReturn("-1", "10");
        PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");
        ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(mockScanner, inputValidator);

        Integer result = inputHandler.getInput("Enter a positive integer: ");
        assertEquals(10, result);
    }

    @Test
    public void testExceptionDuringParsing() {
        when(mockScanner.nextLine()).thenReturn("invalid", "5");
        PositiveIntegerValidator positiveIntegerValidator = new PositiveIntegerValidator();
        InputValidator<Integer> inputValidator = new InputValidator<>(positiveIntegerValidator, "Positive Integer");
        ConsoleInputHandler<Integer> inputHandler = new ConsoleInputHandler<>(mockScanner, inputValidator);

        Integer result = inputHandler.getInput("Enter a positive integer: ");
        assertEquals(5, result);
    }
}
