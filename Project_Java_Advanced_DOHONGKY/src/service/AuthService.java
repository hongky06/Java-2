package service;

import dao.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public boolean isUsernameExist(String username) {
        return userDAO.isUsernameExist(username);
    }

    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("CUSTOMER");

        userDAO.register(user);
    }

    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }
}