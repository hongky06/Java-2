package Session11.baitap04;

import Session11.baitap01.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class searchPatientSafe {
    public void searchPatientSafe(String inputName) {
        // 1. Kiểm tra đầu vào (Validation)
        if (inputName == null || inputName.trim().isEmpty()) {
            System.out.println("Vui lòng nhập tên bệnh nhân!");
            return;
        }

        // 2. Loại bỏ các ký tự nguy hiểm (Sanitization)
        // - Loại bỏ dấu nháy đơn (') để tránh phá vỡ chuỗi SQL
        // - Loại bỏ dấu gạch ngang đôi (--) là ký tự comment trong SQL
        // - Loại bỏ dấu chấm phẩy (;) để tránh chạy nhiều lệnh cùng lúc
        String cleanName = inputName.replace("'", "''") // Trong SQL, hai dấu nháy đơn là cách escape 1 dấu nháy
                .replace("--", "")
                .replace(";", "");

        // 3. Thực hiện truy vấn với Statement sau khi đã làm sạch
        String sql = "SELECT * FROM Patients WHERE full_name = '" + cleanName + "'";

        try (Connection conn = DBContext.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("Bệnh nhân: " + rs.getString("full_name") + " - Bệnh án: " + rs.getString("record"));
            }

            if (!hasData) {
                System.out.println("Không tìm thấy bệnh nhân nào có tên: " + cleanName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
