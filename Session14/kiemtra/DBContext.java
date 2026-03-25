package Session14.kiemtra;

import java.sql.*;

public class DBContext {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bank";
    private static final String USER = "root";
    private static final String PASS = "09042006";
    public static Connection conn =null;
    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(URL,USER,PASS);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
