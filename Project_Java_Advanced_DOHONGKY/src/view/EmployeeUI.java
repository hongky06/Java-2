package view;

import dao.UserDAO;
import model.User;
import utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeUI {

    private Scanner sc = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU NHÂN VIÊN =====");
            System.out.println("1. Xem máy");
            System.out.println("2. Xem khách hàng");
            System.out.println("3. Nạp tiền khách hàng");
            System.out.println("4. Đăng ký khách hàng mới");
            System.out.println("0. Thoát");

            int choice = inputChoice(0, 4);

            switch (choice) {
                case 1 -> System.out.println("Chức năng xem máy");
                case 2 -> showCustomers();
                case 3 -> depositMoney();
                case 4 -> registerCustomerUI();
                case 0 -> {
                    System.out.println("Thoát...");
                    return;
                }
            }
        }
    }

    private void showCustomers() {
        System.out.println("\n--- DANH SÁCH KHÁCH HÀNG ---");
        List<User> list = userDAO.getAllCustomersIgnoreCase();
        System.out.println("DEBUG: Tổng khách hàng = " + list.size());
        if (list.isEmpty()) {
            System.out.println("Không có khách hàng!");
        } else {
            for (User u : list) {
                System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getRole() + " | Số dư: " + u.getBalance());
            }
        }
    }

    private int inputChoice(int min, int max) {
        int choice;
        while (true) {
            System.out.print("Chọn: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= min && choice <= max) return choice;
            } else sc.nextLine();
            System.out.println("❌ Nhập sai!");
        }
    }

    private void depositMoney() {
        System.out.println("\n--- NẠP TIỀN KHÁCH HÀNG ---");
        System.out.print("Nhập username khách: ");
        String username = sc.nextLine().trim();

        User user = userDAO.findCustomerByUsernameIgnoreCase(username);
        if (user == null) {
            System.out.println("❌ Không tìm thấy khách!");
            return;
        }

        System.out.println("Khách: " + user.getUsername() + " | Số dư hiện tại: " + user.getBalance());

        double amount;
        while (true) {
            System.out.print("Nhập số tiền nạp: ");
            try {
                amount = Double.parseDouble(sc.nextLine());
                if (amount <= 0) {
                    System.out.println("❌ Số tiền phải > 0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("❌ Nhập số không hợp lệ!");
            }
        }

        boolean success = userDAO.depositMoney(username, amount);
        if (success) {
            System.out.println("✅ Nạp tiền thành công!");
            user = userDAO.findCustomerByUsernameIgnoreCase(username);
            System.out.println("Số dư mới: " + user.getBalance());
        } else {
            System.out.println("❌ Nạp tiền thất bại!");
        }
    }

    private void registerCustomerUI() {
        System.out.println("\n--- ĐĂNG KÝ KHÁCH HÀNG MỚI ---");
        System.out.print("Nhập username: ");
        String username = sc.nextLine().trim();
        System.out.print("Nhập password: ");
        String password = sc.nextLine().trim();

        if (userDAO.isUsernameExist(username)) {
            System.out.println("❌ Username đã tồn tại!");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("CUSTOMER");

        boolean success = userDAO.register(user);
        if (success) System.out.println("✅ Đăng ký thành công: " + username);
        else System.out.println("❌ Đăng ký thất bại!");
    }
    // ===== LẤY DANH SÁCH KHÁCH HÀNG (KHÔNG PHÂN BIỆT HOA THƯỜNG) =====
    public List<User> getAllCustomersIgnoreCase() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE UPPER(role) = 'CUSTOMER'"; // bỏ phân biệt hoa thường

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));   // thêm id để đầy đủ
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setBalance(rs.getDouble("balance"));
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}