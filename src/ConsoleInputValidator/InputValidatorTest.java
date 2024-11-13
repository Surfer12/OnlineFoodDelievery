package ConsoleInputValidator;

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
}
