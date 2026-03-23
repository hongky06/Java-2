package Session12;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class baitap1 {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_homework_session12";
        return DriverManager.getConnection(url, "root", "09042006");
    }

    public void getAll() throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                printEmployee(rs);
            }
        }
    }

    public void add(String name, boolean gender, String dob, String addr, String comp, double salary) throws SQLException {
        String sql = "INSERT INTO employees(full_name, gender, birthday, address, company, salary) VALUES(?,?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setBoolean(2, gender);
            pstmt.setDate(3, Date.valueOf(dob));
            pstmt.setString(4, addr);
            pstmt.setString(5, comp);
            pstmt.setDouble(6, salary);
            pstmt.executeUpdate();
        }
    }
    public void update(int id, String name, double salary) throws SQLException {
        String sql = "UPDATE employees SET full_name = ?, salary = ? WHERE employee_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, salary);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM employees WHERE full_name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) printEmployee(rs);
        }
    }
    public void searchByCompany(String company) throws SQLException {
        String sql = "SELECT * FROM employees WHERE company = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) printEmployee(rs);
        }
    }
    public void sortBySalaryDesc() throws SQLException {
        String sql = "SELECT * FROM employees ORDER BY salary DESC";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) printEmployee(rs);
        }
    }

    private void printEmployee(ResultSet rs) throws SQLException {
        System.out.printf("ID: %d | Name: %s | Salary: %.2f | Co: %s\n",
                rs.getInt("employee_id"), rs.getString("full_name"),
                rs.getDouble("salary"), rs.getString("company"));
    }
}