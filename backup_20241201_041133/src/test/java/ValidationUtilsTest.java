

import CustomException.ValidationException;
import payment.Payment;
import java.util.Arrays;
import java.util.List;

public class ValidationUtilsTest {

    @Test
    public void testValidateCustomerId_ValidId() {
        Assertions.assertDoesNotThrow(() -> ValidationUtils.validateCustomerId(1L));
    }

    @Test
    public void testValidateCustomerId_InvalidId() {
        ValidationException customException = Assertions.assertThrows(ValidationException.class, () -> ValidationUtils.validateCustomerId(-1L));
        Assertions.assertEquals("Invalid customer ID", customException.getMessage());
    }

    @Test
    public void testValidateItems_ValidItems() {
        List<String> items = Arrays.asList("Item1", "Item2");
        assertDoesNotThrow(() -> ValidationUtils.validateItems(items, 5));
    }

    @Test
    public void testValidateItems_EmptyItems() {
        List<String> items = Arrays.asList();
        ValidationException customException = assertThrows(ValidationException.class, () -> ValidationUtils.validateItems(items, 5));
        assertEquals("List cannot be null or empty", customException.getMessage());
    }

    @Test
    public void testValidateItems_ExceedsMaxItems() {
        List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5", "Item6");
        ValidationException customException = assertThrows(ValidationException.class, () -> ValidationUtils.validateItems(items, 5));
        assertEquals("Exceeds maximum item limit of 5", customException.getMessage());
    }

    @Test
    public void testValidateAmount_ValidAmount() {
        assertDoesNotThrow(() -> ValidationUtils.validateAmount(100.0, 500.0));
    }

    @Test
    public void testValidateAmount_InvalidAmount() {
        ValidationException customException = assertThrows(ValidationException.class, () -> ValidationUtils.validateAmount(-100.0, 500.0));
        assertEquals("Amount must be greater than zero", customException.getMessage());
    }

    @Test
    public void testValidateAmount_ExceedsMaxAmount() {
        ValidationException customException = assertThrows(ValidationException.class, () -> ValidationUtils.validateAmount(600.0, 500.0));
        assertEquals("Amount exceeds maximum limit of 500.0", customException.getMessage());
    }

    @Test
    public void testValidatePayment_ValidPayment() {
        Payment payment = new Payment(1L, 1L, "CREDIT_CARD", 100.0, true);
        assertDoesNotThrow(() -> ValidationUtils.validatePayment(payment));
    }

    @Test
    public void testValidatePayment_InvalidPayment() {
        Payment payment = new Payment(1L, 1L, "CREDIT_CARD", 100.0, false);
        ValidationException customException = assertThrows(ValidationException.class, () -> ValidationUtils.validatePayment(payment));
        assertEquals("Payment must be processed", customException.getMessage());
    }
}
