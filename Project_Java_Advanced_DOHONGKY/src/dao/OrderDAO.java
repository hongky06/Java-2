package dao;

import model.Order;
import model.OrderItem;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Lấy danh sách tất cả order
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id AS order_id, o.user_id, u.username, o.pc_id, p.pc_name, o.total, o.status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "LEFT JOIN pcs p ON o.pc_id = p.id " +
                "ORDER BY o.order_time DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("pc_id"),
                        rs.getString("pc_name"),
                        rs.getDouble("total"),
                        rs.getString("status"),
                        new ArrayList<>() // <-- khởi tạo items trống
                );
                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getPendingOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id AS order_id, o.user_id, u.username, o.pc_id, p.pc_name, o.total, o.status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "LEFT JOIN pcs p ON o.pc_id = p.id " +
                "WHERE o.status IN ('CHỜ ĐƠN','ĐANG LÀM') " +
                "ORDER BY o.order_time ASC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("pc_id"),
                        rs.getString("pc_name"),
                        rs.getDouble("total"),
                        rs.getString("status"),
                        null
                );
                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy order theo ID
    public Order findById(int orderId) {
        String sql = "SELECT o.id AS order_id, o.user_id, u.username, o.pc_id, p.pc_name, o.total, o.status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "LEFT JOIN pcs p ON o.pc_id = p.id " +
                "WHERE o.id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("pc_id"),
                        rs.getString("pc_name"),
                        rs.getDouble("total"),
                        rs.getString("status"),
                        null
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật trạng thái món
    public boolean updateItemStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tạo order khi gọi món (không đặt máy)
    public boolean createFoodOrder(int userId, String foodName, int quantity, double price) {
        String sql = "INSERT INTO orders(user_id, pc_id, food_name, total, status, order_time) " +
                "VALUES (?, NULL, ?, ?, 'CHỜ ĐƠN', CURRENT_TIMESTAMP)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, foodName);
            ps.setDouble(3, price * quantity);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean createFoodOrder(int userId, Integer pcId, String foodName, int quantity, double price) {
        String sql = "INSERT INTO orders(user_id, pc_id, food_name, total, status, order_time) " +
                "VALUES (?, ?, ?, ?, 'CHỜ ĐƠN', CURRENT_TIMESTAMP)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            if (pcId != null) {
                ps.setInt(2, pcId);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setString(3, foodName);
            ps.setDouble(4, price * quantity); // tổng tiền
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}