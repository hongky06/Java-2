package model;

import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String username;
    private int pcId;
    private String pcName;
    private double total;
    private String status;
    private List<OrderItem> items;

    public Order(int id, int userId, String username, int pcId, String pcName, double total, String status, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.pcId = pcId;
        this.pcName = pcName;
        this.total = total;
        this.status = status;
        this.items = items;
    }

    public Order() {}

    // Getter & Setter
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getPcId() { return pcId; }
    public String getPcName() { return pcName; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }
    public void setStatus(String status) { this.status = status; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}