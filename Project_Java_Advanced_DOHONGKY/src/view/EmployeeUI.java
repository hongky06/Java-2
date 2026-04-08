package view;

import dao.ComputerDAO;
import dao.FoodDAO;
import dao.UserDAO;
import model.Computer;
import model.Food;
import model.User;
import utils.Database;
import dao.OrderDAO;
import model.Order;
import model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeUI {

    private Scanner sc = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private OrderDAO orderDAO = new OrderDAO();

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU NHÂN VIÊN =====");
            System.out.println("1. Xem khách hàng");
            System.out.println("2. Xem máy");
            System.out.println("3. Xem menu món ăn");
            System.out.println("4. Xem bàn đang gọi món");
            System.out.println("5. Nạp tiền khách hàng");
            System.out.println("6. Đăng ký khách hàng");
            System.out.println("0. Thoát");

            int choice = inputChoice(0, 6);

            switch (choice) {
                case 1 -> showCustomers();
                case 2 -> showComputers();
                case 3 -> showFoods();
                case 4 -> showOrders();
                case 5 -> depositMoney();
                case 6 -> registerCustomerUI();
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

    private ComputerDAO computerDAO = new ComputerDAO();

    private void showComputers() {
        System.out.println("\n--- DANH SÁCH MÁY ---");
        List<Computer> list = computerDAO.getAll();

        for (Computer c : list) {
            System.out.println(
                    c.getId() + " | " +
                            c.getPcName() + " | " +
                            c.getStatus()
            );
        }
    }

    private FoodDAO foodDAO = new FoodDAO();

    private void showFoods() {
        System.out.println("\n--- MENU ĐỒ ĂN ---");
        List<Food> list = foodDAO.getAll();

        for (Food f : list) {
            System.out.println(
                    f.getId() + " | " +
                            f.getName() + " | " +
                            f.getCategory() + " | " +
                            f.getPrice()
            );
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
            System.out.println(" Nhập sai!");
        }
    }

    private void depositMoney() {
        System.out.println("\n--- NẠP TIỀN KHÁCH HÀNG ---");
        System.out.print("Nhập username khách: ");
        String username = sc.nextLine().trim();

        User user = userDAO.findCustomerByUsernameIgnoreCase(username);
        if (user == null) {
            System.out.println(" Không tìm thấy khách!");
            return;
        }

        System.out.println("Khách: " + user.getUsername() + " | Số dư hiện tại: " + user.getBalance());

        double amount;
        while (true) {
            System.out.print("Nhập số tiền nạp: ");
            try {
                amount = Double.parseDouble(sc.nextLine());
                if (amount <= 0) {
                    System.out.println(" Số tiền phải > 0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println(" Nhập số không hợp lệ!");
            }
        }

        boolean success = userDAO.depositMoney(username, amount);
        if (success) {
            System.out.println(" Nạp tiền thành công!");
            user = userDAO.findCustomerByUsernameIgnoreCase(username);
            System.out.println("Số dư mới: " + user.getBalance());
        } else {
            System.out.println("Nạp tiền thất bại!");
        }
    }

    private void registerCustomerUI() {
        System.out.println("\n--- ĐĂNG KÝ KHÁCH HÀNG MỚI ---");
        System.out.print("Nhập username: ");
        String username = sc.nextLine().trim();
        System.out.print("Nhập password: ");
        String password = sc.nextLine().trim();

        if (userDAO.isUsernameExist(username)) {
            System.out.println(" Username đã tồn tại!");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("CUSTOMER");

        boolean success = userDAO.register(user);
        if (success) System.out.println(" Đăng ký thành công: " + username);
        else System.out.println(" Đăng ký thất bại!");
    }

    public List<User> getAllCustomersIgnoreCase() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE UPPER(role) = 'CUSTOMER'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
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


    public void showOrders() {
        System.out.println("\n🔥 DANH SÁCH BÀN ĐANG GỌI MÓN 🔥");

        List<Order> orders = orderDAO.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Chưa có bàn nào gọi món.");
            return;
        }

        for (Order order : orders) {
            System.out.println("OrderID: " + order.getId()
                    + " | Bàn: " + order.getPcName()
                    + " | Khách: " + order.getUsername());

            List<OrderItem> items = order.getItems();
            if (items == null || items.isEmpty()) {
                System.out.println("  Chưa có món gọi");
                continue;
            }

            System.out.println("  Món gọi:");
            for (OrderItem item : items) {
                System.out.println("    - " + item.getFoodName()
                        + " x" + item.getQuantity()
                        + " | Trạng thái: " + item.getStatus());
            }
        }
    }
    }