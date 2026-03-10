package Session03.baitap04;

import java.util.*;
import java.util.stream.Collectors;

record User(String username, String email) {}

public class Main {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("alice", "alice@gmail.com"),
                new User("bob", "bob@yahoo.com"),
                new User("alice", "alice.new@gmail.com"), // Trùng alice
                new User("charlie", "charlie@gmail.com"),
                new User("bob", "bob.work@gmail.com"),    // Trùng bob
                new User("quinn", "quinn@java.com")
        );

        Collection<User> uniqueUsers = users.stream()
                .collect(Collectors.toMap(
                        User::username,
                        user -> user,
                        (existing, replacement) -> existing
                ))
                .values();

        System.out.println("=== Danh sách User đã loại bỏ trùng lặp theo username ===");
        uniqueUsers.forEach(System.out::println);
    }
}