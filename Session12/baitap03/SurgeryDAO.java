package Session12.baitap03;

import Session12.DBContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * PHẦN 1 - PHÂN TÍCH: TẦM QUAN TRỌNG CỦA registerOutParameter()
 * * 1. Tại sao bắt buộc phải gọi registerOutParameter()?
 * - Trong JDBC, các tham số của Stored Procedure được chia thành IN, OUT, và INOUT.
 * - Khác với tham số IN (chỉ truyền vào), tham số OUT yêu cầu bộ nhớ đệm để nhận giá trị
 * trả về từ Database sau khi thực thi.
 * - Việc gọi registerOutParameter() thông báo cho JDBC Driver biết:
 * + Vị trí nào sẽ nhận giá trị trả về.
 * + Kiểu dữ liệu mong muốn (Mapping giữa SQL và Java).
 * Nếu không đăng ký, Driver sẽ không biết cách ánh xạ dữ liệu và gây lỗi khi gọi getXXX().
 * * * 2. Đăng ký kiểu DECIMAL trong Java:
 * - Nếu tham số đầu ra trong SQL là DECIMAL (thường dùng cho tiền tệ),
 * trong Java chúng ta phải sử dụng hằng số: java.sql.Types.DECIMAL
 * hoặc java.sql.Types.NUMERIC.
 */

public class SurgeryDAO {

    // PHẦN 2 - THỰC THI: GỌI THỦ TỤC TÍNH CHI PHÍ PHẪU THUẬT
    public double getSurgeryFee(int surgeryId) {
        // Cú pháp gọi Stored Procedure: {call ProcedureName(?, ?, ...)}
        String sql = "{call GET_SURGERY_FEE(?, ?)}";
        double totalCost = 0.0;

        try (Connection conn = new DBContext().getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Bước 1: Thiết lập tham số đầu vào (IN parameter)
            cstmt.setInt(1, surgeryId);

            /* Bước 2: ĐĂNG KÝ THAM SỐ ĐẦU RA (OUT parameter) - CỰC KỲ QUAN TRỌNG
             * Vị trí thứ 2 là total_cost, kiểu DECIMAL trong SQL
             */
            cstmt.registerOutParameter(2, Types.DECIMAL);

            // Bước 3: Thực thi thủ tục
            cstmt.execute();

            /* Bước 4: Lấy giá trị từ tham số đầu ra sau khi thực thi
             * Lưu ý: Chỉ lấy được giá trị SAU KHI đã gọi cstmt.execute()
             */
            totalCost = cstmt.getDouble(2);

        } catch (Exception e) {
            System.err.println("Lỗi khi tính chi phí phẫu thuật: " + e.getMessage());
        }

        return totalCost;
    }

    public static void main(String[] args) {
        SurgeryDAO dao = new SurgeryDAO();
        int surgeryId = 505;

        double cost = dao.getSurgeryFee(surgeryId);

        if (cost > 0) {
            System.out.printf("Tổng chi phí cho ca phẫu thuật mã %d là: %,.2f VND\n", surgeryId, cost);
        } else {
            System.out.println("Không tìm thấy thông tin hoặc chi phí không hợp lệ.");
        }
    }
}