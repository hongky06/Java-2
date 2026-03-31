package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private double balance;

    public User() {}

    public User(int id, String username, String password, String role, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    // ===== GETTER =====
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public double getBalance() { return balance; }

    // ===== SETTER =====
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setBalance(double balance) { this.balance = balance; }
}