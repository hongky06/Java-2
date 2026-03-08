import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        User user = new User();
        user.setName("An");

        try {

            user.setAge(-2);

            user.printUser();

            UserService.processUser(user);

        } catch (InvalidAgeException e) {

            Logger.logError(e.getMessage());

        } catch (IOException e) {

            Logger.logError("Lỗi hệ thống khi lưu file: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc.");
    }
}