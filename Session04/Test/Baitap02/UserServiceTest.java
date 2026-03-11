package Baitap02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    @DisplayName("Kiểm tra giá trị biên hợp lệ (đủ 18 tuổi)")
    void checkRegistrationAge() {
        int age = 18;
        boolean very = true;
        boolean act = UserService.checkRegistrationAge(age);
        assertEquals(very,act);
    }
    @Test
    @DisplayName("Kiểm tra nhỏ hơn tuổi yêu cầu")
    void checkRegistrationAge1() {
        int age = 16;
        boolean very = false;
        boolean act = UserService.checkRegistrationAge(age);
        assertEquals(very,act);
    }
    @Test
    @DisplayName("Kiểm tra dữ liệu không hợp lệ (tuổi âm)")
    void checkRegistrationAge2() {
        int age = -1;
        assertThrows(IllegalArgumentException.class,()->{UserService.checkRegistrationAge(age);});
    }



}