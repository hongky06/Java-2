package Session11.baitap05;

import java.util.Scanner;

public class RikkeiCareApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorDAO dao = new DoctorDAO();
        DoctorService service = new DoctorService();

        while (true) {
            System.out.println("\n===== HỆ THỐNG RIKKEI-CARE =====");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ mới");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = Integer.parseInt(sc.nextLine()); // Tránh lỗi trôi dòng

            try {
                switch (choice) {
                    case 1:
                        for (Doctor d : dao.getAll())
                            System.out.printf("Mã: %d | Tên: %s | Khoa: %s\n", d.getDoctorId(), d.getFullName(), d.getSpecialty());
                        break;
                    case 2:
                        System.out.print("Nhập mã số: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập họ tên: "); String name = sc.nextLine();
                        System.out.print("Nhập chuyên khoa: "); String spec = sc.nextLine();
                        service.createDoctor(id, name, spec);
                        break;
                    case 3:
                        dao.getStatistics();
                        break;
                    case 4:
                        System.out.println("Tạm biệt!"); return;
                }
            } catch (Exception e) {
                System.err.println("Có lỗi xảy ra: " + e.getMessage());
            }
        }
    }
}