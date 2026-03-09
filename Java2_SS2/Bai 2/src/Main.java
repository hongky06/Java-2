@FunctionalInterface
interface PassworldValidator{
    boolean isValid(String password);
}
public class Main {
    public static void main(String[] args) {
        PassworldValidator validator = password -> password.length() >= 8;
        System.out.println(validator.isValid("12345678"));
        System.out.println(validator.isValid("12345"));
    }
}