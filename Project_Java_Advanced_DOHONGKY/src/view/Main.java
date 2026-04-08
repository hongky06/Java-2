package view;

import dao.UserDAO;
import model.User;

public class Main {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        if (!userDAO.isUsernameExist("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setRole("ADMIN");

            boolean success = userDAO.register(admin);

            if (success) {
                System.out.println("Tạo ADMIN mặc định thành công!");
            }
        }

        while (true) {
            AuthUI authUI = new AuthUI();
            User user = authUI.start();

            if (user == null) {
                System.out.println("Thoát chương trình!");
                break;
            }

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                new AdminUI().menu();
            } else {
                new CustomerUI(user).menu();
            }
        }
    }
}

