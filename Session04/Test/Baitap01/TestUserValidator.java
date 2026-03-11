package Baitap01;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserValidator {
    @Test
    @DisplayName("Test tên đăng nhập đúng")
    public void testUserValidator() {
        String userName = "DoKhacQuyen";
        boolean act = UserValidator.isValidUserName(userName);
        assertTrue(act);
    }
    @Test
    @DisplayName("Test tên đăng nhập ngắn")
    public void testUserValidator1() {
        String userName = "123";
        boolean act = UserValidator.isValidUserName(userName);
        assertFalse(act);
    }
    @Test
    @DisplayName("Test tên đăng nhập chứa khoảng trắng")
    public void testUserValidator2() {
        String userName = "user name";
        boolean act = UserValidator.isValidUserName(userName);
        assertFalse(act);
    }


}
