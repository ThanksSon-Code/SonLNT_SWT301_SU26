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

    // ---------- isValidEmail ----------

    @ParameterizedTest(name = "Email hợp lệ: {0}")
    @ValueSource(strings = {
            "john@example.com",
            "alice.b@mail.co.uk",
            "carol_99@domain.io"
    })
    @DisplayName("isValidEmail trả về true với email đúng định dạng")
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
    @DisplayName("isValidEmail trả về false với email sai định dạng")
    void isValidEmail_InvalidEmails_ReturnsFalse(String email) {

        assertFalse(service.isValidEmail(email));
    }

    // ---------- registerAccount ----------

    @ParameterizedTest(name = "Row {index}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("registerAccount với dữ liệu CSV")
    void registerAccount_FromCsv(
            String username,
            String password,
            String email,
            boolean expected
    ) {

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertEquals(expected, actual);
    }

    // ---------- Edge Cases ----------

    @Test
    @DisplayName("Password đúng 6 ký tự → false")
    void registerAccount_PasswordExactly6_ReturnsFalse() {

        boolean actual =
                service.registerAccount(
                        "bob",
                        "abcdef",
                        "bob@mail.com"
                );

        assertFalse(actual);
    }

    @Test
    @DisplayName("Password đúng 7 ký tự → true")
    void registerAccount_PasswordExactly7_ReturnsTrue() {

        assertTrue(
                service.registerAccount(
                        "bob",
                        "abcdefg",
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