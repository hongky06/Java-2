import java.util.ArrayList;
import java.util.List;

record User(String username, String email, String status) {}
// record : dùng để tạo 1 class chỉ chứa dữ liệu data class

public class Main {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>();

        users.add(new User("alice", "alice@gmail.com", "ACTIVE"));
        // record : sẽ sử dụng .username thay cho get
        users.add(new User("bob", "bob@gmail.com", "INACTIVE"));
        users.add(new User("charlie", "charlie@gmail.com", "ACTIVE"));

        users.forEach(user -> {
            System.out.println(
                    "Username: " + user.username() +
                            " | Email: " + user.email() +
                            " | Status: " + user.status()
            );
        });
    }
}