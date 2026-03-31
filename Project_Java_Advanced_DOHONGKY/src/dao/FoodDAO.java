package dao;

import model.Food;
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
                        rs.getInt("quantity")   // sửa từ stock thành quantity
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
}