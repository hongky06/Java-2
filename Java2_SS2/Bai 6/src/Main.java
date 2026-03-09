// class User
class User {
    private String username;
    public User(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}

// Functional Interface
@FunctionalInterface
interface UserProcessor {
    // method xử lý User và trả về String
    String process(User u);
}
// Utility class
class UserUtils {
    // static method chuyển username thành chữ hoa
    public static String convertToUpperCase(User u) {
        return u.getUsername().toUpperCase();
    }

}

public class Main {
    public static void main(String[] args) {
        // tạo user
        User user = new User("quyen");

        /*
        Method Reference
        Lambda tương đương:
        u -> UserUtils.convertToUpperCase(u)
        */
        UserProcessor processor = UserUtils::convertToUpperCase;

        // gọi method
        String result = processor.process(user);

        System.out.println("Processed username: " + result);
    }

}