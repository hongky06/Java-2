import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Employee> list = new ArrayList<>();
        int choice ;
        do {
            System.out.println(
                    "1. Hiển thị danh sách toàn bộ nhân viên\n" +
                            "2. Thêm mới nhân viên\n" +
                            "3. Cập nhật thông tin nhân viên theo mã nhân viên\n" +
                            "4. Xóa nhân viên theo mã nhân viên\n" +
                            "5. Tìm kiếm nhân viên theo tên\n" +
                            "6. Lọc danh sách nhân viên xuất sắc\n" +
                            "7. Sắp xếp danh sách nhân viên giảm dần theo lương\n" +
                            "8. Thoát\n" +
                            "Lựa chọn của bạn:"
            );
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    if (list.size() == 0) {
                        System.out.println("Danh sách rỗng!");
                    } else {
                        for (Employee e : list) {
                            e.displayData();
                        }
                    }
                    break;

                case 2:
                    Employee e = new Employee();
                    e.inputData(sc);

                    for (Employee emp : list) {
                        if (emp.getEmpId().equals(e.getEmpId())) {
                            System.out.println("Trùng mã!");
                            break;
                        }
                    }
                    list.add(e);
                    System.out.println("Thêm thành công!");
                    break;


                case 3:
                    System.out.print("Nhập ID: ");
                    String idUpdate = sc.nextLine();
                    boolean foundUpdate = false;

                    for (Employee emp : list) {
                        if (emp.getEmpId().equals(idUpdate)) {
                            System.out.print("Tên mới: ");
                            emp.setEmpName(sc.nextLine());
                            foundUpdate = true;
                            break;
                        }
                    }

                    if (!foundUpdate) {
                        System.out.println("Không tìm thấy!");
                    }
                    break;

                case 4:
                    System.out.print("Nhập ID: ");
                    String idDelete = sc.nextLine();
                    boolean foundDelete = false;

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getEmpId().equals(idDelete)) {
                            list.remove(i);
                            foundDelete = true;
                            break;
                        }
                    }

                    if (!foundDelete) {
                        System.out.println("Không tìm thấy!");
                    }
                    break;

                case 5:
                    System.out.print("Nhập tên: ");
                    String name = sc.nextLine();

                    list.stream()
                            .filter(emp -> emp.getEmpName().toLowerCase().contains(name.toLowerCase()))
                            .forEach(emp -> emp.displayData());
                    break;
                case 6:
                    list.stream()
                            .filter(emp -> emp.getSalary() >= 15000000)
                            .forEach(emp -> emp.displayData());
                    break;





                case 7:
                    list.stream()
                            .sorted((a, b) -> Double.compare(b.getSalary(), a.getSalary()))
                            .forEach(emp -> emp.displayData());
                    break;

                case 8:
                    System.out.println("thoát");
                    break;
            }

        } while (choice != 8 ) ;
    }
}
