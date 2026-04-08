package model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int userId;
    private int pcId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public Booking() {}

    public Booking(int userId, int pcId, LocalDateTime startTime, String status) {
        this.userId = userId;
        this.pcId = pcId;
        this.startTime = startTime;
        this.status = status;
    }

    public Booking(int id, int userId, int computerId, LocalDateTime startTime, String status) {
        this.id = id;
        this.userId = userId;
        this.pcId = computerId;
        this.startTime = startTime;
        this.status = status;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getPcId() { return pcId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setPcId(int pcId) { this.pcId = pcId; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setStatus(String status) { this.status = status; }
}