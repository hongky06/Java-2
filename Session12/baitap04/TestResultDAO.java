package Session12.baitap04;

import Session12.DBContext;
import junit.framework.TestResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * PHẦN 1 - PHÂN TÍCH: TỐI ƯU HÓA HIỆU NĂNG NẠP DỮ LIỆU LỚN
 * * 1. Tại sao Statement nối chuỗi lại lãng phí tài nguyên Database?
 * - Khi nối chuỗi trong vòng lặp (ví dụ: VALUES('A'), VALUES('B')), mỗi câu lệnh là duy nhất.
 * - Database Server phải thực hiện Parsing (phân tích cú pháp) và tạo Execution Plan
 * (kế hoạch thực thi) cho từng câu lệnh riêng biệt (1.000 lần cho 1.000 kết quả).
 * - Việc này gây lãng phí cực lớn tài nguyên CPU và làm tràn bộ nhớ đệm (Plan Cache).
 * * 2. PreparedStatement giúp tối ưu hóa như thế nào?
 * - Bằng cách khởi tạo bên ngoài vòng lặp với dấu hỏi chấm (?), câu lệnh SQL chỉ được
 * biên dịch (Pre-compiled) duy nhất MỘT LẦN.
 * - Trong vòng lặp, Database chỉ việc nhận dữ liệu mới và lắp vào khung đã có sẵn
 * mà không cần tính toán lại kế hoạch thực thi.
 * - Khi kết hợp với Batch Processing (addBatch), hàng ngàn lệnh sẽ được gửi đi trong
 * một gói tin duy nhất, giảm tối đa độ trễ mạng (Network Latency).
 */

public class TestResultDAO {

    public void insertBatchResults(List<TestResult> list) {
        String sql = "INSERT INTO Results(data) VALUES(?)";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (TestResult tr : list) {
                pstmt.setString(1, tr.toString());
                pstmt.addBatch();
            }
            int[] results = pstmt.executeBatch();
            conn.commit();

            System.out.println("Tốc độ tối ưu: Đã nạp " + results.length + " bản ghi thành công!");

        } catch (Exception e) {
            System.err.println("Lỗi nạp dữ liệu hàng loạt: " + e.getMessage());
        }
    }

    /* ĐÁNH GIÁ SỰ KHÁC BIỆT:
     * - Statement (Cũ): Mất khoảng vài phút cho 10.000 dòng vì biên dịch lại 10.000 lần.
     * - PreparedStatement + Batch (Mới): Chỉ mất khoảng vài giây vì biên dịch 1 lần
     * và giảm thiểu số lượng gói tin gửi qua mạng.
     */
}