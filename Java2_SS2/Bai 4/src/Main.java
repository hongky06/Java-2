import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class User {

    private String username;

    // constructor không tham số
    public User() {
        this.username = "guest";
    }

    // constructor có tham số
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Main {

    public static void main(String[] args) {

        // danh sách users
        List<User> users = Arrays.asList(
                new User("quyen"),
                new User("admin"),
                new User("dev")
        );

        /*
        Lambda:
        (user) -> user.getUsername()

        Method Reference:
        User::getUsername
        */
        users.stream()
                .map(User::getUsername) // gọi method reference
                .forEach(System.out::println); // method reference in ra console


        /*
        Lambda:
        (s) -> System.out.println(s)

        Method Reference:
        System.out::println
        */
        Consumer<String> printer = System.out::println;
        printer.accept("Hello Method Reference");


        /*
        Lambda:
        () -> new User()

        Method Reference:
        User::new
        */
        Supplier<User> createUser = User::new;

        User newUser = createUser.get();
        System.out.println("New User: " + newUser.getUsername());
    }
}