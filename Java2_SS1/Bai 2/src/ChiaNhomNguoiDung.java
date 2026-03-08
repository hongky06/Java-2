import java.util.Scanner;

public class ChiaNhomNguoiDung {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Nhập tổng số người dùng: ");
            int tongSoNguoi = scanner.nextInt();

            System.out.print("Nhập số nhóm muốn chia: ");
            int soNhom = scanner.nextInt();

            int soNguoiMoiNhom = tongSoNguoi / soNhom;

            System.out.println("Mỗi nhóm có " + soNguoiMoiNhom + " người.");

        } catch (ArithmeticException e) {
            System.out.println("Không thể chia cho 0!");
        } finally {
            scanner.close();
        }

        System.out.println("Chương trình kết thúc.");
    }
}

