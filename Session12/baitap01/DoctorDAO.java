package Session12.baitap01;

import Session12.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * PHẦN 1 - PHÂN TÍCH BẢO MẬT (LÝ THUYẾT)
 * * 1. Tại sao PreparedStatement là "tấm khiên" chống SQL Injection?
 * - Khác với việc nối chuỗi trực tiếp (Statement), PreparedStatement phân tách rõ ràng
 * giữa "Khung câu lệnh" và "Dữ liệu người dùng".
 * - Khi sử dụng dấu hỏi chấm (?), dữ liệu truyền vào chỉ được coi là giá trị thuần túy (Literals).
 * - Mọi nỗ lực chèn mã độc như "' OR '1'='1" sẽ bị vô hiệu hóa vì Database sẽ tìm đúng
 * chuỗi ký tự đó thay vì thực thi nó như một mệnh đề logic.
 * * 2. Cơ chế "Pre-compiled" (Biên dịch trước) giúp ích gì?
 * - Cấu trúc SQL được gửi đến Database Server để biên dịch trước khi truyền dữ liệu.
 * - Việc biên dịch trước giúp cố định logic của câu lệnh. Sau khi đã biên dịch,
 * không một tham số đầu vào nào có thể thay đổi được cấu trúc nguyên bản của truy vấn.
 */

public class DoctorDAO {
    // PHẦN 2 - THỰC THI (MÃ NGUỒN AN TOÀN)
    public boolean login(String code, String pass) {
        // Sử dụng dấu hỏi chấm (?) làm placeholder để ngăn chặn SQL Injection
        String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

        // Sử dụng Try-with-resources để tự động đóng kết nối, đảm bảo an toàn tài nguyên
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            /* * Cơ chế bảo vệ:
             * pstmt.setString sẽ tự động xử lý (escape) các ký tự đặc biệt
             * và gắn giá trị vào vị trí các dấu ? đã được biên dịch trước.
             */
            pstmt.setString(1, code);
            pstmt.setString(2, pass);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Đăng nhập thành công nếu tìm thấy bản ghi khớp
                    return true;
                }
            }
        } catch (Exception e) {
            // Ghi log lỗi nếu có vấn đề kết nối hoặc SQL
            System.err.println("Lỗi thực thi truy vấn: " + e.getMessage());
        }

        return false; // Mặc định trả về false nếu không khớp hoặc có lỗi
    }

    public static void main(String[] args) {
        DoctorDAO dao = new DoctorDAO();
        // Giả sử dữ liệu được truyền từ Controller/Giao diện vào
        if (dao.login("DOC001", "admin123")) {
            System.out.println("Đăng nhập hệ thống thành công!");
        } else {
            System.out.println("Thông tin không chính xác hoặc tài khoản bị khóa.");
        }
    }
}