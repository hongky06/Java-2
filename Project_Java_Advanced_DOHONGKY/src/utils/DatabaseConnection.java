package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ================== CẤU HÌNH KẾT NỐI DATABASE ==================
    private static final String URL = "jdbc:mysql://localhost:3306/net_management?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";        // Thay bằng username MySQL của bạn
    private static final String PASSWORD = "Ky25122006@";  // Thay bằng password MySQL của bạn

    private static Connection connection = null;

    // Phương thức lấy kết nối
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🔌 Kết nối Database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy Driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối Database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Đóng kết nối (nếu cần)
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}