package dao;

import model.Booking;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // ========================
    // BẮT ĐẦU CHƠI (INSERT)
    // ========================
    public void startBooking(Booking b) {
        String sql = "INSERT INTO bookings(user_id, pc_id, start_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getPcId());
            ps.setTimestamp(3, Timestamp.valueOf(b.getStartTime()));
            ps.setString(4, b.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================
    // LẤY BOOKING ĐANG CHƠI
    // ========================
    public Booking getActiveBooking(int pcId) {
        String sql = "SELECT * FROM bookings WHERE pc_id=? AND status='PLAYING'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pcId);
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
    // KẾT THÚC CHƠI
    // ========================
    public void endBooking(int bookingId, LocalDateTime endTime) {
        String sql = "UPDATE bookings SET end_time=?, status='DONE' WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(endTime));
            ps.setInt(2, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================
    // LẤY TẤT CẢ BOOKING
    // ========================
    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

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
    // LỊCH SỬ THEO USER
    // ========================
    public List<Booking> getByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========================
    // MAP RESULTSET → OBJECT
    // ========================
    private Booking mapResultSet(ResultSet rs) throws SQLException {
        Booking b = new Booking();

        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setPcId(rs.getInt("pc_id"));

        Timestamp start = rs.getTimestamp("start_time");
        if (start != null) {
            b.setStartTime(start.toLocalDateTime());
        }

        Timestamp end = rs.getTimestamp("end_time");
        if (end != null) {
            b.setEndTime(end.toLocalDateTime());
        }

        b.setStatus(rs.getString("status"));

        return b;
    }
}