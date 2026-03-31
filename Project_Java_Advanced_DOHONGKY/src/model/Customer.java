package model;

public class Customer {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private double balance;

    public Customer(int id, String username, String password, String fullName, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.balance = balance;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}