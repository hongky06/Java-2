package view;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.List;

import dao.*;

import model.User;
import model.Food;
import model.Booking;
import model.Computer;

public class CustomerUI {

    private Scanner sc = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private FoodDAO foodDAO = new FoodDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private ComputerDAO computerDAO = new ComputerDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private User currentUser;

    public CustomerUI(User user) {
        this.currentUser = user;
    }

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println(" Xin chào: " + currentUser.getUsername());
            System.out.println(" Số dư: " + currentUser.getBalance());

            System.out.println("1. Nạp tiền");
            System.out.println("2. Chơi game");
            System.out.println("3. Gọi món");
            System.out.println("4. đặt máy ");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    napTien();
                    break;
                case 2:
                    choiGame();
                    break;
                case 3:
                    goiMon();
                    break;
                    case 4:
                        datMay();
                    break;

                case 0:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println(" Sai lựa chọn!");
            }
        }
    }

    // ===== NẠP TIỀN =====
    private void napTien() {
        System.out.println("\n===== NẠP TIỀN =====");

        double amount;
        while (true) {
            System.out.print("Nhập số tiền: ");
            amount = sc.nextDouble();

            if (amount > 0) break;
            System.out.println(" Phải > 0!");
        }

        boolean success = userDAO.depositMoney(currentUser.getUsername(), amount);

        if (success) {
            currentUser = userDAO.findByUsername(currentUser.getUsername());
            System.out.println("Thành công! Số dư: " + currentUser.getBalance());
        } else {
            System.out.println(" Thất bại!");
        }
    }

    // ===== CHƠI GAME =====
    private void choiGame() {
        System.out.println("\n===== THÔNG TIN CHƠI =====");

        currentUser = userDAO.findByUsername(currentUser.getUsername());

        List<Booking> bookings = bookingDAO.getByUser(currentUser.getId());
        LocalDateTime now = LocalDateTime.now();
        Booking activeBooking = null;

        for (Booking b : bookings) {
            if (b.getStartTime() != null && b.getEndTime() != null) {
                if (!now.isBefore(b.getStartTime()) && !now.isAfter(b.getEndTime())) {
                    activeBooking = b;
                    break;
                }
            }
        }

        if (activeBooking == null) {
            System.out.println(" Chưa tới giờ đặt máy của bạn!");
            return;
        }

        Computer c = computerDAO.findById(activeBooking.getPcId());
        if (c == null) {
            System.out.println("Không tìm thấy máy!");
            return;
        }

        double price = 0;
        switch (c.getArea().toUpperCase()) {
            case "PRO": price = 20000; break;
            case "STREAM": price = 50000; break;
            case "VIP": price = 100000; break;
        }

        // Tính thời gian còn lại
        long minutes = java.time.Duration.between(now, activeBooking.getEndTime()).toMinutes();
        System.out.println(" User: " + currentUser.getUsername());
        System.out.println(" Máy: " + c.getPcName());
        System.out.println(" Khu: " + c.getArea());
        System.out.println(" Giá: " + price + "/h");
        System.out.println(" Thời gian còn lại: " + (minutes / 60) + "h " + (minutes % 60) + "p");

        System.out.println(" Bạn đang được phép chơi!");
    }

    // ===== GỌI MÓN =====

    private void goiMon() {
        sc.nextLine(); // đọc bỏ dòng trống

        List<Food> list = foodDAO.getAll();

        if (list.isEmpty()) {
            System.out.println(" Không có món!");
            return;
        }

        // Hiển thị menu
        for (Food f : list) {
            System.out.println(f.getId() + " - " + f.getName()
                    + " - " + f.getCategory()
                    + " - Giá: " + f.getPrice()
                    + " - SL: " + f.getQuantity());
        }

        System.out.print("Chọn ID món: ");
        int foodId = sc.nextInt();
        Food food = foodDAO.findById(foodId);
        if (food == null) {
            System.out.println("Không có món!");
            return;
        }

        System.out.print("Số lượng: ");
        int qty = sc.nextInt();
        if (qty <= 0 || qty > food.getQuantity()) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }

        double total = qty * food.getPrice();
        if (currentUser.getBalance() < total) {
            System.out.println(" Không đủ tiền!");
            return;
        }


        Integer pcId = null;
        boolean success = orderDAO.createFoodOrder(
                currentUser.getId(),
                pcId,
                food.getName(),
                qty,
                food.getPrice()
        );

        if (success) {
            System.out.println(" Gọi món thành công! Món đang chờ phục vụ.");

            userDAO.deductBalanceById(currentUser.getId(), total);

            foodDAO.updateQuantity(food.getId(), food.getQuantity() - qty);

            currentUser = userDAO.findByUsername(currentUser.getUsername());
            System.out.println(" Số dư hiện tại: " + currentUser.getBalance());
        } else {
            System.out.println("Gọi món thất bại!");
        }
    }



    // ===== ĐẶT MÁY =====
    private void datMay() {
        System.out.println("\n===== ĐẶT MÁY THEO GIỜ =====");

        List<Computer> computers = computerDAO.getAll();
        if (computers.isEmpty()) {
            System.out.println(" Hiện tại không có máy!");
            return;
        }

        System.out.println("ID - Tên máy - Khu - Các khung giờ đã đặt:");
        for (Computer c : computers) {
            System.out.print(c.getId() + " - " + c.getPcName() + " - " + c.getArea() + " - ");

            List<Booking> bookings = bookingDAO.getBookingsByPcId(c.getId()); // lấy tất cả booking
            if (bookings.isEmpty()) {
                System.out.println("Trống");
            } else {
                for (Booking b : bookings) {
                    System.out.print("[" + b.getStartTime() + " -> " + b.getEndTime() + "] ");
                }
                System.out.println();
            }
        }

        System.out.print("Nhập ID máy muốn đặt: ");
        int pcId = sc.nextInt();
        sc.nextLine();

        Computer selected = computerDAO.findById(pcId);
        if (selected == null) {
            System.out.println("Máy không hợp lệ!");
            return;
        }

        System.out.print("Nhập giờ bắt đầu (yyyy-MM-dd HH:mm): ");
        String startInput = sc.nextLine();
        System.out.print("Nhập giờ kết thúc (yyyy-MM-dd HH:mm): ");
        String endInput = sc.nextLine();

        LocalDateTime startTime = LocalDateTime.parse(startInput.replace(" ", "T"));
        LocalDateTime endTime = LocalDateTime.parse(endInput.replace(" ", "T"));

        if (!startTime.isBefore(endTime)) {
            System.out.println(" Giờ bắt đầu phải trước giờ kết thúc!");
            return;
        }
        List<Booking> existing = bookingDAO.getBookingsByPcId(pcId);
        for (Booking b : existing) {
            // nếu có xung đột
            if (!(endTime.isBefore(b.getStartTime()) || startTime.isAfter(b.getEndTime()))) {
                System.out.println(" Máy đã được đặt trong khung giờ: "
                        + b.getStartTime() + " -> " + b.getEndTime());
                return;
            }
        }

        long minutes = java.time.Duration.between(startTime, endTime).toMinutes();
        double pricePerHour = 0;
        switch (selected.getArea().toUpperCase()) {
            case "PRO": pricePerHour = 20000; break;
            case "STREAM": pricePerHour = 50000; break;
            case "VIP": pricePerHour = 100000; break;
        }
        double total = pricePerHour * (minutes / 60.0);

        if (currentUser.getBalance() < total) {
            System.out.println(" Số dư không đủ! Cần: " + total);
            return;
        }

        if (!userDAO.deductBalanceById(currentUser.getId(), total)) {
            System.out.println(" Thanh toán thất bại!");
            return;
        }

        boolean booked = bookingDAO.createBooking(currentUser.getId(), pcId, startTime, endTime);
        if (booked) {
            System.out.println(" Đặt máy thành công!");
            System.out.println("Máy: " + selected.getPcName() + " | Loại: " + selected.getArea());
            System.out.println("Từ " + startTime + " tới " + endTime + " | Giá: " + total);
            currentUser = userDAO.findByUsername(currentUser.getUsername());
        } else {
            System.out.println(" Đặt máy thất bại!");
        }
    }
}