package Session11.baitap03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospitalManager {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "09042006";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public void updateBedStatus(int inputId) {
        String sql = "UPDATE Beds SET bed_status = 'Occupied' WHERE bed_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inputId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thành công: Giờ đây giường mã số [" + inputId + "] đã được cập nhật trạng thái 'Occupied'.");
            } else {
                // Trường hợp executeUpdate trả về 0
                System.out.println("Cảnh báo: Không tìm thấy giường bệnh có mã số [" + inputId + "]. Vui lòng kiểm tra lại!");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi hệ thống: Đã xảy ra sự cố khi kết nối Database.");
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        HospitalManager manager = new HospitalManager();

        // Giả sử y tá nhập mã giường 101
        System.out.println("--- Đang xử lý cập nhật giường bệnh ---");
        manager.updateBedStatus(101);
    }
}