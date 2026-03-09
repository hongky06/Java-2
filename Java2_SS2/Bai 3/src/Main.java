interface Authenticatable{
    abstract String getPassword();
    default boolean isAuthenticated(){
        return !getPassword().isEmpty() && getPassword() != null;
    }
    static String encrypt(String rawpassword){
        return new StringBuilder(rawpassword).reverse().toString();
    }
}
class User implements Authenticatable {
    private String password;
    public User(String password) {
        this.password = password;
    }
    @Override
    public String getPassword() {
        return password;
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("123456");
        System.out.println("Authenticated: " + user.isAuthenticated());
        String encrypted = Authenticatable.encrypt("123456");
        System.out.println("Encrypted password: " + encrypted);
    }
}