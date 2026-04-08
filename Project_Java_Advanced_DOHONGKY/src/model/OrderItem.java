package model;

import java.util.List;

public class OrderItem {
    private int id;
    private int foodId;
    private String foodName;
    private int quantity;
    private String status;

    public OrderItem(int id, int foodId, String foodName, int quantity, String status) {
        this.id = id;
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.status = status;
    }

    public OrderItem() {}

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}