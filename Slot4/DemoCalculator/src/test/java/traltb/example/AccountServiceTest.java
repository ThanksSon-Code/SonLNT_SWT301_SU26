package traltb.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService();
    }

    // ==================================================
    // EMAIL TEST
    // ==================================================

    @ParameterizedTest(name = "Email hợp lệ: {0}")
    @ValueSource(strings = {
            "john@example.com",
            "alice@mail.com",
            "carol_99@domain.io"
    })
    @DisplayName("isValidEmail trả về true với email hợp lệ")
    void isValidEmail_ValidEmails_ReturnsTrue(String email) {

        // Act
        boolean result = service.isValidEmail(email);

        // Assert
        assertTrue(result);
    }

    @ParameterizedTest(name = "Email không hợp lệ: {0}")
    @CsvSource(value = {
            "bobmail.com",
            "missing@dot",
            "'@nodomain.com'",
            "' '",
            "NULL"
    }, nullValues = "NULL")
    @DisplayName("isValidEmail trả về false với email sai")
    void isValidEmail_InvalidEmails_ReturnsFalse(String email) {

        // Act
        boolean result = service.isValidEmail(email);

        // Assert
        assertFalse(result);
    }

    // ==================================================
    // PASSWORD DIRECT TEST
    // ==================================================

    @Test
    @DisplayName("Password hợp lệ → true")
    void isValidPassword_ValidPassword_ReturnsTrue() {

        // Arrange
        String password = "Abc@1234";

        // Act
        boolean result = service.isValidPassword(password);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Password không có ký tự đặc biệt → false")
    void isValidPassword_NoSpecialCharacter_ReturnsFalse() {

        // Arrange
        String password = "Abc12345";

        // Act
        boolean result = service.isValidPassword(password);

        // Assert
        assertFalse(result);
    }

    // ==================================================
    // PASSWORD CSV TEST
    // ==================================================

    @ParameterizedTest(name = "Row {index}: {0} -> {1}")
    @CsvFileSource(
            resources = "/password-data.csv",
            numLinesToSkip = 1
    )
    @DisplayName("Kiểm tra password từ file CSV")
    void isValidPassword_FromCsv(
            String password,
            boolean expected
    ) {

        // Act
        boolean actual = service.isValidPassword(password);

        // Assert
        assertEquals(expected, actual);
    }

    // ==================================================
    // REGISTER ACCOUNT CSV TEST
    // ==================================================

    @ParameterizedTest(name = "Row {index}")
    @CsvFileSource(
            resources = "/test-data.csv",
            numLinesToSkip = 1
    )
    @DisplayName("registerAccount với dữ liệu CSV")
    void registerAccount_FromCsv(
            String username,
            String password,
            String email,
            boolean expected
    ) {

        // Act
        boolean actual =
                service.registerAccount(
                        username,
                        password,
                        email
                );

        // Assert
        assertEquals(expected, actual);
    }

    // ==================================================
    // EDGE CASE TEST
    // ==================================================

    @Test
    @DisplayName("Password đúng 8 ký tự → true")
    void registerAccount_PasswordExactly8_ReturnsTrue() {

        assertTrue(
                service.registerAccount(
                        "bob",
                        "Abc@1234",
                        "bob@mail.com"
                )
        );
    }

    @Test
    @DisplayName("Password quá ngắn → false")
    void registerAccount_PasswordTooShort_ReturnsFalse() {

        assertFalse(
                service.registerAccount(
                        "bob",
                        "Ab@12",
                        "bob@mail.com"
                )
        );
    }

    @Test
    @DisplayName("All null → false")
    void registerAccount_AllNull_ReturnsFalse() {

        assertFalse(
                service.registerAccount(
                        null,
                        null,
                        null
                )
        );
    }
}