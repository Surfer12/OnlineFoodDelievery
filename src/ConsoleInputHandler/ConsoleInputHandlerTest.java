package ConsoleInputHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ConsoleInputValidator.InputValidator;

public class ConsoleInputHandlerTest {

    @Mock
    private Scanner mockScanner;

    @Mock
    private InputValidator<String> mockInputValidator;

    private ConsoleInputHandler<String> consoleInputHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleInputHandler = new ConsoleInputHandler<>(mockScanner, mockInputValidator);
    }

    @Test
    public void testGetInput_ValidInput() {
        when(mockScanner.nextLine()).thenReturn("valid input");
        when(mockInputValidator.parse("valid input")).thenReturn("valid input");
        when(mockInputValidator.isValid("valid input")).thenReturn(true);

        String result = consoleInputHandler.getInput("Enter input: ");

        assertEquals("valid input", result);
        verify(mockScanner).nextLine();
        verify(mockInputValidator).parse("valid input");
        verify(mockInputValidator).isValid("valid input");
    }

    @Test
    public void testGetInput_InvalidInput() {
        when(mockScanner.nextLine()).thenReturn("invalid input", "valid input");
        when(mockInputValidator.parse("invalid input")).thenReturn("invalid input");
        when(mockInputValidator.isValid("invalid input")).thenReturn(false);
        when(mockInputValidator.parse("valid input")).thenReturn("valid input");
        when(mockInputValidator.isValid("valid input")).thenReturn(true);

        String result = consoleInputHandler.getInput("Enter input: ");

        assertEquals("valid input", result);
        verify(mockScanner, times(2)).nextLine();
        verify(mockInputValidator, times(2)).parse(anyString());
        verify(mockInputValidator, times(2)).isValid(anyString());
    }

    @Test
    public void testGetInput_ExceptionDuringParsing() {
        when(mockScanner.nextLine()).thenReturn("invalid input", "valid input");
        when(mockInputValidator.parse("invalid input")).thenThrow(new RuntimeException("Parsing error"));
        when(mockInputValidator.parse("valid input")).thenReturn("valid input");
        when(mockInputValidator.isValid("valid input")).thenReturn(true);

        String result = consoleInputHandler.getInput("Enter input: ");

        assertEquals("valid input", result);
        verify(mockScanner, times(2)).nextLine();
        verify(mockInputValidator, times(2)).parse(anyString());
        verify(mockInputValidator).isValid("valid input");
    }

    @Test
    public void testGetMultipleInputs_ValidAndInvalidInputs() {
        when(mockScanner.nextLine()).thenReturn("valid input 1", "invalid input", "valid input 2", "stop");
        when(mockInputValidator.parse("valid input 1")).thenReturn("valid input 1");
        when(mockInputValidator.isValid("valid input 1")).thenReturn(true);
        when(mockInputValidator.parse("invalid input")).thenReturn("invalid input");
        when(mockInputValidator.isValid("invalid input")).thenReturn(false);
        when(mockInputValidator.parse("valid input 2")).thenReturn("valid input 2");
        when(mockInputValidator.isValid("valid input 2")).thenReturn(true);

        List<String> result = consoleInputHandler.getMultipleInputs("Enter input: ", "stop");

        assertEquals(2, result.size());
        assertTrue(result.contains("valid input 1"));
        assertTrue(result.contains("valid input 2"));
        verify(mockScanner, times(4)).nextLine();
        verify(mockInputValidator, times(4)).parse(anyString());
        verify(mockInputValidator, times(3)).isValid(anyString());
    }
}
