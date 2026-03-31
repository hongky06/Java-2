package view;

import java.util.Scanner;

public class CustomerUI {

    private Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println("1. Nạp tiền");
            System.out.println("2. Chơi game");
            System.out.println("2. Gọi món");
            System.out.println("0. Đăng xuất");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Nạp tiền...");
                    break;
                case 2:
                    System.out.println("Vào máy chơi...");
                    break;
                    case 3:
                    System.out.println("Gọi món");
                    break;
                case 0:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println("Sai lựa chọn!");
            }
        }
    }













}