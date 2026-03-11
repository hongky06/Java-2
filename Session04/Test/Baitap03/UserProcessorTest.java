package Baitap03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserProcessorTest {
    private UserProcessor processor;
    @BeforeEach
    void setUp() {
        processor = new UserProcessor();
    }

    @Test
    @DisplayName("Kiểm tra email hợp lệ có ký tự @ và tên miền")
    void testValidEmail() {
        String input = "user@gmail.com";
        String result = processor.processEmail(input);
        assertEquals("user@gmail.com", result, "Email hợp lệ phải được giữ nguyên");
    }

    @Test
    @DisplayName("Kiểm tra email thiếu ký tự @")
    void testEmailMissingAtSign() {
        String input = "usergmail.com";
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(input);
        }, "Phải ném ngoại lệ khi thiếu ký tự @");
    }

    @Test
    @DisplayName("Kiểm tra email có @ nhưng không có tên miền")
    void testEmailMissingDomain() {
        String input = "user@";
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(input);
        }, "Phải ném ngoại lệ khi thiếu tên miền sau @");
    }

    @Test
    @DisplayName("Kiểm tra chuẩn hóa email (chuyển về lowercase)")
    void testEmailNormalization() {
        String input = "Example@Gmail.com";
        String result = processor.processEmail(input);
        assertEquals("example@gmail.com", result, "Email phải được chuyển về dạng chữ thường");
    }
}