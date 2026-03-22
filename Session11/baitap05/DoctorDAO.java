package Session11.baitap05;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bai01", "root", "09042006");
    }

    public List<Doctor> getAll() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Doctor(rs.getInt("doctor_id"), rs.getString("full_name"), rs.getString("specialty")));
            }
        }
        return list;
    }

    public void addDoctor(Doctor d) throws SQLException {
        String sql = "INSERT INTO Doctors (doctor_id, full_name, specialty) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, d.getDoctorId());
            pstmt.setString(2, d.getFullName());
            pstmt.setString(3, d.getSpecialty());
            pstmt.executeUpdate();
        }
    }

    public void getStatistics() throws SQLException {
        String sql = "SELECT specialty, COUNT(*) as total FROM Doctors GROUP BY specialty";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Thống kê chuyên khoa ---");
            while (rs.next()) {
                System.out.println(rs.getString("specialty") + ": " + rs.getInt("total") + " bác sĩ");
            }
        }
    }
}
