package Session11.baitap01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getHostpitalConn{
    public static Connection main(String[] arg){
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bai01","root","09042006");
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

//Trong môi trường y tế (Bệnh viện, Quản lý bệnh nhân), hệ thống phải đối mặt với lưu lượng truy vấn cực lớn và liên tục. Việc khởi tạo kết nối mà không đóng sẽ dẫn đến các hậu quả nghiêm trọng:
//
//Cạn kiệt tài nguyên (Connection Leak): Mỗi kết nối đến Database chiếm một lượng bộ nhớ và một "slot" giới hạn trong MySQL (mặc định thường là 151 kết nối). Nếu không đóng, các kết nối cũ sẽ ở trạng thái "treo", khiến người dùng mới không thể truy cập, gây tê liệt hệ thống đăng ký khám chữa bệnh.
//
//Giảm hiệu năng (Performance Drop): CPU và RAM của máy chủ Database phải tốn tài nguyên để duy trì các kết nối "ma". Điều này làm chậm thời gian phản hồi của các truy vấn khẩn cấp.
//
//Rủi ro bảo mật: Các kết nối hở là mục tiêu cho các cuộc tấn công từ chối dịch vụ (DoS) hoặc khai thác tài nguyên trái phép.