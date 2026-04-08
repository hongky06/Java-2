package dao;

import model.Food;
import model.FoodOrder;
import utils.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    // ================= THÊM MÓN ĂN =================
    public boolean addFood(Food food) {
        String sql = "INSERT INTO food(name, description, category, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, food.getName());
            ps.setString(2, food.getDescription());
            ps.setString(3, food.getCategory());
            ps.setDouble(4, food.getPrice());
            ps.setInt(5, food.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= LẤY THEO CATEGORY =================
    public List<Food> getByCategory(String category) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE UPPER(TRIM(category)) = UPPER(TRIM(?))";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= TÌM THEO TÊN =================
    public Food findByName(String name) {
        String sql = "SELECT * FROM food WHERE name = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= XÓA =================
    public boolean deleteFood(String name) {
        String sql = "DELETE FROM food WHERE name = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Food> getAll() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM food";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Food f = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean updateQuantity(int id, int newQuantity) {
        String sql = "UPDATE food SET quantity = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public class FoodOrderDAO {

        // ===== Thêm đơn đặt món =====
        public boolean addOrder(FoodOrder order) {
            String sql = "INSERT INTO food_order(user_id, food_id, quantity, status) VALUES (?, ?, ?, ?)";

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, order.getUserId());
                ps.setInt(2, order.getFoodId());
                ps.setInt(3, order.getQuantity());
                ps.setString(4, order.getStatus() != null ? order.getStatus() : "CHỜ ĐƠN");

                return ps.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        // ===== Lấy danh sách đơn theo trạng thái =====
        public List<FoodOrder> getByStatus(String status) {
            List<FoodOrder> list = new ArrayList<>();
            String sql = "SELECT * FROM food_order WHERE status = ?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, status);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    FoodOrder order = new FoodOrder(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("food_id"),
                            rs.getInt("quantity"),
                            rs.getString("status"),
                            rs.getTimestamp("order_time").toLocalDateTime()
                    );
                    list.add(order);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        // ===== Cập nhật trạng thái =====
        public boolean updateStatus(int orderId, String newStatus) {
            String sql = "UPDATE food_order SET status = ? WHERE id = ?";

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, newStatus);
                ps.setInt(2, orderId);

                return ps.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        // ===== Lấy tất cả đơn =====
        public List<FoodOrder> getAll() {
            List<FoodOrder> list = new ArrayList<>();
            String sql = "SELECT * FROM food_order ORDER BY order_time ASC";

            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    FoodOrder order = new FoodOrder(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("food_id"),
                            rs.getInt("quantity"),
                            rs.getString("status"),
                            rs.getTimestamp("order_time").toLocalDateTime()
                    );
                    list.add(order);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public boolean createFoodOrder(int userId, int pcId, int foodId, String foodName, int quantity, double price) {
        String sql = "INSERT INTO orders(user_id, pc_id, food_id, food_name, quantity, price, status, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'CHỜ ĐƠN', NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, pcId); // nếu đặt theo bàn/máy
            ps.setInt(3, foodId);
            ps.setString(4, foodName);
            ps.setInt(5, quantity);
            ps.setDouble(6, price);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Food findById(int id) {
        String sql = "SELECT * FROM food WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}