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
        String sql = "SELECT * FROM users WHERE TRIM(UPPER(role)) = 'CUSTOMER'";

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
        String sql = "SELECT * FROM users WHERE UPPER(role) = 'CUSTOMER'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
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

    // ===== NẠP TIỀN (BẢN NÂNG CAO - IGNORE CASE + TRẢ VỀ SỐ DƯ MỚI) =====
    public double depositMoneyAndGetBalance(String username, double amount) {
        String updateSql = "UPDATE users SET balance = balance + ? WHERE UPPER(TRIM(username)) = UPPER(TRIM(?)) AND UPPER(role) = 'CUSTOMER'";
        String selectSql = "SELECT balance FROM users WHERE UPPER(TRIM(username)) = UPPER(TRIM(?))";

        try (Connection conn = Database.getConnection();
             PreparedStatement psUpdate = conn.prepareStatement(updateSql);
             PreparedStatement psSelect = conn.prepareStatement(selectSql)) {

            // update tiền
            psUpdate.setDouble(1, amount);
            psUpdate.setString(2, username);

            int rows = psUpdate.executeUpdate();
            if (rows == 0) {
                return -1; // không tìm thấy user
            }

            psSelect.setString(1, username);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public boolean deductBalanceById(int userId, double amount) {
        String sql = "UPDATE users SET balance = balance - ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // ===== LẤY USER THEO ID =====
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
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
    public boolean hasEnoughMoney(int userId, double amount) {
        String sql = "SELECT balance FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance") >= amount;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}