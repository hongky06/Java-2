package Session14.kiemtra;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApplication {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        String fromId = "ACC01";
        String toId = "ACC02";
        double amount = 1000;

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                double currentBalance = accountDAO.getBalance(conn, fromId);
                if(amount < 0 ){
                    throw new SQLException("Số tiền chuyển không được âm");
                }
                if (currentBalance < amount) {
                    throw new SQLException("Số dư không đủ!");
                }
                accountDAO.updateBalance(conn, fromId, -amount);
                accountDAO.updateBalance(conn, toId, amount);
                conn.commit();
                System.out.println("Giao dịch thành công!");

            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Giao dịch thất bại: " + e.getMessage());
            } finally {
                accountDAO.displayAllAccounts(conn);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối Database: " + e.getMessage());
        }
    }
}