import java.sql.*;

public class TransferMoney {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String pass = "Ky25122006@";
        String sender = "ACC01";
        String receiver = "ACC02";
        double amount = 1000;




        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            conn.setAutoCommit(false);

            String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            double balance = 0;

            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, sender);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    balance = rs.getDouble("Balance");
                } else {
                    throw new Exception("Không tồn tại tài khoản gửi!");
                }
            }

            if (balance < amount) {
                throw new Exception("Số dư không đủ!");
            }

            String callSql = "{call sp_UpdateBalance(?, ?)}";

            try (CallableStatement cs = conn.prepareCall(callSql)) {

                cs.setString(1, sender);
                cs.setDouble(2, -amount);
                cs.execute();

                cs.setString(1, receiver);
                cs.setDouble(2, amount);
                cs.execute();
            }

            conn.commit();
            System.out.println("Chuyển khoản thành công!");


            String resultSql = "SELECT * FROM Accounts WHERE AccountId IN (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(resultSql)) {
                ps.setString(1, sender);
                ps.setString(2, receiver);

                ResultSet rs = ps.executeQuery();

                System.out.println("\n DANH SÁCH SAU KHI CHUYỂN ");
                while (rs.next()) {
                    System.out.println(
                            rs.getString("AccountId") + " | " +
                                    rs.getString("FullName") + " | " +
                                    rs.getDouble("Balance")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra! Rollback");
            e.printStackTrace();
        }
    }
}