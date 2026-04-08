package dao;

import model.Booking;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean createBooking(int userId, int pcId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "INSERT INTO bookings(user_id, pc_id, start_time, end_time, status) VALUES (?, ?, ?, ?, 'CHỜ XÁC NHẬN')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, pcId);
            ps.setTimestamp(3, Timestamp.valueOf(startTime));
            ps.setTimestamp(4, Timestamp.valueOf(endTime));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getBookingsByPcId(int pcId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE pc_id = ? ORDER BY start_time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pcId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Booking getActiveBookingByUserId(int userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ? AND status = 'PLAYING'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    private Booking mapResultSet(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setPcId(rs.getInt("pc_id"));

        Timestamp start = rs.getTimestamp("start_time");
        if (start != null) b.setStartTime(start.toLocalDateTime());

        Timestamp end = rs.getTimestamp("end_time");
        if (end != null) b.setEndTime(end.toLocalDateTime());

        b.setStatus(rs.getString("status"));
        return b;
    }

    public Booking getActiveBooking(int pcId) {
        String sql = "SELECT * FROM bookings WHERE pc_id = ? AND status = 'PLAYING'";
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



    public boolean startNow(int userId, int pcId, int hours) {
        String sql = "INSERT INTO bookings(user_id, pc_id, start_time, end_time, status) VALUES (?, ?, ?, ?, 'PLAYING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusHours(hours);

            ps.setInt(1, userId);
            ps.setInt(2, pcId);
            ps.setTimestamp(3, Timestamp.valueOf(startTime));
            ps.setTimestamp(4, Timestamp.valueOf(endTime));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPCNameByUser(int userId) {
        String sql = "SELECT pc_id FROM bookings WHERE user_id = ? AND status = 'PLAYING'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int pcId = rs.getInt("pc_id");
                return "PC-" + pcId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
}