package dao;

import model.Computer;
import utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ComputerDAO {

    // ========================
    // LẤY DANH SÁCH
    // ========================
    public List<Computer> getAll() {
        List<Computer> list = new ArrayList<>();
        String sql = "SELECT * FROM pcs";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========================
    // THÊM MÁY
    // ========================
    public void add(Computer c) {
        String sql = "INSERT INTO pcs(pc_name, area, price_per_hour, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getPcName());
            ps.setString(2, c.getArea());
            ps.setDouble(3, c.getPricePerHour());
            ps.setString(4, c.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================
    // TÌM THEO ID
    // ========================
    public Computer findById(int id) {
        String sql = "SELECT * FROM pcs WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================
    // TÌM THEO TÊN (dùng cho check trùng)
    // ========================
    public Computer findByName(String name) {
        String sql = "SELECT * FROM pcs WHERE pc_name=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================
    // UPDATE STATUS
    // ========================
    public void updateStatus(int id, String status) {
        String sql = "UPDATE pcs SET status=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================
    // XÓA MÁY
    // ========================
    public void delete(int id) {
        String sql = "DELETE FROM pcs WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================
    // LỌC MÁY TRỐNG
    // ========================
    public List<Computer> getAvailable() {
        List<Computer> list = new ArrayList<>();
        String sql = "SELECT * FROM pcs WHERE status='TRONG'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========================
    // MAP DATA (TỐI ƯU CODE)
    // ========================
    private Computer mapResultSet(ResultSet rs) throws SQLException {
        return new Computer(
                rs.getInt("id"),
                rs.getString("pc_name"),
                rs.getString("area"),
                rs.getDouble("price_per_hour"),
                rs.getString("status")
        );
    }
}