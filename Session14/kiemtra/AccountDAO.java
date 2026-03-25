package Session14.kiemtra;

import java.sql.*;

public class AccountDAO {
    public double getBalance(Connection conn, String accountId) throws SQLException {
        String sql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Balance");
                }
                throw new SQLException("Tài khoản " + accountId + " không tồn tại.");
            }
        }
    }
    public void updateBalance(Connection conn, String accountId, double amount) throws SQLException {
        String sql = "{call sp_UpdateBalance(?, ?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, accountId);
            cstmt.setDouble(2, amount);
            cstmt.executeUpdate();
        }
    }
    public void displayAllAccounts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Accounts";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n--- KẾT QUẢ ĐỐI SOÁT ---");
            System.out.printf("%-10s | %-20s | %-10s%n", "ID", "Họ Tên", "Số dư");
            while (rs.next()) {
                System.out.printf("%-10s | %-20s | %-10.2f%n",
                        rs.getString("AccountId"), rs.getString("FullName"), rs.getDouble("Balance"));
            }
        }
    }
}