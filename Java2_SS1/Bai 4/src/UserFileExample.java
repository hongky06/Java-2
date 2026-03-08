import java.io.IOException;

public class UserFileExample {

    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file...");


        throw new IOException("Lỗi khi ghi file!");
    }

    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng...");
        saveToFile();
    }

    public static void main(String[] args) {

        try {
            processUserData();
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi lưu file: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc.");
    }
}