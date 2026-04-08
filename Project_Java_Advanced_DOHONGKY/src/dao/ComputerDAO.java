package dao;

import model.Computer;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputerDAO {

    public List<Computer> getAll() {
        List<Computer> list = new ArrayList<>();
        String sql = "SELECT * FROM pcs";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getInt("id"));
                c.setPcName(rs.getString("pc_name"));
                c.setArea(rs.getString("area"));
                c.setPricePerHour(rs.getDouble("price_per_hour"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Computer findById(int id) {
        String sql = "SELECT * FROM pcs WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getInt("id"));
                c.setPcName(rs.getString("pc_name"));
                c.setArea(rs.getString("area"));
                c.setPricePerHour(rs.getDouble("price_per_hour"));
                c.setStatus(rs.getString("status"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== TÌM MÁY THEO TÊN =====
    public Computer findByName(String name) {
        String sql = "SELECT * FROM pcs WHERE pc_name = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getInt("id"));
                c.setPcName(rs.getString("pc_name"));
                c.setArea(rs.getString("area"));
                c.setPricePerHour(rs.getDouble("price_per_hour"));
                c.setStatus(rs.getString("status"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== CẬP NHẬT TRẠNG THÁI MÁY =====
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE pcs SET status = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== THÊM MÁY MỚI =====
    public boolean addComputer(Computer c) {
        String sql = "INSERT INTO pcs(pc_name, area, price_per_hour, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getPcName());
            ps.setString(2, c.getArea());
            ps.setDouble(3, c.getPricePerHour());
            ps.setString(4, c.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteComputer(int id) {
        String sql = "DELETE FROM pcs WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // ===== LẤY MÁY CÒN TRỐNG =====
    public List<Computer> getAvailableComputers() {
        List<Computer> list = new ArrayList<>();
        String sql = "SELECT * FROM pcs WHERE status = 'TRONG'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Computer c = new Computer();
                c.setId(rs.getInt("id"));
                c.setPcName(rs.getString("pc_name"));
                c.setArea(rs.getString("area"));
                c.setPricePerHour(rs.getDouble("price_per_hour"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    // ===== THÊM MÁY MỚI =====
    public boolean add(Computer c) {
        String sql = "INSERT INTO pcs(pc_name, area, price_per_hour, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getPcName());
            ps.setString(2, c.getArea());
            ps.setDouble(3, c.getPricePerHour());
            ps.setString(4, c.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // ===== XÓA MÁY =====
    public boolean delete(int id) {
        String sql = "DELETE FROM pcs WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}