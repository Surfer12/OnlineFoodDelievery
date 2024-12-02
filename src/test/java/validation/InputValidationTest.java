package validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import CustomException.ValidationException;

class InputValidationTest {

    @Test
    void validateTextInput_ValidInput_NoException() {
        assertDoesNotThrow(() -> InputValidationUtils.validateTextInput("valid input", "Test Field"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\t", "\n" })
    void validateTextInput_EmptyOrWhitespace_ThrowsException(String input) {
        assertThrows(ValidationException.class, () -> InputValidationUtils.validateTextInput(input, "Test Field"));
    }

    @Test
    void validateEmailFormat_ValidEmail_NoException() {
        assertDoesNotThrow(() -> InputValidationUtils.validateEmailFormat("test@example.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid", "test@", "@example.com", "test@.com" })
    void validateEmailFormat_InvalidEmail_ThrowsException(String email) {
        assertThrows(ValidationException.class, () -> InputValidationUtils.validateEmailFormat(email));
    }

    @Test
    void validatePhoneNumber_ValidNumber_NoException() {
        assertDoesNotThrow(() -> InputValidationUtils.validatePhoneNumber("+1234567890"));
    }
}
