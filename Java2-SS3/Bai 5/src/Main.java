import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

record User(String username, String email) {}

public class Main {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Quinn", "quinn@gmail.com"),
                new User("Alexander", "alex@gmail.com"),
                new User("Charlotte", "char@gmail.com"),
                new User("Bob", "bob@gmail.com"),
                new User("Benjamin", "ben@gmail.com"),
                new User("Dekay", "dekay@gmail.com"),
                new User("Christopher", "chris@gmail.com")
        );

        System.out.println("--- Top 3 người dùng có username dài nhất ---");
        users.stream()
                // Sắp xếp theo độ dài username giảm dần
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())
                // Giới hạn lấy 3 người đầu tiên
                .limit(3)
                // In kết quả
                .forEach(user -> System.out.println(user.username() + " (" + user.username().length() + " ký tự)"));
    }
}