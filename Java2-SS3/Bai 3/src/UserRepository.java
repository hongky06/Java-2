package Session03.baitap03;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    public record User(String username, String email) {};
    private static final List<User> users = Arrays.asList(
            new User("alice", "alice@gmail.com"),
            new User("bob", "bob@yahoo.com"),
            new User("charlie", "charlie@gmail.com")
    );
    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equalsIgnoreCase(username))
                .findFirst();
    }

    public static void main(String[] args) {
        UserRepository repo = new UserRepository();
        String searchName = "alice";

        Optional<User> userOpt = repo.findUserByUsername(searchName);

        userOpt.ifPresentOrElse(
                user -> System.out.println("Welcome " + user.username()),
                () -> System.out.println("Guest login")
        );
    }
}