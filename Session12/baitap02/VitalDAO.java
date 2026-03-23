package Session12.baitap02;

import Session12.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PHẦN 1 - PHÂN TÍCH: TẠI SAO setDouble(), setInt() GIẢI QUYẾT LỖI ĐỊNH DẠNG?
 * * 1. Cơ chế truyền tham số nhị phân (Binary Transfer):
 * - Khi nối chuỗi (Statement), Java phải ép kiểu số (double, int) sang String.
 * Nếu hệ điều hành dùng Locale Việt Nam, số 37.5 có thể bị biến thành "37,5".
 * SQL Server sẽ báo lỗi cú pháp vì dấu phẩy (,) không phải dấu phân cách thập phân chuẩn.
 * - Với PreparedStatement, các phương thức setDouble() và setInt() gửi dữ liệu
 * đến Database dưới dạng giá trị gốc (raw data) hoặc định dạng chuẩn của JDBC.
 * * 2. Độc lập với Locale (Vùng miền):
 * - PreparedStatement đóng vai trò là "người thông dịch". Nó tự động chuyển đổi
 * kiểu dữ liệu Java sang kiểu dữ liệu tương ứng trong SQL mà không phụ thuộc
 * vào cài đặt hiển thị của Windows/Linux (dấu chấm hay dấu phẩy).
 * - Điều này giúp code chạy ổn định trên mọi máy tính mà không cần dùng DecimalFormat để format lại chuỗi.
 */

public class VitalDAO {

    // PHẦN 2 - THỰC THI: CẬP NHẬT CHỈ SỐ SINH TỒN
    public boolean updatePatientVitals(int patientId, double temp, int heartRate) {
        // Câu lệnh SQL với các placeholder (?) cho các chỉ số
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        // Sử dụng Try-with-resources để quản lý kết nối từ DBContext
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            /* * An toàn tuyệt đối về định dạng:
             * Không cần lo lắng temp là 37.5 hay 37,5.
             * setDouble() sẽ đảm bảo Database nhận đúng giá trị số.
             */
            pstmt.setDouble(1, temp);      // Gán nhiệt độ (số thực)
            pstmt.setInt(2, heartRate);    // Gán nhịp tim (số nguyên)
            pstmt.setInt(3, patientId);    // Gán ID bệnh nhân

            // executeUpdate trả về số dòng bị tác động
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Lỗi cập nhật chỉ số sinh tồn: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        VitalDAO dao = new VitalDAO();

        // Giả lập y tá nhập dữ liệu từ máy tính cài Locale Việt Nam (37,5)
        int pId = 101;
        double currentTemp = 38.2;
        int currentHeartRate = 85;

        if (dao.updatePatientVitals(pId, currentTemp, currentHeartRate)) {
            System.out.println("Cập nhật chỉ số cho bệnh nhân " + pId + " thành công!");
        } else {
            System.out.println("Cập nhật thất bại. Vui lòng kiểm tra lại kết nối.");
        }
    }
}