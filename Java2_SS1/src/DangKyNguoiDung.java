import java.util.Scanner;

public class DangKyNguoiDung {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Nhập năm sinh của bạn (ví dụ: 2003): ");
            String namSinhStr = scanner.nextLine();

            int namSinh = Integer.parseInt(namSinhStr);

            int tuoi = 2026 - namSinh;

            if (tuoi < 0 || tuoi > 120) {
                System.out.println("Năm sinh bạn nhập không hợp lý. Tuổi phải từ 0 đến 120.");
            } else {
                System.out.println("Đăng ký thành công!");
                System.out.println("Bạn " + tuoi + " tuổi.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Bạn phải nhập số nguyên hợp lệ (ví dụ: 2003).");
            System.out.println("Vui lòng chạy lại chương trình và nhập đúng định dạng.");

        } finally {
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
            if (scanner != null) {
                scanner.close();
            }
        }

        System.out.println("Chương trình kết thúc.");
    }
}