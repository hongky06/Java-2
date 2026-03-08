import java.io.IOException;

class UserService {

    public static void saveToFile(User user) throws IOException {

        System.out.println("Đang lưu dữ liệu vào file...");


        throw new IOException("Không thể ghi dữ liệu vào file.");
    }

    public static void processUser(User user) throws IOException {

        System.out.println("Đang xử lý dữ liệu người dùng...");

        saveToFile(user);
    }
}