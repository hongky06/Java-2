package Baitap06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    private ProfileService service;
    private User currentUser;
    private List<User> allUsers;

    @BeforeEach
    void setUp() {
        service = new ProfileService();
        allUsers = new ArrayList<>();
        UserProfile currentProfile = new UserProfile("quinn@ptit.edu.vn", LocalDate.of(2006, 4, 9));
        currentUser = new User("U001", currentProfile);
        UserProfile otherProfile = new UserProfile("dekay@gmail.com", LocalDate.of(2000, 1, 1));
        User otherUser = new User("U002", otherProfile);

        allUsers.add(currentUser);
        allUsers.add(otherUser);
    }

    @Test
    @DisplayName("Cập nhật hồ sơ bình thường với thông tin hợp lệ")
    void testUpdateSuccess() {
        UserProfile newInfo = new UserProfile("quinn_new@ptit.edu.vn", LocalDate.of(2006, 4, 9));
        User result = service.updateProfile(currentUser, newInfo, allUsers);

        assertNotNull(result, "Hồ sơ hợp lệ phải cập nhật thành công");
        assertEquals("quinn_new@ptit.edu.vn", result.profile.email);
    }

    @Test
    @DisplayName("Kiểm tra ràng buộc ngày sinh không được ở tương lai")
    void testFutureDob() {
        UserProfile futureProfile = new UserProfile("test@gmail.com", LocalDate.now().plusDays(1));
        User result = service.updateProfile(currentUser, futureProfile, allUsers);

        assertNull(result, "Hệ thống phải từ chối nếu ngày sinh ở tương lai");
    }

    @Test
    @DisplayName("Kiểm tra ràng buộc email trùng với người dùng khác")
    void testDuplicateEmail() {
        UserProfile dupProfile = new UserProfile("dekay@gmail.com", LocalDate.of(2006, 4, 9));
        User result = service.updateProfile(currentUser, dupProfile, allUsers);

        assertNull(result, "Hệ thống không được cho phép trùng email với user khác");
    }

    @Test
    @DisplayName("Cho phép cập nhật khi email mới giống email hiện tại của chính mình")
    void testSameEmailAsCurrent() {
        UserProfile sameEmailProfile = new UserProfile("quinn@ptit.edu.vn", LocalDate.of(2006, 4, 9));
        User result = service.updateProfile(currentUser, sameEmailProfile, allUsers);

        assertNotNull(result, "Email không đổi thì vẫn phải cho phép cập nhật các thông tin khác");
    }

    @Test
    @DisplayName("Cập nhật thành công khi danh sách người dùng khác rỗng")
    void testEmptyUserList() {
        UserProfile validProfile = new UserProfile("newbie@gmail.com", LocalDate.of(2000, 5, 5));
        User result = service.updateProfile(currentUser, validProfile, new ArrayList<>());

        assertNotNull(result);
    }

    @Test
    @DisplayName("Từ chối cập nhật khi vi phạm cả email trùng và ngày sinh tương lai")
    void testMultipleViolations() {
        UserProfile badProfile = new UserProfile("dekay@gmail.com", LocalDate.now().plusYears(1));
        User result = service.updateProfile(currentUser, badProfile, allUsers);

        assertNull(result);
    }
}