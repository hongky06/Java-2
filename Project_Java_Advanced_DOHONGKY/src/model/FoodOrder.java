package model;

import java.time.LocalDateTime;

public class FoodOrder {
    private int id;
    private int foodId;
    private int userId;
    private int quantity;
    private String status;
    private LocalDateTime orderTime;

    public FoodOrder(int id, int foodId, int userId, int quantity, String status, LocalDateTime orderTime) {
        this.id = id;
        this.foodId = foodId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
        this.orderTime = orderTime;
    }

    public FoodOrder(int foodId, int userId, int quantity) {
        this.foodId = foodId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = "CHỜ ĐƠN";
    }

    // Getter & Setter
    public int getId() { return id; }
    public int getFoodId() { return foodId; }
    public int getUserId() { return userId; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getOrderTime() { return orderTime; }
}