import java.util.ArrayList;
import java.util.List;

public class Main {
    record User(String username , String email , String status){}
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();

        users.add(new User("alice", "alice@gmail.com", "ACTIVE"));
        users.add(new User("bob", "bob@yahoo.com", "INACTIVE"));
        users.add(new User("charlie", "charlie@gmail.com", "ACTIVE"));


        users.stream()
                .filter(user -> user.email().contains("gmail.com"))

                .forEach(user -> System.out.println(user.username()));

    }
}