package view;

import model.User;
import service.AuthService;

import java.util.Scanner;

public class AuthUI {

    private Scanner sc = new Scanner(System.in);
    private AuthService authService = new AuthService();

    public User start() {
        while (true) {
            System.out.println("\n===== CYBER MANAGEMENT =====");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    return null;
            }
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        if (authService.isUsernameExist(username)) {
            System.out.println("Trùng username!");
            return;
        }

        System.out.print("Password: ");
        String password = sc.nextLine();

        authService.register(username, password);
        System.out.println("Đăng ký thành công!");
    }

    private void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = authService.login(username, password);

        if (user == null) {
            System.out.println("Sai tài khoản!");
            return;
        }

        System.out.println("Đăng nhập thành công!");


        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            new AdminUI().menu();

        } else if (user.getRole().equalsIgnoreCase("STAFF")) {
            new EmployeeUI().menu();

        } else {
            new CustomerUI(user).menu();
        }
    }
}