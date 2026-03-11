package Baitap04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    @DisplayName("TC01: Kiểm tra mật khẩu Mạnh")
    void testStrongPassword() {
        assertEquals("Mạnh", validator.evaluatePasswordStrength("Abc123!@"));
    }

    @Test
    @DisplayName("TC02-TC05: Kiểm tra mật khẩu Trung bình (Thiếu 1 loại ký tự)")
    void testMediumPasswords() {
        assertAll("Các trường hợp mật khẩu Trung bình",
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("abc123!@"), "Thiếu chữ hoa"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("ABC123!@"), "Thiếu chữ thường"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abcdef!@"), "Thiếu số"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abc12345"), "Thiếu ký tự đặc biệt")
        );
    }

    @Test
    @DisplayName("TC06-TC08: Kiểm tra mật khẩu Yếu")
    void testWeakPasswords() {
        assertAll("Các trường hợp mật khẩu Yếu",
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("Ab1!"), "Quá ngắn"),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("password"), "Chỉ có chữ thường"),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("ABC12345"), "Chỉ có chữ hoa và số")
        );
    }
}