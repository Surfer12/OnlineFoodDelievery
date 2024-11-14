package ConsoleInputHandler;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
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

    @Mock
    private InputValidator<LocalDate> mockDateValidator;

    @Mock
    private InputValidator<Enum<?>> mockEnumValidator;

    @Mock
    private InputValidator<Integer> mockRangeValidator;

    @Mock
    private InputValidator<String> mockPatternValidator;

    @MockA
    private InputValidator<Object> mockCustomObjectValidator;

    private ConsoleInputHandler<String> consoleInputHandler;
    private ConsoleInputHandler<LocalDate> dateInputHandler;
    private ConsoleInputHandler<Enum<?>> enumInputHandler;
    private ConsoleInputHandler<Integer> rangeInputHandler;
    private ConsoleInputHandler<String> patternInputHandler;
    private ConsoleInputHandler<Object> customObjectInputHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consoleInputHandler = new ConsoleInputHandler<>(mockScanner, mockInputValidator);
        dateInputHandler = new ConsoleInputHandler<>(mockScanner, mockDateValidator);
        enumInputHandler = new ConsoleInputHandler<>(mockScanner, mockEnumValidator);
        rangeInputHandler = new ConsoleInputHandler<>(mockScanner, mockRangeValidator);
        patternInputHandler = new ConsoleInputHandler<>(mockScanner, mockPatternValidator);
        customObjectInputHandler = new ConsoleInputHandler<>(mockScanner, mockCustomObjectValidator);
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

    @Test
    public void testDateValidation_ValidInput() {
        LocalDate validDate = LocalDate.of(2023, 1, 1);
        when(mockScanner.nextLine()).thenReturn("2023-01-01");
        when(mockDateValidator.parse("2023-01-01")).thenReturn(validDate);
        when(mockDateValidator.isValid(validDate)).thenReturn(true);

        LocalDate result = dateInputHandler.getInput("Enter date: ");

        assertEquals(validDate, result);
        verify(mockScanner).nextLine();
        verify(mockDateValidator).parse("2023-01-01");
        verify(mockDateValidator).isValid(validDate);
    }

    @Test
    public void testEnumValidation_ValidInput() {
        TestEnum validEnum = TestEnum.VALUE1;
        when(mockScanner.nextLine()).thenReturn("VALUE1");
        when(mockEnumValidator.parse("VALUE1")).thenReturn(validEnum);
        when(mockEnumValidator.isValid(validEnum)).thenReturn(true);

        Enum<?> result = enumInputHandler.getInput("Enter enum value: ");

        assertEquals(validEnum, result);
        verify(mockScanner).nextLine();
        verify(mockEnumValidator).parse("VALUE1");
        verify(mockEnumValidator).isValid(validEnum);
    }

    @Test
    public void testRangeValidation_ValidInput() {
        when(mockScanner.nextLine()).thenReturn("5");
        when(mockRangeValidator.parse("5")).thenReturn(5);
        when(mockRangeValidator.isValid(5)).thenReturn(true);

        Integer result = rangeInputHandler.getInput("Enter a number between 1 and 10: ");

        assertEquals(5, result);
        verify(mockScanner).nextLine();
        verify(mockRangeValidator).parse("5");
        verify(mockRangeValidator).isValid(5);
    }

    @Test
    public void testPatternValidation_ValidInput() {
        when(mockScanner.nextLine()).thenReturn("abc123");
        when(mockPatternValidator.parse("abc123")).thenReturn("abc123");
        when(mockPatternValidator.isValid("abc123")).thenReturn(true);

        String result = patternInputHandler.getInput("Enter a string matching the pattern: ");

        assertEquals("abc123", result);
        verify(mockScanner).nextLine();
        verify(mockPatternValidator).parse("abc123");
        verify(mockPatternValidator).isValid("abc123");
    }

    @Test
    public void testCustomObjectValidation_ValidInput() {
        Object validObject = new Object();
        when(mockScanner.nextLine()).thenReturn("valid object");
        when(mockCustomObjectValidator.parse("valid object")).thenReturn(validObject);
        when(mockCustomObjectValidator.isValid(validObject)).thenReturn(true);

        Object result = customObjectInputHandler.getInput("Enter a custom object: ");

        assertEquals(validObject, result);
        verify(mockScanner).nextLine();
        verify(mockCustomObjectValidator).parse("valid object");
        verify(mockCustomObjectValidator).isValid(validObject);
    }

    private enum TestEnum {
        VALUE1, VALUE2
    }
}
