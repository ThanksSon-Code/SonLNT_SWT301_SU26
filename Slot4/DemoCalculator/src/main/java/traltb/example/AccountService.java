package traltb.example;

import java.util.regex.Pattern;

public class AccountService {

    // Regex email
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            );

    // Regex password:
    // - 8 -> 12 ký tự
    // - ít nhất 1 chữ hoa
    // - ít nhất 1 chữ thường
    // - ít nhất 1 ký tự đặc biệt
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,12}$"
            );

    // ---------- EMAIL ----------

    public boolean isValidEmail(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        return EMAIL_PATTERN.matcher(email).matches();
    }

    // ---------- PASSWORD ----------

    public boolean isValidPassword(String password) {

        if (password == null || password.isBlank()) {
            return false;
        }

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // ---------- REGISTER ACCOUNT ----------

    public boolean registerAccount(
            String username,
            String password,
            String email
    ) {

        if (username == null || username.isBlank()) {
            return false;
        }

        if (!isValidPassword(password)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        return true;
    }
}