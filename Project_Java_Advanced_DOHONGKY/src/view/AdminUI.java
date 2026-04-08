package view;

import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

import dao.UserDAO;
import dao.FoodDAO;
import dao.ComputerDAO;
import dao.BookingDAO;

import model.User;
import model.Food;
import model.Computer;
import model.Booking;


public class AdminUI {

    private UserDAO userDAO = new UserDAO();
    private FoodDAO foodDAO = new FoodDAO();
    private ComputerDAO computerDAO = new ComputerDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("                    🛠  MENU ADMIN  🛠");
            System.out.println("═".repeat(60));

            // Bảng menu
            System.out.printf(" %-4s │ %-45s\n", "STT", "Chức năng");
            System.out.println("──────┼─────────────────────────────────────────────");

            System.out.printf(" %-4s │ %-45s\n", "1", " Xem danh sách khách hàng");
            System.out.printf(" %-4s │ %-45s\n", "2", " Tạo tài khoản nhân viên");
            System.out.printf(" %-4s │ %-45s\n", "3", "️  Xóa khách hàng");

            System.out.println("──────┼─────────────────────────────────────────────");

            System.out.printf(" %-4s │ %-45s\n", "4", " Thêm món ăn / đồ uống mới");
            System.out.printf(" %-4s │ %-45s\n", "5", " Xem menu đồ ăn & đồ uống");
            System.out.printf(" %-4s │ %-45s\n", "6", "  Xóa món");

            System.out.println("──────┼─────────────────────────────────────────────");

            System.out.printf(" %-4s │ %-45s\n", "7", " Thêm máy tính mới");
            System.out.printf(" %-4s │ %-45s\n", "8", " Xem danh sách máy");
            System.out.printf(" %-4s │ %-45s\n", "9", " Bật máy");
            System.out.printf(" %-4s │ %-45s\n", "10", " Tắt máy");
            System.out.printf(" %-4s │ %-45s\n", "11", "  Xóa máy");

            System.out.println("──────┼─────────────────────────────────────────────");
            System.out.printf(" %-4s │ %-45s\n", "0", " Đăng xuất");

            int choice = inputChoice(0, 12);

            switch (choice) {
                case 1: showCustomers(); break;
                case 2: createStaff(); break;
                case 3: deleteCustomer(); break;

                case 4: addFood();; break;

                case 5: showMenu(); break;
                case 6: deleteFood(); break;

                case 7: addComputer(); break;
                case 8: showComputers(); break;
                case 9: turnOnComputer(); break;
                case 10: turnOffComputer(); break;
                case 11: deleteComputer(); break;

                case 0:
                    System.out.println("Đăng xuất");
                    return;
            }
        }
    }

    // ================= INPUT =================
    private int inputChoice(int min, int max) {
        while (true) {
            System.out.print("Chọn: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice >= min && choice <= max) return choice;
                else System.out.println(" Nhập từ " + min + " đến " + max);
            } else {
                System.out.println("Phải nhập số!");
                sc.nextLine();
            }
        }
    }

    // ================= USER =================
    private void showCustomers() {
        List<User> list = userDAO.getAllCustomersIgnoreCase();
        System.out.println("DEBUG -> số khách: " + list.size());
        for (User u : list) {
            System.out.println("DEBUG -> " + u.getUsername() + " | role='" + u.getRole() + "'");
        }
        if (list.isEmpty()) {
            System.out.println("Không có khách hàng!");
        } else {
            for (User u : list) {
                System.out.println("Username: " + u.getUsername()
                        + " | Role: " + u.getRole()
                        + " | Balance: " + u.getBalance());
            }
        }
    }
    private void createStaff() {
        String username;

        while (true) {
            System.out.print("Nhập username: ");
            username = sc.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println(" Không được để trống!");
                continue;
            }

            if (userDAO.isUsernameExist(username)) {
                System.out.println(" Username đã tồn tại!");
                continue;
            }
            break;
        }

        System.out.print("Nhập password: ");
        String password = sc.nextLine().trim();

        boolean success = userDAO.createStaff(username, password);

        System.out.println(success ? " Thành công!" : " Thất bại!");
    }

    private void deleteCustomer() {
        System.out.print("Nhập username cần xóa: ");
        String username = sc.nextLine().trim();

        boolean success = userDAO.deleteUser(username);

        System.out.println(success ? " Xóa thành công!" : " Không tìm thấy!");
    }

    // ================= FOOD =================
    private void addFood() {

        System.out.println("\n=== CHỌN LOẠI ===");
        System.out.println("1.  Đồ ăn");
        System.out.println("2.  Đồ uống");

        int choice = inputChoice(1, 2);

        String category = (choice == 1) ? "FOOD" : "DRINK";

        String name;
        while (true) {
            System.out.print("Tên món: ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }

            if (foodDAO.findByName(name) != null) {
                System.out.println(" Món đã tồn tại!");
                continue;
            }
            break;
        }


        String description;
        while (true) {
            System.out.print("Mô tả: ");
            description = sc.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println(" Không được để trống!");
            } else break;
        }

        // ===== GIÁ =====
        double price;
        while (true) {
            System.out.print("Giá: ");
            if (sc.hasNextDouble()) {
                price = sc.nextDouble();
                sc.nextLine();
                if (price > 0) break;
            } else sc.nextLine();

            System.out.println(" Giá phải > 0!");
        }

        int quantity;
        while (true) {
            System.out.print("Số lượng: ");
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                sc.nextLine();
                if (quantity >= 0) break;
            } else sc.nextLine();

            System.out.println(" Số lượng phải >= 0!");
        }

        // ===== TẠO & LƯU =====
        Food food = new Food(name, description, category, price, quantity);
        boolean success = foodDAO.addFood(food);

        System.out.println(success ? " Thêm thành công!" : " Lỗi DB!");
    }
    private void showMenu() {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1.  Đồ ăn");
            System.out.println("2. Đồ uống");
            System.out.println("0. 🔙 Quay lại");

            int choice = inputChoice(0, 2);
            if (choice == 0) return;

            String category = (choice == 1) ? "FOOD" : "DRINK";
            String title = (choice == 1) ? " ĐỒ ĂN" : " ĐỒ UỐNG";

            List<Food> list = foodDAO.getByCategory(category);

            System.out.println("\n=== " + title + " ===");
            System.out.println("DEBUG: Tìm category = " + category + " → " + list.size() + " món");

            if (list.isEmpty()) {
                System.out.println(" Không có món nào!");
            } else {
                for (Food f : list) {
                    System.out.println("ID: " + f.getId() + " | " + f.getName()
                            + " | " + f.getPrice() + "đ | Kho: " + f.getQuantity()
                            + " | " + f.getDescription());
                }
            }
        }
    }

    private void deleteFood() {
        System.out.print("Tên món: ");
        String name = sc.nextLine();

        boolean success = foodDAO.deleteFood(name);
        System.out.println(success ? " Xóa thành công!" : " Không tìm thấy!");
    }

    // ================= COMPUTER =================
    private void addComputer() {
        System.out.print("Tên máy: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(" Không được để trống!");
            return;
        }

        if (computerDAO.findByName(name) != null) {
            System.out.println(" Đã tồn tại!");
            return;
        }

        System.out.print("Khu vực (PRO / STREAM / VIP): ");
        String area = sc.nextLine().trim().toUpperCase();

        if (!area.equals("PRO") && !area.equals("STREAM") && !area.equals("VIP")) {
            System.out.println(" Khu vực không hợp lệ!");
            return;
        }

        System.out.print("Giá / giờ: ");
        double price;
        try {
            price = Double.parseDouble(sc.nextLine());
            if (price <= 0) {
                System.out.println(" Giá phải > 0!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Giá không hợp lệ!");
            return;
        }

        Computer c = new Computer(0, name, area, price, "TRONG");
        computerDAO.add(c);

        System.out.println(" Đã thêm máy!");
    }

    private void showComputers() {
        List<Computer> list = computerDAO.getAll();

        System.out.println("\n===== DANH SÁCH MÁY =====");
        System.out.printf("%-10s %-10s %-10s %-15s %-40s\n", "Máy", "Khu", "Giá/h", "Trạng thái", "Thời gian sử dụng");

        for (Computer c : list) {
            String status = c.getStatus();
            String icon = "🟢"; // mặc định TRONG
            String timeInfo = "Trống";

            List<Booking> bookings = bookingDAO.getBookingsByPcId(c.getId());
            if (!bookings.isEmpty()) {
                timeInfo = "";
                for (Booking b : bookings) {
                    timeInfo += "[" + b.getStartTime() + " -> " + b.getEndTime() + "] ";
                }
                if (status.equalsIgnoreCase("DANG_SU_DUNG")) icon = "🔴";
                else icon = "🟡";
            }

            System.out.printf("%-10s %-10s %-10.0f %-15s %-40s\n",
                    c.getPcName(),
                    c.getArea(),
                    c.getPricePerHour(),
                    icon + " " + status,
                    timeInfo
            );
        }
    }
    private void turnOnComputer() {
        System.out.print("Tên máy: ");
        String name = sc.nextLine().trim();

        Computer c = computerDAO.findByName(name);

        if (c == null) {
            System.out.println(" Không tìm thấy máy!");
            return;
        }

        if (!c.getStatus().equalsIgnoreCase("TRONG")) {
            System.out.println(" Máy không rảnh!");
            return;
        }

        // Nhập username
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        User u = userDAO.findByUsername(username);

        if (u == null) {
            System.out.println("User không tồn tại!");
            return;
        }
        boolean booked = bookingDAO.startNow(u.getId(), c.getId(), 1);
        if (!booked) {
            System.out.println(" Không thể bật máy!");
            return;
        }

        computerDAO.updateStatus(c.getId(), "DANG_SU_DUNG");

        System.out.println("Máy đã bật!");
    }

    private void turnOffComputer() {
        System.out.print("Tên máy: ");
        String name = sc.nextLine().trim();

        Computer c = computerDAO.findByName(name);

        if (c == null) {
            System.out.println(" Không tìm thấy máy!");
            return;
        }

        if (!c.getStatus().equalsIgnoreCase("DANG_SU_DUNG")) {
            System.out.println(" Máy chưa được sử dụng!");
            return;
        }

        Booking b = bookingDAO.getActiveBooking(c.getId());

        if (b == null) {
            System.out.println("Không có phiên chơi!");
            return;
        }

        LocalDateTime endTime = LocalDateTime.now();

        long minutes = java.time.Duration.between(b.getStartTime(), endTime).toMinutes();
        if (minutes == 0) minutes = 1;

        double hours = minutes / 60.0;


        double pricePerHour;
        switch (c.getArea().toUpperCase()) {
            case "PRO": pricePerHour = 20000; break;
            case "STREAM": pricePerHour = 50000; break;
            case "VIP": pricePerHour = 100000; break;
            default:
                System.out.println("Khu vực không hợp lệ!");
                return;
        }


        double total = hours * pricePerHour;
        total = Math.ceil(total / 1000) * 1000;

        // Trừ tiền user
        boolean paid = userDAO.deductBalanceById(b.getUserId(), total);
        if (!paid) {
            System.out.println(" Trừ tiền thất bại!");
            return;
        }

        bookingDAO.endBooking(b.getId(), endTime);
        computerDAO.updateStatus(c.getId(), "TRONG");

        User u = userDAO.findById(b.getUserId());

        System.out.println("\n===== HÓA ĐƠN =====");
        System.out.println(" Người chơi: " + (u != null ? u.getUsername() : "Không rõ"));
        System.out.println(" Máy: " + c.getPcName());
        System.out.println(" Khu: " + c.getArea());
        System.out.println(" Bắt đầu: " + b.getStartTime());
        System.out.println(" Kết thúc: " + endTime);
        System.out.println(" Thời gian: " + minutes + " phút");
        System.out.println(" Giá: " + pricePerHour + "đ / giờ");
        System.out.println(" Tổng tiền: " + total + " VND");
        if (u != null) {
            System.out.println(" Số dư còn lại: " + u.getBalance());
        }
    }
    private void deleteComputer() {
        System.out.print("Tên máy: ");
        String name = sc.nextLine();

        Computer c = computerDAO.findByName(name);

        if (c == null) {
            System.out.println(" Không tìm thấy!");
            return;
        }

        computerDAO.delete(c.getId());

        System.out.println(" Xóa thành công!");
    }
}