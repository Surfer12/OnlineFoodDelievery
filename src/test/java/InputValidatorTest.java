

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private InputValidator<String> nameValidator;
    private InputValidator<Integer> positiveIntegerValidator;

    @BeforeEach
    void setUp() {
        nameValidator = new InputValidator<>(new NameValidator(), "Name");
        positiveIntegerValidator = new InputValidator<>(new PositiveIntegerValidator(), "Positive Integer");
    }

    @Test
    void testNameValidatorValidInput() {
        String validName = "John Doe";
        assertTrue(nameValidator.isValid(validName));
    }

    @Test
    void testNameValidatorInvalidInput() {
        String invalidName = "John123";
        assertFalse(nameValidator.isValid(invalidName));
    }

    @Test
    void testPositiveIntegerValidatorValidInput() {
        Integer validNumber = 10;
        assertTrue(positiveIntegerValidator.isValid(validNumber));
    }

    @Test
    void testPositiveIntegerValidatorInvalidInput() {
        Integer invalidNumber = -5;
        assertFalse(positiveIntegerValidator.isValid(invalidNumber));
    }

    @Test
    void testPositiveIntegerValidatorParseValidInput() {
        String validInput = "15";
        assertEquals(15, positiveIntegerValidator.parse(validInput));
    }

    @Test
    void testPositiveIntegerValidatorParseInvalidInput() {
        String invalidInput = "abc";
        assertThrows(NumberFormatException.class, () -> positiveIntegerValidator.parse(invalidInput));
    }

    @Test
    void testNameValidatorEmptyInput() {
        String emptyName = "";
        assertFalse(nameValidator.isValid(emptyName));
    }

    @Test
    void testNameValidatorNullInput() {
        String nullName = null;
        assertFalse(nameValidator.isValid(nullName));
    }

    @Test
    void testPositiveIntegerValidatorZeroInput() {
        Integer zeroNumber = 0;
        assertFalse(positiveIntegerValidator.isValid(zeroNumber));
    }

    @Test
    void testPositiveIntegerValidatorLargeInput() {
        Integer largeNumber = Integer.MAX_VALUE;
        assertTrue(positiveIntegerValidator.isValid(largeNumber));
    }

    @Test
    void testPositiveIntegerValidatorUnknownInput() {
        String unknownInput = "unknown";
        assertEquals(10, positiveIntegerValidator.parse(unknownInput));
    }

    @Test
    void testCommandLineInput_ValidName() {
        String validName = "Jane Doe";
        assertTrue(nameValidator.isValid(validName));
    }

    @Test
    void testCommandLineInput_InvalidName() {
        String invalidName = "Jane123";
        assertFalse(nameValidator.isValid(invalidName));
    }

    @Test
    void testCommandLineInput_ValidPositiveInteger() {
        Integer validNumber = 20;
        assertTrue(positiveIntegerValidator.isValid(validNumber));
    }

    @Test
    void testCommandLineInput_InvalidPositiveInteger() {
        Integer invalidNumber = -10;
        assertFalse(positiveIntegerValidator.isValid(invalidNumber));
    }

    @Test
    void testCommandLineInput_ParseValidPositiveInteger() {
        String validInput = "25";
        assertEquals(25, positiveIntegerValidator.parse(validInput));
    }

    @Test
    void testCommandLineInput_ParseInvalidPositiveInteger() {
        String invalidInput = "xyz";
        assertThrows(NumberFormatException.class, () -> positiveIntegerValidator.parse(invalidInput));
    }
}
