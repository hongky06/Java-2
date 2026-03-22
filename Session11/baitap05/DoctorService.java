package Session11.baitap05;

import java.sql.SQLException;

public class DoctorService {
    private DoctorDAO doctorDAO = new DoctorDAO();

    public void createDoctor(int id, String name, String spec) {
        if (name.length() > 50 || spec.length() > 50) {
            System.err.println("Lỗi: Tên hoặc chuyên khoa quá dài (tối đa 50 ký tự)!");
            return;
        }
        try {
            doctorDAO.addDoctor(new Doctor(id, name, spec));
            System.out.println("✅ Thêm bác sĩ thành công!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Mã lỗi MySQL cho Duplicate Entry
                System.err.println("Lỗi: Mã bác sĩ " + id + " đã tồn tại!");
            } else {
                System.err.println("Lỗi Database: " + e.getMessage());
            }
        }
    }
}