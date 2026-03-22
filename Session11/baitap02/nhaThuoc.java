package Session11.baitap02;

import java.sql.*;

public class nhaThuoc {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Hospital_DB";
        String user = "root";
        String password = "09042006";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select medicine_name, stock From pharmacy");
//            if(rs.next()){
//                String medicine_name = rs.getString("medicine_name");
//                System.out.println(medicine_name);
//            }
            ///  if không thể còng lặp để trỏ tới giá trị tiếp theo để in ra nên trong trường hợp này chỉ in ra được cái đầu tiên
            ///  không thể áp dụng in danh sách

            while(rs.next()){
                String medicine_name = rs.getString("medicine_name");
                System.out.println(medicine_name);
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }


    }
}
