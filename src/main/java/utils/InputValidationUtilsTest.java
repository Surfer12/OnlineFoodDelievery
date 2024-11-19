package utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import CustomException.ValidationException;

public class InputValidationUtilsTest {

    @Test
    public void testValidateTextInput_ValidInput() {
        assertDoesNotThrow(() -> InputValidationUtils.validateTextInput("Valid Input", "Field"));
    }

    @Test
    public void testValidateTextInput_NullInput() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validateTextInput(null, "Field"));
        assertEquals("Field cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateTextInput_EmptyInput() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validateTextInput("", "Field"));
        assertEquals("Field cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testValidateNumericInput_ValidInput() {
        assertDoesNotThrow(() -> InputValidationUtils.validateNumericInput("123.45", "Field"));
    }

    @Test
    public void testValidateNumericInput_InvalidInput() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validateNumericInput("abc", "Field"));
        assertEquals("Field must be a valid number", exception.getMessage());
    }

    @Test
    public void testValidatePositiveNumber_ValidInput() {
        assertDoesNotThrow(() -> InputValidationUtils.validatePositiveNumber(10.0, "Field"));
    }

    @Test
    public void testValidatePositiveNumber_InvalidInput() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validatePositiveNumber(-5.0, "Field"));
        assertEquals("Field must be greater than zero", exception.getMessage());
    }

    @Test
    public void testValidateEmailFormat_ValidEmail() {
        assertDoesNotThrow(() -> InputValidationUtils.validateEmailFormat("test@example.com"));
    }

    @Test
    public void testValidateEmailFormat_InvalidEmail() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validateEmailFormat("invalid-email"));
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testValidatePhoneNumber_ValidPhoneNumber() {
        assertDoesNotThrow(() -> InputValidationUtils.validatePhoneNumber("+1234567890"));
    }

    @Test
    public void testValidatePhoneNumber_InvalidPhoneNumber() {
        ValidationException exception = assertThrows(ValidationException.class, () -> InputValidationUtils.validatePhoneNumber("12345"));
        assertEquals("Invalid phone number format", exception.getMessage());
    }
}
