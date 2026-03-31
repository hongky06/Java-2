package dao;

import model.User;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ===== KIỂM TRA USERNAME TỒN TẠI =====
    public boolean isUsernameExist(String username) {
        String sql = "SELECT * FROM users WHERE UPPER(TRIM(username)) = UPPER(TRIM(?))";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== ĐĂNG KÝ KHÁCH HÀNG =====
    public boolean register(User user) {
        String sql = "INSERT INTO users(username, password, role, balance) VALUES (?, ?, ?, 0)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== TẠO NHÂN VIÊN =====
    public boolean createStaff(String username, String password) {
        String sql = "INSERT INTO users(username, password, role, balance) VALUES (?, ?, 'STAFF', 0)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== XÓA USER =====
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE UPPER(TRIM(username)) = UPPER(TRIM(?))";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== TÌM USER THEO USERNAME =====
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE UPPER(TRIM(username)) = UPPER(TRIM(?))";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setBalance(rs.getDouble("balance"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== LẤY TẤT CẢ KHÁCH HÀNG =====
    public List<User> getAllCustomers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE TRIM(UPPER(role)) = 'CUSTOMER'"; // đảm bảo bỏ khoảng trắng

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setBalance(rs.getDouble("balance"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // ===== TÌM KHÁCH HÀNG THEO USERNAME (IGNORE CASE) =====
    public User findCustomerByUsernameIgnoreCase(String username) {
        String sql = "SELECT * FROM users WHERE UPPER(TRIM(username)) = UPPER(TRIM(?)) AND UPPER(TRIM(role)) = 'CUSTOMER'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // ===== NẠP TIỀN CHO KHÁCH HÀNG =====
    public boolean depositMoney(String username, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE username = ? AND role = 'CUSTOMER'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // ===== LẤY DANH SÁCH KHÁCH HÀNG (KHÔNG PHÂN BIỆT HOA THƯỜNG) =====
    public List<User> getAllCustomersIgnoreCase() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE UPPER(role) = 'CUSTOMER'"; // bỏ phân biệt hoa thường

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));   // thêm id để đầy đủ
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setBalance(rs.getDouble("balance"));
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== UserDAO.java =====
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));           // thêm id để đầy đủ
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}