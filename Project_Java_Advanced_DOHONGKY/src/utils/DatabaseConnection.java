package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // ================== CẤU HÌNH KẾT NỐI ==================
    private static final String URL = "jdbc:mysql://localhost:3306/cyber?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🔌 Kết nối Database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println(" Không tìm thấy MySQL Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Kết nối Database thất bại! Kiểm tra username/password và database có tồn tại không.");
            e.printStackTrace();
        }
        return connection;
    }

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