public class Main {

    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-5);
            System.out.println("Tuổi: " + user.getAge());
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc.");
    }
}