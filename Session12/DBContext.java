package Session12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Doctor";
    private static final String USER = "root";
    private static final String PASS = "09042006";
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
